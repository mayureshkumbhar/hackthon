package com.afour.hackthon.wiki.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WikiExcpetionControllerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(WikiExcpetionControllerAdvice.class);
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorInfo> handleWikiException(ValidationException vldex) {
		LOGGER.error("ValidationException: ", vldex);
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(vldex.getMessage()),
				HttpStatus.valueOf(vldex.getStatusCode()));
	}

	@ExceptionHandler(WikiException.class)
	public ResponseEntity<ErrorInfo> handleWikiException(WikiException wikiex) {
		LOGGER.error("WikiException: ", wikiex);
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(wikiex.getMessage()),
				HttpStatus.valueOf(wikiex.getStatusCode()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> handleException(Exception ex) {
		LOGGER.error("Exception: ", ex);
		return new ResponseEntity<ErrorInfo>(new ErrorInfo(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
