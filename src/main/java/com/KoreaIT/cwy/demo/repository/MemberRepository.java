package com.KoreaIT.cwy.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.cwy.demo.vo.Member;

@Mapper
public interface MemberRepository {

	public void doJoinMember(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	public Member getMember(int id);

	public boolean isDupLoginId(String loginId);

	public int getLastInsertId();

	public boolean isDupNameAndEmail(String name, String email);
}