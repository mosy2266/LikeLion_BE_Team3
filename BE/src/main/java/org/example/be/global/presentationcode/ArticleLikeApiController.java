package org.example.be.global.presentationcode;

import lombok.RequiredArgsConstructor;
import org.example.be.like.service.ArticleLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article_like")
public class ArticleLikeApiController {

  private final ArticleLikeService articleLikeService;

  @PostMapping("/articles/{articleId}")
  public ResponseEntity<ArticleLikeResponseDto> likeArticle(@PathVariable Long articleId) {
    ArticleLikeResponseDto responseDto = articleLikeService.likeArticle(articleId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(responseDto);
  }

}
