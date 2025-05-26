package org.example.be.article.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be.article.domain.Article;
import org.example.be.article.domain.ArticleLike;
import org.example.be.article.dto.ArticleMapper;
import org.example.be.article.dto.ArticleRequest;
import org.example.be.article.dto.ArticleResponse;
import org.example.be.article.repository.ArticleLikeRepository;
import org.example.be.article.repository.ArticleRepository;
import org.example.be.user.domain.UserEntity;
import org.example.be.user.repository.UserEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final ArticleMapper articleMapper;
  private final RedisTemplate<String, Object> redisTemplate;

  private final ArticleLikeRepository articleLikeRepository;
  private final UserEntityRepository userRepository;

  // 단일 게시글 조회
  public ArticleResponse findById(Long articleId) {
    return articleMapper.toResponse(articleRepository.findById(articleId)
        .orElseThrow(() -> new RuntimeException("the article is not found")));
  }

  // 게시글 목록 조회(paging)
  public Page<ArticleResponse> getArticlePages(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return articleRepository.findAll(pageable)
        .map(articleMapper::toResponse);
  }

  // admin용 게시글 목록 조회
  public List<ArticleResponse> getArticleListForAdmin() {
    return articleRepository.findAll().stream()
        .map(articleMapper::toResponse)
        .toList();
  }

  // 게시글 생성
  public ArticleResponse create(ArticleRequest request) {
    Article article = articleMapper.toEntity(request);
    return articleMapper.toResponse(articleRepository.save(article));
  }

  // 게시글 수정
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

  //게시글 삭제
  public void deleteById(Long articleId) {
    articleRepository.deleteById(articleId);
  }


  // 게시글 조회 시 조회수 증가
  public void incrementViewCount(Long articleId, Long userId) {
    String userViewKey = "view:" + userId + ":article:" + articleId;

    Boolean isViewed = redisTemplate.hasKey(userViewKey);

    if (!isViewed) {
      String articleViewKey = "article:view:count:" + articleId;
      redisTemplate.opsForValue().increment(articleViewKey);
      // 시간 간격 1시간으로 설정
      redisTemplate.opsForValue().set(userViewKey, true, 1, TimeUnit.HOURS);
    }
  }


  // 좋아요 누르기(new)
  public void likeArticle(Long articleId, Long userId) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

    // 중복 좋아요 확인
    Optional<ArticleLike> existingLike = articleLikeRepository.findByUserAndArticle(user, article);
    if (existingLike.isPresent()) {
      throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
    }

    ArticleLike articleLike = ArticleLike.builder()
        .user(user)
        .article(article)
        .build();
    article.setLikeCount(article.getLikeCount() + 1);
    articleLikeRepository.save(articleLike);
  }

  // 좋아요 누르기 취소(new)
  public void unlikeArticle(Long articleId, Long userId) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

    ArticleLike existingLike = articleLikeRepository.findByUserAndArticle(user, article)
        .orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다"));

    if(article.getLikeCount()>0)
        article.setLikeCount(article.getLikeCount()-1);

    articleLikeRepository.delete(existingLike);
  }


}
