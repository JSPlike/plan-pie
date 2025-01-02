package hanco.planpie.user.controller;

import hanco.planpie.user.dto.CustomUserDto;
import hanco.planpie.user.dto.LoginDto;
import hanco.planpie.user.dto.RegisterUserDto;
import hanco.planpie.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    // 생성자 주입을 통해 UserService를 주입받음
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 애플리케이션 시작 시 기본 사용자 생성
    /*
    @Override
    public void run(String... args) throws Exception {
        userService.createDefaultUser();
    }
    */

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) throws Exception {
        log.info("===================Controller LOGIN START===================");
        log.info("=============EMAIL : " + loginDto.getEmail() + "================");
        log.info("=============PASSWORD : " + loginDto.getPassword() + "================");
        try {
            // 이메일과 비밀번호를 사용해 인증 (예: 사용자 서비스에서 인증 처리)
            String token = userService.authenticateUser(loginDto);
            log.info("===================AUTHENTICATION START===================");
            if (token != null) {
                log.info("===================AUTHENTICATION SUCCESS===================");
                // 인증 성공
                return ResponseEntity.status(HttpStatus.OK).body(token);
            } else {
                log.info("===================AUTHENTICATION FAIL===================");
                // 인증 실패
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            log.info("===================EXCEPTION===================");
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
