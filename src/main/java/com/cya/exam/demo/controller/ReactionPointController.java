package com.cya.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.ReactionPointService;
import com.cya.exam.demo.vo.Rq;

@Controller
public class ReactionPointController {
	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
		
		boolean actorCanMakeGoodReaction = reactionPointService.actorCanMakeGoodReaction(rq.getLoginedMemberId(), relTypeCode, relId);
		boolean actorCanMakeBadReaction = reactionPointService.actorCanMakeBadReaction(rq.getLoginedMemberId(), relTypeCode, relId);
		
		if(actorCanMakeBadReaction == false) {

			return rq.jsReplace("이미 [별로예요]를 선택하셨습니다.", replaceUri);
		}
		
		if(actorCanMakeGoodReaction == false) {
			reactionPointService.cancelGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

			return rq.jsReplace("좋아요 취소", replaceUri);
		}
		
		reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace("좋아요!", replaceUri);
	}
	
	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {
		
		boolean actorCanMakeGoodReaction = reactionPointService.actorCanMakeGoodReaction(rq.getLoginedMemberId(), relTypeCode, relId);
		boolean actorCanMakeBadReaction = reactionPointService.actorCanMakeBadReaction(rq.getLoginedMemberId(), relTypeCode, relId);
		
		if(actorCanMakeGoodReaction == false) {

			return rq.jsReplace("이미 [좋아요]를 선택하셨습니다.", replaceUri);
		}
		
		if(actorCanMakeBadReaction == false) {
			reactionPointService.cancelBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

			return rq.jsReplace("별로예요 취소", replaceUri);
		}
		
		reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace("별로예요!", replaceUri);
	}
}
