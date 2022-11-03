package com.cya.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.MemberService;
import com.cya.exam.demo.util.Ut;
import com.cya.exam.demo.vo.Article;
import com.cya.exam.demo.vo.Member;
import com.cya.exam.demo.vo.Reply;
import com.cya.exam.demo.vo.ResultData;
import com.cya.exam.demo.vo.Rq;

@Controller
public class UsrMemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		
		if(Ut.isEmpty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요.");
		}
		if(Ut.isEmpty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해주세요.");
		}
		if(Ut.isEmpty(name)) {
			return ResultData.from("F-3", "이름을 입력해주세요.");
		}
		if(Ut.isEmpty(nickname)) {
			return ResultData.from("F-4", "닉네임을 입력해주세요.");
		}
		if(Ut.isEmpty(cellphoneNum)) {
			return ResultData.from("F-5", "전화번호를 입력해주세요.");
		}
		if(Ut.isEmpty(email)) {
			return ResultData.from("F-6", "이메일을 입력해주세요.");
		}
		
		// S-1
		// 회원가입이 완료되었습니다
		// F-1~8
		// 실패
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if(joinRd.isFail()) {
			return (ResultData)joinRd;
		}
		
		Member member = memberService.getMemberById((int) joinRd.getData1());
		
		return ResultData.newData(joinRd, "member", member);
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin(HttpSession httpSession) {
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpSession httpSession, String loginId, String loginPw) {
		
		if(Ut.isEmpty(loginId)) {
			return Ut.jsHistoryBack("아이디를 입력해주세요.");
		}
		if(Ut.isEmpty(loginPw)) {
			return Ut.jsHistoryBack("비밀번호를 입력해주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) {
			return Ut.jsHistoryBack("아이디를 잘못 입력했습니다.");
		}
		
		if(member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		
		rq.login(member);
		
		return Ut.jsReplace(Ut.f("%s님 반갑습니다.", member.getNickname()), "/");
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpSession httpSession) {

		rq.logout();
		
		return Ut.jsReplace("로그아웃 되었습니다.", "/");
	}
	

	@RequestMapping("/usr/member/myPage")
	public String showMyPage(Model model, int id) {
		
		if(rq.getLoginedMemberId() != id) {
			return Ut.jsHistoryBack("본인의 MYPAGE만 보실 수 있습니다.");
		}

		Member member = memberService.getMemberById(rq.getLoginedMemberId());

		model.addAttribute("member", member);
		
		return "usr/member/myPage";
	}
}
