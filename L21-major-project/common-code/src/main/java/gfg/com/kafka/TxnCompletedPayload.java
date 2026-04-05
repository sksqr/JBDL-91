package gfg.com.kafka;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TxnCompletedPayload {
    private Long id; // ID for actual txn
    private Boolean success;
    private String reason;
    private String requestId;
}
