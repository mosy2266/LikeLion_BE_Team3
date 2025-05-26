package org.example.be.comment.repository;

import java.util.Optional;
import org.example.be.article.domain.Article;
import org.example.be.article.domain.ArticleLike;
import org.example.be.comment.domain.Comment;
import org.example.be.comment.domain.CommentLike;
import org.example.be.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
  Optional<CommentLike> findByUserAndComment(UserEntity user, Comment comment);
}
