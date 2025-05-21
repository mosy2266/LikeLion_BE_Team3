package org.example.be.article.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.be.comment.domain.Comment;
import org.example.be.like.domain.ArticleLike;

@Builder
@Getter
@Setter
public class ArticleResponse {
  private String author;
  private String title;
  private String content;
  private int likeCount;
  private List<Comment> commentList;
  private List<ArticleLike> likeList;
}
