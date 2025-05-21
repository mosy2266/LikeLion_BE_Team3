package yun.likelion.be_study.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    //필터 적용하지 않을 URL들
    private static final String[] whiteList = {"/", "/api/members/signup", "/api/members/login", "/api/members/logout",
    "/h2-console/**", "/swagger-ui/**"};

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) res;

        try {
            log.info("인증 체크 필터 시작, requestURI = {}", requestURI);

            //화이트리스트를 제외한 모든 경우에 로그인 인증 처리
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행, requestURI = {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청, requestURI = {}", requestURI);

                    //비로그인 사용자를 로그인 화면으로 redirect
                    httpResponse.sendRedirect("/api/members/login?redirectURL=" + requestURI);
                    return; //필터를 더이상 진행시키지 않음
                }
            }

            chain.doFilter(req, res);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료, requestURI = {}", requestURI);
        }
    }

    //화이트리스트(인증 체크 X)
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
