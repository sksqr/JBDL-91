package com.example.L17_SpringSecurity_demo.controller;

import com.example.L17_SpringSecurity_demo.MyAppUserService;
import com.example.L17_SpringSecurity_demo.dto.CreateUserRequestDto;
import com.example.L17_SpringSecurity_demo.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MyAppUserService userService;


    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal AppUser appUser) {
        return appUser.getName()+" Hello from:"+Thread.currentThread().getName();
    }



    @PostMapping("/create-user")
    public Long createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        Long id = userService.createUser(createUserRequestDto);
        return id;
    }


    @GetMapping("/changePassword")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal AppUser appUser, @RequestParam String newPassword){
        userService.changePassword(appUser,newPassword);
        return ResponseEntity.ok("Password changed");
    }




}

/*
88DA5173ABE2F6396F5104074081558C

88DA5173ABE2F6396F5104074081558C

 */