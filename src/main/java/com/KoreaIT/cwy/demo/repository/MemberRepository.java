package com.KoreaIT.cwy.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.KoreaIT.cwy.demo.vo.Member;

@Mapper
public interface MemberRepository {

	public void doJoinMember(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	public Member getMemberById(int id);

	public boolean isDupLoginId(String loginId);

	public int getLastInsertId();

	public boolean isDupNameAndEmail(String name, String email);

	public Member getMemberByLoginId(String loginId);

	@Update("""
			<script>
			UPDATE member
			<set>
			loginId = '${loginId}',
			loginPw = '${loginPw}',
			name = '${name}',
			nickname = '${nickname}',
			cellphoneNum = '${cellphoneNum}',
			email = '${email}',
			updateDate= NOW()
			</set>
			WHERE id = #{id}
			</script>
			""")
	public void doModifyMember(int id, String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email);

	@Update("""
			<script>
			UPDATE member
			<set>
			delStatus = 1,
			delDate = NOW()
			</set>
			WHERE id = #{id}
			</script>
			""")
	public void doDeleteMember(int id);
}