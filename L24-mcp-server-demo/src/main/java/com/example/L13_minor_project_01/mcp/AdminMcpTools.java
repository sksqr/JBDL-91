package com.example.L13_minor_project_01.mcp;

import com.example.L13_minor_project_01.dto.*;
import com.example.L13_minor_project_01.service.AdminService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AdminMcpTools {


    private final AdminService adminService;

    public AdminMcpTools(AdminService adminService) {
        this.adminService = adminService;
    }

    @Tool(description = "Create a new company with its primary admin user")
    public CompanyResponseDto createCompany(
            @ToolParam(description = "Company name") String companyName,
            @ToolParam(description = "Primary user's name") String userName,
            @ToolParam(description = "Primary user's email") String userEmail,
            @ToolParam(description = "Primary user's password") String password) {
        var dto = new CreateCompanyRequestDto();
        dto.setName(companyName);
        CreateCompanyRequestDto.PrimaryUserDto primaryUserDto = new CreateCompanyRequestDto.PrimaryUserDto();
        primaryUserDto.setName(userName);
        primaryUserDto.setEmail(userEmail);
        primaryUserDto.setPassword(password);
        dto.setPrimaryUser(primaryUserDto);
        return adminService.createCompany(dto);
    }

    @Tool(description = "Create a seller user for an existing company")
    public SellerResponseDto createSeller(
            @ToolParam(description = "Seller name") String name,
            @ToolParam(description = "Seller email") String email,
            @ToolParam(description = "Company ID") Long companyId) {
        var dto = new CreateSellerRequestDto();
        // map fields
        return adminService.createSeller(dto);
    }

    @Tool(description = "Create a product category")
    public CategoryResponseDto createCategory(
            @ToolParam(description = "Category name") String name) {
        var dto = new CreateCategoryRequestDto();
        // map fields
        dto.setName(name);
        dto.setDescription(name);
        return adminService.createCategory(dto);
    }

    @Tool(description = "List all sellers with pagination")
    public Page<SellerResponseDto> getAllSellers(
            @ToolParam(description = "Page number (0-based)") int page,
            @ToolParam(description = "Page size") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return adminService.getAllSellers(pageable);
    }

    @Tool(description = "Delete a seller by ID")
    public String deleteSeller(
            @ToolParam(description = "Seller user ID") Long id) {
        adminService.deleteSeller(id);
        return "Seller " + id + " deleted successfully";
    }
}
