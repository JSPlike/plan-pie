package hanco.planpie.common.controller;

import hanco.planpie.user.domain.User;
import hanco.planpie.user.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/api/auth")
public class VerificationCodeController {
    private final UserRepository userRepository;

    public VerificationCodeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 인증 메일을 보내 토큰을 확인하는 api
     * @param token
     * @param model
     * @return
     */
    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token, Model model) {
        Optional<User> optionalUser = userRepository.findByEmailVerificationToken(token);
        
        // 사용자가 없을때
        if (optionalUser.isEmpty()) {
            return "email-verification-failure";  // 실패 페이지
        }

        User user = optionalUser.get();
        user.setEnabled(true);  // 계정 활성화
        user.setEmailVerificationToken(null);  // 토큰 제거

        userRepository.save(user);

        return "email-verification-success";  // 성공 페이지
    }
}
