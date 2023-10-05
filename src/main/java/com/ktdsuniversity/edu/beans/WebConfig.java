package com.ktdsuniversity.edu.beans;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration// 인터셉터를 등록하기 위한 애노테이션. Spring Boot Web에 관한 설정 
@Configurable // Spring Boot 설정을 해주는 클래스로 변경하겠다.
@EnableWebMvc // WebMVC Module 을 사용하겠다. (Validator 사용하기 위해서)
public class WebConfig implements WebMvcConfigurer {

	/**
	 * 인터셉터를 등록한다. 
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//인터셉터 등록 
		registry.addInterceptor(new CheckSessionInterceptor())
				//CheckSessionInterceptor가 모든 URL을 대상으로 
				//요청과 응답을 가로채도록 한다.  /**는 모든 URL이라는 뜻 
				.addPathPatterns("/**");
	}
	
}
