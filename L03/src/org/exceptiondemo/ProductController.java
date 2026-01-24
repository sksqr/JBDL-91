package org.exceptiondemo;

import java.util.List;

public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getAllProductWithKeyword(String keyword) throws ProductNotFoundException {
        List<Product> productList = productService.searchWithKeyword(keyword);
        return productList;
    }
}
