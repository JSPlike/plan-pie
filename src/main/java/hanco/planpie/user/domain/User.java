package hanco.planpie.user.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키

    private String username;
    private String password;
    private String role;

    // 기본 생성자
    public User() {}

    // 매개변수가 있는 생성자
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ID getter
    public Long getId() {
        return id;
    }

    // ID setter
    public void setId(Long id) {
        this.id = id;
    }

    // Username getter
    public String getUsername() {
        return username;
    }

    // Username setter
    public void setUsername(String username) {
        this.username = username;
    }

    // Password getter
    public String getPassword() {
        return password;
    }

    // Password setter
    public void setPassword(String password) {
        this.password = password;
    }

    // Role getter
    public String getRole() {
        return role;
    }

    // Role setter
    public void setRole(String role) {
        this.role = role;
    }
}
