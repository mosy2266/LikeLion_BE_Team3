package org.likelion.be_study.board.presentation.dto;

import org.likelion.be_study.board.domain.Category;

public record UpdateBoardRequest(
    String title,
    String content,
    Category category
) { }
