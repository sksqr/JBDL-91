package com.example.L19_RestTemplate_FeignClient_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/proxy/product")
public class ProxyProductController {

    private static Logger LOGGER = LoggerFactory.getLogger(ProxyProductController.class);


    @Autowired
    private ProductClient productClient;

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        LOGGER.info("Processing get product...");
        ProductDto productDto = productClient.getProduct(id, MDC.get("requestId")).getBody();
        productDto.setServerDateTime(new Date());
        return productDto;
    }
}
