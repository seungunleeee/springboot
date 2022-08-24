package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
//JAP (자바로 데이터를 경구적으로 저장(DB)할수 있는 API를 제공
@Data
@Entity// DB에 태이블생성
public class User {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//번호 증가 전략이 데이터베이스를 따라간다.ㄴ
	 private int id;
	
	@Column(length=100,unique=true)
	 private String username;
	@Column(nullable=false)
	 private String password;
	@Column(nullable=false)
	 private String name;
	 private String website;
	 
	 private String bio;
		@Column(nullable=false)
	 private String email;
	 private String phone;
	 private String gender;
	 
	 private String profileImageUrlString;
	 private String role;
	 
	  //나는 연관관계의 주인이 아니야->테이블에 컬럼 만들지마, 
	 //User 셀렉트 할때  해당 user id->id로 등록된 이미지들을 모두 가져와
	 //LAZY = User를 select 할때 해당 User id로 등록된 image들을 가져오지마-대신 getimages() 함수가 호출될 때 가져와ㅏ
	 //Eager = User를 select할 때 해당  User id로 등록된 image들을 전부 JOIN해서 가져와
	 @OneToMany(mappedBy =  "user",fetch=FetchType.LAZY)
	@JsonIgnoreProperties({"user"}) 
	 private List<image>  images;
	 
	 private LocalDateTime createDate;
	 @PrePersist //디비에 저장되기직전에 실행
	 public void createDate() {
		 this.createDate=LocalDateTime.now();
	 }
	 
	
	 
	 
}
