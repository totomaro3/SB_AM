package com.KoreaIT.cwy.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.cwy.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public Article getArticle(int id);

	public List<Article> getArticles(int boardId);
	
	public List<Article> getAllArticles();

	public int getLastInsertId();

	public void writeArticle(String title, String body, int memberId);

	public void doDeleteArticle(Article article);

	public void doModifyArticle(int id, String title, String body);

	public String getwriterName(int id);

}
