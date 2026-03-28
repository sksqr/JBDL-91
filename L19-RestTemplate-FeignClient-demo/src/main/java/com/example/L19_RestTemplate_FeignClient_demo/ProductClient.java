package com.example.L19_RestTemplate_FeignClient_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product, @RequestHeader(required = false) String requestId);

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id,  @RequestHeader(required = false) String requestId);

    @GetMapping("/product/list")
    public ResponseEntity<List<ProductDto>> getAllProduct();
}
