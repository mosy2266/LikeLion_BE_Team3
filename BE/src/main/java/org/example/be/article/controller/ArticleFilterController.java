package org.example.be.article.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.article.dto.ArticleRequest;
import org.example.be.article.repository.ArticleRepository;
import org.example.be.article.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filter/article")
@RequiredArgsConstructor
public class ArticleFilterController {

  private final ArticleService articleService;
  private final ArticleRepository articleRepository;


  // 단일 게시글 조회(테스트용)
  @GetMapping("/{article_id}")
  public ResponseEntity<?> getArticle(
      @PathVariable(name = "article_id") Long articleId,
      HttpServletRequest request
  ) {

    if(request.getAttribute("userId")==null){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body("Unauthorized");
    }

    Long userId = Long.parseLong(request.getAttribute("userId").toString());

    articleService.incrementViewCount(articleId, userId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(articleService.findById(articleId));
  }

  // 게시글 수정
  @PatchMapping("/{article_id}")
  public ResponseEntity<?> updateArticle(
      @PathVariable(name = "article_id") Long articleId,
      @RequestBody ArticleRequest request,
      HttpServletRequest httpRequest
  ) {
    if(httpRequest.getAttribute("userId")==null){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body("Unauthorized");
    }

    Long userId = Long.parseLong(httpRequest.getAttribute("userId").toString());
    Long articleUserId = articleRepository.findById(articleId).get().getUser().getId();

    if(!userId.equals(articleUserId)){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("you aren't the writer");
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(articleService.update(articleId, request));
  }

  // 게시글 삭제
  @DeleteMapping("/{article_id}")
  public ResponseEntity<?> deleteArticle(
      @PathVariable(name = "article_id") Long articleId,
      HttpServletRequest httpRequest
  ) {
    if(httpRequest.getAttribute("userId")==null){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body("Unauthorized");
    }

    Long userId = Long.parseLong(httpRequest.getAttribute("userId").toString());
    Long articleUserId = articleRepository.findById(articleId).get().getUser().getId();

    if(!userId.equals(articleUserId)){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("you aren't the writer");
    }

    articleService.deleteById(articleId);

    return ResponseEntity.status(HttpStatus.OK)
        .body("delete completed");
  }

}
