package org.example.be.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CommentRequest {
  private Long articleId;
  private String author;
  private String content;
}
