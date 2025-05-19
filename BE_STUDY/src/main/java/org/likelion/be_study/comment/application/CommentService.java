package org.likelion.be_study.comment.application;

import lombok.RequiredArgsConstructor;
import org.likelion.be_study.board.domain.Board;
import org.likelion.be_study.board.persistence.BoardRepository;
import org.likelion.be_study.comment.domain.Comment;
import org.likelion.be_study.comment.persistence.CommentRepository;
import org.likelion.be_study.comment.presentation.dto.CreateCommentRequest;
import org.likelion.be_study.comment.presentation.dto.UpdateCommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createComment(Long boardId, CreateCommentRequest commentRequest){
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        commentRepository.save(commentRequest.toEntity(board));
    }

    @Transactional
    public void updateComment(Long commentId, UpdateCommentRequest commentRequest) {
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }

        commentRepository.findById(commentId)
            .ifPresent(comment -> comment.update(commentRequest.content));

    }


    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }

        commentRepository.deleteById(commentId);
    }
}
