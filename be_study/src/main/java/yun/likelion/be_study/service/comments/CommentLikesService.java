package yun.likelion.be_study.service.comments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.likelion.be_study.entity.comments.CommentLikes;
import yun.likelion.be_study.entity.comments.Comments;
import yun.likelion.be_study.entity.members.Members;
import yun.likelion.be_study.repository.comments.CommentLikesRepository;
import yun.likelion.be_study.repository.comments.CommentsRepository;
import yun.likelion.be_study.repository.members.MembersRepository;

@Service
public class CommentLikesService {
    private final CommentLikesRepository commentLikesRepository;
    private final MembersRepository membersRepository;
    private final CommentsRepository commentsRepository;

    public CommentLikesService(CommentLikesRepository commentLikesRepository, MembersRepository membersRepository,
                           CommentsRepository commentsRepository) {
        this.commentLikesRepository = commentLikesRepository;
        this.membersRepository = membersRepository;
        this.commentsRepository = commentsRepository;
    }

    //댓글 좋아요
    @Transactional
    public long like(Long boardId, Long commentId, Long memberId) {
        Members member = membersRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        Comments comment = commentsRepository.findByCommentIdAndBoard_BoardId(commentId, boardId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        if (commentLikesRepository.existsByMemberAndComment(member, comment)) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
        }

        commentLikesRepository.save(CommentLikes.builder()
                .member(member)
                .comment(comment)
                .build());
        comment.setLikeCount(comment.getLikeCount() + 1);
        return comment.getLikeCount();
    }

    //댓글 좋아요 취소
    @Transactional
    public long unlike(Long boardId, Long commentId, Long memberId) {
        Members member = membersRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        Comments comment = commentsRepository.findByCommentIdAndBoard_BoardId(commentId, boardId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        CommentLikes like = commentLikesRepository.findByMemberAndComment(member, comment)
                .orElseThrow(() -> new IllegalStateException("좋아요를 누르지 않았습니다."));

        commentLikesRepository.delete(like);
        comment.setLikeCount(comment.getLikeCount() - 1);
        return comment.getLikeCount();
    }
}
