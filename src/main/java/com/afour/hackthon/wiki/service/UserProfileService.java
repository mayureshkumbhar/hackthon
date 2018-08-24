package com.afour.hackthon.wiki.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.hackthon.wiki.exceptions.UserProfileNotFoundException;
import com.afour.hackthon.wiki.model.UserProfileModel;
import com.afour.hackthon.wiki.repository.IUserProfileRepository;
import com.afour.hackthon.wiki.vo.UserProfileVO;

@Service
public class UserProfileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileService.class);
			
	
	@Autowired private IUserProfileRepository userProfileRepository;
	
	private final ModelMapper mapper = new ModelMapper();
	
	public UserProfileVO getUserProfile(String userId) {
		
		Optional<UserProfileModel> model = userProfileRepository.findById(userId);
		
		if(!model.isPresent()) {
			LOGGER.error("UserProfile for not found for id: " + userId);
			throw new UserProfileNotFoundException("UserProfile for not found");
		}
			
		UserProfileVO profileVO = mapper.map(model.get(), UserProfileVO.class);
		LOGGER.debug("Got profile for id [{}] : [{}]", userId, profileVO);
		return profileVO;
	}
	
	
	
}
