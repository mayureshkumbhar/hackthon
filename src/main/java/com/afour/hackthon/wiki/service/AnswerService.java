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
import com.afour.hackthon.wiki.entity.AnswerEntity;
import com.afour.hackthon.wiki.entity.QuestionEntity;
import com.afour.hackthon.wiki.entity.UserProfileEntity;
import com.afour.hackthon.wiki.exceptions.NotFoundException;
import com.afour.hackthon.wiki.repository.IAnswerRepository;
import com.afour.hackthon.wiki.repository.IQuestionRepository;
import com.afour.hackthon.wiki.repository.IUserProfileRepository;
import com.afour.hackthon.wiki.vo.AnswerVO;
import com.afour.hackthon.wiki.vo.QuestionVO;
import com.afour.hackthon.wiki.vo.UserProfileVO;

@Service
public class AnswerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnswerService.class);
	
	private final ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private TaggingService tagService;
	
	
	@Autowired
	private IAnswerRepository answerRepository;
	
	@Autowired
	private IUserProfileRepository userProfileRepository;
	
	@Autowired 
	private IQuestionRepository questionRepository;
	
	
	public AnswerVO getAnswer(String ansId) {
		
		Optional<AnswerEntity> optional =  answerRepository.findById(ansId);
		if(!optional.isPresent()) {
			LOGGER.error("Answer not found for id: " + ansId);
			throw new NotFoundException("Answer not found");
		}
		
		AnswerEntity answerEntity = optional.get();
		AnswerVO answerVO = modelMapper.map(answerEntity, AnswerVO.class);
		LOGGER.debug("Answer for id {} found {}", ansId,answerVO);
		return answerVO;
	}

	public AnswerVO postAnswer(AnswerVO answerVO) {
		String validationMsg = answerVO.validate();
		if(StringUtils.isNotBlank(validationMsg)) {
			throw new ValidationException(validationMsg);
		}
		
		Optional<UserProfileEntity> userEntity = userProfileRepository.findById(answerVO.getCreatedBy().getId());
		if(!userEntity.isPresent()) {
			LOGGER.error("UserProfile for not found for id: " + answerVO.getCreatedBy());
			throw new NotFoundException("UserProfile for not found");
		}
		UserProfileEntity userProfileModel = userEntity.get();
		
		
		Optional<QuestionEntity> entity =  questionRepository.findById(answerVO.getQuestion().getId());
		if(!entity.isPresent()) {
			LOGGER.error("Questions not found for id: " + answerVO.getQuestion().getId());
			throw new NotFoundException("Question for not found");
		}
		
		QuestionEntity questionEntity = entity.get();
		
		
		Map<String, Object> result = tagService.getTag(answerVO.getAnswer(),false);
		
		AnswerEntity answerEntity = new  AnswerEntity();
		answerEntity.setAnswer(answerVO.getAnswer());
		answerEntity.setCreateBy(userProfileModel);
		answerEntity.setQuestion(questionEntity);
		answerEntity.setCreateDate(new Date());
		answerEntity.setSpam((boolean) result.get("isSpam"));
		answerEntity = answerRepository.insert(answerEntity);
		
		AnswerVO postedAnswerVO = convertToVO(answerEntity); 
		LOGGER.debug("Posted new answer {}" , postedAnswerVO);
		return postedAnswerVO;
	
	}

	public AnswerVO updateAnswer(String ansId, AnswerVO answerVO) {
	
		String validationMsg = answerVO.validate();
		if(StringUtils.isNotBlank(validationMsg)) {
			throw new ValidationException(validationMsg);
		}
		Map<String, Object> result = tagService.getTag(answerVO.getAnswer(),false);
		
		Optional<AnswerEntity> optional = answerRepository.findById(ansId);
		if(!optional.isPresent()) {
			LOGGER.error("Answer found for id: " + ansId);
			throw new NotFoundException("Answer for not found");
		}
		AnswerEntity currAnswerEntity = optional.get();
		
		currAnswerEntity.setAnswer(answerVO.getAnswer());
		currAnswerEntity.setAccpeted(false);
		currAnswerEntity.setSpam((boolean) result.get("isSpam"));
		currAnswerEntity.setModifiedDate(new Date());
		currAnswerEntity.setUpBy(Collections.EMPTY_SET);
		currAnswerEntity.setDownBy(Collections.EMPTY_SET);
		currAnswerEntity = answerRepository.save(currAnswerEntity);
		
		AnswerVO updatedAnswerVO = convertToVO(currAnswerEntity);
		LOGGER.debug("Updated answer for id {} as {}", ansId, updatedAnswerVO);
		return updatedAnswerVO;
	}

	public List<AnswerVO> getAnswersForQuestion(String qstnId) {
		
		List<AnswerEntity> answerEntities = new LinkedList<>(); 
		if(StringUtils.isNotBlank(qstnId)) {
			answerEntities = answerRepository.findAnswersForQuestion(qstnId, new Sort(Sort.Direction.DESC, "createdDate"));
		}
		
		List<AnswerVO> answerVOs = answerEntities.stream()
				.map(answerEntity -> convertToVO(answerEntity))
				.collect(Collectors.toList());
		LOGGER.debug("Answer found for qstnId {} are {}", qstnId, answerVOs);
		return answerVOs;
	}

	private AnswerVO convertToVO(AnswerEntity answerEntity) {
		UserProfileVO profileVO = modelMapper.map(answerEntity.getCreateBy(),UserProfileVO.class);
		QuestionVO questionVO = modelMapper.map(answerEntity.getQuestion(), QuestionVO.class);
		AnswerVO answerVO  = modelMapper.map(answerEntity, AnswerVO.class);
		answerVO.setCreatedBy(profileVO);
		answerVO.setQuestion(questionVO);
		return answerVO;
	}

	public AnswerVO vote(String type, String ansId, String userId) {
		
		boolean status = false;
		LOGGER.debug("Adding vote to ans [{}] with type [{}] by user [{}]",
				new Object[] {ansId, type, userId});
		if(StringUtils.isBlank(type) || StringUtils.isBlank(ansId) || StringUtils.isBlank(userId))
			throw new ValidationException("Invalid request params");
		
		Optional<AnswerEntity> optional = answerRepository.findById(ansId);
		if(!optional.isPresent()) {
			LOGGER.error("Answer found for id: " + ansId);
			throw new NotFoundException("Answer for not found");
		}

		AnswerEntity currAnswerEntity = optional.get();
		
		Optional<UserProfileEntity> userEntity = userProfileRepository.findById(currAnswerEntity.getCreateBy().getId());
		if(!userEntity.isPresent()) {
			LOGGER.error("UserProfile for not found for id: " + currAnswerEntity.getCreateBy());
			throw new NotFoundException("UserProfile for not found");
		}
		UserProfileEntity userProfileModel = userEntity.get();
		
		
		
		if("UP".equalsIgnoreCase(type)) {
			Set<String> upBySet = currAnswerEntity.getUpBy();
			Set<String> downBySet = currAnswerEntity.getDownBy();
			if(CollectionUtils.isEmpty(upBySet)) 
				upBySet = new HashSet<>();
			if(CollectionUtils.isEmpty(downBySet)) 
				downBySet = new HashSet<>();
			
			upBySet.add(userId);
			downBySet.remove(userId);
			currAnswerEntity.setUpBy(upBySet);
			currAnswerEntity.setDownBy(downBySet);
			userProfileModel.setKarma(userProfileModel.getKarma() + Constants.VOTE_POINT);
			status = true;
		} else if("DOWN".equalsIgnoreCase(type)) {
			Set<String> upBySet = currAnswerEntity.getUpBy();
			Set<String> downBySet = currAnswerEntity.getDownBy();
			if(CollectionUtils.isEmpty(upBySet)) 
				upBySet = new HashSet<>();
			if(CollectionUtils.isEmpty(downBySet)) 
				downBySet = new HashSet<>();
			
			downBySet.add(userId);
			upBySet.remove(userId);
			currAnswerEntity.setDownBy(downBySet);
			currAnswerEntity.setUpBy(upBySet);
			userProfileModel.setKarma(userProfileModel.getKarma() - Constants.VOTE_POINT);
			status = true;
		}
		if(status) {
			currAnswerEntity = answerRepository.save(currAnswerEntity);
			userProfileModel = userProfileRepository.save(userProfileModel);
		}
		
		AnswerVO answerVO = convertToVO(currAnswerEntity);
		LOGGER.debug("Vote updated to answer {}",currAnswerEntity);
		return answerVO;
	}

	public AnswerVO accpet(String ansId) {
		
		if(StringUtils.isBlank(ansId))
			throw new ValidationException("Invalid request params");
		
		Optional<AnswerEntity> optional = answerRepository.findById(ansId);
		if(!optional.isPresent()) {
			LOGGER.error("Answer found for id: " + ansId);
			throw new NotFoundException("Answer for not found");
		}
		AnswerEntity currAnswerEntity = optional.get();
		currAnswerEntity.setAccpeted(true);
		
		UserProfileEntity userProfileModel = currAnswerEntity.getCreateBy();
		userProfileModel.setKarma(userProfileModel.getKarma() + Constants.ACCPTD_POINT);
		currAnswerEntity.setCreateBy(userProfileModel);
		
		userProfileModel = userProfileRepository.save(userProfileModel);
		currAnswerEntity = answerRepository.save(currAnswerEntity);
		
		AnswerVO answerVO = convertToVO(currAnswerEntity);
		LOGGER.debug("Answer accpeted {}", answerVO);
		return answerVO;
	}
	
}
