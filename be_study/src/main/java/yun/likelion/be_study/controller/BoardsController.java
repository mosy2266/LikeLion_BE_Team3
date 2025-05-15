package yun.likelion.be_study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.likelion.be_study.dto.boards.BoardsCreateRequestDto;
import yun.likelion.be_study.dto.boards.BoardsDetailResponseDto;
import yun.likelion.be_study.dto.boards.BoardsSimpleResponseDto;
import yun.likelion.be_study.dto.boards.BoardsUpdateRequestDto;
import yun.likelion.be_study.dto.comments.CommentsResponseDto;
import yun.likelion.be_study.service.BoardsService;
import yun.likelion.be_study.service.CommentsService;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@Tag(name = "Boards", description = "게시판 관련 API")
public class BoardsController {
    private BoardsService boardsService;
    private CommentsService commentsService;

    public BoardsController(BoardsService boardsService,  CommentsService commentsService) {
        this.boardsService = boardsService;
        this.commentsService = commentsService;
    }

    //게시글 목록
    @Operation(summary = "게시글 목록 조회")
    @GetMapping
    //쿼리스트링으로 /api/boards?page=0&size=5 이런 식으로 파라미터 넘겨주면 됨
    public Page<BoardsSimpleResponseDto> getAllBoards(Pageable pageable) {
        return boardsService.getAllBoards(pageable);
    }

    //게시글 작성
    @PostMapping
    @Operation(summary = "게시글 작성")
    public ResponseEntity<BoardsSimpleResponseDto> createBoard(@RequestBody BoardsCreateRequestDto dto) {
        BoardsSimpleResponseDto boardsSimpleResponseDto = boardsService.createBoard(dto);
        return ResponseEntity.ok(boardsSimpleResponseDto);
    }

    //게시글 열람
    @GetMapping("/{boardId}")
    @Operation(summary = "게시글 열람")
    public ResponseEntity<BoardsDetailResponseDto> getBoard(@PathVariable(name = "boardId") Long boardId) {
        BoardsDetailResponseDto dto = boardsService.getBoard(boardId);
        return ResponseEntity.ok(dto);
    }

    //게시글 수정
    @PutMapping("/{boardId}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<BoardsSimpleResponseDto> updateBoard(@PathVariable(name = "boardId") Long boardId,
                                                               @RequestBody BoardsUpdateRequestDto dto) {
        BoardsSimpleResponseDto boardsSimpleResponseDto = boardsService.updateBoard(boardId, dto);
        return ResponseEntity.ok(boardsSimpleResponseDto);
    }

    //게시글 삭제
    @DeleteMapping("/{boardId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<String> deleteBoard(@PathVariable(name = "boardId") Long boardId) {
        boardsService.deleteBoard(boardId);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }
}
