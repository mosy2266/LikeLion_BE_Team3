package org.example.be.article.service;

import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be.article.repository.ArticleRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ViewCountScheduler {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ArticleRepository articleRepository;

  // 1분마다 실행되는 스케줄러
  // 1분마다 DB에 반영
  @Scheduled(fixedRate = 1 * 60 * 1000)
  @Transactional
  public void flushViewCountsToDB() {
    // Redis에 저장된 모든 게시글 조회수 키 가져오기
    Set<String> keys = redisTemplate.keys("article:view:count:*");

    if (keys == null){
      return;
    }

    for (String key : keys) {
      for(String each : keys){
        log.info("keys set: "+each);
      }
      // key 예시: article:view:count:1 → 게시글 ID 추출
      Long articleId = Long.valueOf(key.replace("article:view:count:", ""));
      Integer count = (Integer) redisTemplate.opsForValue().get(key);

      if (count != null && count > 0) {
        // DB에 조회수 누적 반영
        articleRepository.incrementViewCount(articleId, count);
        redisTemplate.delete(key); // 반영 후 캐시 삭제
      }
    }
  }
}

