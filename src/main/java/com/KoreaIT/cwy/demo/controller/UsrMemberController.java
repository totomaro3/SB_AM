package com.KoreaIT.cwy.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.service.MemberService;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Member;

@Controller
public class UsrMemberController {
	
	public static final int isDupLoginId = -1;
	public static final int isDupNameAndEmail = -2;
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		if (Ut.empty(loginId)) {
			return "아이디를 입력해주세요";
		}
		if (Ut.empty(loginPw)) {
			return "비밀번호를 입력해주세요";
		}
		if (Ut.empty(name)) {
			return "이름을 입력해주세요";
		}
		if (Ut.empty(nickname)) {
			return "닉네임을 입력해주세요";
		}
		if (Ut.empty(cellphoneNum)) {
			return "전화번호를 입력해주세요";
		}
		if (Ut.empty(email)) {
			return "이메일을 입력해주세요";
		}
		
		int id = memberService.doJoinMember(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if(id == isDupLoginId) {
			return Ut.f("이미 사용중인 아이디(%s)입니다", loginId);
		}
		
		if(id == isDupNameAndEmail) {
			return Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email);
		}
		
		Member member = memberService.getMember(id);
		
		StringBuilder sb = new StringBuilder();
		sb.append(nickname+" 회원이 가입되었습니다.");
		sb.append("<br>번호 : " + member.getId());
		sb.append("<br>가입날짜 : " + member.getRegDate());
		sb.append("<br>아이디 : " + member.getLoginId());
		sb.append("<br>이름 : " + member.getName());
		
		return sb.toString();
	}
}

//http://localhost:8081/usr/member/doJoin?loginId=1&loginPw=1&name=abc&nickname=toto&cellphoneNum=1&email=abc@gmail.com
//ResultData -> 표준 보고서 양식
//성공, 실패 쉽게 판단이 가능하도록 / 관련 데이터를 같이 주고 받을 수 있도록