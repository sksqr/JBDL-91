package gfg.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1l;

    private String name;
    private String email;
    private String phone;
    private String kycNumber;

}
