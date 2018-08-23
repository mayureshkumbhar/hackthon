package com.afour.hackthon.wiki.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 512871507490376328L;
	private String message;
}
