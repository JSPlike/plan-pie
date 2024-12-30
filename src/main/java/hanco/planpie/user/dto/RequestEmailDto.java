package hanco.planpie.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestEmailDto {
    private String email;
    private String code;
}
