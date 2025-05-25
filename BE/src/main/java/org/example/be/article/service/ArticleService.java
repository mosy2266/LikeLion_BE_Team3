package org.example.be.article.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be.article.domain.Article;
import org.example.be.article.dto.ArticleMapper;
import org.example.be.article.dto.ArticleRequest;
import org.example.be.article.dto.ArticleResponse;
import org.example.be.article.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final ArticleMapper articleMapper;
  private final RedisTemplate<String, Object> redisTemplate;

  public ArticleResponse findById(Long articleId) {
    return articleMapper.toResponse(articleRepository.findById(articleId)
        .orElseThrow(() -> new RuntimeException("the article is not found")));
  }

  public Page<ArticleResponse> getArticlePages(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return articleRepository.findAll(pageable)
        .map(articleMapper::toResponse);
  }

  public List<ArticleResponse> getArticleListForAdmin(){
    return articleRepository.findAll().stream()
        .map(articleMapper::toResponse)
        .toList();
  }

  public ArticleResponse create(ArticleRequest request) {
    Article article = articleMapper.toEntity(request);
    return articleMapper.toResponse(articleRepository.save(article));
  }

  public ArticleResponse update(Long articleId, ArticleRequest request) {
    Article article = articleRepository.findById(articleId).orElseThrow(
        () -> new RuntimeException("the article is not found")
    );

    if (request.getTitle() != null) {
      article.setTitle(request.getTitle());
    }
    if (request.getContent() != null) {
      article.setContent(request.getContent());
    }
    if (request.getAuthor() != null) {
      article.setAuthor(request.getAuthor());
    }

    return articleMapper.toResponse(articleRepository.save(article));
  }

  public void deleteById(Long articleId) {
    articleRepository.deleteById(articleId);
  }

  // 좋아요 누르기
  public ArticleResponse likeArticle(Long articleId){
    Article article = articleRepository.findById(articleId)
        .orElseThrow(()-> new RuntimeException("the article is not found"));

    article.setLikeCount(article.getLikeCount()+1);
    return articleMapper.toResponse(articleRepository.save(article));
  }

  // 좋아요 취소
  public ArticleResponse unlikeArticle(Long articleId){
    Article article = articleRepository.findById(articleId)
        .orElseThrow(()-> new RuntimeException("the article is not found"));

    if(article.getLikeCount()>0)
      article.setLikeCount(article.getLikeCount()-1);
    return articleMapper.toResponse(articleRepository.save(article));
  }

  // 게시글 조회 시 조회수 증가
  public void incrementViewCount(Long articleId, Long userId){
    String userViewKey = "view:" + userId + ":article:" + articleId;

    Boolean isViewed = redisTemplate.hasKey(userViewKey);

    if(!isViewed){
      String articleViewKey = "article:view:count:"+articleId;
      redisTemplate.opsForValue().increment(articleViewKey);
      // 시간 간격 1시간으로 설정
      redisTemplate.opsForValue().set(userViewKey, true, 1, TimeUnit.HOURS);
    }
  }
}
