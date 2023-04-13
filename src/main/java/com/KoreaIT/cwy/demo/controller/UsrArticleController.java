package com.KoreaIT.cwy.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.vo.Article;

@Controller
public class UsrArticleController {
	int lastArticleId;
	List<Article> articles;

	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();

		makeTestData();
	}

	private void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목 " + i;
			String body = "내용 " + i;

			writeArticle(title, body);
		}
	}

	public Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;

		Article article = new Article(id, title, body);
		articles.add(article);
		lastArticleId++;

		return article;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article foundArticle = null;
		for(Article article : articles) {
			if(article.getId() == id) {
				foundArticle = article;
			}
		}
		
		if(foundArticle == null) {
			return id+"번글은 존재하지 않습니다.";
		}
		
		articles.remove(foundArticle);
		
		return id+"번 글이 삭제되었습니다.";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		Article foundArticle = null;
		for(Article article : articles) {
			if(article.getId() == id) {
				foundArticle = article;
			}
		}
		
		if(foundArticle == null) {
			return id+"번글은 존재하지 않습니다.";
		}
		
		foundArticle.setId(id);
		foundArticle.setTitle(title); 
		foundArticle.setBody(body); 
		
		return id+"번 글이 수정되었습니다.";
	}
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = writeArticle(title, body);
		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articles;
	}
}