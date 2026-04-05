package gfg.com.kafka;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletUpdatedPayload implements Serializable {

    private static final long serialVersionUID = 1l;

    private Long userId;
    private String userEmail;
    private Long walletId;
    private Double newBalance;
    private String requestId;
}

