package hanco.planpie.user.controller;

import hanco.planpie.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
