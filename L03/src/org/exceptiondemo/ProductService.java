package org.exceptiondemo;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public List<Product> searchWithKeyword(String keyword) throws ProductNotFoundException {
        List<Product> result = new ArrayList<>();
        // Select name, cost from Product where name LIKE "%keyword%"

        if(result.isEmpty()){
            // LOG Here
            throw new ProductNotFoundException("Product Not Found Exception",keyword);
        }
        return result;
    }
}

class Product{
    String name;
    Double cost;

    public Product(String name, Double cost) {
        this.name = name;
        this.cost = cost;
    }
}