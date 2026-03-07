package com.example.L13_minor_project_01.controller;

import com.example.L13_minor_project_01.dto.CategoryResponseDto;
import com.example.L13_minor_project_01.dto.CompanyResponseDto;
import com.example.L13_minor_project_01.dto.CreateCategoryRequestDto;
import com.example.L13_minor_project_01.dto.CreateCompanyRequestDto;
import com.example.L13_minor_project_01.dto.CreateSellerRequestDto;
import com.example.L13_minor_project_01.dto.SellerResponseDto;
import com.example.L13_minor_project_01.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponseDto createCompany(@Valid @RequestBody CreateCompanyRequestDto requestDto) {
        return adminService.createCompany(requestDto);
    }

    @PostMapping("/seller")
    @ResponseStatus(HttpStatus.CREATED)
    public SellerResponseDto createSeller(@Valid @RequestBody CreateSellerRequestDto requestDto) {
        return adminService.createSeller(requestDto);
    }

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
        return adminService.createCategory(requestDto);
    }

    @GetMapping("/sellers")
    public Page<SellerResponseDto> getAllSellers(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                 @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return adminService.getAllSellers(pageable);
    }
}
