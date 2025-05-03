package yun.likelion.be_study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.likelion.be_study.dto.BoardsCreateRequestDto;
import yun.likelion.be_study.dto.BoardsResponseDto;
import yun.likelion.be_study.dto.BoardsUpdateRequestDto;
import yun.likelion.be_study.service.BoardsService;

@RestController
@RequestMapping("/api/boards")
@Tag(name = "Boards", description = "게시판 관련 API")
public class BoardsController {
    private BoardsService boardsService;

    public BoardsController(BoardsService boardsService) {
        this.boardsService = boardsService;
    }

    //게시글 목록
    @Operation(summary = "게시글 목록 조회")
    @GetMapping
    //쿼리스트링으로 /api/boards?page=0&size=5 이런 식으로 파라미터 넘겨주면 됨
    public Page<BoardsResponseDto> getAllBoards(Pageable pageable) {
        return boardsService.getAllBoards(pageable);
    }

    //게시글 작성
    @PostMapping
    @Operation(summary = "게시글 작성")
    public ResponseEntity<BoardsResponseDto> createBoard(@RequestBody BoardsCreateRequestDto dto) {
        BoardsResponseDto boardsResponseDto = boardsService.createBoard(dto);
        return ResponseEntity.ok(boardsResponseDto);
    }

    //게시글 열람
    @GetMapping("/{id}")
    @Operation(summary = "게시글 열람")
    public ResponseEntity<BoardsResponseDto> getBoard(@PathVariable Long id) {
        BoardsResponseDto boardsResponseDto = boardsService.getBoard(id);
        return ResponseEntity.ok(boardsResponseDto);
    }

    //게시글 수정
    @PutMapping("/{id}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<BoardsResponseDto> updateBoard(@PathVariable Long id,
                                                         @RequestBody BoardsUpdateRequestDto dto) {
        BoardsResponseDto boardsResponseDto = boardsService.updateBoard(id, dto);
        return ResponseEntity.ok(boardsResponseDto);
    }

    //게시글 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {
        boardsService.deleteBoard(id);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }
}
