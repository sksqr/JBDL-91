package com.example.L13_minor_project_01.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDto {

    private Long id;
    private String name;
    private String description;
}
