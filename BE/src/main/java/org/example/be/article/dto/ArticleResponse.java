package org.example.be.article.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.be.comment.domain.Comment;
import org.example.be.comment.dto.CommentResponse;

@Builder
@Getter
@Setter
public class ArticleResponse {
  private String author;
  private String title;
  private String content;
  private int likeCount;
  private List<CommentResponse> commentList;
  private int viewCount;
}
