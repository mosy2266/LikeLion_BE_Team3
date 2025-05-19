package org.example.be.comment.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.be.comment.domain.Comment;
import org.example.be.comment.dto.CommentMapper;
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
  private final CommentMapper commentMapper;

  // 게시글별 댓글 조회/댓글 작성은 ArticleApi에서 담당
  // CommentApiController에서는 기본적인 댓글 CRUD만 담당

  // 모든 댓글 목록 조회
  @GetMapping("/all")
  public ResponseEntity<?> getAllComments() {
    List<Comment> commentList = commentService.findAll();

    return ResponseEntity.status(HttpStatus.OK).body(commentList);
  }

  // 특정 댓글 조회
  @GetMapping("/{comment_id}")
  public ResponseEntity<?> getComment(
      @PathVariable(name = "comment_id") Long commentId
  ) {
    Comment comment = commentService.findById(commentId);
    if (comment == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comment not found");
    }
    return ResponseEntity.status(HttpStatus.OK).body(comment);
  }

  // 댓글 생성
  // request에 article_id까지 담겨있다고 가정
  @PostMapping("/post")
  public ResponseEntity<?> createComment(
      @RequestBody Comment request
  ) {
    Comment saved = commentService.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  // 댓글 수정
  @PatchMapping("/{comment_id}")
  public ResponseEntity<?> updateComment(
      @PathVariable(name = "comment_id") Long commentId,
      @RequestBody Comment request
  ) {
    Comment target = commentService.findById(commentId);
    if (target == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comment not found");
    }

    if (request.getContent() != null) target.setContent(request.getContent());
    if (request.getAuthor() != null) target.setAuthor(request.getAuthor());

    Comment updated = commentService.save(target);
    return ResponseEntity.status(HttpStatus.OK).body(updated);
  }

  // 댓글 삭제
  @DeleteMapping("/{comment_id}")
  public ResponseEntity<?> deleteComment(
      @PathVariable(name = "comment_id") Long commentId
  ) {
    if (commentService.findById(commentId) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comment not found");
    }
    commentService.deleteById(commentId);
    return ResponseEntity.status(HttpStatus.OK).body("delete completed");
  }

  // 댓글 좋아요 수 조회
  @GetMapping("/{comment_id}/likes")
  public ResponseEntity<?> getCommentLikeCount(
      @PathVariable(name = "comment_id") Long commentId
  ) {
    Comment comment = commentService.findById(commentId);
    if (comment == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("comment not found");
    }
    return ResponseEntity.status(HttpStatus.OK).body(comment.getLikeCount());
  }

}
