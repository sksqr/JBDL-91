package com.example.L13_minor_project_01.controller;


import com.example.L13_minor_project_01.dto.*;
import com.example.L13_minor_project_01.service.CustomerService;
import com.example.L13_minor_project_01.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

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
        return customerService.getProductsByKeyword(requestDto);
    }


    @GetMapping("/product/{id}")
    public ProductResponseDto getProductById(@PathVariable long id) {
        return customerService.getProductById(id);
    }


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create User",
            description = "Creates a seller user for an existing company."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error or duplicate email"),
    })
    public UserResponseDto createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        return userService.createUser(requestDto);
    }
}
