package org.likelion.be_study.comment.presentation.dto;

import org.likelion.be_study.board.domain.Board;
import org.likelion.be_study.comment.domain.Comment;

public record CreateCommentRequest (
    String content
) {
    public Comment toEntity(Board board) {
        return Comment.builder()
                .board(board)
                .content(content)
                .build();
    }
}
