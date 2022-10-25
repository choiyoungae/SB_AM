package com.cya.exam.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cya.exam.demo.vo.Board;

@Mapper
public interface ReactionPointRepository {

	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{loginedMemberId},
				relTypeCode = 'article',
				relId = #{id},
				`point` = 1;
			</script>
			""")
	int increaseGoodReactionPoint(int loginedMemberId, int id);

	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{loginedMemberId},
				relTypeCode = 'article',
				relId = #{id},
				`point` = -1;
			</script>
			""")
	int increaseBadReactionPoint(int loginedMemberId, int id);

	@Select("""
			SELECT myA.extra__sumReactionPoint
			FROM 
				(SELECT A.*, M.nickname AS extra__writerName, 
				IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
				IFNULL(SUM(IF(RP.point > 0, RP.point, 0)), 0) AS extra__goodReactionPoint,
				IFNULL(SUM(IF(RP.point < 0, RP.point, 0)), 0) AS extra__badReactionPoint
				FROM article AS A 
				LEFT JOIN `member` AS M
				ON A.memberId = M.id
				LEFT JOIN reactionPoint AS RP
				ON A.id = RP.relId AND RP.relTypeCode = 'article'
				WHERE A.id = #{id}
				GROUP BY A.id) AS myA
			""")
	int getArticleSumReactionPoint(int id);
	
	
}
