package com.example.L08_SpringBoot_Demo;


import org.gfg.jbdl91.keywords.KeywordAnalyzerInterface;
import org.gfg.jbdl91.keywords.UniqueKeywordAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppController {

    @Autowired
    private FlightSearchService flightSearchService;

    @Autowired
    private ProductService productService;

    @Autowired(required = false)
    private KeywordAnalyzerInterface keywordAnalyzer;
//    private KeywordAnalyzerInterface keywordAnalyzer = new UniqueKeywordAnalyzer();


    @GetMapping("/hello")
    public String getHello(){
        return "Hello World! from: "+Thread.currentThread().getName();
    }

    @GetMapping("/flightData")
    public String getFlightData(){
        return flightSearchService.callIndigoAPIForData();
    }

    @GetMapping("/product")
    public Product getProduct(@RequestParam String key){
        if(keywordAnalyzer != null){
            keywordAnalyzer.recordKeyword(key);
        }
        Product product = productService.getProduct(key);
        return  product;
    }

    @GetMapping("/allKeyword")
    public List<String> getAllKeyword(){
        return keywordAnalyzer.getAllKeywords();
    }

}
