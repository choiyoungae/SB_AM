package com.cya.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.ReplyService;
import com.cya.exam.demo.util.Ut;
import com.cya.exam.demo.vo.Reply;
import com.cya.exam.demo.vo.ResultData;
import com.cya.exam.demo.vo.Rq;

@Controller
public class UsrReplyController {
	@Autowired
	private Rq rq;
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doAdd(String relTypeCode, int relId, String body, String replaceUri) {

		if (Ut.isEmpty(relTypeCode)) {
			return rq.jsHistoryBack("relTypeCode을(를) 입력해주세요");
		}

		if (Ut.isEmpty(relId)) {
			return rq.jsHistoryBack("relId을(를) 입력해주세요");
		}

		if (Ut.isEmpty(body)) {
			return rq.jsHistoryBack("body을(를) 입력해주세요");
		}


		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);
		int id = writeReplyRd.getData1();
		
		if (Ut.isEmpty(replaceUri)) {
			switch (relTypeCode) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", relId);
				break;
			}

		}
		
		return Ut.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		
		Reply reply = replyService.getOneReply(rq.getLoginedMemberId(), id);
		
		ResultData actorCanDeleteRd = replyService.actorCanDelete(rq.getLoginedMemberId(), reply);
		
		if(actorCanDeleteRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanDeleteRd.getMsg());
		}
		
		replyService.deleteReply(id);
		return rq.jsReplace("댓글이 삭제되었습니다.", Ut.f("../article/detail?id=%d", reply.getRelId()));
	}
	
//	@RequestMapping("/usr/reply/modify")
//	public String showModify(Model model, int id) {
//
//		Reply reply = replyService.getForPrintArticle(rq.getLoginedMemberId(), id);
//		
//		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);
//		
//		if(actorCanModifyRd.isFail()) {
//			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
//		}
//		
//		model.addAttribute("reply", reply);
//		
//		return "usr/reply/modify";
//	}
//
//	@RequestMapping("/usr/reply/doModify")
//	@ResponseBody
//	public String doModify(int id, String title, String body) {
//		
//		Reply reply = replyService.getForPrintArticle(rq.getLoginedMemberId(), id);
//		
//		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);
//		
//		if(actorCanModifyRd.isFail()) {
//			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
//		}
//		
//		replyService.modifyArticle(id, title, body);
//		
//		return rq.jsReplace("댓글이 수정되었습니다.", Ut.f("../article/detail?id=%d", reply.getRelId()));
//	}
	
}
