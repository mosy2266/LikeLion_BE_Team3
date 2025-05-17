package yun.likelion.be_study.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.likelion.be_study.dto.comments.CommentsCreateRequestDto;
import yun.likelion.be_study.dto.comments.CommentsResponseDto;
import yun.likelion.be_study.dto.comments.CommentsUpdateRequestDto;
import yun.likelion.be_study.entity.Boards;
import yun.likelion.be_study.entity.Comments;
import yun.likelion.be_study.repository.BoardsRepository;
import yun.likelion.be_study.repository.CommentsRepository;

@Service
public class CommentsService {
    private final BoardsRepository boardsRepository;
    private final CommentsRepository commentsRepository;

    public CommentsService(BoardsRepository boardsRepository, CommentsRepository commentsRepository) {
        this.boardsRepository = boardsRepository;
        this.commentsRepository = commentsRepository;
    }

    //댓글 작성
    @Transactional
    public CommentsResponseDto createComment(Long boardId, CommentsCreateRequestDto dto) {
        //게시글 존재 여부 확인
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다. 게시글 id : " + boardId));

        Comments comment = new Comments();
        comment.setBoard(board);
        comment.setNickname(dto.getNickname());
        comment.setPassword(dto.getPassword());
        comment.setContent(dto.getContent());

        commentsRepository.save(comment);

        return CommentsResponseDto.from(comment);
    }

    //댓글 수정
    @Transactional
    public CommentsResponseDto updateComment(Long boardId, Long commentId, CommentsUpdateRequestDto dto) {
        //게시글-댓글 관계 검증
        Comments comment = commentsRepository.findByCommentIdAndBoard_BoardId(commentId, boardId)
                .orElseThrow(() ->  new EntityNotFoundException("해당 댓글이 게시글에 존재하지 않습니다. 댓글 id : " + commentId));

        //비밀번호 검증
        if (!comment.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        //댓글 내용 업데이트
        comment.setContent(dto.getContent());

        return CommentsResponseDto.from(comment);
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long boardId, Long commentId, String password) {
        //게시글-댓글 관계 검증
        Comments comment = commentsRepository.findByCommentIdAndBoard_BoardId(commentId, boardId)
                .orElseThrow(() ->  new EntityNotFoundException("해당 댓글이 게시글에 존재하지 않습니다. 댓글 id : " + commentId));

        //비밀번호 검증
        if (!comment.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        commentsRepository.delete(comment);
    }

    //댓글 좋아요
    @Transactional
    public long incrementLike(Long boardId, Long commentId) {
        Comments comment = commentsRepository.findByCommentIdAndBoard_BoardId(commentId, boardId)
                .orElseThrow(() ->  new EntityNotFoundException("해당 댓글이 게시글에 존재하지 않습니다. 댓글 id : " + commentId));

        comment.setLikeCount(comment.getLikeCount() + 1);

        return comment.getLikeCount();
    }

    //댓글 좋아요 취소
    @Transactional
    public long decrementLike(Long boardId, Long commentId) {
        Comments comment = commentsRepository.findByCommentIdAndBoard_BoardId(commentId, boardId)
                .orElseThrow(() ->  new EntityNotFoundException("해당 댓글이 게시글에 존재하지 않습니다. 댓글 id : " + commentId));

        if (comment.getLikeCount() > 0) {
            comment.setLikeCount(comment.getLikeCount() - 1);
        }

        return comment.getLikeCount();
    }
}
