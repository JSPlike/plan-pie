package hanco.planpie.user.controller;

import hanco.planpie.common.config.security.JwtUtils;
import hanco.planpie.user.domain.User;
import hanco.planpie.user.dto.RegisterUserDto;
import hanco.planpie.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController implements CommandLineRunner {
    private final UserService userService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    // 생성자 주입을 통해 UserService를 주입받음
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 애플리케이션 시작 시 기본 사용자 생성
    @Override
    public void run(String... args) throws Exception {
        userService.createDefaultUser();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) throws Exception {
        try {
            // 이메일과 비밀번호를 사용해 인증 (예: 사용자 서비스에서 인증 처리)
            boolean isAuthenticated = userService.authenticateUser(email, password);

            if (isAuthenticated) {
                // 인증 성공
                String token = userService.accessToken(email);
                return ResponseEntity.status(HttpStatus.OK).body(token);
            } else {
                // 인증 실패
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody RegisterUserDto registerUserDto) throws Exception {
        String resultMsg = "";

        resultMsg = userService.createUser(registerUserDto);
        return resultMsg;
    }
}
