package com.cos.photogramstart.handler.exe;

import java.util.Map;

import net.bytebuddy.implementation.bind.annotation.Super;
 
public class CustomApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/* private Map<String, String> errorMap ; */
	public CustomApiException(String message) {
		super(message);
		
	
	}
	/*
	 * public Map<String, String> getErrorMap() { return errorMap; }
	 */

}
