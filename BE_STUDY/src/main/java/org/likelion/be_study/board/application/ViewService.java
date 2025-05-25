package org.likelion.be_study.board.application;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.likelion.be_study.board.domain.Board;
import org.likelion.be_study.board.persistence.BoardRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ViewService {
    private final RedisTemplate<String, String> redisTemplate;
    private final BoardRepository boardRepository;

    public void handleViewCnt(Long boardId, Long memberId){
        String uniqueKey = String.format("viewed_by_member:%d:on_board: %d", memberId, boardId);
        String viewCountKey = "viewCnt:board" + boardId;

        if (redisTemplate.hasKey(uniqueKey)) {
            return;
        }

        redisTemplate.opsForValue().set(uniqueKey, "1", Duration.ofMinutes(1));
        redisTemplate.opsForValue().increment(viewCountKey);
    }

    @Scheduled(fixedRate = 20000) // 20초 마다 실행
    @Transactional
    public void flushViewCount(){
        String pattern = "viewCnt:board*";
        redisTemplate.keys(pattern).forEach(key -> {
            Long boardId = Long.parseLong(key.replace("viewCnt:board", ""));
            String countStr = redisTemplate.opsForValue().get(key);
            if (countStr != null) {
                redisTemplate.delete(key);
            }

            int viewCountInt = Integer.parseInt(countStr == null ? "0" : countStr);

            increaseViewCount(boardId, viewCountInt);
        });
    }

    @Transactional
    public void increaseViewCount(Long boardId, int count){
        Board board = getBoard(boardId);
        board.addViewCount(count);
    }

    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }
}
