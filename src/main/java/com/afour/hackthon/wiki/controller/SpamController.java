package com.afour.hackthon.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.service.SpamService;
import com.afour.hackthon.wiki.vo.AnswerVO;
import com.afour.hackthon.wiki.vo.QuestionVO;

@RestController
@RequestMapping("/spam")
public class SpamController {

	@Autowired
	SpamService spamService;
	
	@PutMapping(value="/question", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<QuestionVO> reportSpamQstn(@RequestParam("qstnId") String qstnId, @RequestParam("userId") String userId) {
		QuestionVO questionVO = spamService.reportSpamQstn(qstnId,userId);
		return new ResponseEntity<>(questionVO, HttpStatus.OK);
	}
	
	@PutMapping(value="/answer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AnswerVO> reportSpamAns(@RequestParam("ansId") String ansId, @RequestParam("userId") String userId) {
		AnswerVO answerVOVO = spamService.reportSpamAns(ansId,userId);
		return new ResponseEntity<>(answerVOVO, HttpStatus.OK);
	}
	
}
