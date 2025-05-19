package org.example.be.article.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.article.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;


  public Article findById(Long articleId) {
    return articleRepository.findById(articleId).orElse(null);
  }

  public List<Article> findAll(){
    return articleRepository.findAll();
  }

  public Page<Article> getArticlePages(int page, int size){
    Pageable pageable = PageRequest.of(page, size);
    return articleRepository.findAll(pageable);
  }

  public Article save(Article article) {
    return articleRepository.save(article);
  }

  public void deleteById(Long articleId) {
    articleRepository.deleteById(articleId);
  }
}
