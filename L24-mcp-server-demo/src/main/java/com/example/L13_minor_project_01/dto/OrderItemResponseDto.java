package com.example.L13_minor_project_01.dto;

import com.example.L13_minor_project_01.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemResponseDto {

    private Long orderId;
    private OrderStatus orderStatus;
    private BigDecimal orderTotalAmount;
    private Boolean newOrderCreated;

    private Long orderItemId;
    private Long userId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
