package com.KoreaIT.cwy.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.KoreaIT.cwy.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private String loginedMemberNickname;
	@Getter
	private Member loginedMember;
	
	HttpServletRequest req;
	HttpServletResponse resp;
	HttpSession session;
	
	public Rq(HttpServletRequest req,HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		this.session = req.getSession();
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		String loginedMemberNickname = "";
		Member loginedMember = null;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMemberNickname = (String) session.getAttribute("loginedMemberNickname");
			loginedMember = (Member) session.getAttribute("loginedMember");
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMemberNickname = loginedMemberNickname;
		this.loginedMember = loginedMember;
		
		this.req.setAttribute("rq", this);

	}
	
	public String jsHistoryBack(String resultCode, String msg) {
		return Ut.jsHistoryBack(resultCode, msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}
	
	public String jsReplace(String resultCode ,String msg, String uri) {
		return Ut.jsReplace(resultCode, msg, uri);
	}

	public void printHistoryBackJs(String str) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().append(str);
	}
	
	public String jsHitoryBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";
	}
	
	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();
		
		currentUri += "?" + queryString;
		
		return currentUri;
	}
	
	public String getEncodedCurrentUri() {
		return Ut.getEncodedCurrentUri(getCurrentUri());
	}
	
	
	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
		session.setAttribute("loginedMemberNickname", member.getNickname());
		session.setAttribute("loginedMember", member);
	}
	
	public void logout() {
		session.removeAttribute("loginedMemberId");
	}
	
	// 삭제 금지
	public void initOnBeforeActionInterceptor() {

	}

}