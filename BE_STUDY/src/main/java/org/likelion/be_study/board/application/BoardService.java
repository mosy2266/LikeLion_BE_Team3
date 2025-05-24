package org.likelion.be_study.board.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.likelion.be_study.board.domain.Board;
import org.likelion.be_study.board.domain.Category;
import org.likelion.be_study.board.persistence.BoardRepository;
import org.likelion.be_study.board.presentation.dto.BoardResponse;
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
        Board board = getBoard(boardId);
        board.update(boardRequest);
    }

    public List<BoardResponse> getAllBoardsByCategory(Category category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boards = boardRepository.findByCategory(category, pageable);

        return BoardResponse.from(boards);
    }

    @Transactional
    public BoardResponse getBoardById(Long boardId) {
        Board board = getBoard(boardId);
        increaseViewCount(board);
        return BoardResponse.of(board);
    }


    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = getBoard(boardId);
        boardRepository.delete(board);
    }


    public void increaseViewCount(Board board){
        board.addViewCount();
    }

    public void increaseLikeCount(Long boardId) {
        Board board = getBoard(boardId);
        board.addLikeCount();
    }
    public void decreaseLikeCount(Long boardId) {
        Board board = getBoard(boardId);
        board.subLikeCount();
    }


    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

}
