package com.cya.exam.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.MemberService;
import com.cya.exam.demo.util.Ut;
import com.cya.exam.demo.vo.Member;
import com.cya.exam.demo.vo.ResultData;
import com.cya.exam.demo.vo.Rq;

@Controller
public class UsrMemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email, @RequestParam(defaultValue = "/") String afterLoginUri) {
		
		if(Ut.isEmpty(loginId)) {
			return rq.jsHistoryBack("F-1", "아이디를 입력해주세요.");
		}
		if(Ut.isEmpty(loginPw)) {
			return rq.jsHistoryBack("F-2", "비밀번호를 입력해주세요.");
		}
		if(Ut.isEmpty(name)) {
			return rq.jsHistoryBack("F-3", "이름을 입력해주세요.");
		}
		if(Ut.isEmpty(nickname)) {
			return rq.jsHistoryBack("F-4", "닉네임을 입력해주세요.");
		}
		if(Ut.isEmpty(cellphoneNum)) {
			return rq.jsHistoryBack("F-5", "전화번호를 입력해주세요.");
		}
		if(Ut.isEmpty(email)) {
			return rq.jsHistoryBack("F-6", "이메일을 입력해주세요.");
		}
		
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if(joinRd.isFail()) {
			return rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}
		
		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getUriEncoded(afterLoginUri);
		
		return rq.jsReplace("회원가입이 완료되었습니다. 로그인 후 이용해주세요", afterJoinUri);
	}
	
	@RequestMapping("usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {
		
		if(Ut.isEmpty(loginId)) {
			return ResultData.from("F-A1", "아이디를 입력해주세요.");
		}
		
		Member oldMember = memberService.getMemberByLoginId(loginId);

		if(oldMember != null) {			
			return ResultData.from("F-A2", "이미 사용중인 아이디입니다.", "loginId", loginId);
		}

		return ResultData.from("S-1", "사용 가능한 아이디입니다.", "loginId", loginId);
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {
		
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
		
		if(member.getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return Ut.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		
		rq.login(member);
		
		return Ut.jsReplace(Ut.f("%s님 반갑습니다.", member.getNickname()), afterLoginUri);
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {

		rq.logout();
		
		return Ut.jsReplace("로그아웃 되었습니다.", afterLogoutUri);
	}
	

	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {
		
		return "usr/member/myPage";
	}
	
	@RequestMapping("/usr/member/checkPassword")
	public String showcheckPassword() {

		return "usr/member/checkPassword";
	}

	@RequestMapping("/usr/member/doCheckPassword")
	@ResponseBody
	public String doCheckPassword(String loginPw, String replaceUri) {
		
		System.out.println(loginPw + "1");
		if (Ut.isEmpty(loginPw)) {
			System.out.println(loginPw + "2");
			return rq.jsHistoryBack("비밀번호를 입력해주세요");
		}

		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			System.out.println(loginPw + "3");
			return rq.jsHistoryBack("비밀번호가 일치하지 않습니다");
		}

		if (replaceUri.equals("../member/modify")) {
			String memberModifyAuthKey = memberService.genMemberModifyAuthKey(rq.getLoginedMemberId());

			replaceUri += "?memberModifyAuthKey=" + memberModifyAuthKey;
		}
		
		if(replaceUri == null) {
			replaceUri = "/usr/member/myPage";
		}

		return rq.jsReplace("", replaceUri);
	}

	@RequestMapping("/usr/member/modify")
	public String showModify(String memberModifyAuthKey) {
		
		if(Ut.isEmpty(memberModifyAuthKey)) {
			return rq.jsHistoryBackOnView("회원 수정 인증코드가 필요합니다.");
		}
		
		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(), memberModifyAuthKey);
		
		if(checkMemberModifyAuthKeyRd.isFail()) {
			return rq.jsHistoryBackOnView(checkMemberModifyAuthKeyRd.getMsg());
		}
		
		return "usr/member/modify";
	}

	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String memberModifyAuthKey, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		if (Ut.isEmpty(memberModifyAuthKey)) {
			return rq.jsHistoryBack("회원 수정 인증코드가 필요합니다");
		}

		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(),
				memberModifyAuthKey);

		if (checkMemberModifyAuthKeyRd.isFail()) {
			return rq.jsHistoryBack(checkMemberModifyAuthKeyRd.getMsg());
		}
		
		if (Ut.isEmpty(loginPw)) {
			loginPw = null;
		}
		if (Ut.isEmpty(name)) {
			return rq.jsHistoryBack("이름을 입력해주세요");
		}
		if (Ut.isEmpty(nickname)) {
			return rq.jsHistoryBack("닉네임을 입력해주세요");
		}
		if (Ut.isEmpty(cellphoneNum)) {
			return rq.jsHistoryBack("전화번호를 입력해주세요");
		}
		if (Ut.isEmpty(email)) {
			return rq.jsHistoryBack("이메일을 입력해주세요");
		}

		ResultData modifyRd = memberService.modifyMember(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum,
				email);

		return rq.jsReplace(modifyRd.getMsg(), "/");

	}
}
