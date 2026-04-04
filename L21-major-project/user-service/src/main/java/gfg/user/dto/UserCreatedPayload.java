package gfg.user.dto;


import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCreatedPayload implements Serializable {

    private static final long serialVersionUID = 1l;

    private Long userId;
    private String userName;
    private String userEmail;
}
