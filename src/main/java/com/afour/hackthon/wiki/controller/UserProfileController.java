package com.afour.hackthon.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.service.UserProfileService;
import com.afour.hackthon.wiki.vo.UserProfileVO;


@RestController
@RequestMapping("/profile")
public class UserProfileController {

	@Autowired
	private UserProfileService profileService;
	
	@GetMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserProfileVO> getUserProfile(@PathVariable("id") String userId) {
		UserProfileVO profileVO = profileService.getUserProfile(userId);
		return new ResponseEntity<>(profileVO,HttpStatus.OK);
	}
	
	@PostMapping(value= "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserProfileVO> postUserProfile(@RequestBody UserProfileVO profileVO) {
		UserProfileVO newProfileVO = profileService.postUserProfile(profileVO);
		return new ResponseEntity<>(newProfileVO,HttpStatus.OK);
	}
	
	
	
	@PutMapping(value ="/profile/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserProfileVO> updateUserProfile(@PathVariable("id") String userId, @RequestBody UserProfileVO profileVO) {
		UserProfileVO updatedProfileVO = profileService.updateUserProfile(userId,profileVO);
		return new ResponseEntity<>(updatedProfileVO,HttpStatus.OK);
	}
}
