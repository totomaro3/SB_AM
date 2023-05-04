package com.KoreaIT.cwy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.vo.Member;
import com.KoreaIT.cwy.demo.vo.ResultData;
import com.KoreaIT.cwy.demo.controller.UsrMemberController;
import com.KoreaIT.cwy.demo.repository.MemberRepository;
import com.KoreaIT.cwy.demo.util.Ut;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		if(memberRepository.isDupLoginId(loginId)) {
			return ResultData.from("F-7", Ut.f("이미 사용중인 아이디(%s)입니다", loginId));
		}
		
		if(memberRepository.isDupNameAndEmail(name,email)) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email));
		}
		
		memberRepository.doJoinMember(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		int id =  memberRepository.getLastInsertId();
		
		return ResultData.from("S-1", nickname+"회원이 가입되었습니다.","id", id);
		
	}

	public Member getMemberById(int id) {
		
		return memberRepository.getMemberById(id);
	}

	public Member login(String loginId, String loginPw) {

		return memberRepository.getMemberByLoginId(loginId);
	}

	public ResultData<String> doModifyMember(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		memberRepository.doModifyMember(id, loginPw, name, nickname, cellphoneNum, email);
		
		return ResultData.from("S-1", nickname+"회원이 수정되었습니다.","nickname", nickname);
	}

	public ResultData<Integer> doDeleteMember(int id) {
		memberRepository.doDeleteMember(id);
		
		return ResultData.from("S-1", "회원이 삭제되었습니다.","id", id);
	}
}
