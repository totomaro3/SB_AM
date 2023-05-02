package com.KoreaIT.cwy.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.cwy.demo.vo.Reply;

@Mapper
public interface ReplyRepository {

	@Insert("""
			<script>
				INSERT INTO reply
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId =#{relId},
				`body`= #{body}
			</script>
			""")

	void writeReply(int actorId, String relTypeCode, int relId, String body);

	@Select("""
			<script>
				SELECT LAST_INSERT_ID()
			</script>
			""")
	int getLastInsertId();
	
	@Select("""
			<script>
			SELECT R.*, M.nickname AS extra__writer,
			IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
			IFNULL(SUM(IF(RP.point &gt; 0,RP.point,0)),0) AS extra__goodReactionPoint,
			IFNULL(SUM(IF(RP.point &lt; 0,RP.point,0)),0) AS extra__badReactionPoint 
			FROM reply AS R
			INNER JOIN `member` AS M
			ON R.memberId = M.id
			LEFT JOIN reactionPoint AS RP
			ON R.id = RP.relId AND RP.relTypeCode = 'comment'
			WHERE R.relTypeCode = '${relTypeCode}' AND R.relId = ${relId}
			GROUP BY R.id
			</script>
			""")
	public List<Reply> getReplies(int actorId, String relTypeCode, int relId);

}