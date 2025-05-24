package yun.likelion.be_study.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import yun.likelion.be_study.annotations.OwnerRequired;
import yun.likelion.be_study.exception.ForbiddenException;
import yun.likelion.be_study.exception.UnauthenticatedException;
import yun.likelion.be_study.repository.MembersRepository;

@Aspect
@Component
@Slf4j
public class LoginCheckAspect {

    private final MembersRepository membersRepository;
    private static final String KEY = SessionConst.LOGIN_MEMBER;

    public LoginCheckAspect(MembersRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    //인증
    @Pointcut("@annotation(yun.likelion.be_study.annotations.LoginRequired) || " +
            "@within(yun.likelion.be_study.annotations.LoginRequired)")
    public void loginRequired() {
    }

    @Around("loginRequired()")
    public Object loginCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                currentRequestAttributes()).getRequest();

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(KEY) == null) {
            throw new UnauthenticatedException("로그인이 필요합니다.");
        }

        return joinPoint.proceed(); //통과
    }

    //인가
    @Around("@annotation(ownerRequired)")
    public Object checkOwner(ProceedingJoinPoint joinPoint,
                             OwnerRequired ownerRequired) throws Throwable {

        ServletRequestAttributes attr =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        //웹 요청이 아니라면 곧장 401
        if (attr == null) {
            throw new UnauthenticatedException("로그인이 필요합니다.(no request context)");
        }

        HttpSession session = attr.getRequest().getSession(false);
        if (session == null || session.getAttribute(KEY) == null) {
            throw new UnauthenticatedException("로그인이 필요합니다.");
        }

        Long loginMemberId = (Long) session.getAttribute(KEY);

        //메서드 파라미터에서 memberId 추출
        Object[] args = joinPoint.getArgs();
        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
        String[] names = sig.getParameterNames();

        Long targetMemberId = null;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(ownerRequired.memberIdParam())) {
                targetMemberId = (Long) args[i];
                break;
            }
        }
        if (targetMemberId == null || !loginMemberId.equals(targetMemberId)) {
            throw new ForbiddenException("본인만 수정/삭제할 수 있습니다.");
        }
        return joinPoint.proceed();
    }
}
