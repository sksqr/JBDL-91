package com.example.L13_minor_project_01.controller;

import com.example.L13_minor_project_01.dto.CreateOrderItemRequestDto;
import com.example.L13_minor_project_01.dto.OrderDetailsResponseDto;
import com.example.L13_minor_project_01.dto.OrderItemResponseDto;
import com.example.L13_minor_project_01.dto.ProductResponseDto;
import com.example.L13_minor_project_01.dto.ProductSearchRequestDto;
import com.example.L13_minor_project_01.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getProducts(@Valid @ModelAttribute  ProductSearchRequestDto requestDto) {
        return customerService.getProductsByKeyword(requestDto);
    }

    @PostMapping("/order-item")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemResponseDto createOrderItem(@Valid @RequestBody CreateOrderItemRequestDto requestDto) {
        return customerService.createOrAddOrderItem(requestDto);
    }

    @GetMapping("/order/{id}")
    public OrderDetailsResponseDto getOrderDetails(@PathVariable("id") Long id) {
        return customerService.getOrderDetailsById(id);
    }

    @PutMapping("/order/{id}/submit")
    public OrderDetailsResponseDto submitOrder(@PathVariable("id") Long id) {
        return customerService.submitOrder(id);
    }

}
