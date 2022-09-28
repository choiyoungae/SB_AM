package com.cya.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {
	
//	@Insert("INSERT INTO `member` SET regDate = NOW(), updateDate = NOW(), loginId = #{loginId},"
//			+ " loginPw = #{loginPw}, `name` = #{name}, nickName = #{nickName},"
//			+ " cellphoneNum = #{cellphoneNum}, email = #{email}")
	public void join(String loginId, String loginPw, String name, String nickName, String cellphoneNum, String email);
}
