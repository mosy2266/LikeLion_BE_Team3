package org.example.be.like.controller;

import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.article.service.ArticleService;
import org.example.be.like.service.ArticleLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article_like")
public class ArticleLikeApiController {
  private final ArticleService articleService;

  private final ArticleLikeService articleLikeService;


  @GetMapping("/like/{article_id}")
  public ResponseEntity<?> likeArticle(@PathVariable Long article_id){

    articleLikeService.likeArticle(article_id);


    Article target = articleService.findById(article_id);
    target.setLikeCount(target.getLikeCount()+1);
    return ResponseEntity.status(HttpStatus.OK)
        .body(articleService.save(target));
  }
}
