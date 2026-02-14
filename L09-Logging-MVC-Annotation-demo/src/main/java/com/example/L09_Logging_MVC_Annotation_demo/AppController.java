package com.example.L09_Logging_MVC_Annotation_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class AppController {

    private static Logger LOGGER = LoggerFactory.getLogger(AppController.class);
    @Autowired(required = false)
    private GFGService gfgService;

    @Autowired
    private ProductService productService;

    @GetMapping("/lectures")
    private List<String> sessions(){
        return gfgService.getLectures();
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false,name = "nm") String name){
        return "Hello! "+name+" from :"+Thread.currentThread().getName();
    }

    @GetMapping("/{version}/product/{id}")
    public Product getProductById(@PathVariable Long id, @PathVariable String version){
        LOGGER.info("Processing get Product API for: {},{}",version,id);
        Product product = productService.getByID(id);
        return product;
    }

    @PostMapping("/product")
    public Product createProduct(@RequestBody Product product, @RequestHeader String appId){
        LOGGER.info("Processing Create Product API for: {},from {}",product,appId);
        Product createdProduct = productService.create(product);
        return createdProduct;
    }


    @GetMapping("/products")
    public List<Product> getAllProducts(){
        LOGGER.info("Processing get all Products Request");
        return productService.getAll();
    }


}
