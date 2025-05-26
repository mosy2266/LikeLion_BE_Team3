package org.example.be.comment.service;

import static java.util.stream.Collectors.toList;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.article.domain.ArticleLike;
import org.example.be.article.repository.ArticleRepository;
import org.example.be.comment.domain.Comment;
import org.example.be.comment.domain.CommentLike;
import org.example.be.comment.dto.CommentMapper;
import org.example.be.comment.dto.CommentRequest;
import org.example.be.comment.dto.CommentResponse;
import org.example.be.comment.repository.CommentLikeRepository;
import org.example.be.comment.repository.CommentRepository;
import org.example.be.user.domain.UserEntity;
import org.example.be.user.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;
  private final ArticleRepository articleRepository;

  private final UserEntityRepository userRepository;
  private final CommentLikeRepository commentLikeRepository;


  // 댓글 목록 조회
  public List<CommentResponse> findAll() {
    return commentRepository.findAll().stream()
        .map(commentMapper::toResponse)
        .toList();
  }

  // 단일 댓글 조회
  public CommentResponse findById(Long commentId) {
    return commentMapper.toResponse(commentRepository.findById(commentId)
        .orElseThrow(()-> new RuntimeException("the comment is not found")));
  }

  // 댓글 생성
  public CommentResponse create(Long articleId, CommentRequest request) {
    Comment comment = commentMapper.toEntity(request);
    Article article = articleRepository.findById(articleId)
        .orElseThrow(()-> new RuntimeException("the article is not found"));

    comment.setArticle(article);
    return commentMapper.toResponse(commentRepository.save(comment));
  }

  // 댓글 수정
  public CommentResponse update(Long commentId, CommentRequest request){
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(()->new RuntimeException("the comment is not found"));

    if (request.getAuthor() != null) comment.setAuthor(request.getAuthor());
    if (request.getContent() != null) comment.setContent(request.getContent());

    return commentMapper.toResponse(commentRepository.save(comment));
  }

  // 댓글 삭제
  public void deleteById(Long commentId) {
    commentRepository.deleteById(commentId);
  }


  // 좋아요 누르기(new)
  public void likeComment(Long commentId, Long userId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

    Optional<CommentLike> existing = commentLikeRepository.findByUserAndComment(user, comment);
    if (existing.isPresent()) {
      throw new IllegalStateException("이미 좋아요를 누른 댓글입니다.");
    }

    CommentLike like = CommentLike.builder()
        .user(user)
        .comment(comment)
        .build();

    comment.setLikeCount(comment.getLikeCount()+1);
    commentLikeRepository.save(like);
  }


  // 좋아요 누르기 취소(new)
  public void unlikeComment(Long commentId, Long userId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

    CommentLike like = commentLikeRepository.findByUserAndComment(user, comment)
        .orElseThrow(() -> new IllegalStateException("좋아요를 누르지 않은 댓글입니다."));

    if(comment.getLikeCount()>0)
      comment.setLikeCount(comment.getLikeCount()-1);
    commentLikeRepository.delete(like);
  }

}
