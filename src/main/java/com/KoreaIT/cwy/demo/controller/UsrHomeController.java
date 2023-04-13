package com.KoreaIT.cwy.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.KoreaIT.cwy.demo.vo.Article;

import lombok.*;

import java.util.*;

@Controller
public class UsrHomeController {
	
	List<Article> articles = new ArrayList<>();
	
	@RequestMapping("/usr/home/doAdd")
	@ResponseBody
	public String doAdd(String title,String body) {
		articles.add(new Article(title,body));
		return "글이 생성되었습니다";
	}
	
	@RequestMapping("/usr/home/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articles;
	}
}