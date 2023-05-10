package com.KoreaIT.cwy.demo.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.repository.MemberRepository;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Member;
import com.KoreaIT.cwy.demo.vo.ResultData;

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

	public ResultData<Member> getMemberById(int id) {
		
		Member member = memberRepository.getMemberById(id);
		
		return ResultData.from("S-1", "멤버를 찾았습니다.","member", member);
	}

	public ResultData<Member> login(String loginId, String loginPw) {
		
		Member member = memberRepository.getMemberByLoginId(loginId);
		
		return ResultData.from("S-1", Ut.f("%s님 환영합니다.", member.getNickname()),"member", member);
	}

	public ResultData<String> doModifyMember(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		memberRepository.doModifyMember(id, loginPw, name, nickname, cellphoneNum, email);
		
		return ResultData.from("S-1", nickname+"회원이 수정되었습니다.","nickname", nickname);
	}

	public ResultData<Integer> doDeleteMember(int id) {
		memberRepository.doDeleteMember(id);
		
		return ResultData.from("S-1", "회원이 삭제되었습니다.","id", id);
	}

	public ResultData<Boolean> getMemberByLoginId(String loginId) {
		
		if(memberRepository.isDupLoginId(loginId)) {
			return ResultData.from("F-2", "중복된 아이디 입니다.","isDupLoginId", true);
		}
		else {
			return ResultData.from("S-1", "사용 가능한 아이디 입니다.","isDupLoginId", false);
		}
	}

	public ResultData<String> getMemberByNameAndEmail(String name, String email) {
		
		Member member = memberRepository.getMemberByNameAndEmail(name, email);
		
		if(member == null) {
			return ResultData.from("F-1", "찾은 아이디가 없습니다.");
		}
		
		return ResultData.from("S-1", "찾은 아이디는 "+member.getLoginId()+"입니다", "loginId", member.getLoginId());
	}

	public ResultData<String> getLoginPwByNameAndEmail(String name, String email) {
		String loginPw = memberRepository.getLoginPwByNameAndEmail(name, email);
		
		return ResultData.from("S-1", "찾은 비밀번호는 "+loginPw+"입니다", "loginPw", loginPw);
	}
}
