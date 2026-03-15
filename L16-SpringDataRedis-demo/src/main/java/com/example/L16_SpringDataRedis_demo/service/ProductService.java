package com.example.L16_SpringDataRedis_demo.service;


import com.example.L16_SpringDataRedis_demo.entity.Product;
import com.example.L16_SpringDataRedis_demo.repo.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private RedisTemplate<String, Product> redisTemplate;

    @Autowired
    private IProductRepo productRepo;

    public Product findById(Long id) {
        // Check in redis
        String key = "PRODUCT:" + id;
        Product product = redisTemplate.opsForValue().get(key);
        if(product == null) {
            product = productRepo.findById(id).get();
            redisTemplate.opsForValue().set(key,product);
        }
        return product;
    }



    public Long createProduct(Product product){
        product = productRepo.save(product);
        return product.getId();
    }
}
