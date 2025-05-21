package yun.likelion.be_study.repository.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yun.likelion.be_study.entity.comments.Comments;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findByCommentIdAndBoard_BoardId(Long commentId, Long boardId);

    List<Comments> findAllByBoard_BoardId(Long boardId);
}
