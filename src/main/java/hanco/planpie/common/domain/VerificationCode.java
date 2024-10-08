package hanco.planpie.common.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    public VerificationCode(String phoneNumber, String code, LocalDateTime expirationTime) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.expirationTime = expirationTime;
    }
}
