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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleApiController {

  private final ArticleService articleService;

  // 게시글 생성
  @PostMapping("/post")
  public ResponseEntity<?> createArticle(
      @RequestBody ArticleRequest request
  ) {
    ArticleResponse saved = articleService.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  // 단일 게시글 조회
  @GetMapping("/{article_id}")
  public ResponseEntity<?> getArticle(
      @PathVariable(name = "article_id") Long article_id
  ) {
    ArticleResponse response = articleService.findById(article_id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // 게시글 목록 조회
  @GetMapping("/list")
  public ResponseEntity<?> getArticleList(){
    List<ArticleResponse> result = articleService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
        .body(result);
  }

  // paging
  // page, size는 query param로 받아오는 게 좋을 듯
  @GetMapping("/list/{page}/{size}")
  public ResponseEntity<?> getArticlePage(
      @PathVariable int page,
      @PathVariable int size
  ){
    Page<ArticleResponse> result = articleService.getArticlePages(page, size);
    return ResponseEntity.status(HttpStatus.OK)
        .body(result);
  }

  // 게시글 업데이트
  @PatchMapping("/{article_id}")
  public ResponseEntity<?> updateArticle(
      @PathVariable(name = "article_id") Long articleId,
      @RequestBody ArticleRequest request
  ) {
    ArticleResponse response = articleService.update(articleId, request);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  // 게시글 삭제
  @DeleteMapping("/{article_id}")
  public ResponseEntity<?> deleteArticle(
      @PathVariable(name = "article_id") Long articleId
  ) {
    if (articleService.findById(articleId) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("article not found");
    }
    articleService.deleteById(articleId);
    return ResponseEntity.status(HttpStatus.OK).body("delete completed");
  }

}
