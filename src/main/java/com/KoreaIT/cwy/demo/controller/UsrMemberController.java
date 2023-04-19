package com.KoreaIT.cwy.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.service.MemberService;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Member;
import com.KoreaIT.cwy.demo.vo.ResultData;

@Controller
public class UsrMemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<?> doJoin(HttpSession httpsession, String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		if(httpsession.getAttribute("loginedMemberId") != null) {
			return ResultData.from("F-A", "로그아웃 해주세요.");
		}
		
		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return ResultData.from("F-3", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return ResultData.from("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return ResultData.from("F-5", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return ResultData.from("F-6", "이메일을 입력해주세요");
		}
		
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if (joinRd.isFail()) {
			return joinRd;
		}
		
		int id = joinRd.getData1();
		
		Member member = memberService.getMemberById(id);
		
		StringBuilder sb = new StringBuilder();
		sb.append("번호 : " + member.getId());
		sb.append("<br>가입날짜 : " + member.getRegDate());
		sb.append("<br>아이디 : " + member.getLoginId());
		sb.append("<br>이름 : " + member.getName());

		return ResultData.newData(joinRd,"String", sb.toString());
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData<String> doLogin(HttpSession httpsession, String loginId, String loginPw) {
		
		if(httpsession.getAttribute("loginedMemberId") != null) {
			return ResultData.from("F-A", "이미 로그인 상태입니다.");
		}
		
		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해주세요");
		}
		
		Member member = memberService.login(loginId, loginPw);
		
		if(member == null) {
			return ResultData.from("F-3", Ut.f("아이디(%s)가 없습니다.", loginId));
		}
		
		if(!member.getLoginPw().equals(loginPw)) {
			return ResultData.from("F-4", Ut.f("비밀번호가 일치하지 않습니다."));
		}
		
		httpsession.setAttribute("loginedMemberId", member.getId());
		
		return ResultData.from("S-1", Ut.f("%s님 환영합니다.",member.getNickname()));
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData<String> doLogout(HttpSession httpsession) {
		
		if(httpsession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", "이미 로그아웃 상태입니다.");
		}
		
		httpsession.removeAttribute("loginedMemberId");
		
		return ResultData.from("S-1", Ut.f("로그아웃 되었습니다."));
	}
}

//http://localhost:8081/usr/member/doJoin?loginId=1&loginPw=1&name=abc&nickname=toto&cellphoneNum=1&email=abc@gmail.com
//ResultData -> 표준 보고서 양식
//성공, 실패 쉽게 판단이 가능하도록 / 관련 데이터를 같이 주고 받을 수 있도록