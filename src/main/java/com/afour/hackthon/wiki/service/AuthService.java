package com.afour.hackthon.wiki.service;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afour.hackthon.wiki.commons.Constants;
import com.afour.hackthon.wiki.entity.UserProfileEntity;
import com.afour.hackthon.wiki.exceptions.ValidationException;
import com.afour.hackthon.wiki.repository.IUserProfileRepository;
import com.afour.hackthon.wiki.vo.UserProfileVO;

@Service
public class AuthService {

	private final Pattern a4DomainPattern = Pattern.compile(Constants.AFOUR_DMN_REGEX);
	
	@Autowired private IUserProfileRepository userProfileRepository;
	 
	
	public UserProfileVO authenticate(Map<String, String> userDetails) {
	
		if(!validDomain(userDetails.get("email")))
			throw new ValidationException("Only AFourTech users allowed");
		
		UserProfileEntity userProfileModel = userProfileRepository.findByProviderId(userDetails.get("id"));

		if (null == userProfileModel) {
			userProfileModel = new UserProfileEntity();
			userProfileModel.setUsername(extractUserName(userDetails.get("email")));
			userProfileModel.setEmail(userDetails.get("email"));
			userProfileModel.setFirstName(userDetails.get("given_name"));
			userProfileModel.setLastName(userDetails.get("family_name"));
			userProfileModel.setProviderId(userDetails.get("id"));
			userProfileModel.setKarma(0);
			userProfileModel.setComplete(false);
			userProfileModel.setCreationDate(new Date());
			userProfileModel = userProfileRepository.insert(userProfileModel);
		}
		ModelMapper modelMapper = new ModelMapper();
		UserProfileVO userProfileVO = modelMapper.map(userProfileModel, UserProfileVO.class);
		return userProfileVO;
	}
	

	
	/*private UserProfileModel getCurrentUser(Map<String, String> userDetails) {
		
		UserProfileModel userProfileModel = userProfileRepository.findByProviderId(userDetails.get("id"));

		if (null == userProfileModel) {
			userProfileModel = new UserProfileModel();
			userProfileModel.setUsername(extractUserName(userDetails.get("email")));
			userProfileModel.setEmail(userDetails.get("email"));
			userProfileModel.setFirstName(userDetails.get("given_name"));
			userProfileModel.setLastName(userDetails.get("family_name"));
			userProfileModel.setProviderId(userDetails.get("id"));
			userProfileModel.setKarma(0);
			userProfileModel.setComplete(false);
			userProfileModel.setCreationDate(new Date());
			userProfileModel = userProfileRepository.save(userProfileModel);
		}
		return userProfileModel;

	}
	
	private Map<String, String> extractUser() {
		// Step 1 get use from security context
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (oAuth2Authentication.isAuthenticated()) {
			Authentication authentication = oAuth2Authentication.getUserAuthentication();
			Map<String, String> userDetails = (Map<String, String>) authentication.getDetails();
			return userDetails;
		} else {
			throw new WikiException(401, "User is not authenticated");
		}

	}
	*/
	
	private String extractUserName(String email) {
		if(StringUtils.isNotBlank(email)) {
			int endIndex = email.indexOf("@");
			return email.substring(0, endIndex);
		}
		return email;
	}
	
	private boolean validDomain(String email) {
		return StringUtils.isNotBlank(email) 
			&& a4DomainPattern.matcher(email).matches();
	}
}
