package com.KoreaIT.cwy.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.cwy.demo.vo.Article;
import com.KoreaIT.cwy.demo.vo.Reply;

@Mapper
public interface ArticleRepository {

	public Article getArticle(int id);

	public List<Article> getArticles(int boardId, int limitFrom, int itemsInAPage, String searchKeywordTypeCode, String searchKeyword);
	
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword );

	public int getLastInsertId();

	public void writeArticle(String title, String body, int memberId, int boardId);

	public void doDeleteArticle(Article article);

	public void doModifyArticle(int id, String title, String body);

	public String getwriterName(int id);

	public int increaseHitCount(int id);
	
	public int getArticleHitCount(int id);

	public int increaseGoodReactionPoint(int relId);

	public int increaseBadReactionPoint(int relId);

	public int decreaseGoodReactionPoint(int relId);

	public int decreaseBadReactionPoint(int relId);
}