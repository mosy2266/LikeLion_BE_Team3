package yun.likelion.be_study.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import yun.likelion.be_study.dto.JwtToken;
import yun.likelion.be_study.entity.Members;
import yun.likelion.be_study.repository.MembersRepository;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
//JWT를 생성, 검증, 복호화하여 사용자 인증 정보를 처리
public class JwtTokenProvider {

    private final MembersRepository membersRepository;
    private final Key key;
    public static final long ACCESS_TIME = Duration.ofMinutes(30).toMillis(); //액세스 토큰 만료 시간
    public static final long REFRESH_TIME = Duration.ofDays(14).toMillis(); //리프레시 토큰 만료 시간

    public JwtTokenProvider(MembersRepository membersRepository,
                            @Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.membersRepository = membersRepository;
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();

        Date accessTokenExpiresIn = new Date(now + ACCESS_TIME);
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TIME);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = extractClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("Invalid token");
        }

        Members member = membersRepository.findByUsername(claims.getSubject())
                .orElseThrow(() -> new RuntimeException("Username not found"));

        return new UsernamePasswordAuthenticationToken(member, "", member.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature");
            return false;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT");
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT");
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty");
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public Claims extractClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

