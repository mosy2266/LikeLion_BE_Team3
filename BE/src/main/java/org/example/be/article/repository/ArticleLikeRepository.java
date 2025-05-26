package org.example.be.article.repository;

import java.util.Optional;
import org.example.be.article.domain.Article;
import org.example.be.article.domain.ArticleLike;
import org.example.be.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
  Optional<ArticleLike> findByUserAndArticle(UserEntity user, Article article);
}
