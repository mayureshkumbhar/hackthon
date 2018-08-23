package com.afour.hackthon.wiki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WikiExcpetionControllerAdvice {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorInfo> handleWikiException(ValidationException vldex) {
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(vldex.getMessage()),
				HttpStatus.valueOf(vldex.getStatusCode()));
	}

	@ExceptionHandler(WikiException.class)
	public ResponseEntity<ErrorInfo> handleWikiException(WikiException wikiex) {

		return new ResponseEntity<ErrorInfo>(new ErrorInfo(wikiex.getMessage()),
				HttpStatus.valueOf(wikiex.getStatusCode()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> handleException(Exception ex) {
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
