package yun.likelion.be_study.controller.comments;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.likelion.be_study.filter.SessionConst;
import yun.likelion.be_study.service.comments.CommentLikesService;

@RestController
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentLikesController {
    private final CommentLikesService commentLikesService;

    public CommentLikesController(CommentLikesService commentLikesService) {
        this.commentLikesService = commentLikesService;
    }

    @PostMapping("/{commentId}/likes")
    public ResponseEntity<Long> like(@PathVariable("boardId") Long boardId,
                                     @PathVariable("commentId") Long commentId,
                                     HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok(commentLikesService.like(boardId, commentId, memberId));
    }

    @DeleteMapping("/{commentId}/likes")
    public ResponseEntity<Long> unlike(@PathVariable("boardId") Long boardId,
                                       @PathVariable("commentId") Long commentId,
                                       HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        return ResponseEntity.ok(commentLikesService.unlike(boardId, commentId, memberId));
    }
}
