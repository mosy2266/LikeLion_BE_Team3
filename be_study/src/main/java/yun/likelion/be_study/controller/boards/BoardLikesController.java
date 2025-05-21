package yun.likelion.be_study.controller.boards;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.likelion.be_study.filter.SessionConst;
import yun.likelion.be_study.service.boards.BoardLikesService;

@RestController
@RequestMapping("/api/boards/likes/{boardId}")
public class BoardLikesController {
    private final BoardLikesService boardLikesService;

    public BoardLikesController(BoardLikesService boardLikesService) {
        this.boardLikesService = boardLikesService;
    }

    @PostMapping
    public ResponseEntity<Long> like(@PathVariable Long boardId, HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok(boardLikesService.like(boardId, memberId));
    }

    @DeleteMapping
    public ResponseEntity<Long> unlike(@PathVariable Long boardId, HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok(boardLikesService.unlike(boardId, memberId));
    }
}
