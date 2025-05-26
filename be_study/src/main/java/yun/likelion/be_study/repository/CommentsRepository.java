package yun.likelion.be_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yun.likelion.be_study.entity.Comments;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findByCommentIdAndBoard_BoardId(Long commentId, Long boardId);
    boolean existsByCommentIdAndMember_MemberId(Long commentId, Long memberId);

    List<Comments> findAllByBoard_BoardIdAndMember_MemberId(Long boardId, Long memberId);
}
