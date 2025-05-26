package yun.likelion.be_study.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yun.likelion.be_study.entity.Members;
import yun.likelion.be_study.repository.MembersRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MembersRepository membersRepository;

    public CustomUserDetailsService(MembersRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return membersRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user"));
    }

    private UserDetails createUserDetails(Members members) {
        return members;
    }
}
