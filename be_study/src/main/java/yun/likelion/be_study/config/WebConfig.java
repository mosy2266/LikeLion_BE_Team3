package yun.likelion.be_study.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yun.likelion.be_study.filter.LogFilter;
import yun.likelion.be_study.filter.LoginCheckFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    //FilterRegistrationBean : 스프링부트 사용 시 필터 등록을 위한 클래스
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); //등록할 필터 지정
        filterRegistrationBean.setOrder(1); //필터 우선순위(순서) 지정
        filterRegistrationBean.addUrlPatterns("/*"); //필터에 적용할 URL 패턴 지정

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
