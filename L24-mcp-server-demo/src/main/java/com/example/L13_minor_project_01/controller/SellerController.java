package com.example.L13_minor_project_01.controller;

import com.example.L13_minor_project_01.dto.BulkProductUploadResponseDto;
import com.example.L13_minor_project_01.dto.CreateProductRequestDto;
import com.example.L13_minor_project_01.dto.ImageUploadResponseDto;
import com.example.L13_minor_project_01.dto.OrderDetailsResponseDto;
import com.example.L13_minor_project_01.dto.ProductResponseDto;
import com.example.L13_minor_project_01.dto.UpdateProductRequestDto;
import com.example.L13_minor_project_01.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
@Tag(name = "Seller APIs", description = "Endpoints for seller operations like product management and order processing")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create product", description = "Creates a new product for a company and category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Company or category not found")
    })
    public ProductResponseDto createProduct(@Valid @RequestBody CreateProductRequestDto requestDto) {
        return sellerService.createProduct(requestDto);
    }

    @PostMapping(value = "/product/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload product image", description = "Uploads an image to local storage and returns a public image URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Image uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing image file")
    })
    public ImageUploadResponseDto uploadProductImage(@RequestParam("file") MultipartFile file) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();
        return sellerService.uploadProductImage(file, baseUrl);
    }

    @PostMapping(value = "/products/upload-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Bulk upload products via CSV",
            description = "Uploads a CSV file and creates products in bulk. Returns created/failed summary with row-level errors."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CSV processed"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing CSV file")
    })
    public BulkProductUploadResponseDto uploadProductsCsv(@RequestParam("file") MultipartFile file) {
        return sellerService.uploadProductsCsv(file);
    }

    @PutMapping("/products/{productId}")
    @Operation(summary = "Update product", description = "Updates an existing product by product id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Product, company, or category not found")
    })
    public ProductResponseDto updateProduct(@PathVariable("productId") Long productId,
                                            @Valid @RequestBody UpdateProductRequestDto requestDto) {
        return sellerService.updateProduct(productId, requestDto);
    }

    @GetMapping("/orders/pending")
    @Operation(summary = "Get pending orders", description = "Fetches all PLACED orders for a given company id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pending orders fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    public List<OrderDetailsResponseDto> getPendingOrders(
            @Parameter(description = "Company id", example = "1")
            @RequestParam("companyId") Long companyId) {
        return sellerService.getPendingOrdersByCompanyId(companyId);
    }

    @PutMapping("/orders/{orderId}/accept")
    @Operation(summary = "Accept order", description = "Changes order status from PLACED to ACCEPTED.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order accepted successfully"),
            @ApiResponse(responseCode = "400", description = "Order is not in PLACED status"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public OrderDetailsResponseDto acceptOrder(
            @Parameter(description = "Order id", example = "101")
            @PathVariable("orderId") Long orderId) {
        return sellerService.acceptOrder(orderId);
    }
}
