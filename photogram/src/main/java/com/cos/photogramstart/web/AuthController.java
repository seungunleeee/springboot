package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Max;

import org.aspectj.weaver.reflect.GenericSignatureInformationProvider;
import org.hibernate.internal.build.AllowSysOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties.Registration.Signing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.exe.CustomApiException;
import com.cos.photogramstart.handler.exe.CustomValidationException;
import com.cos.photogramstart.service.Services;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import com.sun.jdi.Field;


import lombok.RequiredArgsConstructor;




@RequiredArgsConstructor//FINAL필드를 DI할 떄 사용
@Controller
public class AuthController {
	
	private final Services service;
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
@GetMapping("/auth/signin")
public String SigninForm() {
	return "/auth/signin";
}
	
	
	
@GetMapping("/auth/signup")
public String SignupForm() {
	return "/auth/signup";
}
//회원가입 ㄱ
@PostMapping("/auth/signup")
public  String  Signup(@Valid SignupDto  signupdto,BindingResult bindingResult) {// 체크한단거.. 바인딩에러스 ->수틀리면..if else임
			if(bindingResult.hasErrors()) {
				Map<String, String>errorMap=new HashMap<>();
				for(FieldError error : bindingResult.getFieldErrors()) {
					errorMap.put(error.getField(), error.getDefaultMessage());
					
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
					System.out.println(error.getDefaultMessage());
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
					
				}
				throw  new CustomValidationException("유효성 검사 실패함",errorMap);
			}
			else {
				 
				log.info(signupdto.toString());
				System.out.println("Sign in post 요청 실행됨");
				
				User userentity;
				User user=signupdto.toEntity();
				try {
				 userentity=service.회원가입(user);
				}
				catch (Exception e) {
					throw new CustomApiException("중복된 아이디,비밀번호 입니다.");
				}
				System.out.println(userentity);
				return "/auth/signin";
			}
				
		}


}
