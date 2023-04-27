package com.KoreaIT.cwy.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReactionRepository {

	public void doIncreaseGoodReaction(int id, int memberId);

	public boolean isAlreadyReaction(int id, int memberId);
	
}
