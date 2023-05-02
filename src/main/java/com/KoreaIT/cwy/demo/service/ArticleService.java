package com.KoreaIT.cwy.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.repository.ArticleRepository;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Article;
import com.KoreaIT.cwy.demo.vo.Reply;
import com.KoreaIT.cwy.demo.vo.ResultData;

@Service
public class ArticleService {
	
	@Autowired
	ArticleRepository articleRepository;

	public Article getArticle(int id) {
		
		return articleRepository.getArticle(id);
	}
	
	public List<Article> getArticles(int boardId, int limitFrom, int itemsInAPage, String searchKeywordTypeCode, String searchKeyword) {
		
		return articleRepository.getArticles(boardId, limitFrom, itemsInAPage, searchKeywordTypeCode, searchKeyword);
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

	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	public ResultData increaseHitCount(int id) {
		int affectedRow = articleRepository.increaseHitCount(id);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없음", "affectedRow", affectedRow);
		}

		return ResultData.from("S-1", "조회수 증가", "affectedRow", affectedRow);
	}

	public int getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}

	public ResultData increaseGoodReactionPoint(int relId) {
		int affectedRow = articleRepository.increaseGoodReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 증가", "affectedRow", affectedRow);
	}

	public ResultData increaseBadReactionPoint(int relId) {

		int affectedRow = articleRepository.increaseBadReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 증가", "affectedRow", affectedRow);
	}

	public ResultData decreaseGoodReationPoint(int relId) {
		int affectedRow = articleRepository.decreaseGoodReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 취소", "affectedRow", affectedRow);
	}
	
	public ResultData decreaseBadReationPoint(int relId) {
		int affectedRow = articleRepository.decreaseBadReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 취소", "affectedRow", affectedRow);
	}
}
