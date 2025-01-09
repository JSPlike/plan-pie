package hanco.planpie.user.controller;

import hanco.planpie.user.dto.CustomUserDto;
import hanco.planpie.user.dto.JwtTokenDto;
import hanco.planpie.user.dto.LoginDto;
import hanco.planpie.user.dto.RegisterUserDto;
import hanco.planpie.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    // 생성자 주입을 통해 UserService를 주입받음
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginDto loginDto) throws Exception {
        JwtTokenDto response = null;
        try {
            // 이메일과 비밀번호를 사용해 인증 (예: 사용자 서비스에서 인증 처리)
            response = userService.authenticateUser(loginDto);
            log.info("===================AUTHENTICATION START===================");
            if (response != null) {
                log.info("===================AUTHENTICATION SUCCESS===================");
                // 인증 성공
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                log.info("===================AUTHENTICATION FAIL===================");
                // 인증 실패
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            log.info("===================EXCEPTION===================");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody RegisterUserDto registerUserDto) throws Exception {
        String resultMsg = "";

        resultMsg = userService.createUser(registerUserDto);
        return resultMsg;
    }
}
