package yun.likelion.be_study.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories //Spring Data Redis의 레포지토리 기능(RedisRepository 등)을 활성화
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    //redis 서버 연결 설정
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    //redis 명령(연산)을 보낼 때 사용할 템플릿
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);


        //Key와 Hash key는 String
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //Value와 Hash value는 Long
        GenericToStringSerializer<Long> genericToStringSerializer = new GenericToStringSerializer<>(Long.class);
        redisTemplate.setValueSerializer(genericToStringSerializer);
        redisTemplate.setHashValueSerializer(genericToStringSerializer);

        //프로퍼티 설정 이후 내부 초기화 작업
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
