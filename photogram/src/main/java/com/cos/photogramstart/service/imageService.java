package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageTranscoder;
import javax.transaction.TransactionScoped;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.image;
import com.cos.photogramstart.domain.image.imageRepository;
import com.cos.photogramstart.web.dto.image.imageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class imageService {
	private final imageRepository imagerepository;
	@Value("${file.path}") // yml에서 가져온거임
	private String uploadFolder;

	
	  @Transactional(readOnly = true) public List<image> 인기사진(){
	  
	  
	  return imagerepository.mpopular(); 
	  }
	 

	@Transactional(readOnly = true)
	// 내 구독자들의 스토리를 가져옴
	public Page<image> 이미지스토리(int principalid, Pageable pageable) {
		Page<image> images = imagerepository.mstory(principalid, pageable);

		images.forEach((image) -> {

			image.getLikes().forEach((like) -> {
				if (like.getUser().getId() == principalid) {

					image.setLikeState(true);
				}

			});

		});

		return images;
	}

	@Transactional
	public void 사진업로드(imageUploadDto imageuploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();// UUID 네트워크상에서 고유성이 보장되는 id를만들기 위한 표준 규약 유일성보장 이것도근데 운이 아주안좋으면 중복가능
		String imageFilename = uuid + imageuploadDto.getFile().getOriginalFilename();
		System.out.println("2 이미지 파일이름: " + imageFilename);

		Path imageFilePath = Paths.get(uploadFolder + imageFilename);
		// 통신 혹은IO가 일어날 때 예외 발생가능->예외처리해야지..
		try {
			Files.write(imageFilePath, imageuploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// image 테이블에 저장
		image image = imageuploadDto.toEntity(imageFilename, principalDetails.getuser());
		image imageEntity = imagerepository.save(image);
		/* System.out.println(imageEntity); */

	}

}
