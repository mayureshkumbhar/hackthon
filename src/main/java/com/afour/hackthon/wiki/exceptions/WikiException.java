package com.afour.hackthon.wiki.exceptions;

import lombok.Getter;
import lombok.ToString;

@ToString

public class WikiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2336901920767793366L;
	
	@Getter
	private int statusCode;
	
	public WikiException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}
	
	public WikiException(int statusCode, String message, Throwable th) {
		super(message, th);
		this.statusCode = statusCode;
	}
	
}
