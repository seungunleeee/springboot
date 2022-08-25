package com.example.demo.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserService {
	private  final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	@Transactional
	public User 회원가입(User user) {
		String rawpassword =user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawpassword);
		
		user.setPassword(encPassword);
		User userentity  =userRepository.save(user);
		
		return userentity;
	}
	
	
	
	
}
