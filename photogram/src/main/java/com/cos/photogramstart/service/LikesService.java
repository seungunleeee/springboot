package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Service
public class LikesService {
private final LikesRepository likesRepository;
@Transactional
public void 좋아요(int imageid,int principalid) {
	likesRepository.mlikes(imageid, principalid);
}

@Transactional
public void 좋아요취소(int imageid,int principalid) {
	likesRepository.mUnlikes(imageid, principalid);
}
	
	
	
}
