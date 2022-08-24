package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.exe.CustomValidationException;
import com.cos.photogramstart.handler.exe.CustomValidationapiException;

//공통기능
@Aspect
@Component
public class ValidationAdvice {
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		System.out.println("web 컨트롤러<================================");
		// ProceedingJoinPoint=> profile함수의 모든곳에 접근 할 수 있는 변수.
		// profile 함수보다 먼저 실행
		Object[] args = proceedingJoinPoint.getArgs();// 매개변수에 접근하는것임
		for (Object arg : args) {
			if (arg instanceof BindingResult) {
				System.out.println("유효성 검사를 하는 함수입니다.");
				BindingResult bindingResult = (BindingResult) arg;

				if (bindingResult.hasErrors()) {
					System.out.println("bindingResult.hasErrors() 일함");
					Map<String, String> errorMap = new HashMap<>();
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());

					}
					throw new CustomValidationException(" 유효성 검사실패", errorMap);
				}

			}
		}

		return proceedingJoinPoint.proceed(); // profile함수가 실행됨
	}

	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		System.out.println("web api 컨트롤러<================================");

		Object args[] = proceedingJoinPoint.getArgs();// 매개변수에 접근하는것임
		for (Object arg : args) {
			if(arg instanceof BindingResult) {
				System.out.println("유효성 검사를 하는 함수입니다.");
				BindingResult bindingResult =(BindingResult)arg;
				
				if (bindingResult.hasErrors()) {
					System.out.println("bindingResult.hasErrors() 일함");
					Map<String, String> errorMap = new HashMap<>();
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());

					}
					throw new CustomValidationapiException(" 유효성 검사실패", errorMap);
				}
				
				
				
			}
		}
			
		

		return proceedingJoinPoint.proceed();
	}
	
}
