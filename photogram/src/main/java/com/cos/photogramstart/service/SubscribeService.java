package com.cos.photogramstart.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper; //데이터베이스 결과를 메핑해줌
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.exe.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em; //repository는 ENtityManager를 구현해서 만들어져 있는 구현체
	
	
	
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalid, int pageUserid) {
		//쿼리준비
		
		System.out.println("subscribe 서비스 내부1");
		
		
		
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT u.id, u.username,u.profileImageUrlString ,  ");
			sb.append("if((SELECT 1 FROM subscribe WHERE fromuserid=? AND touserid=u.id),1,0) SubscribeState ,  ");
			sb.append("if((?=u.id),1,0)  EqualuserState ");
			sb.append("FROM user u INNER JOIN subscribe s  ");
			sb.append("ON u.id= s.toUserid ");
			sb.append("WHERE s.fromUserid=?  ");
		//한칸씩 끝에 뛰어주기 세미콜롬 첨부 X
		//WHERE fromuserid=?로그인한 아이디 principal id
		//위의 ?=u.id 로그인한 아이디
		//마지막 물음표 pageuser ID
			System.out.println("subscribe 서비스 내부2");
			//쿼리 완성
		Query query =em.createNativeQuery(sb.toString())
				.setParameter(1, principalid)
				.setParameter(2, principalid)
				.setParameter(3, pageUserid);
			//쿼리 실행(qlrm 라이브러리 필요 = DTO에 DB껼과를 매핑하기 위해서)
			JpaResultMapper result =new JpaResultMapper();
			System.out.println("subscribe 서비스 내부3");
		List<SubscribeDto>subscribeDtos=	result.list(query, SubscribeDto.class);
		return subscribeDtos;
	}
	
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) { 
		try {
	subscribeRepository.mSubscribe(fromUserId, toUserId); 
		}catch (Exception e) {
		throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
			subscribeRepository.mUnSubscribe(fromUserId, toUserId);
		
	}
}
