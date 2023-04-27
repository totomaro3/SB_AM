package com.KoreaIT.cwy.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.cwy.demo.repository.ArticleRepository;
import com.KoreaIT.cwy.demo.repository.ReactionRepository;
import com.KoreaIT.cwy.demo.vo.ResultData;

@Service
public class ReactionService {

	@Autowired
	ReactionRepository reactionRepository;
	
	public ResultData doIncreaseGoodReaction(int id, int memberId) {
		
		if(reactionRepository.isAlreadyReaction(id, memberId)) {
			return ResultData.from("F-1", "이미 좋아요를 눌렀습니다.");
		}
		
		reactionRepository.doIncreaseGoodReaction(id, memberId);
		return ResultData.from("S-1", "해당 글을 좋아요했습니다.");
	}
	
}
