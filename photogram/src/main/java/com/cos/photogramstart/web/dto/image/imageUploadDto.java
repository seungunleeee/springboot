package com.cos.photogramstart.web.dto.image;

import javax.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;




@Data
public class imageUploadDto {
	private MultipartFile file;

	private String caption;
	
	public image toEntity(String postimageUrl,User user) {
		return image.builder()
				.caption(caption)
				.postimageUri(postimageUrl)
				.user(user)
				.build();
	}
	
}
