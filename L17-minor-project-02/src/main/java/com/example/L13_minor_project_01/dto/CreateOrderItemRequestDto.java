package com.example.L13_minor_project_01.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderItemRequestDto {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
