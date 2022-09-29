package com.cya.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.MemberService;
import com.cya.exam.demo.util.Ut;
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
		
		if(Ut.isEmpty(loginId)) {
			return "아이디를 입력해주세요.";
		}
		if(Ut.isEmpty(loginPw)) {
			return "바밀번호를 입력해주세요.";
		}
		if(Ut.isEmpty(name)) {
			return "이름을 입력해주세요.";
		}
		if(Ut.isEmpty(nickName)) {
			return "닉네임을 입력해주세요.";
		}
		if(Ut.isEmpty(cellphoneNum)) {
			return "전화번호를 입력해주세요.";
		}
		if(Ut.isEmpty(email)) {
			return "이메일을 입력해주세요.";
		}
		
		int id = memberService.join(loginId, loginPw, name, nickName, cellphoneNum, email);
		
		if(id == -1) {
			return "중복된 아이디입니다.";
		}
		if(id == -2) {
			return "이미 존재하는 회원입니다.";
		}
		
		Member member = memberService.getMemberbyId(id);
		
		return member;
	}
}
