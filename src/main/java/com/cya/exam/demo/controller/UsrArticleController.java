package com.cya.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.ArticleService;
import com.cya.exam.demo.util.Ut;
import com.cya.exam.demo.vo.Article;
import com.cya.exam.demo.vo.ResultData;

@Controller
public class UsrArticleController {

	@Autowired // 의존성 주입 관련
	private ArticleService articleService;

	// 액션메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpsession, String title, String body) {
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if(httpsession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpsession.getAttribute("loginedMemberId");
		}
		
		if(isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		if (Ut.isEmpty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		if (Ut.isEmpty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, loginedMemberId);

		int id = (int) writeArticleRd.getData1();
		Article article = articleService.getArticle(id);
		
		return ResultData.newData(writeArticleRd, article);
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {
		List<Article> articles = articleService.getArticles();

		return ResultData.from("S-3", "게시글 리스트", articles);
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {
		Article article = articleService.getArticle(id);

		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), article);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpsession, int id) {
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if(httpsession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpsession.getAttribute("loginedMemberId");
		}
		
		if(isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getArticle(id);

		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id), id);
		}
		
		if(article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-B", "해당 게시물에 대한 권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
		return ResultData.from("S-1", Ut.f("%d번 게시물이 삭제되었습니다.", id), id);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(HttpSession httpsession, int id, String title, String body) {
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if(httpsession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpsession.getAttribute("loginedMemberId");
		}
		
		if(isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getArticle(id);

		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id), id);
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);
		
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		return articleService.modifyArticle(id, title, body);
	}
}