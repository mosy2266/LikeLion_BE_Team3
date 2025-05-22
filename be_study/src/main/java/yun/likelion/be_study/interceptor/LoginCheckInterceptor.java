package yun.likelion.be_study.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 : {}", requestURI);

        HttpSession session = request.getSession(false); //새 세션 생성 방지

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청입니다.");

            /*
            //로그인 화면으로 redirect
            response.sendRedirect("/api/members/login?redirectURL=" + requestURI);
            */

            //JSON API -> 401
            if (requestURI.startsWith("/api")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write("{\"message\" : \"로그인이 필요합니다.\"}");
            } else { //화면이 있는 페이지일 때만 redirect
                response.sendRedirect("/login?redirectURL=" + requestURI);
            }
            return false;
        }

        return true;
    }
}
