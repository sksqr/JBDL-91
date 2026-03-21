package com.example.L13_minor_project_01.service;

import com.example.L13_minor_project_01.dto.CreateProductRequestDto;
import com.example.L13_minor_project_01.dto.BulkProductUploadResponseDto;
import com.example.L13_minor_project_01.dto.ImageUploadResponseDto;
import com.example.L13_minor_project_01.dto.OrderDetailsResponseDto;
import com.example.L13_minor_project_01.dto.ProductResponseDto;
import com.example.L13_minor_project_01.dto.UpdateProductRequestDto;
import com.example.L13_minor_project_01.entity.*;
import com.example.L13_minor_project_01.repo.CategoryRepository;
import com.example.L13_minor_project_01.repo.CompanyRepository;
import com.example.L13_minor_project_01.repo.OrderRepository;
import com.example.L13_minor_project_01.repo.ProductRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SellerService {
    private static final Path PRODUCT_IMAGE_DIR = Path.of("/tmp/product-images");


    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    public SellerService(ProductRepository productRepository,
                         CompanyRepository companyRepository,
                         CategoryRepository categoryRepository,
                         OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public ProductResponseDto createProduct(CreateProductRequestDto requestDto) {
        if (requestDto == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        User seller = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Company company = seller.getCompany();

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + requestDto.getCategoryId()));

        Product product = new Product();
        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setImageUrl(requestDto.getImageUrl());
        product.setPrice(requestDto.getPrice());
        product.setStock(requestDto.getStock());
        product.setCompany(company);
        product.setCategory(category);
        product.setIsActive(requestDto.getIsActive() != null ? requestDto.getIsActive() : Boolean.TRUE);

        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long productId, UpdateProductRequestDto requestDto) {
        if (requestDto == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        User seller = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = seller.getCompany();

        if(!company.equals(product.getCompany())) {
            throw new IllegalArgumentException("Company not match");
        }



        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + requestDto.getCategoryId()));

        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setImageUrl(requestDto.getImageUrl());
        product.setPrice(requestDto.getPrice());
        product.setStock(requestDto.getStock());
        product.setCompany(company);
        product.setCategory(category);
        if (requestDto.getIsActive() != null) {
            product.setIsActive(requestDto.getIsActive());
        }

        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<OrderDetailsResponseDto> getPendingOrders() {
        User seller = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = seller.getCompany();
        return orderRepository.findDistinctByStatusAndOrderItemsProductCompanyId(OrderStatus.PLACED, company.getId())
                .stream()
                .map(this::mapToOrderDetailsResponse)
                .toList();
    }

    @Transactional
    public OrderDetailsResponseDto acceptOrder(Long orderId) {
        Order order = orderRepository.findWithDetailsById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.PLACED) {
            throw new IllegalArgumentException("Only PLACED orders can be accepted");
        }

        User seller = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = seller.getCompany();

        List<OrderItem> orderItems = order.getOrderItems();
        if(!company.equals(orderItems.get(0).getProduct().getCompany())) {
            throw new IllegalArgumentException("Company not match");
        }

        order.setStatus(OrderStatus.ACCEPTED);
        Order savedOrder = orderRepository.save(order);
        return mapToOrderDetailsResponse(savedOrder);
    }

    public ImageUploadResponseDto uploadProductImage(MultipartFile file, String baseUrl) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        try {
            Files.createDirectories(PRODUCT_IMAGE_DIR);

            String originalName = file.getOriginalFilename();
            String extension = getSafeExtension(originalName);
            String fileName = UUID.randomUUID() + extension;
            Path targetPath = PRODUCT_IMAGE_DIR.resolve(fileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            String imageUrl = baseUrl + "/product-images/" + fileName;
            return ImageUploadResponseDto.builder()
                    .imageUrl(imageUrl)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload product image", e);
        }
    }

    @Transactional
    public BulkProductUploadResponseDto uploadProductsCsv(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("CSV file is required");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Only CSV files are allowed");
        }

        int totalRows = 0;
        int createdCount = 0;
        int failedCount = 0;
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] columns = line.split(",", -1);
                if (lineNumber == 1 && isHeaderRow(columns)) {
                    continue;
                }

                totalRows++;
                try {
                    Product product = buildProductFromCsv(columns, lineNumber);
                    productRepository.save(product);
                    createdCount++;
                } catch (Exception ex) {
                    failedCount++;
                    errors.add("Row " + lineNumber + ": " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file", e);
        }

        return BulkProductUploadResponseDto.builder()
                .totalRows(totalRows)
                .createdCount(createdCount)
                .failedCount(failedCount)
                .errors(errors)
                .build();
    }

    private ProductResponseDto mapToProductResponse(Product product) {
        Company company = product.getCompany();
        Category category = product.getCategory();
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice())
                .stock(product.getStock())
                .isActive(product.getIsActive())
                .companyId(company != null ? company.getId() : null)
                .companyName(company != null ? company.getName() : null)
                .categoryId(category != null ? category.getId() : null)
                .categoryName(category != null ? category.getName() : null)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    private OrderDetailsResponseDto mapToOrderDetailsResponse(Order order) {
        List<OrderDetailsResponseDto.OrderItemDetailsDto> items = order.getOrderItems()
                .stream()
                .map(item -> OrderDetailsResponseDto.OrderItemDetailsDto.builder()
                        .orderItemId(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .lineTotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .build())
                .toList();

        return OrderDetailsResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .userName(order.getUser().getName())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .items(items)
                .build();
    }

    private String getSafeExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int idx = fileName.lastIndexOf('.');
        if (idx == -1 || idx == fileName.length() - 1) {
            return "";
        }
        String extension = fileName.substring(idx).toLowerCase();
        if (!extension.matches("\\.[a-z0-9]{1,10}")) {
            return "";
        }
        return extension;
    }

    private Product buildProductFromCsv(String[] columns, int lineNumber) {
        if (columns.length < 7) {
            throw new IllegalArgumentException("Expected at least 7 columns: name,description,imageUrl,price,stock,companyId,categoryId[,isActive]");
        }

        String name = cleanCsvValue(columns[0]);
        String description = cleanCsvValue(columns[1]);
        String imageUrl = cleanCsvValue(columns[2]);
        String priceRaw = cleanCsvValue(columns[3]);
        String stockRaw = cleanCsvValue(columns[4]);
        String companyIdRaw = cleanCsvValue(columns[5]);
        String categoryIdRaw = cleanCsvValue(columns[6]);
        String isActiveRaw = columns.length >= 8 ? cleanCsvValue(columns[7]) : "";

        if (name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }



        BigDecimal price;
        Integer stock;
        Long companyId;
        Long categoryId;
        try {
            price = new BigDecimal(priceRaw);
            stock = Integer.parseInt(stockRaw);
            companyId = Long.parseLong(companyIdRaw);
            categoryId = Long.parseLong(categoryIdRaw);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Stock must be >= 0");
        }

        User seller = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Company company = seller.getCompany();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));

        Product product = new Product();
        product.setName(name);
        product.setDescription(description.isBlank() ? null : description);
        product.setImageUrl(imageUrl.isBlank() ? null : imageUrl);
        product.setPrice(price);
        product.setStock(stock);
        product.setCompany(company);
        product.setCategory(category);
        product.setIsActive(isActiveRaw.isBlank() || Boolean.parseBoolean(isActiveRaw));
        return product;
    }

    private boolean isHeaderRow(String[] columns) {
        if (columns.length == 0) {
            return false;
        }
        return "name".equalsIgnoreCase(cleanCsvValue(columns[0]));
    }

    private String cleanCsvValue(String value) {
        String trimmed = value == null ? "" : value.trim();
        if (trimmed.length() >= 2 && trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
            return trimmed.substring(1, trimmed.length() - 1).trim();
        }
        return trimmed;
    }
}
