package yun.likelion.be_study.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //request 로그
        log.info("▶ {} {} | args={}", request.getMethod(), request.getRequestURI(),
                Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        //response 로그
        log.info("◀ {} {} | result={}", request.getMethod(), request.getRequestURI(), result);

        return result; //@Around는 반드시 원본 메서드를 실행(proceed())하고 그 결과를 그대로 반환해야 함 -> 안 그러면 로직 아예 실행 X
    }
}
