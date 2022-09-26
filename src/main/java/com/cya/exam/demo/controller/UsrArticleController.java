package com.cya.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.vo.Article;

@Controller
public class UsrArticleController {

	private int lastArticleId;
	private List<Article> articles;

	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();
		
		makeTestData();
	}

	private void makeTestData() {
		for(int i=1; i<=10; i++) {
			int id = ++lastArticleId;
			String title = "제목"+id;
			String body = "내용"+id;
			
			articles.add(new Article(id, title, body));
		}
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = lastArticleId + 1;
		Article article = new Article(id, title, body);

		articles.add(article);
		lastArticleId = id;

		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articles;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article target = null;
		
		for(Article article : articles) {
			if(article.getId() == id) {
				target = article;
			}
		}

		if(target == null) {
			return String.format("%d번 글은 존재하지 않습니다.", id);
		} else {
			articles.remove(target);
			return String.format("%d번 글이 삭제되었습니다.", id);
		}
	}
}