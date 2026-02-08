package com.example.L08_SpringBoot_Demo;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    private Map<String,Product> dataStore;

    @PostConstruct
    public void init(){
        dataStore = new HashMap<>();
        dataStore.put("laptop", new Product("laptop",50000.00));
        dataStore.put("mobile", new Product("mobile",5000.00));
        dataStore.put("pen", new Product("pen",50.00));
    }


    public Product getProduct(String key) {
        return dataStore.get(key);
    }

}
