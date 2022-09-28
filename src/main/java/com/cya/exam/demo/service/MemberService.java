package com.cya.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.exam.demo.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public void join(String loginId, String loginPw, String name, String nickName, String cellphoneNum, String email) {
		memberRepository.join(loginId, loginPw, name, nickName, cellphoneNum, email);
	}

}
