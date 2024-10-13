package hanco.planpie.user.service;

import hanco.planpie.common.service.EmailService;
import hanco.planpie.user.domain.User;
import hanco.planpie.user.dto.RegisterUserDto;
import hanco.planpie.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void createDefaultUser() {
        if (userRepository.findByEmail("admin@email.com").isEmpty()) {
            User user = new User("admin@email.com", passwordEncoder.encode("password"), true, UUID.randomUUID().toString(), "ADMIN");
            userRepository.save(user);
            System.out.println("기본 사용자(admin) 생성됨");
        }
    }
    
    public String createUser(RegisterUserDto registerUserDto) throws MessagingException {
        if (registerUserDto != null) {
            if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
                return "비밀번호가 일치하지 않습니다.";
            }

            User user = new User(); // 신규유저
            user.setUsername(registerUserDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
            user.setEnabled(false); // 활성화여부
            user.setRole("USER"); // 일반 사용자

            // 이메일 인증토큰
            String verificationToken = UUID.randomUUID().toString();
            user.setEmailVerificationToken(verificationToken);
            
            userRepository.save(user);
        }

        return "Please check your email to complete the registration.";
    }
}
