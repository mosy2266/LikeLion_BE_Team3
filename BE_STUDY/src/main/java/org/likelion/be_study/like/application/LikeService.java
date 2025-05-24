package org.likelion.be_study.like.application;


import lombok.RequiredArgsConstructor;
import org.likelion.be_study.board.application.BoardService;
import org.likelion.be_study.board.domain.Board;
import org.likelion.be_study.comment.application.CommentService;
import org.likelion.be_study.comment.domain.Comment;
import org.likelion.be_study.like.domain.BoardLike;
import org.likelion.be_study.like.domain.CommentLike;
import org.likelion.be_study.like.persistence.BoardLikeRepository;
import org.likelion.be_study.like.persistence.CommentLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final CommentService commentService;
    private final CommentLikeRepository commentLikeRepository;
    private final BoardService boardService;
    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    public void likeToBoard(Long boardId) {
        boardLikeRepository.findByBoardId(boardId)
            .ifPresentOrElse(
                BoardLike::like, // 이미 존재하면 like() 실행
                () -> {
                    Board board = boardService.getBoard(boardId);
                    boardLikeRepository.save(BoardLike.of(board)); // 없으면 새로 생성

                }
            );
        boardService.increaseLikeCount(boardId);
    }

    @Transactional
    public void unlikeToBoard(Long boardId){
        boardLikeRepository.findByBoardId(boardId)
            .ifPresentOrElse(
                BoardLike::unlike,
                () -> {
                    Board board = boardService.getBoard(boardId);
                    boardLikeRepository.save(BoardLike.of(board)); // 없으면 새로 생성
                }
            );
        boardService.decreaseLikeCount(boardId);
    }

    @Transactional
    public void likeToComment(Long commentId){
       commentLikeRepository.findByCommentId(commentId)
            .ifPresentOrElse(
                CommentLike::like, // 이미 존재하면 like() 실행
                () -> {
                    Comment comment = commentService.getComment(commentId);
                    commentLikeRepository.save(CommentLike.of(comment)); // 없으면 새로 생성
                }
            );
        commentService.increaseLikeCount(commentId);
    }


    @Transactional
    public void unlikeComment(Long commentId){

        commentLikeRepository.findByCommentId(commentId)
            .ifPresentOrElse(
                CommentLike::unlike,
                () -> {
                    Comment comment = commentService.getComment(commentId);
                    commentLikeRepository.save(CommentLike.of(comment)); // 없으면 새로 생성
                }
            );
        commentService.decreaseLikeCount(commentId);
    }

}
