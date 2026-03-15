package com.example.L16_SpringSecurity_demo.controller;

import com.example.L16_SpringSecurity_demo.entity.AppUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {


    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal AppUser appUser) {
        return appUser.getName()+" Hello from:"+Thread.currentThread().getName();
    }

}

/*
88DA5173ABE2F6396F5104074081558C

88DA5173ABE2F6396F5104074081558C

 */