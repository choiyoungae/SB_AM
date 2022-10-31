package com.cya.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.cya.exam.demo.vo.Member;

@Mapper
public interface MemberRepository {
	
	public void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	public int getLastInsertId();

	public Member getMemberById(int id);
	
	public int isLoginIdDup(String loginId);

	public int isNameAndEmailDup(String name, String email);

	public Member getMemberByLoginId(String loginId);
}
