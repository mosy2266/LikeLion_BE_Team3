package org.likelion.be_study.comment.presentation.dto;

import org.likelion.be_study.comment.domain.Comment;

public record CommentResponse (
    Long commentId,
    String content
) {
    public static CommentResponse of(final Comment comment) {
        return new CommentResponse(
            comment.getId(),
            comment.getContent()
        );
    }
}
