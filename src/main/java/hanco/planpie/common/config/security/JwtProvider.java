package hanco.planpie.common.config.security;

import hanco.planpie.user.domain.User;
import hanco.planpie.user.dto.JwtTokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    @Value("${jwt.secret}")
    private String jwtSecret;

    /*
    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getId);
    }
    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        // Check token validation
        if(Boolean.FALSE.equals(validateToken((token)))) {
            return null;
        }

        final Claims claims = getAllClaimsFromToken(token);

        log.info("====================getClaimFromToken ::: Subject ::: " + claims.getSubject());
        log.info("====================getClaimFromToken ::: Id ::: " + claims.getId());
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateAccessToken(final String id) {
        return generateAccessToken(id, new HashMap<>());
    }

    public String generateAccessToken(final long id) {
        return generateAccessToken(String.valueOf(id), new HashMap<>());
    }

    public String generateAccessToken(final String id, final Map<String, Object> claims) {
        return doGenerateAccessToken(id, claims);
    }

    private String doGenerateAccessToken(final String id, final Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 300000)) // 5min
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(final String id) {
        return doGenerateRefreshToken(id);
    }

    public String generateRefreshToken(final long id) {
        return doGenerateRefreshToken(String.valueOf(id));
    }

    private String doGenerateRefreshToken(final String id) {
        return Jwts.builder()
                .setId(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (300000 * 12) * 24)) // 24hours
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
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

    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        User u = new User(claims.getSubject(), "", Collections.singletonList(authority));
        return new UsernamePasswordAuthenticationToken(u, "", authorities);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    */




    public JwtTokenDto getToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", user.getAuthorities());

        String accessToken = Jwts.builder()
                .setSubject(user.getEmail())  // 사용자 이메일을 Subject로 설정
                .setIssuedAt(new Date())  // 토큰 발급 시간
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 300000))  // 만료 시간 (24시간)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)  // HS256 알고리즘을 사용해 서명
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        return JwtTokenDto.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public List<String> getAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return (List<String>) claims.get("roles");
    }

    public Authentication getAuthentication(String token) {
        String username = getUsernameFromToken(token);
        List<String> authorities = getAuthoritiesFromToken(token);
        User userDetails = new User(username, "", true, "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
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
