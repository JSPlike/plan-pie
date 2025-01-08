package hanco.planpie.user.service;

import hanco.planpie.common.config.security.JwtUtils;
import hanco.planpie.common.service.EmailService;
import hanco.planpie.user.domain.RoleType;
import hanco.planpie.user.domain.User;
import hanco.planpie.user.dto.CustomUserDto;
import hanco.planpie.user.dto.JwtTokenDto;
import hanco.planpie.user.dto.LoginDto;
import hanco.planpie.user.dto.RegisterUserDto;
import hanco.planpie.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    
    public String createUser(RegisterUserDto registerUserDto) throws MessagingException {
        if (registerUserDto != null) {
            if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
                return "비밀번호가 일치하지 않습니다.";
            }
            // 이메일 인증토큰
            String verificationToken = UUID.randomUUID().toString();

            User user = User.builder()
                    .email(registerUserDto.getEmail())
                    .password(passwordEncoder.encode(registerUserDto.getPassword()))
                    .isEnabled(false)
                    .emailVerificationToken(verificationToken)
                    .role(RoleType.USER) // 하나의 객체만 담고있는 set
                    .build();
            
            userRepository.save(user);
        }

        return "Please check your email to complete the registration.";
    }

    public JwtTokenDto authenticateUser(LoginDto loginDto) {
        log.debug("=============Service authenticateUser================");
        log.debug("=============EMAIL : " + loginDto.getEmail() + "================");
        log.debug("=============PASSWORD : " + loginDto.getPassword() + "================");
        // check email
        User user = userRepository.findByEmail(loginDto.getEmail());
        Map<String , Object> response = new HashMap<>();

        if (user == null) {
            throw new BadCredentialsException("There is no this user!!!!");
        }

        if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("This is not correct password!!!!");
        }

        CustomUserDto dto = modelMapper.map(user, CustomUserDto.class);

        // {accessToken, reflashToken}
        JwtTokenDto token = jwtUtils.generatorToken(dto);

        log.info("============================================================================");
        log.info(token.getAccessToken());
        return token;
    }
}
