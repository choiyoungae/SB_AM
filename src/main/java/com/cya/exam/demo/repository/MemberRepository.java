package com.cya.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.cya.exam.demo.vo.Member;

@Mapper
public interface MemberRepository {
	
	public void join(String loginId, String loginPw, String name, String nickName, String cellphoneNum, String email);

	public int getLastInsertId();

	public Member getMemberbyId(int id);
	
	public int isLoginIdDup(String loginId);
}
