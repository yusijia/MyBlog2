package com.ysj.myblog.dao;

import com.ysj.myblog.entity.Blog;

import java.util.List;
import java.util.Map;


/**
 * 博客Dao接口
 * @author ysj
 *
 */
public interface BlogDao {

	/**
	 * 根据日期分月分组查询
	 * @return
	 */
	public List<Blog> blogTypesAndCountsByDate();
	
	/**
	 * 分页查询博客
	 * @param map
	 * @return
	 */
	public List<Blog> list(Map<String, Object> map);
	
	/**
	 * 获取文章总数
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);

	/**
	 * 根据id查找实体
	 * @param id
	 * @return
	 */
	public Blog findById(Integer id);

	/**
	 * 更新博客信息
	 * @param blog
	 * @return
	 */
	public Integer update(Blog blog);
	
	/**
	 * 获取上一个博客
	 * @param id
	 * @return
	 */
	public Blog getLastBlog(Integer id);
	
	/**
	 * 获取下一个博客
	 * @param id
	 * @return
	 */
	public Blog getNextBlog(Integer id);
	
	/**
	 * 添加博客信息
	 * @param blog
	 * @return 返回操作记录数
	 */
	public Integer add(Blog blog);
	
	/**
	 * 删除博客信息
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 查询指定的博客类别下的博客数量
	 * @param typeId
	 * @return
	 */
	public Integer getBlogByTypeId(Integer typeId);
}
