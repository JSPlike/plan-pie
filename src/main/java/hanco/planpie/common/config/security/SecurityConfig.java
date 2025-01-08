package hanco.planpie.common.config.security;

import hanco.planpie.user.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService userDetailService;
    private final JwtUtils jwtUtils;

    private static final String[] PERMITLIST = {
            "/api/v1/user/**", "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/api/v1/auth/**", "/public/**", "/assets/**",
            "/static/**", "/biz/**", "/css/**", "/images/**", "/favicon.ico", "/templates/**", "/fragments/**",
            "/", "/user/**", "api/user/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(PERMITLIST).permitAll()
                .requestMatchers("/dashboard/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
        );
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtils, userDetailService),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
