package hanco.planpie.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
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

    public User(User user) {
    }

    public Collection<? extends GrantedAuthority> getAuthorities(User u) {
        // 권한이 "ROLE_USER" 또는 "ROLE_ADMIN" 등으로 있을 수 있기 때문에
        // 권한을 SimpleGrantedAuthority로 변환하여 리스트로 반환
        return Collections.singletonList(new SimpleGrantedAuthority(u.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한을 SimpleGrantedAuthority로 반환
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않음을 기본값으로 설정
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠겨 있지 않음을 기본값으로 설정
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명이 만료되지 않음을 기본값으로 설정
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
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
