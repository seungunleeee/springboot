package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.exe.CustomValidationException;
import com.cos.photogramstart.handler.exe.CustomValidationapiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMrespDTO;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;


import lombok.RequiredArgsConstructor;




@RequiredArgsConstructor
@RestController
public class apiController {
private final UserService userService;
private final SubscribeService subscribeService;


	@PutMapping("/api/user/{principalid}/profileimageUrl")
	public ResponseEntity<?> profileimageUrlUpdate(@PathVariable int principalid,MultipartFile profileImageFile,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
	User usertntity =	userService.회원프로필사진변경(principalid,profileImageFile);
	principalDetails.setuser(usertntity); //세션 변경
		
		return new ResponseEntity<> (new  CMrespDTO<>(1,"프로필사진변경 성공",null),HttpStatus.OK);
	}




	@GetMapping("/api/user/{pageuserId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageuserId,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
	List<SubscribeDto> subscribeDto =subscribeService.구독리스트(principalDetails.getuser().getId(),pageuserId);
	
	return new ResponseEntity<>(new CMrespDTO<>(1, "구독자정보가져오기 성공",subscribeDto),HttpStatus.OK);
	}
		
	
	@PutMapping("/api/user/{id}")
	public CMrespDTO<?> update(@PathVariable int id,
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult,//@R꼭valid가 적혀있는 다음 파라메터로 적어야됨
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
	/*	if(bindingResult.hasErrors()) {
			Map<String, String>errorMap=new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println(error.getDefaultMessage());
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
				
			}
			throw  new CustomValidationapiException("유효성 검사 실패함",errorMap);
		}
		else {*/
			
		System.out.println(userUpdateDto);
		
		
		User userentity=userUpdateDto.toEntity();
		userService.회원수정(id, userentity);
		principalDetails.setuser(userentity);
		return new CMrespDTO<>(1,"회원수정완료",userentity);//응답시에 userentitiy의 모든getter함수 실행 json으로 파싱하여 응답.
		
		
	}
	
}
