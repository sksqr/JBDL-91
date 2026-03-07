package com.example.L13_minor_project_01.service;

import com.example.L13_minor_project_01.dto.CreateProductRequestDto;
import com.example.L13_minor_project_01.dto.ProductResponseDto;
import com.example.L13_minor_project_01.entity.Category;
import com.example.L13_minor_project_01.entity.Company;
import com.example.L13_minor_project_01.entity.Product;
import com.example.L13_minor_project_01.repo.CategoryRepository;
import com.example.L13_minor_project_01.repo.CompanyRepository;
import com.example.L13_minor_project_01.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    public SellerService(ProductRepository productRepository,
                         CompanyRepository companyRepository,
                         CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductResponseDto createProduct(CreateProductRequestDto requestDto) {
        if (requestDto == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        Company company = companyRepository.findById(requestDto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + requestDto.getCompanyId()));

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + requestDto.getCategoryId()));

        Product product = new Product();
        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setPrice(requestDto.getPrice());
        product.setStock(requestDto.getStock());
        product.setCompany(company);
        product.setCategory(category);
        product.setIsActive(requestDto.getIsActive() != null ? requestDto.getIsActive() : Boolean.TRUE);

        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .price(savedProduct.getPrice())
                .stock(savedProduct.getStock())
                .isActive(savedProduct.getIsActive())
                .companyId(company.getId())
                .companyName(company.getName())
                .categoryId(category.getId())
                .categoryName(category.getName())
                .createdAt(savedProduct.getCreatedAt())
                .updatedAt(savedProduct.getUpdatedAt())
                .build();
    }
}
