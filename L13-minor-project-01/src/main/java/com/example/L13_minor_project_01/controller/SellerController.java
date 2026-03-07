package com.example.L13_minor_project_01.controller;

import com.example.L13_minor_project_01.dto.CreateProductRequestDto;
import com.example.L13_minor_project_01.dto.ProductResponseDto;
import com.example.L13_minor_project_01.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@Valid @RequestBody CreateProductRequestDto requestDto) {
        return sellerService.createProduct(requestDto);
    }
}
