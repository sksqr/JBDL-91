package com.wallet.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PGPaymentStatusDTO {

    private String status;
    private Long userId;
    private Double amount;
}
