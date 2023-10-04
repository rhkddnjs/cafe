package com.ktdsuniversity.edu.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Spring 이 직접 Bean 으로 만드는 @Component Annotation 이 적용된
 * 	Class 와 함께 수동으로 Bean 을 만들도록 하는 클래스.
 */
@SpringBootConfiguration
public class CustomBeanInitalizer {
	
	/**
	 * @Value 환경설정파일(application.yml) 에서
	 * 설정 값을 찾아, 반환하는 Annotation 이다.
	 * 문법: ${key.key.key.....:기본값}
	 */ 
	@Value("${app.multipart.base-dir:C:/uploadFiles}")
	private String baseDir;
	
	@Value("${app.multipart.obfuscation.enable:false}")
	private boolean enableObfuscation;
	
	@Value("${app.multipart.obfuscation.hide-ext.enable:false}")
	private boolean enableObfuscationHideExt;
	
	/**
	 * @Bean Annotation 은 수동으로 Bean 을 생성해
	 * Bean Container 에 적재하도록 해준다.
	 * 
	 * Method 의 반환타입이 Bean 의 타입이 되고
	 * Method 이름이 Bean 의 이름이 됩니다.
	 * 
	 * 이 메소드는 받드시 반환이 되어야 합니다.
	 * @return
	 */
	@Bean
	public FileHandler fileHandler() {
		FileHandler fileHandler = new FileHandler();
		fileHandler.setBaseDir(baseDir);
		fileHandler.setEnableObfuscation(enableObfuscation);
		fileHandler.setEnableObfuscationHideExt(enableObfuscationHideExt);
		return fileHandler;
	}
	
	@Bean
	public SHA sha() {
		return new SHA();
	}
}
