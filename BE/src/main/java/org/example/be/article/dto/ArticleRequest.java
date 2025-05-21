package org.example.be.article.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Setter
@Getter
public class ArticleRequest {
  private String author;
  private String title;
  private String content;
}
