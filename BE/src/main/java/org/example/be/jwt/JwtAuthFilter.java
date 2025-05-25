package org.example.be.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter implements Filter {

  private final String SECRET_KEY = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    // request에서 header 찾기
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String header = request.getHeader("Authorization");

    String uri = request.getRequestURI();

    // filter로 시작하면 인증 거침
    if(uri.startsWith("/filter")) {
      if (header != null && header.startsWith("Bearer ")) {
        String token = header.substring(7);

        try {
          Claims claims = Jwts.parserBuilder()
              .setSigningKey(SECRET_KEY)
              .build()
              .parseClaimsJws(token)
              .getBody();

          request.setAttribute("userId", claims.get("userId"));
        } catch (ExpiredJwtException | IllegalArgumentException e) {
          HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
          httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          httpResponse.getWriter().write("Expired or Invalid");
          return;
        } catch (Exception e){
          HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
          httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          httpResponse.getWriter().write("Invalid Token");
          return;
        }
      }
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
