package com.afour.hackthon.wiki.service;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.afour.hackthon.wiki.commons.Constants;
import com.afour.hackthon.wiki.exceptions.ValidationException;
import com.afour.hackthon.wiki.model.UserProfileModel;
import com.afour.hackthon.wiki.repository.IUserProfileRepository;
import com.afour.hackthon.wiki.vo.UserProfileVO;

@Service
public class AuthService {

	private final Pattern a4DomainPattern = Pattern.compile(Constants.AFOUR_DMN_REGEX);
	
	@Autowired private IUserProfileRepository userProfileRepository;
	
	public UserProfileVO authenticate() {

		//step 1 get user from security context
		
		
		
		
		//step 2 check if user already registered or not 
		
		
		
		
		UserProfileVO userProfileVO = new UserProfileVO();
		return userProfileVO;
	}
	
	private boolean validDomain(String email) {
		return StringUtils.isNotBlank(email) && a4DomainPattern.matcher(email).matches();
	}
	
	private UserProfileModel getCurrentUser() {
		
		//Step 1 get use from security context
		//SecurityContextHolder.getContext().getAuthentication();

		//step 2 validation for afour users
		String email = "";
		
		if(!validDomain(email)) {
			throw new ValidationException("Only AfourTech Users allowed");
		}
		
		//Step 3 Check if registration requied for user  
		String providerId = "";
		UserProfileModel userProfileModel = userProfileRepository.findByProviderId(providerId);
		
		if(null == userProfileModel) {
			userProfileModel = new UserProfileModel();
			
			// read values from Principal and assign to object
			
			// save object to mongo collection
		}
		
		return userProfileModel;
	}
	
	private String generateToken() {
		
	}
}
