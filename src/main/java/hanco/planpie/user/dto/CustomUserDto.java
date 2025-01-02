package hanco.planpie.user.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDto {
    private long id;
    private String email;
    private String password;
    private String role;
}
