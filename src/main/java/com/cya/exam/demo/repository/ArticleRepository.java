package com.cya.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cya.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	
	public void writeArticle(int boardId, String title, String body, int loginedMemberId);
	
	public Article getForPrintArticle(int id);

	public List<Article> getForPrintArticles(int boardId, int limitStart, int limitTake, String searchKeywordTypeCode, String searchKeyword);

	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();

	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	public int increaseHitCount(int id);

	@Select("""
			<script>
				SELECT hitCount
				FROM article
				WHERE id = #{id}
			</script>
			""")
	public int getArticleHitCount(int id);
	
	@Select("""
			<script>
				SELECT IFNULL(SUM(RP.point),0) AS s
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = 'article'
				AND RP.relId = #{id}
				AND RP.memberId = #{memberId}
			</script>
						""")
	public int getSumReactionPointByMemberId(int memberId, int id);

	
}
