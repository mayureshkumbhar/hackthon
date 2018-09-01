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

import com.afour.hackthon.wiki.service.BlogService;
import com.afour.hackthon.wiki.vo.BlogVO;
import com.afour.hackthon.wiki.vo.QuestionVO;

@RestController
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@GetMapping(value="/{id}", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BlogVO> getBlog(@PathVariable("id") String blogId,@RequestParam(value = "userId", required = false) String userId) {
		BlogVO blogVO = blogService.getBlog(blogId,userId);
		return new ResponseEntity<>(blogVO, HttpStatus.OK);
	}
	
	@PostMapping(produces= MediaType.APPLICATION_JSON_UTF8_VALUE, consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BlogVO> postBlog(@RequestBody BlogVO blogVO) {
		BlogVO postedBlogVO = blogService.postBlog(blogVO);
		return new ResponseEntity<>(postedBlogVO, HttpStatus.OK);
	}
	
	@PutMapping(value="/{id}", produces= MediaType.APPLICATION_JSON_UTF8_VALUE, consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BlogVO> postBlog(@PathVariable("id") String blogId, @RequestBody BlogVO blogVO) {
		BlogVO updatedBlogVO = blogService.updateBlog(blogId, blogVO);
		return new ResponseEntity<>(updatedBlogVO, HttpStatus.OK);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Set<BlogVO>> getQuestions(@RequestParam(value = "user", required = false) String userId) {
		Set<BlogVO> blogVOs = blogService.getBlogs(userId);
		return new ResponseEntity<>(blogVOs,HttpStatus.OK);
	}
}
