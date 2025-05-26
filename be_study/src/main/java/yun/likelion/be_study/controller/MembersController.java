package yun.likelion.be_study.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yun.likelion.be_study.dto.JwtToken;
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

    @PostMapping("/signup")
    public ResponseEntity<Members> signup(@RequestBody @Valid SignupDto signupDto) {
        return ResponseEntity.ok(membersService.signup(signupDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody @Valid LoginDto loginDto) {
        return ResponseEntity.ok(membersService.login(loginDto.getUsername(), loginDto.getPassword()));
    }
}
