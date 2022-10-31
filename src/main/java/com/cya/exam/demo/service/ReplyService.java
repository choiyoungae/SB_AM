package com.cya.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.exam.demo.repository.ReactionPointRepository;
import com.cya.exam.demo.repository.ReplyRepository;
import com.cya.exam.demo.util.Ut;
import com.cya.exam.demo.vo.ResultData;


@Service
public class ReplyService {
	@Autowired
	private ReplyRepository replyRepository;

	public ResultData<Integer> writeReply(String relTypeCode, int relId, String body, int actorId) {
		replyRepository.writeArticle(relTypeCode, relId, body, actorId);

		return ResultData.from("S-1", "댓글이 작성되었습니다.");
	}

	


}
