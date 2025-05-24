package org.likelion.be_study.board.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.likelion.be_study.board.application.BoardService;
import org.likelion.be_study.board.domain.Category;
import org.likelion.be_study.board.presentation.dto.BoardResponse;
import org.likelion.be_study.board.presentation.dto.CreateBoardRequest;
import org.likelion.be_study.board.presentation.dto.UpdateBoardRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateBoardRequest boardRequest){
        boardService.createBoard(boardRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{boardId}")
    public ResponseEntity<Void> update(@PathVariable Long boardId, @RequestBody UpdateBoardRequest boardRequest){
        boardService.updateBoard(boardId, boardRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category")
    public ResponseEntity<List<BoardResponse>> getBoardsByCategory(
        @RequestParam Category category,
        @RequestParam int page,
        @RequestParam int size
    ) {
        List<BoardResponse> response = boardService.getAllBoardsByCategory(category, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{boardId}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Long boardId) {
        BoardResponse response = boardService.getBoardById(boardId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{boardId}")
    public ResponseEntity<Void> delete(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }
}
