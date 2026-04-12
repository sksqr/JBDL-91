package com.example.L13_minor_project_01.mcp;

import com.example.L13_minor_project_01.dto.*;
import com.example.L13_minor_project_01.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMcpTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMcpTools.class);

    private final CustomerService customerService;

    public CustomerMcpTools(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Tool(description = "Search products by keyword in name or description")
    public List<ProductResponseDto> searchProducts(
            @ToolParam(description = "Search keyword") String keyword) {
        var dto = new ProductSearchRequestDto();
        dto.setKeyword(keyword);
        LOGGER.info("Call getProductsByKeyword with dto: {}", keyword);
        return customerService.getProductsByKeyword(dto);
    }

    @Tool(description = "Add an item to user's cart/draft order. Creates a new order if none exists.")
    public OrderItemResponseDto addOrderItem(
            @ToolParam(description = "User ID") Long userId,
            @ToolParam(description = "Product ID") Long productId,
            @ToolParam(description = "Quantity") int quantity) {
        var dto = new CreateOrderItemRequestDto();
        dto.setUserId(userId);
        dto.setProductId(productId);
        dto.setQuantity(quantity);
        return customerService.createOrAddOrderItem(dto);
    }

    @Tool(description = "Get full order details including items and total")
    public OrderDetailsResponseDto getOrderDetails(
            @ToolParam(description = "Order ID") Long orderId) {
        return customerService.getOrderDetailsById(orderId);
    }

    @Tool(description = "Submit a draft order, changing status from DRAFT to PLACED")
    public OrderDetailsResponseDto submitOrder(
            @ToolParam(description = "Order ID") Long orderId) {
        return customerService.submitOrder(orderId);
    }
}
