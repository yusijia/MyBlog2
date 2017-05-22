package com.ysj.myblog.servlet.listener;

import com.ysj.myblog.entity.Blog;
import com.ysj.myblog.entity.BlogType;
import com.ysj.myblog.entity.Blogger;
import com.ysj.myblog.entity.Link;
import com.ysj.myblog.service.BlogService;
import com.ysj.myblog.service.BlogTypeService;
import com.ysj.myblog.service.BloggerService;
import com.ysj.myblog.service.LinkService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 *  上下文监听, 获取Application, 将一些静态资源存入Application里
 *  注意这个组件也要加注解 
 * @author ysj
 *
 */
@WebListener
public class InitComponent implements ServletContextListener,ApplicationContextAware{

	// applicationContext上下文(通过继承Spring封装的ApplicationContextAware接口)
	private static ApplicationContext applicationContext;

	// ServletContext上下文初始化时需要做的操作
	public void contextInitialized(ServletContextEvent sce) {
		// 获取ServletContext上下文
		ServletContext application = sce.getServletContext();
		
		// 静态化博主信息
		// 通过继承Spring封装的ApplicationContextAware接口，和getBean方法获取Spring管理的Bean实例
		BloggerService bloggerService = (BloggerService) applicationContext.getBean("bloggerService");
		Blogger blogger = bloggerService.find(); // 获取博主信息
		if (blogger != null) {
			blogger.setPassword(null);// 清空密码
		}
		application.setAttribute("blogger", blogger);
		
		// 静态化友情链接信息
		LinkService linkService = (LinkService) applicationContext.getBean("linkService");
		List<Link> linkList = linkService.list(null); // 查询所有的友情链接信息
		application.setAttribute("linkList", linkList);
		
		// 静态化博客类别及博客数量信息
		BlogTypeService blogTypeService = (BlogTypeService) applicationContext.getBean("blogTypeService");
		List<BlogType> blogTypeCountList = blogTypeService.blogTypesAndCounts(); // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		// 静态化根据日期查询的博客数量信息
		BlogService blogService = (BlogService) applicationContext.getBean("blogService");
		List<Blog> blogTypesAndCountsByDate = blogService.blogTypesAndCountsByDate(); // 根据日期分组查询博客
		application.setAttribute("blogTypesAndCountsByDate", blogTypesAndCountsByDate);
	}

	// ServletContext上下文释放时需要做的操作
	public void contextDestroyed(ServletContextEvent sce) {
		
		
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
