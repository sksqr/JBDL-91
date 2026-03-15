package com.example.L16_SpringSecurity_demo.controller;

import com.example.L16_SpringSecurity_demo.MyAppUserService;
import com.example.L16_SpringSecurity_demo.dto.CreateUserRequestDto;
import com.example.L16_SpringSecurity_demo.entity.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MyAppUserService userService;


    @PostMapping
    public Long createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        Long id = userService.createUser(createUserRequestDto);
        return id;
    }

    @GetMapping("/hello")
    public String hello() {
        return "User Hello from:"+Thread.currentThread().getName();
    }


}
