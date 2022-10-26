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
}
