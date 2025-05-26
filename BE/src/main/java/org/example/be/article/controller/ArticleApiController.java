package org.example.be.article.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.article.dto.ArticleMapper;
import org.example.be.article.dto.ArticleRequest;
import org.example.be.article.dto.ArticleResponse;
import org.example.be.article.service.ArticleService;
import org.example.be.comment.domain.Comment;
import org.example.be.comment.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleApiController {

  private final ArticleService articleService;

  // 단일 게시글 조회
  @GetMapping("/{article_id}")
  public ResponseEntity<?> getArticle(
      @PathVariable(name = "article_id") Long article_id,
      @RequestParam(name = "user_id") Long userId
  ) {

    articleService.incrementViewCount(article_id, userId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(articleService.findById(article_id));
  }

  // 게시글 목록 조회
  @GetMapping("/list")
  public ResponseEntity<?> getArticlePage(
      @RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size
  ){
    return ResponseEntity.status(HttpStatus.OK)
        .body(articleService.getArticlePages(page, size));
  }

  @GetMapping("/list/admin")
  public ResponseEntity<?> getArticleListForAdmin(){
    List<ArticleResponse> result = articleService.getArticleListForAdmin();
    return ResponseEntity.status(HttpStatus.OK)
        .body(result);
  }

  // 게시글 생성
  @PostMapping("/post")
  public ResponseEntity<?> createArticle(
      @RequestBody ArticleRequest request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(articleService.create(request));
  }

  // 게시글 수정
  @PatchMapping("/{article_id}")
  public ResponseEntity<?> updateArticle(
      @PathVariable(name = "article_id") Long articleId,
      @RequestBody ArticleRequest request
  ) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(articleService.update(articleId, request));
  }

  // 게시글 삭제
  @DeleteMapping("/{article_id}")
  public ResponseEntity<?> deleteArticle(
      @PathVariable(name = "article_id") Long articleId
  ) {
    articleService.deleteById(articleId);
    return ResponseEntity.status(HttpStatus.OK)
        .body("delete completed");
  }

}
