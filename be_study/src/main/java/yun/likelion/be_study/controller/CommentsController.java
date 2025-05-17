package yun.likelion.be_study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.likelion.be_study.dto.comments.CommentsCreateRequestDto;
import yun.likelion.be_study.dto.comments.CommentsDeleteRequestDto;
import yun.likelion.be_study.dto.comments.CommentsResponseDto;
import yun.likelion.be_study.dto.comments.CommentsUpdateRequestDto;
import yun.likelion.be_study.service.CommentsService;

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
                                                             @RequestBody @Valid CommentsCreateRequestDto dto) {
        CommentsResponseDto responseDto = commentsService.createComment(boardId, dto);
        return ResponseEntity.ok(responseDto);
    }

    //댓글 수정
    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentsResponseDto> updateComment(@PathVariable Long boardId,
                                                             @PathVariable Long commentId,
                                                             @RequestBody @Valid CommentsUpdateRequestDto dto) {
        CommentsResponseDto updated = commentsService.updateComment(boardId, commentId, dto);
        return ResponseEntity.ok(updated);
    }

    //댓글 삭제
    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long boardId,
                                                @PathVariable Long commentId,
                                                @RequestBody @Valid CommentsDeleteRequestDto dto) {
        commentsService.deleteComment(boardId, commentId, dto.getPassword());
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }

    //댓글 좋아요
    @Operation(summary = "좋아요")
    @PostMapping("/{commentId}/likes")
    public ResponseEntity<Long> like(@PathVariable Long boardId, @PathVariable Long commentId) {
        long updatedCount = commentsService.incrementLike(boardId, commentId);
        return ResponseEntity.ok(updatedCount);
    }

    //댓글 좋아요 취소
    @Operation(summary = "좋아요 취소")
    @DeleteMapping("/{commentId}/likes")
    public ResponseEntity<Long> unLike(@PathVariable Long boardId, @PathVariable Long commentId) {
        long updatedCount = commentsService.decrementLike(boardId, commentId);
        return ResponseEntity.ok(updatedCount);
    }
}
