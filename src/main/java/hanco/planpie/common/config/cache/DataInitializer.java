package hanco.planpie.common.config.cache;

import hanco.planpie.user.domain.User;
import hanco.planpie.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // admin 계정이 없으면 생성
        if (!userRepository.existsByEmail("admin@planpie.com")) {
            String encodedPassword = passwordEncoder.encode("admin");  // 비밀번호 암호화
            User adminUser = User.builder()
                    .email("admin@planpie.com")
                    .password(encodedPassword)
                    .isEnabled(true)
                    .authorities(new HashSet<>(List.of("ADMIN")))
                    .emailVerificationToken(null)  // 이메일 인증 토큰이 필요하지 않으면 null로 설정
                    .build();

            userRepository.save(adminUser);  // admin 계정 저장
            System.out.println("Admin account created");
        }
    }
}
