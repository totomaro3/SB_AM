package com.KoreaIT.cwy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.repository.ArticleRepository;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Article;
import com.KoreaIT.cwy.demo.vo.ResultData;

@Service
public class ArticleService {
	
	@Autowired
	ArticleRepository articleRepository;

	public Article getArticle(int id) {
		
		return articleRepository.getArticle(id);
	}
	
	public List<Article> getArticles(int boardId, int limitFrom, int itemsInAPage) {
		
		return articleRepository.getArticles(boardId, limitFrom, itemsInAPage);
	}
	
	public ResultData<Integer> writeArticle(String title, String body, int memberId, int boardId) {

		articleRepository.writeArticle(title, body, memberId, boardId);

		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었습니다", id),"id", id);

	}
	
	public void doDeleteArticle(Article article) {
		articleRepository.doDeleteArticle(article);
	}

	public void doModifyArticle(int id, String title, String body) {
		articleRepository.doModifyArticle(id,title,body);
	}

	public String getwriterName(int id) {
		return articleRepository.getwriterName(id);
	}

	public int getArticlesCount(int boardId) {
		return articleRepository.getArticlesCount(boardId);
	}

}
