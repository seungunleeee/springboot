package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
	
	
	//1. 패스워드는 알아서 채킹함
	//2. 리턴이 잘되면 자동으로 세션을 만든다
	private final UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		 
		User userEntity=userRepository.findByUsername(username);
		if(userEntity==null)	{return null;
		}
		else {
			return new PrincipalDetails(userEntity);
		}
		
			
			
			
		
		
		
	}

}
