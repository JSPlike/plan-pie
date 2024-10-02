package hanco.planpie.user.service;

import hanco.planpie.user.domain.User;
import hanco.planpie.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createDefaultUser() {
        if (userRepository.findByUsername("admin") == null) {
            User user = new User("admin", passwordEncoder.encode("password"), "ADMIN");
            userRepository.save(user);
            System.out.println("기본 사용자(admin) 생성됨");
        }
    }
}
