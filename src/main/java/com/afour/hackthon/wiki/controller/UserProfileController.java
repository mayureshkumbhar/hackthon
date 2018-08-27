package com.afour.hackthon.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.service.UserProfileService;
import com.afour.hackthon.wiki.vo.UserProfileVO;


@RestController
public class UserProfileController {

	@Autowired
	private UserProfileService profileService;
	
	@GetMapping(value ="/profile/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserProfileVO> getUserProfile(@PathVariable("id") String userId) {
		UserProfileVO profileVO = profileService.getUserProfile(userId);
		return new ResponseEntity<>(profileVO,HttpStatus.OK);
	}
	
	@PutMapping(value ="/profile/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserProfileVO> updateUserProfile(@PathVariable("id") String userId, @RequestBody UserProfileVO profileVO) {
		UserProfileVO updatedProfileVO = profileService.updateUserProfile(userId,profileVO);
		return new ResponseEntity<>(updatedProfileVO,HttpStatus.OK);
	}
}
