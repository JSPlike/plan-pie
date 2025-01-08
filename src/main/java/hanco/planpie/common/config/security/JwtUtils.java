package hanco.planpie.common.config.security;

import hanco.planpie.user.dto.CustomUserDto;
import hanco.planpie.user.dto.JwtTokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    private final Key key;
    private final long accessExpiredTime;
    private final long refreshExpiredTime;
    @Value("${jwt.secret}")
    private String jwtSecret;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public JwtUtils(@Value("${jwt.secret}") String sKey, @Value("${jwt.accessExpiredTime}") long accessExpiredTime
                                                     , @Value("${jwt.refreshExpiredTime}") long refreshExpiredTime ) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(sKey));
        this.accessExpiredTime = accessExpiredTime;
        this.refreshExpiredTime = refreshExpiredTime;
    }

    public JwtTokenDto generatorToken(CustomUserDto userDto) {
        Claims claims = Jwts.claims();
        claims.put("id", userDto.getId());
        claims.put("email", userDto.getEmail());
        claims.put("role", userDto.getRole());

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())  // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiredTime))  // 만료 시간 (24시간)
                .signWith(SignatureAlgorithm.HS256, key)  // HS256 알고리즘을 사용해 서명
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiredTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        log.info("================accessToken================");
        log.info(accessToken);
        log.info("================refreshToken================");
        log.info(refreshToken);
        return JwtTokenDto.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String getUserName(String token) {
        return parseClaims(token).get("email", String.class);
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
}
