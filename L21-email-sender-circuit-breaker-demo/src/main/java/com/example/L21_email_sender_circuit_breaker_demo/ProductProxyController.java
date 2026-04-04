package com.example.L21_email_sender_circuit_breaker_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/proxy")
public class ProductProxyController {
    private static Logger LOGGER = LoggerFactory.getLogger(ProductProxyController.class);

    @Autowired
    private ProductServiceClient productServiceClient;


    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;


    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getProductProxy(@PathVariable Long id) {
        LOGGER.info("Calling actual ProductApp to get a Product");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("cb01");
        //ProductDto productDto = productServiceClient.getProduct(id, "123");

        ProductDto productDto = circuitBreaker.run(()->productServiceClient.getProduct(id,"123"), throwable -> fallbackMethodForGetProduct());
        return ResponseEntity.ok(productDto);
    }

    public ProductDto fallbackMethodForGetProduct(){
        return new ProductDto(1l,"Dummy Product", 1000.00);
    }


}
