package org.example.be.comment.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.article.repository.ArticleRepository;
import org.example.be.comment.domain.Comment;
import org.example.be.comment.dto.CommentMapper;
import org.example.be.comment.dto.CommentRequest;
import org.example.be.comment.dto.CommentResponse;
import org.example.be.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;
  private final ArticleRepository articleRepository;


  public List<CommentResponse> findAll() {
    return commentRepository.findAll().stream()
        .map(commentMapper::toResponse)
        .toList();
  }

  public CommentResponse findById(Long commentId) {
    return commentMapper.toResponse(commentRepository.findById(commentId)
        .orElseThrow(()-> new RuntimeException("the comment is not found")));
  }

  public CommentResponse create(Long articleId, CommentRequest request) {
    Comment comment = commentMapper.toEntity(request);
    Article article = articleRepository.findById(articleId)
        .orElseThrow(()-> new RuntimeException("the article is not found"));

    comment.setArticle(article);
    return commentMapper.toResponse(commentRepository.save(comment));
  }

  public CommentResponse update(Long commentId, CommentRequest request){
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(()->new RuntimeException("the comment is not found"));

    if (request.getAuthor() != null) comment.setAuthor(request.getAuthor());
    if (request.getContent() != null) comment.setContent(request.getContent());

    return commentMapper.toResponse(commentRepository.save(comment));
  }

  public void deleteById(Long commentId) {
    commentRepository.deleteById(commentId);
  }
}
