package com.example.L13_minor_project_01.dto;

import com.example.L13_minor_project_01.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderDetailsResponseDto {

    private Long orderId;
    private Long userId;
    private String userName;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemDetailsDto> items;

    @Getter
    @Builder
    public static class OrderItemDetailsDto {
        private Long orderItemId;
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal lineTotal;
    }
}
