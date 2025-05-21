package yun.likelion.be_study.controller.members;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yun.likelion.be_study.dto.members.LoginDto;
import yun.likelion.be_study.dto.members.SignupDto;
import yun.likelion.be_study.entity.members.Members;
import yun.likelion.be_study.filter.SessionConst;
import yun.likelion.be_study.service.members.MembersService;

@RestController
@RequestMapping("/api/members")
@Tag(name = "Members", description = "회원 관련 API")
public class MembersController {

    private final MembersService membersService;

    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDto dto) {
        membersService.signup(dto);
        return ResponseEntity.ok("회원가입 성공");
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto,
                                   HttpServletRequest request) {
        Members loginMember = membersService.login(dto);
        //세션에 회원 id 보관
        request.getSession(true)
                .setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getMemberId());
        return ResponseEntity.ok("로그인 성공");
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }

        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setPath("/"); //전체 경로에 대해 제거
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); //즉시 만료
        response.addCookie(cookie);

        return ResponseEntity.ok("로그아웃 성공");
    }
}
