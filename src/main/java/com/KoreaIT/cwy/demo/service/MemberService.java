package com.KoreaIT.cwy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.vo.Member;
import com.KoreaIT.cwy.demo.controller.UsrMemberController;
import com.KoreaIT.cwy.demo.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;

	public int doJoinMember(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		if(memberRepository.isDupLoginId(loginId)) {
			return UsrMemberController.isDupLoginId;
		}
		
		if(memberRepository.isDupNameAndEmail(name,email)) {
			return UsrMemberController.isDupNameAndEmail;
		}
		
		memberRepository.doJoinMember(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		return memberRepository.getLastInsertId();
	}

	public Member getMember(int id) {
		
		return memberRepository.getMember(id);
	}
}
