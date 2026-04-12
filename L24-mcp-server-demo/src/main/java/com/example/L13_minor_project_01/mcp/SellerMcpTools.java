package com.example.L13_minor_project_01.mcp;


import com.example.L13_minor_project_01.dto.ProductResponseDto;
import com.example.L13_minor_project_01.service.SellerService;
import org.springframework.stereotype.Service;
import com.example.L13_minor_project_01.dto.*;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SellerMcpTools {

    private final SellerService sellerService;

    public SellerMcpTools(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Tool(description = "Create a new product for a company")
    public ProductResponseDto createProduct(
            @ToolParam(description = "Product name") String name,
            @ToolParam(description = "Product description") String description,
            @ToolParam(description = "Price") double price,
            @ToolParam(description = "Company ID") Long companyId,
            @ToolParam(description = "Category ID") Long categoryId) {
        var dto = new CreateProductRequestDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setCategoryId(categoryId);
        dto.setCompanyId(companyId);
        dto.setPrice(BigDecimal.valueOf(price));
        dto.setIsActive(true);
        return sellerService.createProduct(dto);
    }

    @Tool(description = "Get all pending (PLACED) orders for a company")
    public List<OrderDetailsResponseDto> getPendingOrders(
            @ToolParam(description = "Company ID") Long companyId) {
        return sellerService.getPendingOrdersByCompanyId(companyId);
    }

    @Tool(description = "Accept a placed order, changing status from PLACED to ACCEPTED")
    public OrderDetailsResponseDto acceptOrder(
            @ToolParam(description = "Order ID") Long orderId) {
        return sellerService.acceptOrder(orderId);
    }
}
