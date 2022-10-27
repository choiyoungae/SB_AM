package com.cya.exam.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {

	@Select("""
			<script>
				SELECT IFNULL(SUM(RP.point),0) AS s
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = #{relTypeCode}
				AND RP.relId = #{relId}
				AND RP.memberId = #{actorId}
			</script>
						""")
	int getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId);
	
	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`point` = 1;
			</script>
			""")
	int addGoodReactionPoint(int actorId, String relTypeCode, int relId);
	
	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`point` = -1;
			</script>
			""")
	int addBadReactionPoint(int actorId, String relTypeCode, int relId);

}
