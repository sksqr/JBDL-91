package com.example.L13_minor_project_01.controller;

import com.example.L13_minor_project_01.dto.CategoryResponseDto;
import com.example.L13_minor_project_01.dto.CompanyResponseDto;
import com.example.L13_minor_project_01.dto.CreateCategoryRequestDto;
import com.example.L13_minor_project_01.dto.CreateCompanyRequestDto;
import com.example.L13_minor_project_01.dto.CreateSellerRequestDto;
import com.example.L13_minor_project_01.dto.SellerResponseDto;
import com.example.L13_minor_project_01.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin APIs", description = "Endpoints for company, seller and category administration")
public class AdminController {


    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create company",
            description = "Creates a company and its primary user in one request."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error or duplicate values")
    })
    public CompanyResponseDto createCompany(@Valid @RequestBody CreateCompanyRequestDto requestDto) {
        return adminService.createCompany(requestDto);
    }

    @PostMapping("/seller")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create seller",
            description = "Creates a seller user for an existing company."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Seller created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error or duplicate email"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    public SellerResponseDto createSeller(@Valid @RequestBody CreateSellerRequestDto requestDto) {
        return adminService.createSeller(requestDto);
    }

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create category",
            description = "Creates a new product category."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error or duplicate category name")
    })
    public CategoryResponseDto createCategory(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
        return adminService.createCategory(requestDto);
    }

    @GetMapping("/sellers")
    @Operation(
            summary = "Get all sellers (paginated)",
            description = "Returns paginated list of users having SELLER role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sellers fetched successfully")
    })
    public Page<SellerResponseDto> getAllSellers(
            @Parameter(description = "Zero-based page index", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field", example = "createdAt")
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction: asc or desc", example = "asc")
            @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return adminService.getAllSellers(pageable);
    }

    @DeleteMapping("/seller/{id}")
    @Operation(
            summary = "Delete seller",
            description = "Deletes seller by id if seller exists and is not configured as a primary company user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Seller deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid operation for this user"),
            @ApiResponse(responseCode = "404", description = "Seller not found")
    })
    public ResponseEntity<Void> deleteSeller(@PathVariable("id") Long id) {
        adminService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

}
