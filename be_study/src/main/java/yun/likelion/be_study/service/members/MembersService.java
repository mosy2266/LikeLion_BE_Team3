package yun.likelion.be_study.service.members;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.likelion.be_study.dto.members.LoginDto;
import yun.likelion.be_study.dto.members.SignupDto;
import yun.likelion.be_study.entity.members.Members;
import yun.likelion.be_study.repository.members.MembersRepository;

@Service
public class MembersService {
    private final MembersRepository membersRepository;

    public MembersService(MembersRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    //회원 가입
    @Transactional
    public Members signup(SignupDto signupDto) {
        if (membersRepository.findByUsername(signupDto.getUsername()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        Members member = Members.builder()
                .nickname(signupDto.getNickname())
                .username(signupDto.getUsername())
                .password(signupDto.getPassword())
                .build();

        return membersRepository.save(member);
    }

    //로그인 검증
    @Transactional(readOnly = true)
    public Members login(LoginDto loginDto) {
        Members member = membersRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        if (!member.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        return member;
    }
}
