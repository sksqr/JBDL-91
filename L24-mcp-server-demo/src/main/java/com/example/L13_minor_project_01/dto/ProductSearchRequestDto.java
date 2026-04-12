package com.example.L13_minor_project_01.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequestDto {

    @NotBlank
    private String keyword;
}
