package com.afour.hackthon.wiki.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afour.hackthon.wiki.exceptions.ValidationException;
import com.afour.hackthon.wiki.service.AuthService;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@GetMapping( value = "/authenticate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void authenticate() {
		throw new ValidationException("Sample Valdi Exception", new RuntimeException("Runtime Exception"));
	}
	
}
