package com.example.L09_Logging_MVC_Annotation_demo;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private List<Product> products = new ArrayList<>();
    private AtomicLong nextId= new AtomicLong(3);
    @PostConstruct
    public void  init(){
        products.add(new Product(1l,"P1"));
        products.add(new Product(2l,"P2"));
        products.add(new Product(3l,"P3"));
    }


    public Product getByID(Long id){
        return products.stream().filter(p->p.getId() == id).findFirst().orElse(null);
    }

    public Product create(Product product){
        product.setId(nextId.incrementAndGet());
        LOGGER.info("Creating Product: {} ",product);
        products.add(product);
        return product;
    }

    public List<Product> getAll(){
        return products;
    }


}
