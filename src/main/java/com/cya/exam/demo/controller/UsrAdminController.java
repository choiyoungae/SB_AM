package com.cya.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cya.exam.demo.service.MemberService;
import com.cya.exam.demo.vo.Member;
import com.cya.exam.demo.vo.Rq;

@Controller
public class UsrAdminController {
	@Autowired
	private Rq rq;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/usr/home/admin")
	public String showMain(Model model) {
		
		Member loginedMember = memberService.getMemberById(rq.getLoginedMemberId());
		
		if(loginedMember != null) {
			boolean isAdminLogined = memberService.getMemberById(rq.getLoginedMemberId()).getAuthLevel() == 7;
			model.addAttribute("isAdminLogined", isAdminLogined);
			
		}
		
		return "usr/home/admin";
	}
	
}
