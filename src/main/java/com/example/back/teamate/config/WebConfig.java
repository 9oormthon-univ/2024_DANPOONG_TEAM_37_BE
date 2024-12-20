package com.example.back.teamate.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
			.allowedOriginPatterns("*") // 모든 출처 허용 (allowedOriginPatterns로 변경)
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 모든 HTTP 메서드 허용
			.allowedHeaders("*") // 모든 헤더 허용
			.allowCredentials(true) // 쿠키 인증 허용
			.maxAge(3600); // preflight 요청 캐싱 시간 (초)
	}
}