package com.example.L13_minor_project_01.controller;

import com.example.L13_minor_project_01.dto.CreateOrderItemRequestDto;
import com.example.L13_minor_project_01.dto.OrderDetailsResponseDto;
import com.example.L13_minor_project_01.dto.OrderItemResponseDto;
import com.example.L13_minor_project_01.dto.ProductResponseDto;
import com.example.L13_minor_project_01.dto.ProductSearchRequestDto;
import com.example.L13_minor_project_01.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer APIs", description = "Endpoints for product browsing and order lifecycle actions")
public class CustomerController {

    private final CustomerService customerService;

    private static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/products")
    @Operation(
            summary = "Search products by keyword",
            description = "Returns products where keyword matches name or description."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid keyword")
    })
    public List<ProductResponseDto> getProducts(@Valid @ModelAttribute ProductSearchRequestDto requestDto) {
        LOGGER.info("Get products by keyword: " + requestDto.getKeyword());
        return customerService.getProductsByKeyword(requestDto);
    }

    @PostMapping("/order-item")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add order item",
            description = "Adds item to user's active DRAFT order, or creates a new DRAFT order and adds it."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order item added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or stock issue"),
            @ApiResponse(responseCode = "404", description = "User or product not found")
    })
    public OrderItemResponseDto createOrderItem(@Valid @RequestBody CreateOrderItemRequestDto requestDto) {
        return customerService.createOrAddOrderItem(requestDto);
    }

    @GetMapping("/order/{id}")
    @Operation(
            summary = "Get order details",
            description = "Fetches order summary, total, and all order-item details by order id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public OrderDetailsResponseDto getOrderDetails(
            @Parameter(description = "Order id", example = "101")
            @PathVariable("id") Long id) {
        return customerService.getOrderDetailsById(id);
    }

    @PutMapping("/order/{id}/submit")
    @Operation(
            summary = "Submit order",
            description = "Changes order status from DRAFT to PLACED."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Order is not in DRAFT status"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public OrderDetailsResponseDto submitOrder(
            @Parameter(description = "Order id", example = "101")
            @PathVariable("id") Long id) {
        return customerService.submitOrder(id);
    }

}
