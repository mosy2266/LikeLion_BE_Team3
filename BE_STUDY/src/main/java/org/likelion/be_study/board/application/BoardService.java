package org.likelion.be_study.board.application;

import lombok.RequiredArgsConstructor;
import org.likelion.be_study.board.domain.Board;
import org.likelion.be_study.board.domain.Category;
import org.likelion.be_study.board.persistence.BoardRepository;
import org.likelion.be_study.board.presentation.dto.CreateBoardRequest;
import org.likelion.be_study.board.presentation.dto.UpdateBoardRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void createBoard(CreateBoardRequest boardRequest){
        boardRepository.save(boardRequest.toEntity());
    }

    @Transactional
    public void updateBoard(Long boardId, UpdateBoardRequest boardRequest){
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        board.update(boardRequest);
    }

    public Page<Board> getBoardsByCategory(Category category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return boardRepository.findByCategory(category, pageable);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boardRepository.delete(board);
    }
}
