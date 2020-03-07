package com.xu.kinggame.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xu.kinggame.dao.AdminMapper;
import com.xu.kinggame.entity.User;
import com.xu.kinggame.service.AdminService;
import com.xu.kinggame.util.MD5Util;
@Service
public class AdminServiceImpl implements AdminService{

	@Resource
	private AdminMapper adminMapper;
	
	@Override
	public User selectUser(String username, String password) {
		return adminMapper.login(username, password);
	}

	@Override
	public boolean addUser(User user) {
		String md5password = MD5Util.MD5Encode(user.getLoginpassword(), "UTF-8");
		user.setLoginpassword(md5password);
		System.out.println(user.getLoginpassword());
		if(adminMapper.addUser(user)>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public User selectUserById(Long loginId) {
		return adminMapper.selectUserById(loginId);
	}

	@Override
	public boolean updatePassword(Long loginId, String originalPassword, String newPassword) {
		User user = adminMapper.selectUserById(loginId);
		if(user==null) {
			return false;
		}else {
			String orginPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
			String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
			if(!orginPasswordMd5.equals(user.getLoginpassword())) {
				return false;
			}else {
				user.setLoginpassword(newPasswordMd5);
				return adminMapper.updatePassword(user)>0;
			}
		}
		
		
	}

	@Override
	public boolean updateName(Long loginId, String loginUserName, String nickName) {
		User user = adminMapper.selectUserById(loginId);
		if(user==null) {
			return false;
		}
		user.setLoginname(loginUserName);
		user.setAdminnickname(nickName);
		return adminMapper.updatePassword(user)>0;
	}

}
