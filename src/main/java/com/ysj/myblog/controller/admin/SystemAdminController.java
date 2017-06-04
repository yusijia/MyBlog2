package com.ysj.myblog.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysj.myblog.entity.Blog;
import com.ysj.myblog.entity.BlogType;
import com.ysj.myblog.entity.Blogger;
import com.ysj.myblog.entity.Link;
import com.ysj.myblog.service.BlogService;
import com.ysj.myblog.service.BlogTypeService;
import com.ysj.myblog.service.BloggerService;
import com.ysj.myblog.service.LinkService;
import com.ysj.myblog.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 管理员系统Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

	@Resource
	private BloggerService bloggerService;
	
	@Resource
	private LinkService linkService;
	
	@Resource
	private BlogTypeService blogTypeService;
	
	@Resource
	private BlogService blogService;

	// json转换器
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * 刷新系统缓存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public void refreshSystem(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ServletContext application=RequestContextUtils.findWebApplicationContext(request).getServletContext();
		// 获取博主信息
		Blogger blogger=bloggerService.find();
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		// 查询所有的友情链接信息
		List<Link> linkList=linkService.list(null);
		application.setAttribute("linkList", linkList);
		// 查询博客类别以及博客的数量
		List<BlogType> blogTypeCountList=blogTypeService.blogTypesAndCounts();
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		// 根据日期分组查询博客
		List<Blog> blogCountList=blogService.blogTypesAndCountsByDate();
		application.setAttribute("blogTypesAndCountsByDate", blogCountList);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		String result = mapper.writeValueAsString(resultMap);
		ResponseUtil.write(response, result);
	}
	
	
}
