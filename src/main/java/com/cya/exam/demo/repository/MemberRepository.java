package com.cya.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.cya.exam.demo.vo.Member;

@Mapper
public interface MemberRepository {
	
	public void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	public int getLastInsertId();

	public Member getMemberById(int id);
	
	public int isLoginIdDup(String loginId);

	public int isNameAndEmailDup(String name, String email);

	public Member getMemberByLoginId(String loginId);

	@Update("""
			<script>
				UPDATE `member`
				<set>	
					updateDate = NOW(),
					<if test="loginPw != null">
						loginPw = #{loginPw},
					</if>
					<if test="name != null">
						name = #{name},
					</if>
					<if test="nickname != null">
						nickname = #{nickname},
					</if>
					<if test="cellphoneNum != null">
						cellphoneNum = #{cellphoneNum},
					</if>
					<if test="email != null">
						email = #{email}
					</if>
				</set>
				WHERE id = #{id};
			</script>
			""")
	public void modifyMember(int id, String loginPw, String name, String nickname, String cellphoneNum, String email);
}
