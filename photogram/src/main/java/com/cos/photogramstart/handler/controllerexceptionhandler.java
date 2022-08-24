package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.exe.CustomApiException;
import com.cos.photogramstart.handler.exe.CustomException;
import com.cos.photogramstart.handler.exe.CustomValidationException;
import com.cos.photogramstart.handler.exe.CustomValidationapiException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMrespDTO;

@RestController
@ControllerAdvice //모든 exception을 다낚아챔 응답은 data를 응답할거라 ..restcontroller
public class controllerexceptionhandler {
	
	//CMrespDto,Script 비교
	// 1.클라이언트에게 응답할 때는 Script좋음.
	//2.Ajax 통신-CMrespDto쓰게됨 ->개발자가 응답받을때
	//Android xxhdtls CMREspdDto->개발자가응답받을때
	@ExceptionHandler(CustomValidationException.class)
	public String/* CMrespDTO<?>*/ validationException(CustomValidationException e) {
		System.out.println(e.getErrorMap());
		if(e.getErrorMap()==null) {
			return Script.back(e.getMessage());
		}
		
		else {
		return Script.back(e.getErrorMap().toString());
		}
		/*
		 * return new CMrespDTO<Map<String,String>>(-1,e.getMessage(),e.getErrorMap());
		 */
	}
	@ExceptionHandler(CustomValidationapiException.class)
	public  ResponseEntity<?> validationapiException(CustomValidationapiException e) {
		
		
		
		/* return Script.back(e.getErrorMap().toString()); */
		System.out.println(">>>>>>>>>>>>>>>>>"+e.getMessage());
		 return new ResponseEntity<CMrespDTO>(new CMrespDTO<>(-1,e.getMessage(),e.getErrorMap()),
				 HttpStatus.BAD_REQUEST);
		 
	}
	
	
	@ExceptionHandler(CustomApiException.class)
	public  ResponseEntity<?> validationsameidException(CustomApiException e) {
		
		
		
		/* return Script.back(e.getErrorMap().toString()); */
		System.out.println(">>>>>>>>>>>>>>>>>"+e.getMessage());
		 return new ResponseEntity<CMrespDTO>(new CMrespDTO<>(-1,e.getMessage(),null),
				 HttpStatus.BAD_REQUEST);
		 
	}
	
	
	@ExceptionHandler(CustomException.class)
	public String/* CMrespDTO<?>*/ CustomExceptions(CustomException e) {
		return Script.back(e.getMessage());
	}
	
	
	
}
