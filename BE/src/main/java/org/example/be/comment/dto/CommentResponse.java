package org.example.be.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentResponse {
  private Long articleId;
  private String author;
  private String content;
  private int likeCount;
}
