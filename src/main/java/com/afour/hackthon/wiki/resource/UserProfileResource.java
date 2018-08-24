package com.afour.hackthon.wiki.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.service.UserProfileService;
import com.afour.hackthon.wiki.vo.UserProfileVO;


@RestController
public class UserProfileResource {

	@Autowired
	private UserProfileService profileService;
	
	@GetMapping(value ="/user-profile/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserProfileVO> getUserProfile(@PathVariable("id") String userId) {
		UserProfileVO profileVO = profileService.getUserProfile(userId);
		return new ResponseEntity<>(profileVO,HttpStatus.OK);
	}
	
}
