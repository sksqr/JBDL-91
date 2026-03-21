package com.example.L17_SpringSecurity_demo.controller;

import com.example.L17_SpringSecurity_demo.MyAppUserService;
import com.example.L17_SpringSecurity_demo.dto.CreateUserRequestDto;
import com.example.L17_SpringSecurity_demo.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MyAppUserService userService;


    @GetMapping("/hello")
    public String hello() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = appUser.getName();
        return "User "+name+" Hello from:"+Thread.currentThread().getName();
    }


}
