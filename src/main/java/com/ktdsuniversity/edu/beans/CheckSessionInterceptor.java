package com.ktdsuniversity.edu.beans;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CheckSessionInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Controller가 실행되기 전 입니다.");
		System.out.println(handler.getClass().getName()+"가 실행됩니다.");
		System.out.println(request.getRequestURI());
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("Controller가 실행된 후 입니다.");
		System.out.println(handler.getClass().getName()+"가 실행되었습니다.");
		System.out.println(modelAndView+"를 반환했습니다.");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("view를 ㅂ만들어 브라우저에게 반환하기 전 입니다.");
		System.out.println(handler.getClass().getName()+"가 실행되었습니다.");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
