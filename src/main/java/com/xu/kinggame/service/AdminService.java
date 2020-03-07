package com.xu.kinggame.service;

import com.xu.kinggame.entity.User;

public interface AdminService {

	User selectUser(String username,String password);
	boolean addUser(User user);
	User selectUserById(Long loginId);
	boolean updatePassword(Long loginId, String originalPassword, String newPassword);
	boolean updateName(Long loginId, String loginUserName, String nickName);
}
