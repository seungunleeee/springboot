package com.cos.photogramstart.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.image.image;
import com.cos.photogramstart.domain.image.imageRepository;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.exe.CustomApiException;
import com.cos.photogramstart.handler.exe.CustomException;
import com.cos.photogramstart.handler.exe.CustomValidationException;
import com.cos.photogramstart.handler.exe.CustomValidationapiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
/*import com.nimbusds.openid.connect.sdk.claims.ClaimType;*/

import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;


@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final  SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	@Value("${file.path}") // yml에서 가져온거임
	private String uploadFolder;
	private final imageRepository imagerepository;
	
	@Transactional
	public User 회원프로필사진변경(int principalid, MultipartFile profileImageFile) {
	
		UUID uuid = UUID.randomUUID();// UUID 네트워크상에서 고유성이 보장되는 id를만들기 위한 표준 규약 유일성보장 이것도근데 운이 아주안좋으면 중복가능
		String imageFilename = uuid + profileImageFile.getOriginalFilename();
		System.out.println("2 이미지 파일이름: " + imageFilename);

		Path imageFilePath = Paths.get(uploadFolder + imageFilename);
		// 통신 혹은IO가 일어날 때 예외 발생가능->예외처리해야지..
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// image 테이블에 저장
	User user =userRepository.findById(principalid).orElseThrow(()->{
		throw new CustomApiException("유저를 찾을 수 없습니다.");
		
	});
	user.setProfileImageUrlString(imageFilename);
	return   user;
	
	}//더티체킹으로 업데이트됨
	
	
	
	
	@Transactional  (readOnly = true) 
	public UserProfileDto  회원프로필(int pageuserid,int principalid) {
		// SELECT * FROM image Where userid =:userid;
		
		UserProfileDto dto=new UserProfileDto() ;
		
		User userentity = userRepository.findById(pageuserid).orElseThrow(() -> {
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});
		/*
		 * System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 * userentity.getImages().get(0);
		 */
		
		dto.setUser(userentity);
		dto.setPageowner(pageuserid==principalid);
		dto.setImagecount(userentity.getImages().size());
		
		int subscribestate=subscribeRepository.mSubscribeState(principalid, pageuserid);
		int subscribecount =subscribeRepository.mSubscribeCount(pageuserid);

		dto.setSubscribecount(subscribecount);
		dto.setSubscribestate(subscribestate==1);
		return dto;
	}

	@Transactional
	public User 회원수정(int id, User user) {
		// 1.영속화
		User userentity = userRepository.findById(id).orElseThrow(() -> {
			return new CustomValidationapiException("찾을 수 없는 id입니다");
		});// 1.무조건 찾았다 걱정마(get함수) 2.못찾았어 exception 발생할게 or else 실행
		userentity.setName(user.getName());

		userentity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userentity.setBio(user.getBio());
		userentity.setWebsite(user.getWebsite());
		userentity.setPhone(user.getPhone());
		userentity.setGender(user.getGender());

		/*
		 * String rawpassword= user.getPassword(); String encPassword=
		 * bCryptPasswordEncoder.encode(rawpassword);
		 */

		// 2.영속화된 오브젝트를 수정-더티체킹(업데이트 완료)
		return userentity;
}
}
