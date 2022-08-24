package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class UserController {
	private final UserService UserServices;
	
	
	
		 	 @GetMapping("/user/{pageuserid}")
				public String profile(@PathVariable int pageuserid,Model model,@AuthenticationPrincipal PrincipalDetails user){
						UserProfileDto dto= UserServices.회원프로필(pageuserid,user.getuser().getId());
						model.addAttribute("dto", dto);
						return "user/profile";
					}




			@GetMapping("/user/{id}/update")
			public String update(@PathVariable int id,@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
				//session 안에 securityContextholder 안에 Authentication 안에 principaldetails 안에 user....->@AuthenticationPrincipalDetails
					Authentication auth =SecurityContextHolder.getContext().getAuthentication();
					
					PrincipalDetails mPrincipalDetails= (PrincipalDetails)auth.getPrincipal();
					/* User userentity= UserServices.회원프로필(id); */
					/*
					 * System.out.println("직접 찾은 세션정보"+mPrincipalDetails.getuser());
					 * 
					 * System.out.println("update에서 발생"+principalDetails.getuser());
					 */
					 
					/* System.out.println(userentity.getImages()); */
					model.addAttribute("principal",principalDetails.getuser());
				
				return "user/update";
			}

}
