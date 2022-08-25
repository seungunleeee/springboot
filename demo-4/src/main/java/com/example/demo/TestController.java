package com.example.demo;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.example.demo.domain.User;
import com.example.demo.dto.SignupDto;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class TestController {
	private final UserService userService;
	
	
	@GetMapping("/")
	public String set() {
		
		System.out.println("working");
		
		
		return "/test/main";
	}
	
	
	
	@GetMapping("/auth/signin")
	public String signin() {
		
		System.out.println("working");
		
		
		return "login";
	}
	
	@GetMapping("/auth/signup")
	public String signup() {
		
	
		
		
		return "signup";
	}
	
	@PostMapping("/auth/signup")
	public String postsignup(SignupDto signupDto ) {

		User user = signupDto.toEntity();
		userService.회원가입(user);
		return "login";
	}

}
