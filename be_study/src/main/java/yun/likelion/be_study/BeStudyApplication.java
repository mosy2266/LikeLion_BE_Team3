package yun.likelion.be_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@EnableScheduling //스케줄링 기능 활성화를 위한 애너테이션
@EnableJpaAuditing //Jpa Auditing 기능 활성화
public class BeStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeStudyApplication.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
