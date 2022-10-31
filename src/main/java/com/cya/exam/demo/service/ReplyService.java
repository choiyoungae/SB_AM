package com.cya.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.exam.demo.repository.ReactionPointRepository;
import com.cya.exam.demo.repository.ReplyRepository;
import com.cya.exam.demo.util.Ut;
import com.cya.exam.demo.vo.Reply;
import com.cya.exam.demo.vo.ResultData;


@Service
public class ReplyService {
	@Autowired
	private ReplyRepository replyRepository;

	public ResultData<Integer> writeReply(int actorId, String relTypeCode, int relId, String body) {
		replyRepository.writeArticle(actorId, relTypeCode, relId, body);
		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 댓글이 등록되었습니다", id), "id", id);
	}

	public List<Reply> getForPrintReplies(int id) {
		return replyRepository.getForPrintReplies("article", id);
	}

	public int getRepliesCount(int id) {
		return replyRepository.getRepliesCount("article", id);
	}

	


}
