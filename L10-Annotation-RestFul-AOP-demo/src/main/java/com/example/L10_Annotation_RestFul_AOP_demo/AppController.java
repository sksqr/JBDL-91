package com.example.L10_Annotation_RestFul_AOP_demo;

import org.gfg.jbdl91.keywords.KeywordAnalyzerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class AppController {

    private static Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private ProductService productService;

    @Autowired
//    @Qualifier("keywordFreqAnalyzer")
    private KeywordAnalyzerInterface keywordAnalyzer;


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

    @GetMapping("/search/product")
    public List<Product> searchProduct(@RequestParam String keyword){
        LOGGER.info("Processing searchProduct Request");
        keywordAnalyzer.recordKeyword(keyword);
        return productService.getProductsByKeyword(keyword);
    }


}
