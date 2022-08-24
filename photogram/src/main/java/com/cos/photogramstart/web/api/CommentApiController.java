package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.exe.CustomValidationapiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMrespDTO;
import com.cos.photogramstart.web.dto.comment.CommentDto;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

public class CommentApiController {

	public final CommentService commentService;
	
	@PostMapping("api/comment")
	public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto  commentDto, BindingResult  bindingResult,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		/*
		 * if(bindingResult.hasErrors()) { Map<String,String>errorMap =new HashMap<>();
		 * for(FieldError error : bindingResult.getFieldErrors()){
		 * errorMap.put(error.getField(), error.getDefaultMessage());
		 * 
		 * } throw new CustomValidationapiException("api/comment 유효성 검사실패",errorMap); }
		 */
		
		
		
	
		Comment comment= commentService.댓글쓰기(commentDto.getContent(),commentDto.getImageid()
				,principalDetails.getuser().getId());
		return new ResponseEntity<>(new CMrespDTO<Comment>(1,"댓글 쓰기,저장 성공",comment),HttpStatus.CREATED);
	}
	
	@DeleteMapping("api/comment/{id}")
	public ResponseEntity<?> commenteDelete(@PathVariable int id){
		
		commentService.댓글삭제(id);
		
		
		
		return new ResponseEntity<>(new CMrespDTO<>(1,"댓글삭제 성공",null),HttpStatus.OK);
	}
	
	
	
	
}
