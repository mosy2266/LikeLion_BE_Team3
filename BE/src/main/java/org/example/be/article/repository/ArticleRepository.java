package org.example.be.article.repository;

import org.example.be.article.domain.Article;
import org.example.be.article.dto.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  Page<Article> findAll(Pageable pageable);
}
