package com.afour.hackthon.wiki.resource;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.commons.TokenUtils;
import com.afour.hackthon.wiki.exceptions.WikiException;
import com.afour.hackthon.wiki.service.AuthService;
import com.afour.hackthon.wiki.vo.UserProfileVO;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private TokenUtils tokenUtils;

	@GetMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserProfileVO> authenticate(Principal principal ) {

		/*OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();*/
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;

		if (oAuth2Authentication.isAuthenticated()) {
			Authentication authentication = oAuth2Authentication.getUserAuthentication();
			Map<String, String> userDetails = (Map<String, String>) authentication.getDetails();
			UserProfileVO profileVO = authService.authenticate(userDetails);
			userDetails.put("username", profileVO.getUsername());
			userDetails.put("userId", profileVO.getId());
			String token = tokenUtils.generateToken(userDetails);

			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, token);

			return new ResponseEntity<>(profileVO, headers, HttpStatus.OK);

		} else {
			throw new WikiException(401, "User is not authenticated");
		}
	}

}
