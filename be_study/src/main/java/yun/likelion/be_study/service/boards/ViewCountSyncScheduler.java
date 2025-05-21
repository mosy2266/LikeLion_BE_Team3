package yun.likelion.be_study.service.boards;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import yun.likelion.be_study.repository.boards.BoardsRepository;

import java.util.Map;

@Component
public class ViewCountSyncScheduler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Long> hashOps;
    private final BoardsRepository boardsRepository;

    public ViewCountSyncScheduler(RedisTemplate<String, Object> redisTemplate,
                                  BoardsRepository boardsRepository) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
        this.boardsRepository = boardsRepository;
    }

    //1분마다 실행
    @Scheduled(fixedRateString = "${app.view-sync-interval-ms:60000}")
    @Transactional
    public void syncViewCounts() {
        System.out.println("!!!syncViewCounts Started!!!");

        /*
        //Redis Hash에서 모든 boardId -> 증가분 꺼내오기
        //ClassCastException -> Map<String, Object>로 꺼내기
        Map<Object, Object> counts = redisTemplate.opsForHash().entries("post:view_counts");
        if (counts.isEmpty()) {
            System.out.println("...반영 사항 X...");
            return; //반영할 게 없으면 종료
        }

        //Map<Object, Object>를 Map<Long, Long>으로 변환(ClassCassException을 피하기 위해)
        counts.forEach((key, value) -> {
            Long boardId;
            Long increment;
            try {
                boardId = Long.valueOf(key.toString());
                //value가 Number면 바로 뽑고 아니면 문자열 파싱
                if (value instanceof Number n) {
                    increment = n.longValue();
                } else {
                    increment = Long.parseLong(value.toString());
                }
            } catch (NumberFormatException e) {
                return; //키나 값이 파싱되지 않으면 무시
            }
            */

        //Redis에서 조회수 변경사항 가져 오기
        Map<String, Long> counts = hashOps.entries("post:view_counts");

        //반영할 게 없으면 종료
        if (counts.isEmpty()) {
            System.out.println("...반영 사항 X...");
            return;
        }

        //가져온 조회수 DB에 반영
        counts.forEach((boardId, inc) -> {
            Long postId = Long.valueOf(boardId);
            System.out.println("boardId: " + postId +"인 게시글의 증가된 조회수 반영");
            boardsRepository.incrementViewCount(postId, inc);
        });

        //반영이 끝나면 캐시 초기화
        redisTemplate.delete("post:view_counts");
        System.out.println("!!!Redis post:view_counts Initialized!!!");
    }
}
