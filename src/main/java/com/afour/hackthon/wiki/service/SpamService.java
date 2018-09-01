package com.afour.hackthon.wiki.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.afour.hackthon.wiki.commons.Constants;
import com.afour.hackthon.wiki.entity.AnswerEntity;
import com.afour.hackthon.wiki.entity.QuestionEntity;
import com.afour.hackthon.wiki.exceptions.NotFoundException;
import com.afour.hackthon.wiki.repository.IAnswerRepository;
import com.afour.hackthon.wiki.repository.IQuestionRepository;
import com.afour.hackthon.wiki.vo.AnswerVO;
import com.afour.hackthon.wiki.vo.QuestionVO;
@Service
public class SpamService {

	
	@Autowired
	private IAnswerRepository answerRepository;
	
	@Autowired 
	private IQuestionRepository questionRepository;
	
	private final ModelMapper modelMapper = new ModelMapper();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpamService.class);
	
	public QuestionVO reportSpamQstn(String qstnId, String userId) {
		
		
		if(StringUtils.isBlank(qstnId)) {
			throw new ValidationException("Question Id should be mentioned");
		}
		
		Optional<QuestionEntity> entity =  questionRepository.findById(qstnId);
		if(!entity.isPresent()) {
			LOGGER.error("Questions not found for id: " + qstnId);
			throw new NotFoundException("Question for not found");
		}
		
		QuestionEntity currQuestionEntity = entity.get();
		
		Set<String> spammedBy = currQuestionEntity.getSpammedBy();
		if(CollectionUtils.isEmpty(spammedBy)) {
			spammedBy = new HashSet<>();	
		}
		spammedBy.add(userId);
		currQuestionEntity.setSpammedBy(spammedBy);
		
		int viewCnt = (null != currQuestionEntity.getViewedBy())? currQuestionEntity.getViewedBy().size() : 0;
		
		boolean isSpam = qualifiedForSpam(viewCnt, spammedBy.size());
		LOGGER.debug("Spam qualification {}", isSpam);
		currQuestionEntity.setSpam(isSpam);
		
		currQuestionEntity = questionRepository.save(currQuestionEntity);
		
		QuestionVO questionVO = modelMapper.map(currQuestionEntity, QuestionVO.class);
		
		LOGGER.debug("questionVO after report spam {}", questionVO);
		return questionVO;
	}

	public AnswerVO reportSpamAns(String ansId, String userId) {
		
		if(StringUtils.isBlank(ansId)) {
			throw new ValidationException("Answer Id should be mentioned");
		}
		
		Optional<AnswerEntity> optional = answerRepository.findById(ansId);
		if(!optional.isPresent()) {
			LOGGER.error("Answer found for id: " + ansId);
			throw new NotFoundException("Answer for not found");
		}
		AnswerEntity currAnswerEntity = optional.get();
		
		Set<String> spammedBy = currAnswerEntity.getSpammedBy();
		if(CollectionUtils.isEmpty(spammedBy)) {
			spammedBy = new HashSet<>();	
		}
		spammedBy.add(userId);
		currAnswerEntity.setSpammedBy(spammedBy);
		
		
		int viewCnt = (null != currAnswerEntity.getQuestion().getViewedBy())? currAnswerEntity.getQuestion().getViewedBy().size() : 0;
		
		boolean isSpam = qualifiedForSpam(viewCnt, spammedBy.size());
		LOGGER.debug("Spam qualification {}", isSpam);
		currAnswerEntity.setSpam(isSpam);
		
		currAnswerEntity = answerRepository.save(currAnswerEntity);
		
		AnswerVO answerVO = modelMapper.map(currAnswerEntity, AnswerVO.class);
		
		LOGGER.debug("answerVO after report spam {}", answerVO);
		return answerVO;
		
	}

	private boolean qualifiedForSpam(int viewCount, int spamCount) {
		if(Constants.VIEW_TRSHLD < viewCount) {
			int spamFactor = (spamCount * 10)/viewCount;
			if(Constants.SPM_TRSHLD <= spamFactor)
				return true;
		}
		return false;
	}
	
}
