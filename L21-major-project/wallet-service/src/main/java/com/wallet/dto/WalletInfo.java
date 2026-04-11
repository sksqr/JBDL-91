package com.wallet.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WalletInfo {
    private Long id;
    private Long userId;
    private Double balance;

}
