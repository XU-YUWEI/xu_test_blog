package com.xu.kinggame.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.xu.kinggame.entity.User;

@Component
public interface AdminMapper {

	int addUser(User user);
	User login(@Param("username") String username,@Param("password") String password);
	User selectUserById(Number loginId);
	int updatePassword(User user);
	
}
