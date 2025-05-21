package yun.likelion.be_study.repository.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yun.likelion.be_study.entity.comments.CommentLikes;
import yun.likelion.be_study.entity.comments.Comments;
import yun.likelion.be_study.entity.members.Members;

import java.util.Optional;

@Repository
public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    boolean existsByMemberAndComment(Members member, Comments comment);
    Optional<CommentLikes> findByMemberAndComment(Members member, Comments comment);
}
