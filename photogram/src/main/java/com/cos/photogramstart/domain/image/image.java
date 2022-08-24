package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
//JPA(자바로 데이터를 경구적으로 저장(DB)할수 있는 API를 제공
@Data
@Entity// DB에 태이블생성
public class image {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//번호 증가 전략이 데이터베이스를 따라간다.ㄴ
	 private int id;
	
	
	private String caption;
	private String postimageUri; //사진을 전송받아 그 사진을 서버에 특정 폴더에 저장 - DB에 그 저장된 경로를 insert
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userid")
	@ManyToOne(fetch = FetchType.EAGER)//이미지를 select하면 조인해서 User 정보를 같이 들고옴
	private User user;
	//이미지 좋아요 lazy로 호출됨
	
	@OneToMany(mappedBy ="image")
	@JsonIgnoreProperties({ /* "user", */"image"})
	private List<Likes> likes;
	
	@Transient
	private boolean likeState;
	
	 private LocalDateTime createDate;
	 @PrePersist //디비에 저장되기직전에 실행
	 public void createDate() {
		 this.createDate=LocalDateTime.now();
	 }
	 @OrderBy("id DESC")
	 @JsonIgnoreProperties({"image"})
	 @OneToMany(mappedBy = "image")
	 private List<Comment> comments;
	 
	 
		/*
		 * @Transient //DB에 칼럼이 만들어지지않음 private boolean likeState;
		 */
		 
		
		
		/*
		 * @Override public String toString() { return "image [id=" + id + ", caption="
		 * + caption + ", postimageUri=" + postimageUri + ", createDate=" + createDate +
		 * "]"; }
		 */
		 
		 
		  //무한참조 방지코드 오브젝트를 콘솔에 출력할 때 문제가될 수 있어서 User 부분을 출력되지 않게 함.
	 
	 //그리고 또.,. 이미지 좋아요,, 하고.. 댓글도 넣어야 한다
	
	
	
	

}
