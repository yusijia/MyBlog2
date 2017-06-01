package com.ysj.myblog.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysj.myblog.entity.Blog;
import com.ysj.myblog.entity.PageBean;
import com.ysj.myblog.lucene.BlogIndex;
import com.ysj.myblog.service.BlogService;
import com.ysj.myblog.util.ResponseUtil;
import com.ysj.myblog.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 后台博客Controller层
 * @author ysj
 *
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

	private BlogIndex blogIndex = new BlogIndex();
	
	@Resource
	private BlogService blogService;

	// json转换器
	private ObjectMapper mapper = new ObjectMapper();


	/**
	 * 
	 * 后台添加/修改博客
	 * @param blog 前台传过来的json里的数据通过SpringMVC传到blog对象里对应的字段里了
	 * @param response
	 * @return 返回json对象
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(Blog blog, HttpServletResponse response) throws Exception{
		int resultTotal;
		// 传来了id就是修改，没传就是添加
		if(blog.getId() == null){// 添加新文章
			resultTotal = blogService.add(blog);
			// 写博客后将博客的相关信息做成索引分词,用于前台lucene搜索用
			blogIndex.addIndex(blog);
		}else{// 修改文章
			resultTotal = blogService.update(blog); 
			blogIndex.updateIndex(blog); 
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(resultTotal > 0){
			resultMap.put("success", true);
		}else{
			resultMap.put("success", false);
		}
		String result = mapper.writeValueAsString(resultMap);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 后台管理页面分页查询博客信息
	 * @param page 当前第page页
	 * @param rows	pageSize
	 * @param blog  将查询条件封装到该Blog对象里例如标题
	 * @param response ajax时用
	 * @return 根据easyUI分页组件的要求，返回的是json对象
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void list(@RequestParam(value="page", required=false)String page,
						@RequestParam(value="rows",required=false)String rows,
						Blog blog,
						HttpServletResponse response)throws Exception{
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", StringUtil.formatLike(blog.getTitle()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getCountOfBlogInPage());
		List<Blog> blogList = blogService.list(map);
		Long total = blogService.getTotal(map);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// easyUI规范要求必须返回rows和total两个参数才能解析成分页查询
		resultMap.put("rows", blogList);// 将该页的blogList放到rows里
		resultMap.put("total", total);// 总记录数
		String result = mapper.writeValueAsString(resultMap);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 博客信息删除
	 * @param ids
	 * @param response
	 * @return 返回json对象
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="ids", required=false)String ids,
						HttpServletResponse response)throws Exception{
		String []idsStr = ids.split(",");
		for(int i = 0; i < idsStr.length; i++){
			// 删除博客
			blogService.delete(Integer.parseInt(idsStr[i]));
			// 删除lucene检索用的博客索引
			blogIndex.deleteIndex(idsStr[i]);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);

		String result = mapper.writeValueAsString(resultMap);
		ResponseUtil.write(response, result);
	}
	
	
	/**
	 * 通过Id查找实体
	 * @param id 必须传来id，默认require为true
	 * @param response
	 * @return 根据ueditor的需求，返回json对象
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public void findById(@RequestParam(value="id")String id,
							HttpServletResponse response)throws Exception {
		Blog blog = blogService.findById(Integer.parseInt(id));
		
		// 将blog对象封装成json对象
		String result = mapper.writeValueAsString(blog);
		ResponseUtil.write(response, result);
	}
}
