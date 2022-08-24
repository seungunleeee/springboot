package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;


//어노테이션 없어도 JPAREPOSITORY 상속시 IOC등록이 자동으로 됨
public interface UserRepository extends JpaRepository<User, Integer>{
	//JPA  Query Method사용
	
	
	User findByUsername(String username);
}
