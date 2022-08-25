package com.example.demo.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;


import com.example.demo.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
	
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	
	private String email;
	
	public User toEntity() {
		return User.builder()
				.name(name)
				.password(password)
				.username(username)
				.email(email)
				.createDate(null)
				.build();
		
		
	}

}
