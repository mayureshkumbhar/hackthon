package com.afour.hackthon.wiki.exceptions;

import org.springframework.http.HttpStatus;

public class UserProfileNotFoundException extends WikiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4553102604019644857L;

	public UserProfileNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND.value(), message);
		
	}

	public UserProfileNotFoundException(String message, Throwable th) {
		super(HttpStatus.NOT_FOUND.value(), message, th);
		
	}
}
