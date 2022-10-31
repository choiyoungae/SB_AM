package com.cya.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cya.exam.demo.service.ReplyService;
import com.cya.exam.demo.util.Ut;
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
	public String doAdd(String relTypeCode, int relId, String body) {

		if (Ut.isEmpty(body)) {
			return Ut.jsHistoryBack("내용을 입력해주세요");
		}

		ResultData<Integer> writeReplyRd = replyService.writeReply(relTypeCode, relId, body, rq.getLoginedMemberId());

		String replaceUri = Ut.f("../article/detail?id=%d", relId);
		
		return Ut.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}
	
}
