package com.example.L10_RestFul_demo.controller;


import com.example.L10_RestFul_demo.entity.Product;
import com.example.L10_RestFul_demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/product")
public class ProductController {


    private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        LOGGER.info("Processing get Product API for: {}",id);
        Product product = productService.getByID(id);
        if(product == null){
            return ResponseEntity.notFound().build(); //Alternative: Global Exception handler
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws URISyntaxException {
        LOGGER.info("Processing Create Product API for: {}",product);
        Product createdProduct = productService.create(product);
        URI location = new URI("/product/"+createdProduct.getId());
        return ResponseEntity.created(location).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductById(@RequestBody Product product, @PathVariable Long id){
        LOGGER.info("Processing Update Product API for: {}",product);
        Product updatedProduct = productService.update(product,id);
        if(updatedProduct == null){
            return ResponseEntity.notFound().build(); //Alternative: Global Exception handler
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable Long id){
        LOGGER.info("Processing get Product API for: {}",id);
        Product product = productService.delete(id);
        if(product == null){
            return ResponseEntity.notFound().build(); //Alternative: Global Exception handler
        }
        return ResponseEntity.ok(product);
    }

}
