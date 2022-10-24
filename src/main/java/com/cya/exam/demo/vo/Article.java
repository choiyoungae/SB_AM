package com.cya.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	private int id;
	private String title;
	private String body;
	private String regDate;
	private String updateDate;
	private int memberId;
	private int boardId;
	private int hitCount;
	
	private String extra__writerName;
	private boolean extra__actorCanDelete;
	private boolean extra__actorCanModify;
	
	private int extra__sumReactionPoint;
	private int extra__goodReactionPoint;
	private int extra__badReactionPoint;
	
	public String getForPrintType1RegDate() {
		return regDate.substring(2, 16).replace(" ", "<br />");
	}
}