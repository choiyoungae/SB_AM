package com.cya.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

	@Update("""
			<script>
				UPDATE article
				SET goodReactionPoint = goodReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseGoodReactionPoint(int relId);
	
	@Update("""
			<script>
				UPDATE article
				SET badReactionPoint = badReactionPoint + 1
				WHERE id = #{relId}
			</script>
			""")
	public int increaseBadReactionPoint(int relId);

	@Update("""
			<script>
				UPDATE article
				SET goodReactionPoint = goodReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseGoodReactionPoint(int relId);

	@Update("""
			<script>
				UPDATE article
				SET badReactionPoint = badReactionPoint - 1
				WHERE id = #{relId}
			</script>
			""")
	public int decreaseBadReactionPoint(int relId);
	
	@Select("""
			<script>
				SELECT *
				FROM article
				WHERE id = #{id}
			</script>
						""")
	public Article getArticle(int id);

	
}
