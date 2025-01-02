package hanco.planpie.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title="LOGIN_REQ_01 : 로그인 요청 DTO")
public class LoginDto {
    @NotNull(message = "이메일 입력은 필수입니다.")
    @Email
    private String email;

    @NotNull(message = "패스워드 입력은 필수입니다.")
    private String password;
}
