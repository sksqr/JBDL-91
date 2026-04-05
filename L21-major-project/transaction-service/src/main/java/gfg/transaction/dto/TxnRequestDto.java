package gfg.transaction.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TxnRequestDto {


    private Long fromUserId;


    private Long toUserId;


    private Double amount;

    private String comment;
}
