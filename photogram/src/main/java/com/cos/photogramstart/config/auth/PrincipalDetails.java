package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.hibernate.id.enhanced.TableStructure;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;


@Data
public class PrincipalDetails implements UserDetails,OAuth2User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
private 	User user;
private	Map<String , Object>attributes;


	public PrincipalDetails(User user) {
		this.user=user;
	}
	
	

	public PrincipalDetails(User user,Map<String, Object>attributes) {
		this.user=user;
		this.attributes=attributes;
	}
	
	public User getuser() {
		return this.user;
	}
	public void setuser(User user) {
		this.user=user;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		Collection<GrantedAuthority> collector =new ArrayList<>();
		collector.add(()-> {	return user.getRole();
			
		});
		return collector;
	}

	
	
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
	
	
	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (String)attributes.get("name");
	}

}
