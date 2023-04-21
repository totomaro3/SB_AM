package com.KoreaIT.cwy.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.KoreaIT.cwy.demo.vo.Rq;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/list")
	public String showList(Model model) {

		List<Article> articles = articleService.getArticles();

		model.addAttribute("articles", articles);

		// ResultData.from("S-1", "게시글 목록을 조회합니다.","articles", articles);
		return "usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			// return ResultData.from("F-1", id+"번글은 존재하지 않습니다.");
			return id + "번글은 존재하지 않습니다.";
		}

		// ResultData.from("S-1", id+"번글을 조회합니다.","String", sb.toString());

		model.addAttribute("article", article);

		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/write")
	public String write(HttpServletRequest req, Model model) {

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
		if (!rq.isLogined()) {
			return "로그인을 해주세요.";// ResultData.from("F-A", "로그인을 해주세요.");
		}

		if (Ut.empty(title)) {
			return "제목을 입력해주세요";// ResultData.from("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			return "내용을 입력해주세요";// ResultData.from("F-2", "내용을 입력해주세요");
		}

		int loginedMemberId = rq.getLoginedMemberId();

		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, loginedMemberId);

		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getArticle(id);

		// ResultData.newData(writeArticleRd, "String", sb.toString());

		return Ut.jsReplace("S-1", id + "번글이 작성되었습니다.", "list");
	}

	@RequestMapping("/usr/article/modify")
	public String modify(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq) req.getAttribute("rq");
		if (!rq.isLogined()) {
			// ResultData.from("F-A", "로그인을 해주세요.");
			return "로그인을 해주세요.";
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			// ResultData.from("F-1", id + "번글은 존재하지 않습니다.");
			return id + "번글은 존재하지 않습니다.";
		}

		int loginedMemberId = rq.getLoginedMemberId();
		if (article.getMemberId() != loginedMemberId) {
			// ResultData.from("F-2", Ut.f("해당 글에 대한 권한이 없습니다."));
			return "해당 글에 대한 권한이 없습니다.";
		}

		model.addAttribute("article", article);

		return "usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(int id, String title, String body) {
		articleService.doModifyArticle(id, title, body);

		Article article = articleService.getArticle(id);

		return ResultData.from("S-1", id + "번글이 수정되었습니다.", "article", article);
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq) req.getAttribute("rq");
		if (!rq.isLogined()) {
			return Ut.jsHistoryBack("F-A", "로그인을 해주세요.");
		}

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

	public String loginCheck(HttpSession httpSession) {
		if (httpSession.getAttribute("loginedMemberId") == null) {
			return Ut.jsHistoryBack("F-A", "로그인을 해주세요.");
		}
		return "";
	}
}