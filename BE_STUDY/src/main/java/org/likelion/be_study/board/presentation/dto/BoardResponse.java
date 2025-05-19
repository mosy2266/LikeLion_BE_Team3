package org.likelion.be_study.board.presentation.dto;

import java.util.List;
import java.util.stream.Collectors;
import org.likelion.be_study.board.domain.Board;
import org.springframework.data.domain.Page;

public record BoardResponse(
    Long boardId,
    String title,
    String content
){
    // Page<Board> to BoardResponse
    public static BoardResponse of(final Board board) {
        return new BoardResponse(
            board.getId(),
            board.getTitle(),
            board.getContent()
        );
    }

    // Page<Board> to List<BoardResponse>
    public static List<BoardResponse> from(Page<Board> boards) {
        return boards.stream()
            .map(BoardResponse::of)
            .collect(Collectors.toList());
    }
}
