package yun.likelion.be_study.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter init");
    }

    @Override
    //Http 요청이 아닌 경우를 고려
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        log.info("LogFilter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String requestURI = httpRequest.getRequestURI();

        //Http 요청을 구분하기 위한 로그 기록 -> 요청 당 임의의 UUID 생성
        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(req, res); //다음 필터가 있으면 필터를 호출, 없으면 서블릿 호출(이 로직이 없으면 다음 단계 진행 X)
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("LogFilter destroy");
    }
}
