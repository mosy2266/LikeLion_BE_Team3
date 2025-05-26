package org.example.be.comment.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.comment.domain.Comment;
import org.example.be.comment.dto.CommentMapper;
import org.example.be.comment.dto.CommentRequest;
import org.example.be.comment.dto.CommentResponse;
import org.example.be.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentApiController {
  private final CommentService commentService;

  // 모든 댓글 목록 조회
  @GetMapping("/all")
  public ResponseEntity<?> getAllComments() {
    List<CommentResponse> responses = commentService.findAll();

    return ResponseEntity.status(HttpStatus.OK)
        .body(responses);
  }

  // 특정 댓글 조회
  @GetMapping("/{comment_id}")
  public ResponseEntity<?> getComment(
      @PathVariable(name = "comment_id") Long commentId
  ) {
    CommentResponse response = commentService.findById(commentId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  // 댓글 생성
  @PostMapping("/post/{article_id}")
  public ResponseEntity<?> createComment(
      @PathVariable(name="article_id") Long articleId,
      @RequestBody CommentRequest request
  ) {
    CommentResponse response = commentService.create(articleId, request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(response);
  }

  // 댓글 수정
  @PatchMapping("/{comment_id}")
  public ResponseEntity<?> updateComment(
      @PathVariable(name = "comment_id") Long commentId,
      @RequestBody CommentRequest request
  ) {
    CommentResponse response = commentService.update(commentId, request);

    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  // 댓글 삭제
  @DeleteMapping("/{comment_id}")
  public ResponseEntity<?> deleteComment(
      @PathVariable(name = "comment_id") Long commentId
  ) {
    commentService.deleteById(commentId);

    return ResponseEntity.status(HttpStatus.OK)
        .body("delete completed");
  }

}
