package yun.likelion.be_study.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yun.likelion.be_study.dto.members.LoginDto;
import yun.likelion.be_study.dto.members.SignupDto;
import yun.likelion.be_study.entity.Members;
import yun.likelion.be_study.interceptor.SessionConst;
import yun.likelion.be_study.service.MembersService;

@RestController
@RequestMapping("/api/members")
public class MembersController {
    private final MembersService membersService;

    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupDto signupDto) {
        membersService.signup(signupDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto,
                                   HttpServletRequest request) {
        Members loginMember = membersService.login(loginDto);
        request.getSession(true).setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getMemberId());
        return ResponseEntity.ok("로그인 성공");
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }

        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("로그아웃 성공");
    }
}
