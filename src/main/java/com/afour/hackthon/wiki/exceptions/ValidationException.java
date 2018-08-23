package com.afour.hackthon.wiki.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends WikiException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3344962921192777207L;

	public ValidationException(String message) {
		super(HttpStatus.BAD_REQUEST.value(), message);
	}

	public ValidationException(String message, Throwable th) {
		super(HttpStatus.BAD_REQUEST.value(), message, th);
	}
}
