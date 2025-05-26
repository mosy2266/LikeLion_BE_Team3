package yun.likelion.be_study.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.likelion.be_study.config.jwt.JwtTokenProvider;
import yun.likelion.be_study.dto.JwtToken;
import yun.likelion.be_study.dto.members.SignupDto;
import yun.likelion.be_study.entity.Members;
import yun.likelion.be_study.repository.MembersRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembersService {

    private final MembersRepository membersRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public MembersService(MembersRepository membersRepository, AuthenticationManagerBuilder authenticationManagerBuilder,
                          JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.membersRepository = membersRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입
    @Transactional
    public Members signup(SignupDto signupDto) {
        if (membersRepository.existsByUsername(signupDto.getUsername())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        Members member = Members.builder()
                .nickname(signupDto.getNickname())
                .username(signupDto.getUsername())
                .password(encodedPassword)
                .roles(roles)
                .build();

        return membersRepository.save(member);
    }

    //로그인
    @Transactional(readOnly = true)
    public JwtToken login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }
}
