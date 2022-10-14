package com.cya.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.ArticleService;
import com.cya.exam.demo.util.Ut;
import com.cya.exam.demo.vo.Article;
import com.cya.exam.demo.vo.ResultData;
import com.cya.exam.demo.vo.Rq;

@Controller
public class UsrArticleController {

	@Autowired // 의존성 주입 관련
	private ArticleService articleService;

	// 액션메서드
	@RequestMapping("/usr/article/write")
	public String showAdd(HttpSession httpSession) {
		
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doAdd(HttpServletRequest req, String title, String body) {
		
		Rq rq = (Rq)req.getAttribute("rq");
		
		if (Ut.isEmpty(title)) {
			return Ut.jsHistoryBack("제목을 입력해주세요");
		}
		if (Ut.isEmpty(body)) {
			return Ut.jsHistoryBack("내용을 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, rq.getLoginedMemberId());

		int id = (int) writeArticleRd.getData1();
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		return Ut.jsReplace("게시물이 작성되었습니다.", Ut.f("detail?id=%d", id));
	}

	@RequestMapping("/usr/article/list")
	public String showList(HttpServletRequest req, Model model) {
		
		Rq rq = (Rq)req.getAttribute("rq");

		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId());
		
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {
		
		Rq rq = (Rq)req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if(article == null) {
			return Ut.jsHistoryBack(Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		
		if(article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("해당 게시물에 대한 권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
		return Ut.jsReplace(Ut.f("%d번 게시물이 삭제되었습니다.", id), "../article/list");
	}
	
	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq)req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if(article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번은 존재하지 않는 게시물입니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if(actorCanModifyRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {
		
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if(article == null) {
			return Ut.jsHistoryBack(Ut.f("%d번 게시물은 존재하지 않습니다", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if(actorCanModifyRd.isFail()) {
			return Ut.jsHistoryBack(actorCanModifyRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		
		return Ut.jsReplace(Ut.f("%d번 게시물을 수정했습니다", id), Ut.f("../article/detail?id=%d", id));
	}
}