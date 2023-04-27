package com.KoreaIT.cwy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.service.ReactionService;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.ResultData;

import com.KoreaIT.cwy.demo.vo.Rq;

@Controller
public class UsrReactionController {
	
	@Autowired
	private ReactionService reactionService;
	@Autowired
	private Rq rq;

	@RequestMapping("/usr/reaction/doIncreaseGoodReactionRd")
	@ResponseBody
	public ResultData doIncreaseGoodReactionRd(int id) {
		
		int memberId = rq.getLoginedMemberId();

		ResultData doIncreaseGoodReactionRd = reactionService.doIncreaseGoodReaction(id, memberId);
		
		if(doIncreaseGoodReactionRd.isFail()) {
			return doIncreaseGoodReactionRd;
		}

		return doIncreaseGoodReactionRd;
	}
	
	@RequestMapping("/usr/reaction/doIncreaseGoodReaction")
	@ResponseBody
	public String doIncreaseGoodReaction(int id) {
		
		int memberId = rq.getLoginedMemberId();

		ResultData doIncreaseGoodReactionRd = reactionService.doIncreaseGoodReaction(id, memberId);
		
		if(doIncreaseGoodReactionRd.isFail()) {
			return Ut.jsReplace("F-1", "이미 좋아요를 눌렀습니다.", "../article/detail?id="+id);
		}

		return Ut.jsReplace("S-1", "해당 글에 좋아요를 눌렀습니다.", "../article/detail?id="+id);
	}
}
