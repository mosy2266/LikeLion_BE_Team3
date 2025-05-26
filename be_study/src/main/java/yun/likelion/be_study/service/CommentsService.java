package yun.likelion.be_study.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yun.likelion.be_study.dto.comments.CommentsCreateRequestDto;
import yun.likelion.be_study.dto.comments.CommentsResponseDto;
import yun.likelion.be_study.dto.comments.CommentsUpdateRequestDto;
import yun.likelion.be_study.entity.Boards;
import yun.likelion.be_study.entity.Comments;
import yun.likelion.be_study.entity.Members;
import yun.likelion.be_study.repository.BoardsRepository;
import yun.likelion.be_study.repository.CommentsRepository;
import yun.likelion.be_study.util.SecurityUtils;

import java.util.List;

import static yun.likelion.be_study.util.SecurityUtils.getLoginMember;
import static yun.likelion.be_study.util.SecurityUtils.validateMember;

@Service
public class CommentsService {
    private final BoardsRepository boardsRepository;
    private final CommentsRepository commentsRepository;
    private final SecurityUtils securityUtils;

    public CommentsService(BoardsRepository boardsRepository, CommentsRepository commentsRepository,
                           SecurityUtils securityUtils) {
        this.boardsRepository = boardsRepository;
        this.commentsRepository = commentsRepository;
        this.securityUtils = securityUtils;
    }

    //댓글 작성
    @Transactional
    public CommentsResponseDto createComment(Long boardId, CommentsCreateRequestDto dto) {
        //게시글 존재 여부 확인
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다. 게시글 id : " + boardId));

        Members loginMember = getLoginMember();

        Comments comment = new Comments();
        comment.setBoard(board);
        comment.setMember(loginMember);
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

        validateMember(comment.getMember(), getLoginMember());
        //댓글 내용 업데이트
        comment.setContent(dto.getContent());

        return CommentsResponseDto.from(comment);
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long boardId, Long commentId) {
        //게시글-댓글 관계 검증
        Comments comment = commentsRepository.findByCommentIdAndBoard_BoardId(commentId, boardId)
                .orElseThrow(() ->  new EntityNotFoundException("해당 댓글이 게시글에 존재하지 않습니다. 댓글 id : " + commentId));

        validateMember(comment.getMember(), getLoginMember());

        commentsRepository.delete(comment);
    }

    //댓글 목록
    @Transactional(readOnly = true)
    public List<CommentsResponseDto> getMyComments(Long boardId) {
        Long loginMemberId = getLoginMember().getMemberId();
        return commentsRepository.findAllByBoard_BoardIdAndMember_MemberId(boardId, loginMemberId).stream()
                .map(CommentsResponseDto::from)
                .toList();
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
