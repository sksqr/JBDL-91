package com.example.L19_RestTemplate_FeignClient_demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class BlogDto {

    private Long id;

    private String title;

    private String body;

    private Date serverDateTime;
}
