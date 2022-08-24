package com.cos.photogramstart.service;

import org.eclipse.jdt.internal.compiler.classfmt.NonNullDefaultAwareTypeAnnotationWalker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.exe.CustomApiException;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	@Transactional
	public Comment 댓글쓰기(String comment,int imageid ,int principalid) {
		
		System.out.println(comment +imageid +principalid);
		//Tip 객체를 만들 때 ID값만 담아서 insert를 할 수 있다.
		image image =new image();
		image.setId(imageid);
		User user =userRepository.findById(principalid).orElseThrow(()->{
			
			throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
			
		});
		Comment comment1 = new Comment();
		comment1.setContent(comment);
		comment1.setImage(image);
		comment1.setUser(user);
		
	return commentRepository.save(comment1);	
	}
	
	@Transactional
	public void 댓글삭제(int id) {
		try {
		commentRepository.deleteById(id);
		}
		catch(Exception e) {
			throw new CustomApiException(e.getMessage());
		}
		
	}
	

}
