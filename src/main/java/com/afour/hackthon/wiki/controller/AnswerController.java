package com.afour.hackthon.wiki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.service.AnswerService;
import com.afour.hackthon.wiki.vo.AnswerVO;

@RestController
@RequestMapping("/answer")
public class AnswerController {

	@Autowired
	private AnswerService answerService;  
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AnswerVO> getAnswer(@PathVariable("id") String ansId){
		AnswerVO answerVO = answerService.getAnswer(ansId);
		return new ResponseEntity<>(answerVO,HttpStatus.OK);
	}
	
	@PostMapping(consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AnswerVO> postAnswer(@RequestBody AnswerVO answerVO){
		AnswerVO newAnswerVO = answerService.postAnswer(answerVO);
		return new ResponseEntity<>(newAnswerVO,HttpStatus.OK);
	}
	
	@PostMapping(value="/{id}", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AnswerVO> postAnswer(@PathVariable("id") String ansId, @RequestBody AnswerVO answerVO){
		AnswerVO newAnswerVO = answerService.updateAnswer(ansId, answerVO);
		return new ResponseEntity<>(newAnswerVO,HttpStatus.OK);
	}

	@GetMapping(value="/{qstnId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AnswerVO>> getAnswersForQuestion(@PathVariable("qstnId") String qstnId){
		List<AnswerVO> answerVOs = answerService.getAnswersForQuestion(qstnId);
		return new ResponseEntity<>(answerVOs,HttpStatus.OK);
	}
	
}
