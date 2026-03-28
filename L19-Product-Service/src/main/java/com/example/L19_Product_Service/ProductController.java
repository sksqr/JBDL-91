package com.example.L19_Product_Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @RequestHeader(required = false) String requestId){
        LOGGER.info("Processing createProduct Req: {}",product);
        product = productService.createProduct(product);
        LOGGER.info("Created Product");
        return ResponseEntity.ok(product);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        LOGGER.info("Processing getProduct Req: {}",id);
        Product product = productService.getProduct(id);
        LOGGER.info("Returning final response");
        return ResponseEntity.ok(product);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAllProduct(){
        LOGGER.info("Processing getAllProduct Req");
        List<Product> products = productService.getAll();
        LOGGER.info("Returning final response");
        return ResponseEntity.ok(products);
    }
}