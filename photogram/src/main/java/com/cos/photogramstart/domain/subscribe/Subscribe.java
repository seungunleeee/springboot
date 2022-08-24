package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.print.event.PrintJobAttributeEvent;

import com.cos.photogramstart.domain.user.User;

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
@Table(
		uniqueConstraints  ={
				@UniqueConstraint(
						name="subscribe_uk",
						columnNames = {"fromUserID","toUserID"})
		} )
public class Subscribe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//번호 증가 전략이 데이터베이스를 따라간다.ㄴ
	 private int id;
	@JoinColumn(name="fromUserID")//이렇게 컬럼명 만들어 니맘대로 만들지말고
	@ManyToOne
	private User fromUser;
	
	
	@JoinColumn(name="toUserID")
	@ManyToOne
	private User toUser;
	
	 private LocalDateTime createDate;
	 @PrePersist //디비에 저장되기직전에 실행
	 public void createDate() {
		 this.createDate=LocalDateTime.now();
	 }
	
}
