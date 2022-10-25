package com.cya.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.ReactionPointService;
import com.cya.exam.demo.vo.ResultData;
import com.cya.exam.demo.vo.Rq;

@Controller
public class ReactionPointController {

	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/reaction/doIncreaseGoodReactionPointRd")
	@ResponseBody
	public ResultData<Integer> doIncreaseGoodReactionPointRd(int id) {
		ResultData<Integer> increaseGoodReactionPointRd = reactionPointService.increaseGoodReactionPoint(rq.getLoginedMemberId(), id);
		
		if(increaseGoodReactionPointRd.isFail()) {
			return increaseGoodReactionPointRd;
		}
		
		ResultData<Integer> rd = ResultData.newData(increaseGoodReactionPointRd, "sumReactionPoint",
				reactionPointService.getArticleSumReactionPoint(id));

		rd.setData2("id", id);

		return rd;
	}
	
	@RequestMapping("/usr/reaction/doIncreaseBadReactionPointRd")
	@ResponseBody
	public ResultData<Integer> doIncreaseBadReactionPointRd(int id) {
		ResultData<Integer> increaseBadReactionPointRd = reactionPointService.increaseBadReactionPoint(rq.getLoginedMemberId(), id);
		
		if(increaseBadReactionPointRd.isFail()) {
			return increaseBadReactionPointRd;
		}
		
		ResultData<Integer> rd = ResultData.newData(increaseBadReactionPointRd, "sumReactionPoint",
				reactionPointService.getArticleSumReactionPoint(id));

		rd.setData2("id", id);

		return rd;
	}
}
