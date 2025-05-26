package org.example.be.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.be.comment.dto.CommentRequest;
import org.example.be.comment.dto.CommentResponse;
import org.example.be.comment.repository.CommentRepository;
import org.example.be.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filter/comment")
@RequiredArgsConstructor
public class CommentFilterController {

  private final CommentService commentService;
  private final CommentRepository commentRepository;

  // 댓글 수정
  @PatchMapping("/{comment_id}")
  public ResponseEntity<?> updateComment(
      @PathVariable(name = "comment_id") Long commentId,
      @RequestBody CommentRequest request,
      HttpServletRequest httpServletRequest
  ) {
    // request에 userId attribute 없으면 unauthorized
    if(httpServletRequest.getAttribute("userId")==null){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body("Unauthorized");
    }

    Long userId = Long.parseLong(httpServletRequest.getAttribute("userId").toString());
    Long commentUserId = commentRepository.findById(commentId).get().getId();

    // request의 userId랑 수정하려는 userId 다르면 예외처리
    if(commentUserId==null || userId!=commentUserId){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("the comment is not found or you aren't the writer");
    }

    CommentResponse response = commentService.update(commentId, request);

    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  // 댓글 삭제
  @DeleteMapping("/{comment_id}")
  public ResponseEntity<?> deleteComment(
      @PathVariable(name = "comment_id") Long commentId,
      HttpServletRequest httpServletRequest
  ) {
    // request에 userId attribute 없으면 unauthorized
    if(httpServletRequest.getAttribute("userId")==null){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body("Unauthorized");
    }

    Long userId = Long.parseLong(httpServletRequest.getAttribute("userId").toString());
    Long commentUserId = commentRepository.findById(commentId).get().getId();

    // request의 userId랑 수정하려는 userId 다르면 예외처리
    if(commentUserId==null || userId!=commentUserId){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("the comment is not found or you aren't the writer");
    }

    commentService.deleteById(commentId);

    return ResponseEntity.status(HttpStatus.OK)
        .body("delete completed");
  }

  // 댓글 좋아요
  @PostMapping("/{comment_id}/like")
  public ResponseEntity<?> likeComment(
      @PathVariable("comment_id") Long commentId,
      HttpServletRequest request
  ) {
    if (request.getAttribute("userId") == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
    }

    Long userId = Long.parseLong(request.getAttribute("userId").toString());
    commentService.likeComment(commentId, userId);
    return ResponseEntity.ok("댓글 좋아요 완료");
  }

  // 댓글 좋아요 취소
  @DeleteMapping("/{comment_id}/like")
  public ResponseEntity<?> unlikeComment(
      @PathVariable("comment_id") Long commentId,
      HttpServletRequest request
  ) {
    if (request.getAttribute("userId") == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
    }

    Long userId = Long.parseLong(request.getAttribute("userId").toString());
    commentService.unlikeComment(commentId, userId);
    return ResponseEntity.ok("댓글 좋아요 취소 완료");
  }


}
