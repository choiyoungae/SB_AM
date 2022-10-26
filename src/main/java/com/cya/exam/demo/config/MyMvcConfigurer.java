package com.cya.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
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
		
		InterceptorRegistration ir;
		
		ir = registry.addInterceptor(beforeActionInterceptor);
		ir.addPathPatterns("/**");
		ir.addPathPatterns("/favicon.ico");
		ir.excludePathPatterns("/resource/**");
		ir.excludePathPatterns("/error");
		
		ir = registry.addInterceptor(needLoginInterceptor);
		ir.addPathPatterns("/usr/article/write");
		ir.addPathPatterns("/usr/article/doWrite");
		ir.addPathPatterns("/usr/article/modify");
		ir.addPathPatterns("/usr/article/doModify");
		ir.addPathPatterns("/usr/article/doDelete");
		ir.addPathPatterns("/usr/member/doLogout");
		ir.addPathPatterns("/usr/reactionPoint/doGoodReaction");
		ir.addPathPatterns("/usr/reactionPoint/doBadReaction");
		
		ir = registry.addInterceptor(needLogoutInterceptor);
		ir.addPathPatterns("/usr/member/login");
	}

}
