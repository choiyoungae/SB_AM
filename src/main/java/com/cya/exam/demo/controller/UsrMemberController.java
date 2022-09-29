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
		
		if(loginId == null || loginId.trim().length() == 0) {
			return "아이디를 입력해주세요.";
		}
		if(loginPw == null || loginPw.trim().length() == 0) {
			return "바밀번호를 입력해주세요.";
		}
		if(name == null || name.trim().length() == 0) {
			return "이름을 입력해주세요.";
		}
		if(nickName == null || nickName.trim().length() == 0) {
			return "닉네임을 입력해주세요.";
		}
		if(cellphoneNum == null || cellphoneNum.trim().length() == 0) {
			return "전화번호를 입력해주세요.";
		}
		if(email == null || email.trim().length() == 0) {
			return "이메일을 입력해주세요.";
		}
		
		int id = memberService.join(loginId, loginPw, name, nickName, cellphoneNum, email);
		
		if(id == -1) {
			return "중복된 아이디입니다.";
		}
		
		Member member = memberService.getMemberbyId(id);
		
		return member;
	}
}
