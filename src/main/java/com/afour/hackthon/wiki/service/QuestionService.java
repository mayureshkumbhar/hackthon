package com.afour.hackthon.wiki.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.afour.hackthon.wiki.commons.Constants;
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
	
	@Autowired 
	private IQuestionRepository questionRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	
	public Set<QuestionVO> getQuestions(String userId) {
		LOGGER.debug("getting questions posted by user {}" , userId);
		
		List<QuestionEntity> questionEntitySet; 
		
		if(StringUtils.isNotBlank(userId)) {
			questionEntitySet = questionRepository.findByUserId(userId, new Sort(Sort.Direction.DESC, "createdDate"));	
		}else {
			questionEntitySet = questionRepository.findAllByNotSpam(new Sort(Sort.Direction.DESC, "createdDate"));
		}
		Set<QuestionVO> questionVOs2 = questionEntitySet.stream()
				 					.map(questionVO -> modelMapper.map(questionVO, QuestionVO.class))
				 					.collect(Collectors.toSet());
		LOGGER.debug("Question posted by user {} are {}", userId, questionVOs2);
		return questionVOs2;
	}
	
	
	
	
	public QuestionVO getQuestion(String qstnId, String userId) {
		Optional<QuestionEntity> entity =  questionRepository.findById(qstnId);
		if(!entity.isPresent()) {
			LOGGER.error("Questions not found for id: " + qstnId);
			throw new NotFoundException("Question not found");
		}
		
		QuestionEntity questionEntity = entity.get();
		
		if(StringUtils.isNotBlank(userId)) {
			Set<String> viewedSet = questionEntity.getViewedBy();
			if(CollectionUtils.isEmpty(viewedSet)) {
				viewedSet = new HashSet<>();
			}
			viewedSet.add(userId);
			questionEntity.setViewedBy(viewedSet);
			questionEntity = questionRepository.save(questionEntity);
		}
		
		
		QuestionVO questionVO = modelMapper.map(questionEntity, QuestionVO.class);
		LOGGER.debug("Question for id [{}] : {}" , qstnId, questionVO);
		return questionVO;
	}

	public QuestionVO postQuestion(QuestionVO questionVO) {
		
		String validationMsg = questionVO.validate();
		if(StringUtils.isNotBlank(validationMsg)) {
			throw new ValidationException(validationMsg);
		}
		
		Optional<UserProfileEntity> userEntity = userProfileRepository.findById(questionVO.getCreatedBy().getId());
		if(!userEntity.isPresent()) {
			LOGGER.error("UserProfile for not found for id: " + questionVO.getCreatedBy());
			throw new NotFoundException("UserProfile for not found");
		}
		
		UserProfileEntity userProfileModel = userEntity.get();
		userProfileModel.setKarma(userProfileModel.getKarma() + Constants.QSTN_ASKD_POINT);
		
		
		Map<String, Object> result = tagService.getTag(questionVO.getQuestion(),true);
		
		QuestionEntity questionEntity = new QuestionEntity();
		questionEntity.setQuestion(questionVO.getQuestion());
		questionEntity.setDescription(questionVO.getDescription());
		questionEntity.setCreatedBy(userProfileModel);
		questionEntity.setCreatedDate(new Date());
		questionEntity.setTags((Set<String>) result.get("tags"));
		questionEntity.setSpam((boolean) result.get("isSpam"));
		questionEntity = questionRepository.insert(questionEntity);
		
		notificationService.saveNotifications(new LinkedList(questionEntity.getTags()), questionEntity.getId());
		
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
		
		Map<String, Object> result = tagService.getTag(questionVO.getQuestion(),true);
		
		currQuestionEntity.setQuestion(questionVO.getQuestion());
		currQuestionEntity.setDescription(questionVO.getDescription());
		currQuestionEntity.setModifiedDate(new Date());
		currQuestionEntity.setTags((Set<String>) result.get("tags"));
		currQuestionEntity.setSpam((boolean) result.get("isSpam"));
		currQuestionEntity.setSpammedBy(Collections.EMPTY_SET);
		currQuestionEntity = questionRepository.save(currQuestionEntity);
		LOGGER.debug("Putting notification for quest {} and tags {}", qstnId,currQuestionEntity.getTags());
		notificationService.saveNotifications(new LinkedList(currQuestionEntity.getTags()), qstnId);
		
		QuestionVO updatedQuestionVO = modelMapper.map(currQuestionEntity, QuestionVO.class);
		return updatedQuestionVO;
		
	}

	public Set<String> getTagsSet() {

		List<QuestionEntity> questionEntityTags = questionRepository.findTagsAndExcludeOther();
		Set<String> allTags = new HashSet<>();
		for(QuestionEntity questionEntity : questionEntityTags)
			allTags.addAll(questionEntity.getTags());
		
		return allTags;
	}

	public Set<QuestionVO> getAllQuestionsForTag(String tag) {
		List<QuestionEntity> allQuestionsForTag = questionRepository.findByTags(tag,new Sort(Sort.Direction.DESC, "createdDate"));
		Set<QuestionVO> questionVOs2 = allQuestionsForTag.stream()
				.map(questionVO -> modelMapper.map(questionVO, QuestionVO.class)).collect(Collectors.toSet());
		LOGGER.debug("Question posted for tag {} are {}", tag, questionVOs2);
		return questionVOs2;
	}
	
}
