package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;


import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.exe.CustomValidationException;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class Services {
private  final UserRepository userRepository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;



@Transactional//write(insert,Update,Delete 할때 transcation 이걸림 앞으로.. 2,user data의 비밀번호 암호화
	public User 회원가입 (User user) {
	String rawpassword= user.getPassword();
	String encPassword= bCryptPasswordEncoder.encode(rawpassword);
	
	user.setPassword(encPassword);
	user.setRole("ROLE_USER");//관리자 ROLE_ADMIN
	
		User userentity=userRepository.save(user);
		return userentity;
	
		
	}
	
		
	
}
