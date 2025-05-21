package yun.likelion.be_study.service.boards;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ViewCountService {

    //ip 저장 TTL(Time to leave) : 24시간
    private static final Duration IP_TTL = Duration.ofHours(24);
    //조회수 누적용 Hash key 이름
    private static final String VIEW_COUNT_HASH = "post:view_counts";

    private final RedisTemplate<String, Object> redisTemplate;
    private final SetOperations<String, Object> setOps; //ip는 중복되면 안 되므로 Set을 사용해서 저장
    private final HashOperations<String, String, Long> hashOps;

    public ViewCountService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.setOps = redisTemplate.opsForSet();
        this.hashOps = redisTemplate.opsForHash();
    }

    //글 조회 처리
    public boolean recordView(Long boardId, String userIp) {

        //ip 중복 확인을 위한 Redis Set key 생성
        String ipKey = "post:" + boardId + ":viewed_ips";

        //해당 ip가 set에 없으면 추가(1 return), 이미 있으면 0 return
        Long added = setOps.add(ipKey, userIp);

        //새로 추가된 ip(added가 1)면
        if (added != null && added == 1) {
            //Set에 TTL 미설정 상태라면 TTL을 설정
            if (redisTemplate.getExpire(ipKey) == -1) {
                redisTemplate.expire(ipKey, IP_TTL);
            }
            //Hash에 boardId 필드값(=조회수) 1 증가
            hashOps.increment(VIEW_COUNT_HASH, boardId.toString(), 1L);
            return true;
        }

        //중복 조회(added가 0)면 아무 동작 X
        return false;
    }
}
