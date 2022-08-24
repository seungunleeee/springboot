package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;


import lombok.Data;


@Data
public class UserUpdateDto {
	@NotBlank
	private String name;//필수 
	@NotBlank
	private String password;//필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	//조금위험 코드수정이 필요할 예정
	public User toEntity() {
		
		return User.builder()
				.name(name)
				.password(password)
				.phone(phone)
				.website(website)
				.bio(bio)
				.gender(gender)
				.build();
	}
	

}
