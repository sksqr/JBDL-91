package com.example.L17_SpringSecurity_demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {


    @GetMapping("/hello")
    public String hello() {
        return "Public Hello from:"+Thread.currentThread().getName();
    }
}
