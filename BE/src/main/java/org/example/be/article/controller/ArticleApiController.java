package org.example.be.article.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.article.dto.ArticleMapper;
import org.example.be.article.dto.ArticleRequest;
import org.example.be.article.service.ArticleService;
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
  private final ArticleMapper articleMapper;

  // 게시글 생성
  @PostMapping("/post")
  public ResponseEntity<?> createArticle(
      @RequestBody Article request
  ) {
    Article saved = articleService.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  // 단일 게시글 조회
  @GetMapping("/{article_id}")
  public ResponseEntity<?> getArticle(
      @PathVariable(name = "article_id") Long articleId
  ) {
    Article article = articleService.findById(articleId);
    if (article == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("article not found");
    }
    return ResponseEntity.status(HttpStatus.OK).body(article);
  }

  // 게시글 목록 조회
  @GetMapping("/list")
  public ResponseEntity<?> getArticleList(){
    List<Article> result = articleService.findAll();

    return ResponseEntity.status(HttpStatus.OK)
        .body(result);
  }

  // 게시글 업데이트
  @PatchMapping("/{article_id}")
  public ResponseEntity<?> updateArticle(
      @PathVariable(name = "article_id") Long articleId,
      @RequestBody Article request
  ) {
    Article target = articleService.findById(articleId);
    if (target == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("article not found");
    }

    if (request.getTitle() != null) target.setTitle(request.getTitle());
    if (request.getContent() != null) target.setContent(request.getContent());
    if (request.getAuthor() != null) target.setAuthor(request.getAuthor());

    Article updated = articleService.save(target);
    return ResponseEntity.status(HttpStatus.OK).body(updated);
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

  // 게시글 좋아요
  @GetMapping("/like/{article_id}")
  public ResponseEntity<?> likeArticle(@PathVariable Long article_id){
    Article target = articleService.findById(article_id);
    if (target == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("article not found");
    }

    target.setLikeCount(target.getLikeCount()+1);

    return ResponseEntity.status(HttpStatus.OK)
        .body(target);
  }

  // 게시글 좋아요 수 조회
  @GetMapping("/{article_id}/likes")
  public ResponseEntity<?> getArticleLikeCount(
      @PathVariable(name = "article_id") Long articleId
  ) {
    Article article = articleService.findById(articleId);
    if (article == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("article not found");
    }
    return ResponseEntity.status(HttpStatus.OK).body(article.getLikeCount());
  }

  // 게시글에 달린 댓글 목록 조회
  @GetMapping("/{article_id}/comments")
  public ResponseEntity<?> getCommentsOfArticle(
      @PathVariable(name = "article_id") Long articleId
  ) {
    Article article = articleService.findById(articleId);
    if (article == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("article not found");
    }
    return ResponseEntity.status(HttpStatus.OK).body(article.getCommentList());
  }

}
