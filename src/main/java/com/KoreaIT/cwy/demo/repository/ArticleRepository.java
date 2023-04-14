package com.KoreaIT.cwy.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.cwy.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public Article getArticle(int id);

	public List<Article> getArticles();

	public int getLastInsertId();

	public void doWriteArticle(String title, String body);

	public void doDeleteArticle(Article article);

	public void doModifyArticle(int id, String title, String body);

}
