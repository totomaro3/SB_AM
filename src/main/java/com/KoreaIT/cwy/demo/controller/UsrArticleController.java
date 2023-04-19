package com.KoreaIT.cwy.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.service.ArticleService;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Article;
import com.KoreaIT.cwy.demo.vo.ResultData;

@Controller
public class UsrArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/usr/article/list")
	public String getArticles(Model model) {
		
		List<Article> articles = articleService.getArticles();

		model.addAttribute("articles", articles);
		
		//request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
		
		//ResultData.from("S-1", "게시글 목록을 조회합니다.","articles", articles);
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String getArticle(Model model, int id) {
		
		Article article =  articleService.getArticle(id);
		
		if(article == null) {
			//return ResultData.from("F-1", id+"번글은 존재하지 않습니다.");
			return id+"번글은 존재하지 않습니다.";
		}
		
		//ResultData.from("S-1", id+"번글을 조회합니다.","String", sb.toString());
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public ResultData<String> doWrite(HttpSession httpSession, String title, String body) {
		
		if(httpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", "로그인을 해주세요.");
		}
		
		if (Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요");
		}

		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, loginedMemberId);

		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticle(id);
		
		StringBuilder sb = new StringBuilder();
		sb.append("번호 : " + article.getId());
		sb.append("<br>작성날짜 : " + article.getRegDate());
		sb.append("<br>수정날짜 : " + article.getUpdateDate());
		sb.append("<br>제목 : " + article.getTitle());
		sb.append("<br>내용 : " + article.getBody());
		sb.append("<br>작성자 : " + article.getMemberId());

		return ResultData.newData(writeArticleRd,"String", sb.toString());
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<String> doModify(HttpSession httpSession, int id, String title, String body) {
		
		if(httpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", "로그인을 해주세요.");
		}
		
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return ResultData.from("F-1", id+"번글은 존재하지 않습니다.");
		}
		
		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		if(article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("해당 글에 대한 권한이 없습니다."));
		}

		articleService.doModifyArticle(id,title,body);
		
		article = articleService.getArticle(id);
		
		StringBuilder sb = new StringBuilder();
		sb.append("번호 : " + article.getId());
		sb.append("<br>작성날짜 : " + article.getRegDate());
		sb.append("<br>수정날짜 : " + article.getUpdateDate());
		sb.append("<br>제목 : " + article.getTitle());
		sb.append("<br>내용 : " + article.getBody());
		sb.append("<br>작성자 : " + article.getMemberId());
		
		return ResultData.from("S-1", id+"번글이 수정되었습니다.","String",sb.toString());
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<String> doDelete(HttpSession httpSession,int id) {
		
		if(httpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", "로그인을 해주세요.");
		}
		
		Article article =  articleService.getArticle(id);
		
		if(article == null) {
			return ResultData.from("F-1", id+"번글은 존재하지 않습니다.");
		}
		
		int loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		if(article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", "해당 글에 대한 권한이 없습니다.");
		}
		
		articleService.doDeleteArticle(article);
		
		return ResultData.from("S-1", id+"번글이 삭제되었습니다.");
	}
}