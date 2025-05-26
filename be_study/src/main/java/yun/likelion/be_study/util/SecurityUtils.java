package yun.likelion.be_study.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import yun.likelion.be_study.entity.Members;
import yun.likelion.be_study.repository.CommentsRepository;

@Component("securityUtils")
public class SecurityUtils {

    private final CommentsRepository commentsRepository;

    public SecurityUtils(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public static Members getLoginMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }

        return (Members) authentication.getPrincipal();
    }

    public static void validateMember(Members member, Members loginMember) {
        if (!member.getMemberId().equals(loginMember.getMemberId())) {
            throw new RuntimeException("작성자만 수정/삭제할 수 있습니다.");
        }
    }

    public boolean isCommentMember(Long commentId) {
        Members member = getLoginMember();
        return commentsRepository.existsByCommentIdAndMember_MemberId(commentId, member.getMemberId());
    }
}
