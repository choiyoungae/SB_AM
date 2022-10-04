package com.cya.exam.demo.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.exam.demo.repository.MemberRepository;
import com.cya.exam.demo.util.Ut;
import com.cya.exam.demo.vo.Member;
import com.cya.exam.demo.vo.ResultData;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickName, String cellphoneNum, String email) {
		
		// 로그인 아이디 중복 체크
		if(memberRepository.isLoginIdDup(loginId) != 0) {
			return ResultData.from("F-7", Ut.f("이미 사용중인 아이디(%s)입니다", loginId));
		}
		
		// 이름 + 이메일 중복 체크
		if(memberRepository.isNameAndEmailDup(name, email) != 0) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email));
		}
		
		memberRepository.join(loginId, loginPw, name, nickName, cellphoneNum, email);
		
		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", "회원가입이 완료되었습니다", id);
	}

	public Member getMemberbyId(int id) {
		
		return memberRepository.getMemberbyId(id);
	}
	
	public Member getMemberByLoginId(String loginId) {
		
		return memberRepository.getMemberByLoginId(loginId);
	}

}
