package com.KoreaIT.cwy.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.service.ArticleService;
import com.KoreaIT.cwy.demo.service.BoardService;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Article;
import com.KoreaIT.cwy.demo.vo.Board;
import com.KoreaIT.cwy.demo.vo.ResultData;
import com.KoreaIT.cwy.demo.vo.Rq;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private BoardService boardService;

	@RequestMapping("/usr/article/list")
	public String showList(Model model, int boardId) {
		Board board = boardService.getBoardById(boardId);

		List<Article> articles = articleService.getArticles(boardId);
		
		if(board == null) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg","존재하지 않는 게시판 입니다.");
			return "usr/common/js";
		}

		model.addAttribute("board", board);
		model.addAttribute("articles", articles);

		// ResultData.from("S-1", "게시글 목록을 조회합니다.","articles", articles);
		return "usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			// ResultData.from("F-1", id + "번글은 존재하지 않습니다.");
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", id + "번글은 존재하지 않습니다.");
			return "usr/common/js";
		}
		// ResultData.from("S-1", id+"번글을 조회합니다.","String", sb.toString());

		model.addAttribute("article", article);

		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/write")
	public String showWrite(HttpServletRequest req, Model model) {

		Rq rq = (Rq) req.getAttribute("rq");
		
		int loginedMemberId = rq.getLoginedMemberId();

		model.addAttribute("loginedMemberId", loginedMemberId);

		// ResultData.newData(writeArticleRd, "String", sb.toString());

		return "usr/article/write";
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.empty(title)) {
			// ResultData.from("F-1", "제목을 입력해주세요");
			return Ut.jsHistoryBack("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			// ResultData.from("F-2", "내용을 입력해주세요");
			return Ut.jsHistoryBack("F-2", "내용을 입력해주세요");
		}

		int loginedMemberId = rq.getLoginedMemberId();

		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, loginedMemberId);

		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticle(id);

		// ResultData.newData(writeArticleRd, "String", sb.toString());

		return Ut.jsReplace("S-1", id + "번글이 작성되었습니다.", "detail?id="+id);
	}

	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticle(id);

		if (article == null) {
			// ResultData.from("F-1", id + "번글은 존재하지 않습니다.");
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", id + "번글은 존재하지 않습니다.");
			return "usr/common/js";
		}

		int loginedMemberId = rq.getLoginedMemberId();
		
		if (article.getMemberId() != loginedMemberId) {
			// ResultData.from("F-2", Ut.f("해당 글에 대한 권한이 없습니다."));
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "해당 글에 대한 권한이 없습니다.");
			return "usr/common/js";
		}

		model.addAttribute("article", article);

		return "usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		Article article = articleService.getArticle(id);
		
		if (article == null) {
			return Ut.jsHistoryBack("F-1", id + "번글은 존재하지 않습니다.");
		}

		int loginedMemberId = rq.getLoginedMemberId();
		
		if (article.getMemberId() != loginedMemberId) {
			return Ut.jsHistoryBack("F-2", "해당 글에 대한 권한이 없습니다.");
		}
		
		articleService.doModifyArticle(id, title, body);
		
		//ResultData.from("S-1", id + "번글이 수정되었습니다.", "article", article);
		return Ut.jsReplace("S-1", id + "번글이 수정되었습니다.", "detail?id="+id);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticle(id);

		if (article == null) {
			return Ut.jsHistoryBack("F-1", id + "번글은 존재하지 않습니다.");
		}

		int loginedMemberId = rq.getLoginedMemberId();
		if (article.getMemberId() != loginedMemberId) {
			return Ut.jsHistoryBack("F-2", "해당 글에 대한 권한이 없습니다.");
		}

		articleService.doDeleteArticle(article);

		return Ut.jsReplace("S-1", id + "번글이 삭제되었습니다.", "list");
	}
}