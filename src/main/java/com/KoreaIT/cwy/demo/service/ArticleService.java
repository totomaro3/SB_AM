package com.KoreaIT.cwy.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.repository.ArticleRepository;
import com.KoreaIT.cwy.demo.vo.Article;

@Service
public class ArticleService {
	
	@Autowired
	ArticleRepository articleRepository;

	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}
	
	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}
	
	public int doWriteArticle(String title, String body) {
		articleRepository.doWriteArticle(title,body);
		return articleRepository.getLastInsertId();
	}
	
	public void doDeleteArticle(Article article) {
		articleRepository.doDeleteArticle(article);
	}

	public void doModifyArticle(int id, String title, String body) {
		articleRepository.doModifyArticle(id,title,body);	
	}



}
