package com.cya.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.exam.demo.repository.MemberRepository;
import com.cya.exam.demo.vo.Member;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public int join(String loginId, String loginPw, String name, String nickName, String cellphoneNum, String email) {
		
		if(memberRepository.isLoginIdDup(loginId) != 0) {
			return -1;
		}
		
		memberRepository.join(loginId, loginPw, name, nickName, cellphoneNum, email);
		
		return memberRepository.getLastInsertId();
	}

	public Member getMemberbyId(int id) {
		
		return memberRepository.getMemberbyId(id);
	}

}
