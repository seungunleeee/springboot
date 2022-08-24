package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.photogramstart.domain.user.User;
/*import com.nimbusds.openid.connect.sdk.claims.ClaimType;*/

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
	@Modifying//insert delete update 네이티브 쿼리로 작성하려면 해당 어노테이션 필요
	@Query(value="INSERT INTO subscribe(fromUserID,toUserID, createDate) VALUES(:fromUserId, :toUserId, now())"
			,nativeQuery=true)
	void mSubscribe(int fromUserId,int toUserId);//성공하면 1,실패하면 -1 리턴
	
	@Modifying
	@Query(value="DELETE FROM subscribe WHERE fromUserID=:fromUserId AND toUserID=:toUserID ",nativeQuery=true)
	void mUnSubscribe(int fromUserId,int toUserID);

	@Query(value="SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalid AND toUserId =:pageuserid",nativeQuery = true)
	int mSubscribeState(int principalid, int pageuserid);
	
	@Query(value="SELECT COUNT(*) FROM subscribe WHERE fromuserId =:pageuserid",nativeQuery = true)
	int mSubscribeCount(int pageuserid);
}
