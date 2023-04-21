package com.KoreaIT.cwy.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Rq;

@Component
public class NeedLogoutIntercepter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		resp.setContentType("text/html; charset=UTF-8");
		Rq rq = (Rq) req.getAttribute("rq");
		if (rq.isLogined()) {
			rq.printHistoryBackJs(Ut.jsHistoryBack("F-B", "로그아웃을 먼저 해주세요."));
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}