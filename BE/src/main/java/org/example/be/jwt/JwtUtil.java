package org.example.be.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {

  // 이후 환경변수 등으로 secretKey 설정
  private final String SECRET_KEY = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

  // 만료시간: 30분
  private final long EXPIRATION_TIME = 30 * 60 * 1000;

  // token 생성
  public String generateToken(Long userId){
    return Jwts.builder()
        .setSubject(userId.toString())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  // validate token
  public boolean validateToken(String token){
    try{
      parseClaims(token);
      return true;
    } catch (ExpiredJwtException e){
      log.warn("Token expired");
      System.out.println("token expired");
    } catch (JwtException e){
      log.warn("invalid token");
      System.out.println("invalid token");
    }

    return false;
  }

  // token에서 사용자 id 추출
  public Long getUserIdFromToken(String token){
    Claims claims = parseClaims(token);

    return Long.parseLong(claims.getSubject());
  }



  // token parsing
  public Claims parseClaims(String token){
    return Jwts.parserBuilder()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

}
