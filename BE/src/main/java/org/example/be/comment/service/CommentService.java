package org.example.be.comment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.be.comment.domain.Comment;
import org.example.be.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;


  public List<Comment> findAll() {
    return commentRepository.findAll();
  }

  public Comment findById(Long commentId) {
    return commentRepository.findById(commentId).orElse(null);
  }

  public Comment save(Comment request) {
    return commentRepository.save(request);
  }

  public void deleteById(Long commentId) {
    commentRepository.deleteById(commentId);
  }
}
