package org.example.be.global.presentationcode;

import lombok.RequiredArgsConstructor;
import org.example.be.article.domain.Article;
import org.example.be.article.repository.ArticleRepository;
import org.example.be.comment.domain.Comment;
import org.example.be.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

  ...

  // 1. 경쟁 조건 내에서의 동시성 문제
  public void increaseViewCount(Long articleId) {
    // 1. 게시글을 DB에서 조회
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new RuntimeException("Article not found"));

    // 2. 현재 조회수를 가져와 1 증가
    int currentViewCount = article.getViewCount();
    article.setViewCount(currentViewCount + 1);

    // 3. 다시 저장
    articleRepository.save(article);
  }


  // 2. 게시글과 댓글을 저장하는 메서드
  public void createArticleWithComment(Article article, Comment comment) {
    Article savedArticle = articleRepository.save(article);  // 1. 게시글 저장

    comment.setArticle(savedArticle);
    commentRepository.save(comment);                         // 2. 댓글 저장
    // 2번 과정에서 예외 발생하면 게시글은 저장되고, 댓글은 저장되지 않은 상태로 남게 됨
  }
}
