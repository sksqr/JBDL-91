package com.example.L19_RestTemplate_FeignClient_demo;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    private Long id;

    private String name;

    private Double cost;

    private Date serverDateTime;
}
