package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.auth.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

   
@RequiredArgsConstructor
@EnableWebSecurity// 해당파일로 시큐리티를 활성화
@Configuration//IOC     
public class SecurityConfig extends WebSecurityConfigurerAdapter{
private final OAuth2DetailsService oAuth2DetailsService;
	
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/","/image/**","/user/**","/subscribe/**","/comment/**","/api/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin")//get을 낚아챔
			.loginProcessingUrl("/auth/signin")//post를 낚아챔
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(oAuth2DetailsService);
		/*
		 * .and() .oauth2Login()//form로그인도 하는데, oauth로그인도 할거야
		 * .userInfoEndpoint()//oauth2로그인을 하면 최종응답을 회원정보로 바로돌려줘
		 * .userService(oAuth2DetailsService);
		 */
			
			
			
		
	}
	
}
