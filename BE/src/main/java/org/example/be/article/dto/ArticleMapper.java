package org.example.be.article.dto;

import java.util.ArrayList;
import org.example.be.article.domain.Article;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
  public Article toEntity(ArticleRequest request){
    return Article.builder()
        .author(request.getAuthor())
        .title(request.getTitle())
        .content(request.getContent())
        .likeCount(0)
        .likeList(new ArrayList<>())
        .build();
  }

  public ArticleResponse toResponse(Article entity){
    return ArticleResponse.builder()
        .author(entity.getAuthor())
        .title(entity.getTitle())
        .content(entity.getContent())
        .likeCount(entity.getLikeCount())
        .build();
  }
}
