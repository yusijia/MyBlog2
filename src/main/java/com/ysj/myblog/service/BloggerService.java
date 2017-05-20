package com.ysj.myblog.service;


import com.ysj.myblog.entity.Blogger;

/**
 * 博主service接口
 * @author ysj
 *
 */
public interface BloggerService {

	/**
	 * 通过用户名获取用户相关信息
	 * @param userName
	 * @return
	 */
	public Blogger getByUserName(String userName);

	/**
	 * 查询博主信息
	 * @return
	 */
	public Blogger find();
	
	/**
	 * 更新博主信息
	 * @param blogger
	 * @return
	 */
	public Integer update(Blogger blogger);

}
