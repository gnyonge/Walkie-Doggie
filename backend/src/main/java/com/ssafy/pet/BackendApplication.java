package com.ssafy.pet;

import java.util.Arrays;

import com.ssafy.pet.util.JWTInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication implements WebMvcConfigurer {

	@Autowired
	public JWTInterceptor jwtInterceptor;
	
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	// JWTInterceptor를 설치한다. 경로 조건을 설정한다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**") // 기본 적용 경로
				.excludePathPatterns("/")
				.excludePathPatterns(Arrays.asList("/user/confirm/**"))// 회원 관련 인터셉터 제외
				.excludePathPatterns(Arrays.asList("/login/**"))
				// 스웨거도 인터셉터 제외
				.excludePathPatterns("/pet/swagger-resources/**").excludePathPatterns("/pet/swagger-ui.html/")
				.excludePathPatterns("/pet/v2/api-docs?group=V1/**").excludePathPatterns("/pet/webjars/**").
				excludePathPatterns(
                    "/v2/api-docs",
                    "/swagger-resources/**",
                    "/swagger-ui.html",
                    "/webjars/**"
            );
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*").maxAge(3600)
				.exposedHeaders("doggie_token");
	}
}
