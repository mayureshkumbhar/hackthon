package com.afour.hackthon.wiki.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.hackthon.wiki.repository.IBlogRepository;
import com.afour.hackthon.wiki.repository.IQuestionRepository;
import com.afour.hackthon.wiki.repository.IUserProfileRepository;

@Service
public class CommonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);
			
	
	@Autowired
	private IQuestionRepository questionRepository;
	
	@Autowired
	private IUserProfileRepository userProfileRepository;
	
	@Autowired
	private IBlogRepository blogRepository ;
	
	@Autowired
	private QuestionService questionService;
	
	
	
	public Map<String, Long> getStats() {
		Map<String, Long> counts = new HashMap<>();
		counts.put("questions", questionRepository.count());
		counts.put("users", userProfileRepository.count());
		counts.put("tags",  (long) questionService.getTagsSet().size());
		counts.put("blogs", blogRepository.count());
		LOGGER.debug("Stats: {}", counts);
		return counts;
	}

}
