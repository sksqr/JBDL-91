package com.example.L13_minor_project_01.service;

import com.example.L13_minor_project_01.dto.CategoryResponseDto;
import com.example.L13_minor_project_01.dto.CompanyResponseDto;
import com.example.L13_minor_project_01.dto.CreateCategoryRequestDto;
import com.example.L13_minor_project_01.dto.CreateCompanyRequestDto;
import com.example.L13_minor_project_01.dto.CreateSellerRequestDto;
import com.example.L13_minor_project_01.dto.SellerResponseDto;
import com.example.L13_minor_project_01.entity.Category;
import com.example.L13_minor_project_01.entity.Company;
import com.example.L13_minor_project_01.entity.User;
import com.example.L13_minor_project_01.entity.UserRole;
import com.example.L13_minor_project_01.exception.BadRequestException;
import com.example.L13_minor_project_01.exception.ConflictException;
import com.example.L13_minor_project_01.exception.ResourceNotFoundException;
import com.example.L13_minor_project_01.repo.CategoryRepository;
import com.example.L13_minor_project_01.repo.CompanyRepository;
import com.example.L13_minor_project_01.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminService(CompanyRepository companyRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CompanyResponseDto createCompany(CreateCompanyRequestDto requestDto) {
        validateCreateCompanyRequest(requestDto);

        if (companyRepository.existsByNumber(requestDto.getNumber())) {
            throw new ConflictException("Company number already exists");
        }

        if (userRepository.existsByEmail(requestDto.getPrimaryUser().getEmail())) {
            throw new ConflictException("Primary user email already exists");
        }

        Company company = new Company();
        company.setName(requestDto.getName());
        company.setNumber(requestDto.getNumber());
        company.setIsActive(requestDto.getIsActive() != null ? requestDto.getIsActive() : Boolean.TRUE);

        Company savedCompany = companyRepository.save(company);

        User primaryUser = new User();
        primaryUser.setName(requestDto.getPrimaryUser().getName());
        primaryUser.setEmail(requestDto.getPrimaryUser().getEmail());
        primaryUser.setPassword(requestDto.getPrimaryUser().getPassword());
        primaryUser.setRole(requestDto.getPrimaryUser().getRole() != null ? requestDto.getPrimaryUser().getRole() : UserRole.SELLER);
        primaryUser.setCompany(savedCompany);

        User savedPrimaryUser = userRepository.save(primaryUser);

        savedCompany.setUser(savedPrimaryUser);
        Company updatedCompany = companyRepository.save(savedCompany);

        return CompanyResponseDto.builder()
                .id(updatedCompany.getId())
                .name(updatedCompany.getName())
                .number(updatedCompany.getNumber())
                .isActive(updatedCompany.getIsActive())
                .createdAt(updatedCompany.getCreatedAt())
                .updatedAt(updatedCompany.getUpdatedAt())
                .primaryUser(CompanyResponseDto.PrimaryUserResponseDto.builder()
                        .id(savedPrimaryUser.getId())
                        .name(savedPrimaryUser.getName())
                        .email(savedPrimaryUser.getEmail())
                        .role(savedPrimaryUser.getRole())
                        .createdAt(savedPrimaryUser.getCreatedAt())
                        .updatedAt(savedPrimaryUser.getUpdatedAt())
                        .build())
                .build();
    }

    @Transactional
    public SellerResponseDto createSeller(CreateSellerRequestDto requestDto) {
        if (requestDto == null) {
            throw new BadRequestException("Request body is required");
        }

        Company company = companyRepository.findById(requestDto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + requestDto.getCompanyId()));

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new ConflictException("Seller email already exists");
        }

        User seller = new User();
        seller.setName(requestDto.getName());
        seller.setEmail(requestDto.getEmail());
        seller.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        seller.setRole(UserRole.SELLER);
        seller.setCompany(company);

        User savedSeller = userRepository.save(seller);

        return SellerResponseDto.builder()
                .id(savedSeller.getId())
                .name(savedSeller.getName())
                .email(savedSeller.getEmail())
                .role(savedSeller.getRole())
                .companyId(company.getId())
                .companyName(company.getName())
                .createdAt(savedSeller.getCreatedAt())
                .updatedAt(savedSeller.getUpdatedAt())
                .build();
    }

    @Transactional
    public CategoryResponseDto createCategory(CreateCategoryRequestDto requestDto) {
        if (requestDto == null) {
            throw new BadRequestException("Request body is required");
        }

        if (categoryRepository.existsByName(requestDto.getName())) {
            throw new ConflictException("Category name already exists");
        }

        Category category = new Category();
        category.setName(requestDto.getName());
        category.setDescription(requestDto.getDescription());

        Category savedCategory = categoryRepository.save(category);

        return CategoryResponseDto.builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .description(savedCategory.getDescription())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<SellerResponseDto> getAllSellers(Pageable pageable) {
        return userRepository.findAllByRole(UserRole.SELLER, pageable)
                .map(this::mapToSellerResponse);
    }

    @Transactional
    public void deleteSeller(Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + sellerId));

        if (seller.getRole() != UserRole.SELLER) {
            throw new BadRequestException("User is not a seller");
        }

        userRepository.delete(seller);
    }

    private SellerResponseDto mapToSellerResponse(User seller) {
        Company company = seller.getCompany();
        return SellerResponseDto.builder()
                .id(seller.getId())
                .name(seller.getName())
                .email(seller.getEmail())
                .role(seller.getRole())
                .companyId(company != null ? company.getId() : null)
                .companyName(company != null ? company.getName() : null)
                .createdAt(seller.getCreatedAt())
                .updatedAt(seller.getUpdatedAt())
                .build();
    }

    private void validateCreateCompanyRequest(CreateCompanyRequestDto requestDto) {
        if (requestDto == null) {
            throw new BadRequestException("Request body is required");
        }

        if (requestDto.getPrimaryUser() == null) {
            throw new BadRequestException("Primary user data is required");
        }
    }
}
