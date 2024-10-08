package hanco.planpie.user.controller;

import hanco.planpie.user.domain.User;
import hanco.planpie.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController implements CommandLineRunner {
    private final UserService userService;

    // 생성자 주입을 통해 UserService를 주입받음
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 애플리케이션 시작 시 기본 사용자 생성
    @Override
    public void run(String... args) throws Exception {
        userService.createDefaultUser();
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) throws Exception {
        String resultMsg = "";

        // 이메일 인증 토큰 생성
        resultMsg = userService.createUser(user);
        user.setEnabled(false);  // 계정 비활성화

        return resultMsg;
    }
}
