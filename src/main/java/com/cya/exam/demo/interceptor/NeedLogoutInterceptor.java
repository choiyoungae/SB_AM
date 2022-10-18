package com.cya.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cya.exam.demo.vo.ResultData;
import com.cya.exam.demo.vo.Rq;

@Component
public class NeedLogoutInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

		Rq rq = (Rq)req.getAttribute("rq");

		if(rq.isLogined()) {
			
			rq.printHistoryBackJs("로그아웃 후 이용해주세요.");
			
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}

}