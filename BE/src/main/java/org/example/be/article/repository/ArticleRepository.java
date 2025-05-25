package org.example.be.article.repository;

import org.example.be.article.domain.Article;
import org.example.be.article.dto.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  Page<Article> findAll(Pageable pageable);

  @Modifying
  @Query("UPDATE Article SET viewCount = viewCount + :count WHERE id = :id")
  void incrementViewCount(@Param("id") Long articleId, @Param("count") int count);
}
