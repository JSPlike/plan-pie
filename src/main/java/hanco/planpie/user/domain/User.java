package hanco.planpie.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isEnabled;  // 계정 활성화 여부

    @Column(unique = true)
    private String emailVerificationToken;

    private String role;

    // 매개변수가 있는 생성자
    public User(String username, String password, boolean isEnabled, String emailVerificationToken, String role) {
        this.email = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.emailVerificationToken = emailVerificationToken;
        this.role = role;
    }

    public String getUsername() {
        return this.email;
    }

    // ID setter
    public void setId(Long id) {
        this.id = id;
    }

    // Username setter
    public void setUsername(String username) {
        this.email = username;
    }

    // Password setter
    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    // Role setter
    public void setRole(String role) {
        this.role = role;
    }
}
