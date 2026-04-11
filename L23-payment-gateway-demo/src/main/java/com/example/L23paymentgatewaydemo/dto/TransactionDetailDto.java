package com.example.L23paymentgatewaydemo.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDetailDto {
    private String status;
    private Long userId;
    private Double amount;
}
