package com.ysj.myblog.service.impl;

import com.ysj.myblog.dao.UserDao;
import com.ysj.myblog.entity.User;
import com.ysj.myblog.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	@Override
	public User login(User user) {
		return userDao.login(user);
	}

}
