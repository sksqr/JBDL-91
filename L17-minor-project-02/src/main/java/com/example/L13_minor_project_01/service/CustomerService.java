package com.example.L13_minor_project_01.service;

import com.example.L13_minor_project_01.dto.CreateOrderItemRequestDto;
import com.example.L13_minor_project_01.dto.OrderDetailsResponseDto;
import com.example.L13_minor_project_01.dto.OrderItemResponseDto;
import com.example.L13_minor_project_01.dto.ProductResponseDto;
import com.example.L13_minor_project_01.dto.ProductSearchRequestDto;
import com.example.L13_minor_project_01.entity.Category;
import com.example.L13_minor_project_01.entity.Company;
import com.example.L13_minor_project_01.entity.Order;
import com.example.L13_minor_project_01.entity.OrderItem;
import com.example.L13_minor_project_01.entity.OrderStatus;
import com.example.L13_minor_project_01.entity.Product;
import com.example.L13_minor_project_01.entity.User;
import com.example.L13_minor_project_01.repo.OrderItemRepository;
import com.example.L13_minor_project_01.repo.OrderRepository;
import com.example.L13_minor_project_01.repo.ProductRepository;
import com.example.L13_minor_project_01.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public CustomerService(ProductRepository productRepository,
                           UserRepository userRepository,
                           OrderRepository orderRepository,
                           OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByKeyword(ProductSearchRequestDto requestDto) {
        if (requestDto == null) {
            throw new IllegalArgumentException("Request is required");
        }

        String keyword = requestDto.getKeyword().trim();
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    @Transactional
    public OrderItemResponseDto createOrAddOrderItem(CreateOrderItemRequestDto requestDto) {
        if (requestDto == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + requestDto.getProductId()));

        if (!Boolean.TRUE.equals(product.getIsActive())) {
            throw new IllegalArgumentException("Product is inactive");
        }

        Order draftOrder;
        boolean newOrderCreated;
        var existingDraftOrder = orderRepository.findFirstByUserIdAndStatusOrderByUpdatedAtDesc(user.getId(), OrderStatus.DRAFT);
        if (existingDraftOrder.isPresent()) {
            draftOrder = existingDraftOrder.get();
            newOrderCreated = false;
        } else {
            Order newOrder = new Order();
            newOrder.setUser(user);
            newOrder.setStatus(OrderStatus.DRAFT);
            newOrder.setTotalAmount(BigDecimal.ZERO);
            draftOrder = orderRepository.save(newOrder);
            newOrderCreated = true;
        }

        OrderItem orderItem = orderItemRepository.findByOrderIdAndProductId(draftOrder.getId(), product.getId())
                .orElse(null);

        int requestedQty = requestDto.getQuantity();
        int existingQty = orderItem != null ? orderItem.getQuantity() : 0;
        int finalQty = existingQty + requestedQty;

        if (finalQty > product.getStock()) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock");
        }

        if (orderItem == null) {
            orderItem = new OrderItem();
            orderItem.setOrder(draftOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(requestedQty);
            orderItem.setPrice(product.getPrice());
        } else {
            orderItem.setQuantity(finalQty);
        }

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        BigDecimal unitPrice = savedOrderItem.getPrice();
        BigDecimal increment = unitPrice.multiply(BigDecimal.valueOf(requestedQty));
        BigDecimal currentTotal = draftOrder.getTotalAmount() != null ? draftOrder.getTotalAmount() : BigDecimal.ZERO;
        draftOrder.setTotalAmount(currentTotal.add(increment));
        Order savedOrder = orderRepository.save(draftOrder);

        return OrderItemResponseDto.builder()
                .orderId(savedOrder.getId())
                .orderStatus(savedOrder.getStatus())
                .orderTotalAmount(savedOrder.getTotalAmount())
                .newOrderCreated(newOrderCreated)
                .orderItemId(savedOrderItem.getId())
                .userId(user.getId())
                .productId(product.getId())
                .productName(product.getName())
                .quantity(savedOrderItem.getQuantity())
                .price(savedOrderItem.getPrice())
                .build();
    }

    @Transactional(readOnly = true)
    public OrderDetailsResponseDto getOrderDetailsById(Long orderId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = orderRepository.findWithDetailsByIdAndUser(orderId,user)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
        return mapToOrderDetailsResponse(order);
    }

    @Transactional
    public OrderDetailsResponseDto submitOrder(Long orderId) {
        Order order = orderRepository.findWithDetailsById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.getId().equals(order.getUser().getId())) {
            throw new IllegalArgumentException("User mismatch");
        }

        if (order.getStatus() != OrderStatus.DRAFT) {
            throw new IllegalArgumentException("Only DRAFT orders can be submitted");
        }

        order.setStatus(OrderStatus.PLACED);
        Order savedOrder = orderRepository.save(order);
        return mapToOrderDetailsResponse(savedOrder);
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
}
