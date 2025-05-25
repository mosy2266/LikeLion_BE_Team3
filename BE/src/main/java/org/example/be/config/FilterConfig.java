package org.example.be.config;

import jakarta.servlet.Filter;
import org.example.be.jwt.JwtAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<Filter> jwtFilterRegistration(JwtAuthFilter jwtAuthFilter) {
    FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();

    registration.setFilter(jwtAuthFilter);
    registration.addUrlPatterns("/*"); // 해당 경로에만 필터 적용
    registration.setOrder(1); // 필터 실행 순서

    return registration;
  }
}
