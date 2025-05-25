package org.example.be.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

// client가 토큰값과 함께 요청을 보내면 filter에서 토큰을 추출하고 인가에 활용하기 위한 클래스
@Component
public class JwtProvider {

  private static final String HEADER = "Authorization";
  private static final String PREFIX = "Bearer ";

  public String exractToken(HttpServletRequest request){
    String header = request.getHeader(HEADER);

    if(header!=null && header.startsWith(PREFIX)){
      // header 중 Authorization value 찾아옴 && Bearer로 시작하는지 판단
      // Bearer 빼고 나머지 부분 return
      return header.substring(PREFIX.length());
    }

    return null;
  }

}
