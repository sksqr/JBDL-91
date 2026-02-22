package com.example.l11jdbcdemo.service;


import com.example.l11jdbcdemo.dao.ProductDAO;
import com.example.l11jdbcdemo.entity.Product;
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
    private List<Product> products = new ArrayList<>();
    private AtomicLong nextId= new AtomicLong(3);
    @PostConstruct
    public void  init(){
        products.add(new Product(1l,"P1",100.00));
        products.add(new Product(2l,"P2",100.00));
        products.add(new Product(3l,"P3", 100.00));
    }

    @Autowired
    private ProductDAO productDAO;

    public Product getByID(Long id){
        return productDAO.getByID(id);
    }

    public Product create(Product product){
        return productDAO.create(product);
    }

    public Product update(Product product, Long id){
        Product existing = getByID(id);
        if(existing == null){
            return null;
        }
        existing.setName(product.getName());
        LOGGER.info("Update Product: {} ",product);
        return existing;
    }

    public Product delete( Long id){
        Product existing = getByID(id);
        if(existing == null){
            return null;
        }
        products.remove(existing);
        LOGGER.info("Deleted Product: {} ",existing);
        return existing;
    }



    public List<Product> getAll(){
        return products;
    }

    public List<Product> getProductsByKeyword(String keyword){
        List<Product> list = new ArrayList<>();
        for(Product product : products){
            if(product.getName().contains(keyword)){
                list.add(product);
            }
        }
        return list;
    }


}
