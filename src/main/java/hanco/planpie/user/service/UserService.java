package hanco.planpie.user.service;

import hanco.planpie.common.service.EmailService;
import hanco.planpie.user.domain.User;
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
        if (userRepository.findByEmail("admin").isEmpty()) {
            User user = new User("admin@email.com", passwordEncoder.encode("password"), true, UUID.randomUUID().toString(), "ADMIN");
            userRepository.save(user);
            System.out.println("기본 사용자(admin) 생성됨");
        }
    }
    
    public String createUser(User user) throws MessagingException {
        if (user != null) {
            user.setUsername(user.getUsername()); // email
            user.setPassword(passwordEncoder.encode(user.getPassword())); // password
            user.setEnabled(false); // 활성화여부
            
            // 이메일 인증토큰
            String verificationToken = UUID.randomUUID().toString();
            user.setEmailVerificationToken(verificationToken);
            
            user.setRole("USER");
            
            userRepository.save(user);

            // 이메일 인증 링크 전송
            String verificationLink = "http://localhost:8080/api/auth/verify?token=" + verificationToken;
            String emailBody = "<p>To complete your registration, please verify your email:</p>"
                    + "<a href='" + verificationLink + "'>Verify Email</a>";

            emailService.sendEmail(user.getEmail(), "Complete Your Registration", emailBody);
        }

        return "Please check your email to complete the registration.";
    }
}
