package hanco.planpie.common.config.security;

import hanco.planpie.user.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomUserDetailService userDetailService;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(AUTHORIZATION_HEADER);

        if(header != null && header.startsWith(BEARER_PREFIX)) {
            String token = header.substring(7);

            // check token validation
            if(jwtUtils.validateToken(token)) {
                String username = jwtUtils.getUserName(token);

                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                if(userDetails != null) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        log.info("============request ::: " + request + " ==================" );
        chain.doFilter(request, response);
    }

    // 무한 리다이렉트 방지
    private boolean isLoginRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/user/login") || uri.startsWith("/api/user/login");
    }

    private void handleException(Exception e, HttpServletResponse response) throws IOException {
        if (e instanceof IOException) {
            log.error("IOException occurred: {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        } else if (e instanceof ServletException) {
            log.error("ServletException occurred: {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
        } else {
            log.error("Unexpected error occurred: {}", e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }
}
