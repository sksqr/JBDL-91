package com.example.L19_Product_Service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private Map<Long,Product> map = new HashMap<>();

    private AtomicLong nextId = new AtomicLong(1);

    public Product createProduct(Product product){
        product.setId(nextId.getAndIncrement());
        map.put(product.getId(), product);
        return product;
    }

    public Product getProduct(Long id){
        return  map.get(id);
    }

    public List<Product> getAll(){
        return map.values().stream().toList();
    }
}
