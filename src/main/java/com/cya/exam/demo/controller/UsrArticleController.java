package com.cya.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.ArticleService;
import com.cya.exam.demo.vo.Article;

@Controller
public class UsrArticleController {

	@Autowired // 의존성 주입 관련
	private ArticleService articleService;

	// 액션메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = articleService.writeArticle(title, body);
		
		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articleService.getArticles();
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticleAction(int id) {
		Article article = articleService.getArticle(id);

		if(article == null) {
			return id + "번 글은 존재하지 않습니다.";
		}
		
		return article;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticle(id);

		if(article == null) {
			return id + "번 글은 존재하지 않습니다.";
		}
		
		articleService.deleteArticle(id);
		return id + "번 글이 삭제되었습니다.";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);

		if(article == null) {
			return id + "번 글은 존재하지 않습니다.";
		}
		
		articleService.modifyArticle(id, title, body);
		
		return article;
	}
}