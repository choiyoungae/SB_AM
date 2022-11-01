package com.cya.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.exam.demo.repository.ReactionPointRepository;
import com.cya.exam.demo.repository.ReplyRepository;
import com.cya.exam.demo.util.Ut;
import com.cya.exam.demo.vo.Article;
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

	public List<Reply> getForPrintReplies(int actorId, int relId, int repliesInAPage, int replyPage) {
		int limitStart = (replyPage - 1) * repliesInAPage;
		int limitTake = repliesInAPage;
		List<Reply> replies = replyRepository.getForPrintReplies("article", relId, limitStart, limitTake);
		
		for (Reply reply : replies) {
			updateForPrintData(actorId, reply);
		}
		
		return replies;
	}

	private void updateForPrintData(int actorId, Reply reply) {
		if(reply == null) {
			return;
		}
		
		ResultData actorCanDeleteRd = actorCanDelete(actorId, reply);
		reply.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actorId, reply);
		reply.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
	}

	public ResultData actorCanDelete(int actorId, Reply reply) {
		if(reply == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다.");
		}
		
		if(reply.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 댓글에 대한 권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "삭제 가능");
	}

	public ResultData actorCanModify(int actorId, Reply reply) {
		if(reply.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 댓글에 대한 권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "수정 가능");
	}


	public int getRepliesCount(int id) {
		return replyRepository.getRepliesCount("article", id);
	}

	public Reply getOneReply(int actorId, int id) {
		Reply reply = replyRepository.getOneReply(id);
		
		updateForPrintData(actorId, reply);
		
		return reply;
	}

	public void deleteReply(int id) {
		replyRepository.deleteReply(id);
	}

	


}
