package yun.likelion.be_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //스케줄링 기능 활성화를 위한 애너테이션
public class BeStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeStudyApplication.class, args);
    }

}
