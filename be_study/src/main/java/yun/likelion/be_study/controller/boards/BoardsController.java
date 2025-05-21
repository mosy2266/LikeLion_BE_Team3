package yun.likelion.be_study.controller.boards;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.likelion.be_study.dto.boards.BoardsCreateRequestDto;
import yun.likelion.be_study.dto.boards.BoardsDetailResponseDto;
import yun.likelion.be_study.dto.boards.BoardsSimpleResponseDto;
import yun.likelion.be_study.dto.boards.BoardsUpdateRequestDto;
import yun.likelion.be_study.filter.SessionConst;
import yun.likelion.be_study.service.boards.BoardsService;
import yun.likelion.be_study.service.boards.ViewCountService;

@RestController
@RequestMapping("/api/boards")
@Tag(name = "Boards", description = "게시판 관련 API")
public class BoardsController {
    private final BoardsService boardsService;
    private final ViewCountService viewCountService;

    public BoardsController(BoardsService boardsService,  ViewCountService viewCountService) {
        this.boardsService = boardsService;
        this.viewCountService = viewCountService;
    }

    //게시글 목록
    @Operation(summary = "게시글 목록 조회")
    @GetMapping
    //쿼리스트링으로 /api/boards?name=john&keyword=Spring&page=0&size=5 이런 식으로 파라미터 넘겨주면 됨
    public Page<BoardsSimpleResponseDto> getAllBoards(@RequestParam(value = "name", required = false) String name,
                                                      @RequestParam(value = "keyword", required = false) String keyword,
                                                      Pageable pageable) {
        return boardsService.getAllBoards(name, keyword, pageable);
    }

    //게시글 작성
    @PostMapping
    @Operation(summary = "게시글 작성")
    public ResponseEntity<BoardsSimpleResponseDto> createBoard(@RequestBody BoardsCreateRequestDto dto,
                                                               HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        BoardsSimpleResponseDto boardsSimpleResponseDto = boardsService.createBoard(dto,memberId);
        return ResponseEntity.ok(boardsSimpleResponseDto);
    }

    //게시글 열람
    @GetMapping("/{boardId}")
    @Operation(summary = "게시글 열람")
    public ResponseEntity<BoardsDetailResponseDto> getBoard(@PathVariable(name = "boardId") Long boardId,
                                                            HttpServletRequest request) {

        //조회 시 DeletedBoardException 발생 -> 410 Gone + "삭제된 글입니다." 반환
        BoardsDetailResponseDto dto = boardsService.getBoard(boardId);

        //클라이언트 ip 추출
        String clientIp = request.getRemoteAddr();

        //중복 조회 여부 체크 후 Redis에 기록
        boolean incremented = viewCountService.recordView(boardId, clientIp);

        //Redis에 쌓인 조회수 + 1을 즉시 화면에 반영
        if (incremented) {
            //Redis <-> DB 간 비동기 처리 로직은 깨지지 않음
            dto.setViewCount(dto.getViewCount() + 1);
        }

        return ResponseEntity.ok(dto);
    }

    //게시글 수정
    @PutMapping("/{boardId}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<BoardsSimpleResponseDto> updateBoard(@PathVariable(name = "boardId") Long boardId,
                                                               @RequestBody BoardsUpdateRequestDto dto,
                                                               HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        BoardsSimpleResponseDto boardsSimpleResponseDto = boardsService.updateBoard(boardId, dto, memberId);
        return ResponseEntity.ok(boardsSimpleResponseDto);
    }

    //게시글 삭제
    @DeleteMapping("/{boardId}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<String> deleteBoard(@PathVariable(name = "boardId") Long boardId,
                                              HttpServletRequest request) {

        Long memberId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        boardsService.deleteBoard(boardId, memberId);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }
}
