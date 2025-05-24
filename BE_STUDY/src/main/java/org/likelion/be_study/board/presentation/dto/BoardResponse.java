package org.likelion.be_study.board.presentation.dto;

import java.util.List;
import java.util.stream.Collectors;
import org.likelion.be_study.board.domain.Board;
import org.likelion.be_study.board.domain.Category;
import org.likelion.be_study.comment.presentation.dto.CommentResponse;
import org.springframework.data.domain.Page;

public record BoardResponse(
    Long boardId,
    String title,
    String content,
    Category category,
    int viewCount,
    int likeCount,
    List<CommentResponse> comments
){
    // Page<Board> to BoardResponse
    public static BoardResponse of(final Board board) {
        return new BoardResponse(
            board.getId(),
            board.getTitle(),
            board.getContent(),
            board.getCategory(),
            board.getViewCount(),
            board.getLikeCount(),
            board.getComments().stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList())
        );
    }

    // Page<Board> to List<BoardResponse>
    public static List<BoardResponse> from(Page<Board> boards) {
        return boards.stream()
            .map(BoardResponse::of)
            .collect(Collectors.toList());
    }
}
