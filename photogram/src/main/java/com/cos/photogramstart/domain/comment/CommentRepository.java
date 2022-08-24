package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	/*
	 * @Modifying
	 * 
	 * @Query(
	 * value="INSERT INTO comment (content,imageid,userid,createDate) VALUES(:content,:imageid,:userid,now()"
	 * ,nativeQuery = true) Comment mSave(String content,int imageid, int userid);
	 * int ,void 만리턴받음
	 */
}
