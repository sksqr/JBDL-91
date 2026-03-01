package com.example.L12_Spring_Data_JPA_Demo.service;




import com.example.L12_Spring_Data_JPA_Demo.entity.Product;
import com.example.L12_Spring_Data_JPA_Demo.repo.IProductRepo;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private IProductRepo productRepo;


    public Product getByID(Long id){
        return productRepo.findById(id).get();
    }

    public Product create(Product product){
        return productRepo.save(product);
    }

    public Product update(Product product, Long id){
        Product existing = getByID(id);
        if(existing == null){
            return null;
        }
        existing.setName(product.getName());
        LOGGER.info("Update Product: {} ",product);
        productRepo.save(existing);
        return existing;
    }

    public Product delete( Long id){
        Product existing = getByID(id);
        if(existing == null){
            return null;
        }

        LOGGER.info("Deleted Product: {} ",existing);
        productRepo.delete(existing);
        return existing;
    }






}
