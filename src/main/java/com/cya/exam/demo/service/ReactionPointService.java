package com.cya.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.exam.demo.repository.ReactionPointRepository;
import com.cya.exam.demo.vo.ResultData;

@Service
public class ReactionPointService {
	
	@Autowired
	private ReactionPointRepository reactionPointRepository;
	
	public ReactionPointService(ReactionPointRepository reactionPointRepository) {
		this.reactionPointRepository = reactionPointRepository;
	}

	public ResultData<Integer> increaseGoodReactionPoint(int loginedMemberId, int id) {
		int affectedRowsCount = reactionPointRepository.increaseGoodReactionPoint(loginedMemberId, id);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "좋아요 증가", "affectedRowsCount", affectedRowsCount);
	}

	public int getArticleSumReactionPoint(int id) {
		return reactionPointRepository.getArticleSumReactionPoint(id);
	}

	public ResultData<Integer> increaseBadReactionPoint(int loginedMemberId, int id) {
		int affectedRowsCount = reactionPointRepository.increaseBadReactionPoint(loginedMemberId, id);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다", "affectedRowsCount", affectedRowsCount);
		}

		return ResultData.from("S-1", "별로예요 증가", "affectedRowsCount", affectedRowsCount);
	}
	
}
