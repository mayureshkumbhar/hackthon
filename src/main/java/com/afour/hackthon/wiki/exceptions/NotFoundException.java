package com.afour.hackthon.wiki.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends WikiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4553102604019644857L;

	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND.value(), message);
		
	}

	public NotFoundException(String message, Throwable th) {
		super(HttpStatus.NOT_FOUND.value(), message, th);
		
	}
}
