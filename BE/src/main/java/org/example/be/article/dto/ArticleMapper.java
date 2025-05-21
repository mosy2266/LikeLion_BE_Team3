package org.example.be.article.dto;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.comment.dto.CommentMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleMapper {

  private final CommentMapper commentMapper;

  public Article toEntity(ArticleRequest request){
    return Article.builder()
        .author(request.getAuthor())
        .title(request.getTitle())
        .content(request.getContent())
        .commentList(new ArrayList<>())
        .likeCount(0)
        .build();
  }

  public ArticleResponse toResponse(Article entity){
    return ArticleResponse.builder()
        .author(entity.getAuthor())
        .title(entity.getTitle())
        .content(entity.getContent())
        .likeCount(entity.getLikeCount())
        .commentList(entity.getCommentList().stream()
            .map(commentMapper::toResponse).toList())
        .build();
  }
}
