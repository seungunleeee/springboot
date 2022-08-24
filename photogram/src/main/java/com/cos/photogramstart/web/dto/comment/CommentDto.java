package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

//NOTNULL =Null값 체크
//NotEmpty =빈값이거나 null체크 그리고
//NotBlank =빈값이거나 null체크 그리고 빈공백까지

@Data
public class CommentDto {
	@NotBlank //빈값,NULL,빈값 이이거나 null 체크 ,notempty둘다 string 만 허용 X
	private String content;
	@NotNull//모든 값null 허용안함 
	private int imageid;
	

}
