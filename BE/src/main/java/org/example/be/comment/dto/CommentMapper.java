package org.example.be.comment.dto;

import java.util.ArrayList;
import org.example.be.comment.domain.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

  // toEntity() method는 주로 comment 새로 생성하는 작업에만 사용
  // article 필드는 service 계층에서 처리
  public Comment toEntity(CommentRequest request){
    return Comment.builder()
        .author(request.getAuthor())
        .content(request.getContent())
        .likeCount(0)
        .build();
  }

  public CommentResponse toResponse(Comment entity){
    return CommentResponse.builder()
        .articleId(entity.getArticle().getId())
        .author(entity.getAuthor())
        .content(entity.getContent())
        .likeCount(entity.getLikeCount())
        .build();
  }
}
