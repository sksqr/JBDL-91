package com.example.L21_email_sender_circuit_breaker_demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "products", url = "${product.service.baseUrl}")
public interface ProductServiceClient {

    @GetMapping("/product/{id}")
    ProductDto getProduct(@PathVariable Long id, @RequestHeader String requestId);
}
