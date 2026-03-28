package com.example.L19_RestTemplate_FeignClient_demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;

import java.util.Date;

@RestController
@RequestMapping("/blog")
public class BlogController {

    private static Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BlogClient blogClient;


    private String baseUrl = "https://jsonplaceholder.typicode.com/posts/";


//    @GetMapping("/{id}")
//    public ResponseEntity<JsonNode> getBlogData(@PathVariable String id) {
//
//        String url = baseUrl + id;
//
//        JsonNode response = restTemplate.getForEntity(url, JsonNode.class).getBody();
//        LOGGER.info("response: {}", response);
//        return ResponseEntity.ok(response);
//
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BlogDto> getBlogData(@PathVariable String id) {
//        LOGGER.info("Processing get Blog API call");
//        String url = baseUrl + id;
//
//        BlogDto blogDto = restTemplate.getForEntity(url, BlogDto.class).getBody();
//
//        blogDto.setServerDateTime(new Date());
//        LOGGER.info("response: {}", blogDto);
//        return ResponseEntity.ok(blogDto);
//
//    }


    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> getBlogData(@PathVariable Long id, @RequestHeader String requestId) {
        LOGGER.info("Processing get Blog API call");
        //MDC.get("requestId");
        BlogDto blogDto = blogClient.getBlog(id,requestId);
        blogDto.setServerDateTime(new Date());
        LOGGER.info("response: {}", blogDto);
        return ResponseEntity.ok(blogDto);

    }
}
