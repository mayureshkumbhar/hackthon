package com.afour.hackthon.wiki.service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.hackthon.wiki.entity.QuestionEntity;
import com.afour.hackthon.wiki.entity.UserProfileEntity;
import com.afour.hackthon.wiki.exceptions.NotFoundException;
import com.afour.hackthon.wiki.repository.IQuestionRepository;
import com.afour.hackthon.wiki.repository.IUserProfileRepository;
import com.afour.hackthon.wiki.vo.QuestionVO;

@Service
public class QuestionService {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);
	
	private final ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private TaggingService tagService;
	
	@Autowired
	private IUserProfileRepository userProfileRepository;
	
	@Autowired IQuestionRepository questionRepository;
	
	
	public Set<QuestionVO> getQuestions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	public QuestionVO getQuestion(String qstnId) {
		Optional<QuestionEntity> entity =  questionRepository.findById(qstnId);
		if(!entity.isPresent()) {
			LOGGER.error("Questions not found for id: " + qstnId);
			throw new NotFoundException("Question not found");
		}
		
		QuestionEntity questionEntity =entity.get();
		QuestionVO questionVO = modelMapper.map(questionEntity, QuestionVO.class);
		LOGGER.debug("Question for id [{}] : {}" , qstnId, questionVO);
		return questionVO;
	}

	public QuestionVO postQuestion(QuestionVO questionVO) {
		
		String validationMsg = questionVO.validate();
		if(StringUtils.isNotBlank(validationMsg)) {
			throw new ValidationException(validationMsg);
		}
		
		Optional<UserProfileEntity> entity = userProfileRepository.findById(questionVO.getCreatedBy().getId());
		if(!entity.isPresent()) {
			LOGGER.error("UserProfile for not found for id: " + questionVO.getCreatedBy());
			throw new NotFoundException("UserProfile for not found");
		}
		
		UserProfileEntity userProfileModel = entity.get();
		
		Set<String> tags  = tagService.getTag(questionVO.getQuestion());
		
		QuestionEntity questionEntity = new QuestionEntity();
		questionEntity.setQuestion(questionVO.getQuestion());
		questionEntity.setCreatedBy(userProfileModel);
		questionEntity.setCreatedDate(new Date());
		questionEntity.setTags(tags);
		questionEntity = questionRepository.insert(questionEntity);
		
		QuestionVO postedQuestionVO = modelMapper.map(questionEntity, QuestionVO.class);
		return postedQuestionVO;
	}

	public QuestionVO updateQuestion(String qstnId, QuestionVO questionVO) {
		
		String validationMsg = questionVO.validate();
		if(StringUtils.isNotBlank(validationMsg)) {
			throw new ValidationException(validationMsg);
		}
		
		Optional<QuestionEntity> entity =  questionRepository.findById(qstnId);
		if(!entity.isPresent()) {
			LOGGER.error("Questions not found for id: " + qstnId);
			throw new NotFoundException("Question for not found");
		}
		
		QuestionEntity currQuestionEntity = entity.get();
		
		Set<String> tags  = tagService.getTag(questionVO.getQuestion());
		
		currQuestionEntity.setQuestion(questionVO.getQuestion());
		currQuestionEntity.setModifiedDate(new Date());
		currQuestionEntity.setTags(tags);
		currQuestionEntity.setSpam(false);
		currQuestionEntity.setSpammedBy(Collections.EMPTY_SET);
		currQuestionEntity = questionRepository.save(currQuestionEntity);
		
		QuestionVO updatedQuestionVO = modelMapper.map(currQuestionEntity, QuestionVO.class);
		return updatedQuestionVO;
		
	}

	

}
