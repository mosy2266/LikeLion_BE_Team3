package yun.likelion.be_study.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yun.likelion.be_study.annotations.LoginRequired;
import yun.likelion.be_study.dto.members.LoginDto;
import yun.likelion.be_study.dto.members.SignupDto;
import yun.likelion.be_study.entity.Members;
import yun.likelion.be_study.service.MembersService;

@RestController
@RequestMapping("/api/members")
public class MembersController {

    private final MembersService membersService;

    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<Members> signup(@RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(membersService.signup(signupDto));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Members> login(@RequestBody LoginDto loginDto,
                                         HttpSession session) {
        return ResponseEntity.ok(membersService.login(loginDto, session));
    }

    //로그아웃
    @PostMapping("/logout")
    @LoginRequired
    public ResponseEntity<String> logout(HttpSession session) {
        membersService.logout(session);
        return ResponseEntity.ok("로그아웃 성공");
    }
}
