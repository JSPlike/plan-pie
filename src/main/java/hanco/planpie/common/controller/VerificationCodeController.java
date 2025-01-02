package hanco.planpie.common.controller;

import hanco.planpie.common.service.EmailService;
import hanco.planpie.user.dto.RequestEmailDto;
import hanco.planpie.user.domain.User;
import hanco.planpie.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/auth")
public class VerificationCodeController {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public VerificationCodeController(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    /**
     * 인증코드를 보낸다
     * @param requestEmailDto
     * @return
     */
    @PostMapping("/sendEmail")
    public Map<String, Object> sendEmail(@RequestBody RequestEmailDto requestEmailDto) throws MessagingException {
        Map<String, Object> retMap = new HashMap<>();

        if(requestEmailDto != null){
            User optionalUser = userRepository.findByEmail(requestEmailDto.getEmail());

            // Validation
            if (optionalUser != null) { // 이메일 중복
                retMap.put("succes", false);
                retMap.put("msg", "이미 동일한 이메일이 있습니다.");
                return retMap;
            }

            // sending email message
            emailService.sendEmail(requestEmailDto.getEmail());
            retMap.put("succes", true);
            retMap.put("msg", "인증번호를 전송하였습니다.");
        }

        return retMap;
    }

    /**
     * 인증코드를 확인한다
     * @param requestEmailDto
     * @return
     */
    @PostMapping("/verify")
    public Map<String, Object> verifyEmail(@RequestBody RequestEmailDto requestEmailDto) throws MessagingException {
        User optionalUser = userRepository.findByEmail(requestEmailDto.getEmail());
        Map<String, Object> retMap = new HashMap<>();

        // Validation
        if (optionalUser != null) { // 이메일 중복
            retMap.put("succes", false);
            retMap.put("msg", "이미 동일한 이메일이 있습니다.");
            return retMap;
        }

        emailService.sendEmail(requestEmailDto.getEmail());
        retMap.put("succes", true);
        retMap.put("msg", "인증번호를 전송하였습니다.");
        return retMap;
    }
}
