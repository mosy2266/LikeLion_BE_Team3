package yun.likelion.be_study.controller.comments;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.likelion.be_study.dto.comments.CommentsCreateRequestDto;
import yun.likelion.be_study.dto.comments.CommentsResponseDto;
import yun.likelion.be_study.dto.comments.CommentsUpdateRequestDto;
import yun.likelion.be_study.filter.SessionConst;
import yun.likelion.be_study.service.comments.CommentsService;

@RestController
@RequestMapping("/api/boards/{boardId}/comments")
@Tag(name = "Comments", description = "댓글 관련 API")
public class CommentsController {
    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    //댓글 작성
    @Operation(summary = "댓글 작성")
    @PostMapping
    public ResponseEntity<CommentsResponseDto> createComment(@PathVariable Long boardId,
                                                             @RequestBody @Valid CommentsCreateRequestDto dto,
                                                             HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        CommentsResponseDto responseDto = commentsService.createComment(boardId, dto, memberId);
        return ResponseEntity.ok(responseDto);
    }

    //댓글 수정
    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentsResponseDto> updateComment(@PathVariable Long boardId,
                                                             @PathVariable Long commentId,
                                                             @RequestBody @Valid CommentsUpdateRequestDto dto,
                                                             HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        CommentsResponseDto updated = commentsService.updateComment(boardId, commentId, dto, memberId);
        return ResponseEntity.ok(updated);
    }

    //댓글 삭제
    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long boardId,
                                                @PathVariable Long commentId, HttpServletRequest request) {
        Long memberId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        commentsService.deleteComment(boardId, commentId, memberId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }
}
