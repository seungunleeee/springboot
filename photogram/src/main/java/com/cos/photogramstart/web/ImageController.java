package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.image;
import com.cos.photogramstart.handler.exe.CustomValidationException;
import com.cos.photogramstart.service.imageService;
import com.cos.photogramstart.web.dto.image.imageUploadDto;


import lombok.RequiredArgsConstructor;




@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final imageService imageservices;

	@GetMapping({"/","/image/story"})
	public String stroy() {
		
		
		
		return "image/story"; 
	}
	@GetMapping("/image/popular")
	public String popular(Model model){
		
		 List<image> images =imageservices.인기사진(); 
			
		model.addAttribute("images",images);

		
		
		
		return"image/popular";
	}
	
	
	@GetMapping("/image/upload")
	public String upload(){
		return"image/upload";
	}
	
	
	
	@PostMapping("/image")
	public String imageUpload(imageUploadDto imageuploadDto,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		//서비스 호출
		
		System.out.println("imageUpload   1");
		if(imageuploadDto.getFile().isEmpty()) {
			System.out.println("이미지가 첨부되지 않았습니다.   -1");
			throw new CustomValidationException("이미지가 첨부되지 않았습니다", null);
		}
		
	
		imageservices.사진업로드(imageuploadDto, principalDetails);
		
		
		
		return "redirect:/user/"+principalDetails.getuser().getId();
	}
}
