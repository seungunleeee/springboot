package com.cos.photogramstart.web.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.image;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.service.imageService;
import com.cos.photogramstart.web.dto.CMrespDTO;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class imageApiController {
	public final imageService imageservices;
	private final LikesService likesService;
	
@GetMapping("/api/image")
	public ResponseEntity<?> imagestory(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PageableDefault(size=3) Pageable pageable){
		//pageable ->data domain 꺼임
	Page<image>images= imageservices.이미지스토리(principalDetails.getuser().getId(),pageable);
		return new ResponseEntity<>(new CMrespDTO<>(1,"성공",images),HttpStatus.OK);
	}

@PostMapping("/api/image/{imageid}/likes")
	public ResponseEntity<?> Likes(@PathVariable int imageid,@AuthenticationPrincipal PrincipalDetails principalDetails){
	likesService.좋아요(imageid,principalDetails.getuser().getId());
	
	return new ResponseEntity<>(new CMrespDTO<>(1,"좋아요 등록 성공",null) , HttpStatus.CREATED);
	}


@DeleteMapping("/api/image/{imageid}/likes")
	public ResponseEntity<?> Unlikes(@PathVariable int imageid,@AuthenticationPrincipal PrincipalDetails principalDetails){
	likesService.좋아요취소(imageid,principalDetails.getuser().getId());
	
	return new ResponseEntity<>(new CMrespDTO<>(1,"좋아요 취소 성공",null) , HttpStatus.OK);
	}

	
}
