package com.KoreaIT.cwy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.service.ReactionPointService;
import com.KoreaIT.cwy.demo.vo.ResultData;
import com.KoreaIT.cwy.demo.vo.Rq;

@Controller
public class UsrReactionPointController {
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;

	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
		boolean actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode,
				relId);
		
		if (actorCanMakeReaction == false) {
			boolean actorHasGoodReaction = reactionPointService.actorHasGoodReaction(rq.getLoginedMemberId(), relTypeCode,
					relId);
			
			if(actorHasGoodReaction == true) {
				reactionPointService.delGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
				return rq.jsReplace("좋아요 취소", replaceUri);
			}
			else {
				reactionPointService.delBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
				reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
				return rq.jsReplace("싫어요 취소 그리고 좋아요!", replaceUri);
			}
		}

		ResultData rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "좋아요 실패");
		}

		return rq.jsReplace("좋아요!", replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {
		boolean actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode,
				relId);
		if (actorCanMakeReaction == false) {
			boolean actorHasBadReaction = reactionPointService.actorHasBadReaction(rq.getLoginedMemberId(), relTypeCode,
					relId);
			
			if(actorHasBadReaction == true) {
				reactionPointService.delBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
				return rq.jsReplace("싫어요 취소", replaceUri);
			}
			else {
				reactionPointService.delGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
				reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
				return rq.jsReplace("좋아요 취소 그리고 싫어요!", replaceUri);
			}
		}

		ResultData rd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "싫어요 실패");
		}

		return rq.jsReplace("싫어요!", replaceUri);
	}

}