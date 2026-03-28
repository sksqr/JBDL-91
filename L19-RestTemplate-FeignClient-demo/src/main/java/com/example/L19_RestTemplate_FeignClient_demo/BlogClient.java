package com.example.L19_RestTemplate_FeignClient_demo;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "blogs", url = "https://jsonplaceholder.typicode.com")
public interface BlogClient {

    @GetMapping("/posts/{id}")
    BlogDto getBlog(@PathVariable Long id, @RequestHeader String requestId);


}
