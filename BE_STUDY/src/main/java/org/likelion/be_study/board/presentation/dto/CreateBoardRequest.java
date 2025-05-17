package org.likelion.be_study.board.presentation.dto;

import org.likelion.be_study.board.domain.Board;
import org.likelion.be_study.board.domain.Category;

public record CreateBoardRequest(
    String title,
    String content,
    Category category
) {
    public Board toEntity() {
        return Board.builder()
            .title(title)
            .content(content)
            .category(category)
            .build();
    }
}
