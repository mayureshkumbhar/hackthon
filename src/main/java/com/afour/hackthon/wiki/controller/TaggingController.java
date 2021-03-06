package com.afour.hackthon.wiki.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.entity.QuestionEntity;
import com.afour.hackthon.wiki.service.QuestionService;
import com.afour.hackthon.wiki.service.TaggingService;

@RestController
public class TaggingController {

	@Autowired
	private TaggingService tagService;
	
	
	@Autowired
	private QuestionService questionService;

	
	@PostMapping( value = "/tags")
	public ResponseEntity<Map> processTags(@RequestBody Map<String, Object> params) {
		//Map<String, Set<String>> result = tagService.callTaggingService(params);
		
		
		Map<String, Object> result = tagService.getTag((String)params.get("input"), (Boolean)params.get("isQuestion"));
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@GetMapping(value="/tags/getAllTags")
	public ResponseEntity<Set<String>> getAllTags() {
		Set<String> allTags = questionService.getTagsSet() ;
		return new ResponseEntity<>(allTags,HttpStatus.OK);
	}
	
}
