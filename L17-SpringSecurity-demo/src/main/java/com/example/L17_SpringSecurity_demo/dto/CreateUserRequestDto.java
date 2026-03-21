package com.example.L17_SpringSecurity_demo.dto;

import lombok.*;


@NoArgsConstructor
@Setter
@AllArgsConstructor
@Builder
@Getter
public class CreateUserRequestDto {

    private String email;


    private String name;


    private String password;


    private String role;
}
