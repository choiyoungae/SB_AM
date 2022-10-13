package com.cya.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cya.exam.demo.interceptor.BeforeActionInterceptor;
import com.cya.exam.demo.interceptor.NeedLoginInterceptor;
import com.cya.exam.demo.interceptor.NeedLogoutInterceptor;

@Configuration
public class MyMvcConfigurer implements WebMvcConfigurer {
	
	// BeforeActionInterceptor 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;
	// NeedLoginInterceptor 불러오기
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;
	// NeedLogoutInterceptor 불러오기
	@Autowired
	NeedLogoutInterceptor needLogoutInterceptor;
	
	// 인터셉터 적용
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**")
		.excludePathPatterns("/error");
		
		registry.addInterceptor(needLoginInterceptor).addPathPatterns("/usr/article/write")
		.addPathPatterns("/usr/article/doAdd").addPathPatterns("/usr/article/modify")
		.addPathPatterns("/usr/article/doModify").addPathPatterns("/usr/article/doDelete")
		.addPathPatterns("/usr/member/doLogout");
		
		registry.addInterceptor(needLogoutInterceptor).addPathPatterns("/usr/member/login");
	}

}
