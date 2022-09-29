package com.cya.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.MemberService;
import com.cya.exam.demo.vo.Article;
import com.cya.exam.demo.vo.Member;

@Controller
public class UsrMemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickName, String cellphoneNum,
			String email) {
		
		int id = memberService.join(loginId, loginPw, name, nickName, cellphoneNum, email);
		
		if(id == -1) {
			return "중복된 아이디입니다.";
		}
		
		Member member = memberService.getMemberbyId(id);
		
		return member;
	}
}
