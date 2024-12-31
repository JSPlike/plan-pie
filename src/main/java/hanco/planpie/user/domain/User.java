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
import java.util.HashSet;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_auth", joinColumns = @JoinColumn(name = "email"))
    @Column(name = "authority")
    private Collection<String> authorities = new HashSet<>();

    public User(User user) {
    }

    @Builder
    public User(String email, String password, boolean isEnabled, String emailVerificationToken, Collection<String> authorities) {
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.emailVerificationToken = emailVerificationToken;
        this.authorities = authorities != null ? authorities : new HashSet<>();
    }

    public void addAuthority(String authority) {
        this.authorities.add(authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(authorty -> (GrantedAuthority) () -> authorty)
                .collect(Collectors.toList());
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

    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }
}
