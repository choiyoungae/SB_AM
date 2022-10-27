package com.cya.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.exam.demo.repository.ReactionPointRepository;
import com.cya.exam.demo.vo.ResultData;


@Service
public class ReactionPointService {
	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;

	public boolean actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		if(actorId == 0) {
			return false;
		}
		
		return reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId) == 0;
	}

	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.increaseGoodReactionPoint(relId);
			break;
		}
		
		return ResultData.from("S-1", "좋아요 처리 완료");
	}

	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
		}
		
		return ResultData.from("S-1", "별로예요 처리 완료");
	}

	public boolean actorCanMakeGoodReaction(int actorId, String relTypeCode, int relId) {
		if(actorId == 0) {
			return false;
		}
		
		return reactionPointRepository.getGoodReactionPointByMemberId(actorId, relTypeCode, relId) == 0;
	}

	public boolean actorCanMakeBadReaction(int actorId, String relTypeCode, int relId) {
		if(actorId == 0) {
			return false;
		}
		
		return reactionPointRepository.getBadReactionPointByMemberId(actorId, relTypeCode, relId) == 0;
	}

	public void cancelGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.cancelGoodReactionPoint(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.decreaseGoodReactionPoint(relId);
			break;
		}
	}

	public void cancelBadReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.cancelBadReactionPoint(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.decreaseBadReactionPoint(relId);
			break;
		}
	}


}
