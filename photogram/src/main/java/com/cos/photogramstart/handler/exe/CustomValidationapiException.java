package com.cos.photogramstart.handler.exe;

import java.util.Map;

import net.bytebuddy.implementation.bind.annotation.Super;
 
public class CustomValidationapiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Map<String, String> errorMap ;
	
	public CustomValidationapiException(String message) {
		super(message);
		
	
		
	}
	
	
	public CustomValidationapiException(String message,Map<String, String> errorMap) {
		super(message);
		this.errorMap=errorMap;
	
		
	}
	public Map<String, String> getErrorMap() {                                                                                                                                            
		return errorMap;
	}


}
