package com.cya.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReplyRepository {

	@Insert("""
			<script>
				INSERT INTO reply
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`body` = #{body};	
			</script>
			""")
	void writeArticle(String relTypeCode, int relId, String body, int actorId);


	

}
