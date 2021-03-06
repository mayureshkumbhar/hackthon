package com.afour.hackthon.wiki.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.hackthon.wiki.entity.UserProfileEntity;
import com.afour.hackthon.wiki.exceptions.NotFoundException;
import com.afour.hackthon.wiki.repository.IUserProfileRepository;
import com.afour.hackthon.wiki.vo.UserProfileVO;

@Service
public class UserProfileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileService.class);
			
	
	@Autowired private IUserProfileRepository userProfileRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	private final ModelMapper mapper = new ModelMapper();
	
	public UserProfileVO getUserProfile(String userId) {
		
		Optional<UserProfileEntity> model = userProfileRepository.findById(userId);
		
		if(!model.isPresent()) {
			LOGGER.error("UserProfile for not found for id: " + userId);
			throw new NotFoundException("UserProfile for not found");
		}
			
		UserProfileVO profileVO = mapper.map(model.get(), UserProfileVO.class);
		LOGGER.debug("Got profile for id [{}] : [{}]", userId, profileVO);
		return profileVO;
	}

	public UserProfileVO updateUserProfile(String userId, UserProfileVO profileVO) {
		Optional<UserProfileEntity> model = userProfileRepository.findById(userId);
		if(!model.isPresent()) {
			LOGGER.error("UserProfile for not found for id: " + userId);
			throw new NotFoundException("UserProfile for not found");
		}
		
		UserProfileEntity userProfileModel = model.get();
		userProfileModel.setSummary(profileVO.getSummary());
		userProfileModel.setTags(profileVO.getTags());
		userProfileModel.setComplete(profileVO.isComplete());
		userProfileModel.setModifiedDate(new Date());
		userProfileModel = userProfileRepository.save(userProfileModel);
		
		UserProfileVO updatedProfileVO = mapper.map(userProfileModel, UserProfileVO.class);
		LOGGER.debug("Updated profile for id [{}] : [{}]", userId, updatedProfileVO);
		return updatedProfileVO;
	}

	public UserProfileVO postUserProfile(UserProfileVO profileVO) {
		UserProfileEntity userProfileModel = userProfileRepository.findByProviderId(profileVO.getProviderId());
		if (null == userProfileModel) {
			userProfileModel = modelMapper.map(profileVO, UserProfileEntity.class);
			userProfileModel.setKarma(0);
			userProfileModel.setCreationDate(new Date());
			userProfileModel.setTags(Collections.EMPTY_SET);
			userProfileModel.setSummary(null);
			userProfileModel = userProfileRepository.insert(userProfileModel);
		}
		UserProfileVO userProfileVO = modelMapper.map(userProfileModel, UserProfileVO.class);
		return userProfileVO;
	}
	
	public Set<String> getAllUserByTag(List<String> tags ) {
		/*Iterator<String> itr = tags.iterator();
		Set<String> userIds = new HashSet<>();
		while(itr.hasNext()){
			UserProfileEntity userProfileModel  = userProfileRepository.findByTags(itr.next());
			if (null != userProfileModel) {
				userIds.add(userProfileModel.getId());
			}
		}
		
		return userIds;*/
		
		Iterator<String> itr = tags.iterator();
		Set<String> userIds = new HashSet<>();
		while(itr.hasNext()){
			List<UserProfileEntity> userProfileModel  = userProfileRepository.findByTags(itr.next());
			if (null != userProfileModel) {
				for(UserProfileEntity user : userProfileModel)
					userIds.add(user.getId());
			}
		}
		
		return userIds;
	}
	
}
