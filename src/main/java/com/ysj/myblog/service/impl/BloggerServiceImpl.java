package com.ysj.myblog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ysj.myBlog.dao.BloggerDao;
import com.ysj.myBlog.entity.Blogger;
import com.ysj.myBlog.service.BloggerService;

/**
 * 博主Service实现类
 * @author ysj
 *
 */
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService{

	@Resource
	private BloggerDao bloggerDao;
	
	public Blogger getByUserName(String userName) {
		return bloggerDao.getByUserName(userName);
	}

	public Blogger find(){
		return bloggerDao.find();
	}

	public Integer update(Blogger blogger) {
		return bloggerDao.update(blogger);
	}
	
}
