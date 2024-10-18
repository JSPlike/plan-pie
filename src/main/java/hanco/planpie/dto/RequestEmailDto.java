package hanco.planpie.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestEmailDto {
    private String email;
    private String code;
}
