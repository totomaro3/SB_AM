package com.KoreaIT.cwy.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.Getter;

public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	
	HttpServletRequest req;
	HttpServletResponse resp;
	HttpSession session;
	
	public Rq(HttpServletRequest req,HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		this.session = req.getSession();
		
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
	}

	public void printHistoryBackJs(String str) throws IOException {
		resp.getWriter().append(str);
	}
	
	public void returnHistoryBackJs(String str) throws IOException {
		resp.getWriter().append(str);
	}
	
	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
		session.setAttribute("loginedMemberNickname", member.getNickname());
	}
	
	public void logout() {
		session.removeAttribute("loginedMemberId");
	}

}