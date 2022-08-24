package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface imageRepository extends JpaRepository<image, Integer> {
	@Query(value = "SELECT * FROM image WHERE userid IN"
			+ " (SELECT touserid FROM subscribe WHERE fromUserId =:principalid) ORDER BY id DESC  ", nativeQuery = true)
	Page<image> mstory(int principalid, Pageable pageable);

	
	  @Query(value="SELECT i.* FROM image i INNER JOIN (SELECT imageid, COUNT(imageid) likecount FROM likes GROUP BY imageid) c   ON\r\n"
	  		+ " i.id=c.imageid ORDER BY likecount desc ", nativeQuery =true)
	  List<image>mpopular( );
	 

}
