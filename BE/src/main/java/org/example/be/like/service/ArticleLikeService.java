package org.example.be.like.service;

import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.article.repository.ArticleRepository;
import org.example.be.article.service.ArticleService;
import org.example.be.like.repository.ArticleLikeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

  private final ArticleLikeRepository articleLikeRepository;
  private final ArticleRepository articleRepository;

  public void likeArticle(Long articleId) {
    Article target = articleRepository.findById(articleId).orElse(null);
    if(target==null){

    }

  }
}
