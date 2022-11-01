package com.cya.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cya.exam.demo.vo.Reply;

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
	void writeArticle(int actorId, String relTypeCode, int relId, String body);
	
	@Select("""
			<script>
				SELECT LAST_INSERT_ID()
			</script>
			""")

	int getLastInsertId();

	@Select("""
			<script>
				SELECT R.*, M.nickname AS extra__writerName
				FROM reply AS R
				LEFT JOIN `member` AS M
				ON R.memberId = M.id
				WHERE R.relId = #{relId}
				AND R.relTypeCode = #{relTypeCode}
				<if test="limitTake != -1">
					LIMIT #{limitStart}, #{limitTake}
				</if>
			</script>
			""")
	List<Reply> getForPrintReplies(String relTypeCode, int relId, int limitStart, int limitTake);

	@Select("""
			<script>
				SELECT COUNT(*)
				FROM reply
				WHERE relId = #{relId}
				AND relTypeCode = #{relTypeCode}
			</script>
			""")
	int getRepliesCount(String relTypeCode, int relId);


	

}
