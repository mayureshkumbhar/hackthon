package com.afour.hackthon.wiki.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.service.QuestionService;
import com.afour.hackthon.wiki.vo.QuestionVO;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService; 
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Set<QuestionVO>> getQuestions(@RequestParam(value = "user", required = false) String userId) {
		Set<QuestionVO> questionVOs = questionService.getQuestions(userId);
		return new ResponseEntity<>(questionVOs,HttpStatus.OK);
	}
	
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<QuestionVO> getQuestion(@PathVariable("id") String qstnId, @RequestParam(value = "userId", required = false) String userId) {
		QuestionVO questionVO = questionService.getQuestion(qstnId, userId);
		return new ResponseEntity<>(questionVO,HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<QuestionVO> postQuestion(@RequestBody QuestionVO questionVO) {
		QuestionVO postedQuestionVO = questionService.postQuestion(questionVO);
		return new ResponseEntity<>(postedQuestionVO,HttpStatus.OK);
	}
	
	@PutMapping(value="{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<QuestionVO> updateQuestion(@PathVariable("id") String qstnId, @RequestBody QuestionVO questionVO) {
		QuestionVO updatedQuestionVO = questionService.updateQuestion(qstnId,questionVO);
		return new ResponseEntity<>(updatedQuestionVO,HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllQuestionsForTag", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Set<QuestionVO>> getAllQuestionsForTag(@RequestParam("tag") String tag) {
		Set<QuestionVO> questionVO = questionService.getAllQuestionsForTag(tag);
		return new ResponseEntity<>(questionVO,HttpStatus.OK);
	}
}
