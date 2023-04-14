package com.KoreaIT.cwy.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.service.ArticleService;
import com.KoreaIT.cwy.demo.vo.Article;

@Controller
public class UsrArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public String getArticle(int id) {
		
		Article article =  articleService.getArticle(id);
		
		if(article == null) {
			return id+"번글은 존재하지 않습니다.";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(id+"번 글 상세보기");
		sb.append("번호 : " + article.getId());
		sb.append("<br>작성날짜 : " + article.getRegDate());
		sb.append("<br>수정날짜 : " + article.getUpdateDate());
		sb.append("<br>제목 : " + article.getTitle());
		sb.append("<br>내용 : " + article.getBody());
		
		return sb.toString();
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		
		Article article =  articleService.getArticle(id);
		
		if(article == null) {
			return id+"번글은 존재하지 않습니다.";
		}
		
		articleService.doDeleteArticle(article);
		
		return id+"번 글이 삭제되었습니다.";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return id+"번글은 존재하지 않습니다.";
		}
		
		articleService.doModifyArticle(id,title,body);
		
		article = articleService.getArticle(id);
		
		StringBuilder sb = new StringBuilder();
		sb.append(id+"번 글이 수정되었습니다.");
		sb.append("<br>번호 : " + article.getId());
		sb.append("<br>작성날짜 : " + article.getRegDate());
		sb.append("<br>수정날짜 : " + article.getUpdateDate());
		sb.append("<br>제목 : " + article.getTitle());
		sb.append("<br>내용 : " + article.getBody());
		
		return sb.toString();
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(String title, String body) {
		int id = articleService.doWriteArticle(title, body);
		
		Article article = articleService.getArticle(id);
		
		StringBuilder sb = new StringBuilder();
		sb.append(id+"번 글이 작성되었습니다.");
		sb.append("<br>번호 : " + article.getId());
		sb.append("<br>작성날짜 : " + article.getRegDate());
		sb.append("<br>수정날짜 : " + article.getUpdateDate());
		sb.append("<br>제목 : " + article.getTitle());
		sb.append("<br>내용 : " + article.getBody());
		
		return sb.toString();
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articleService.getArticles();
	}
}