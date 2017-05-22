package com.ysj.myblog.controller;

import com.ysj.myblog.entity.Blog;
import com.ysj.myblog.lucene.BlogIndex;
import com.ysj.myblog.service.BlogService;
import com.ysj.myblog.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


/**
 * 博客Controller
 * @author ysj
 *
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;
	
	private BlogIndex blogIndex = new BlogIndex();

	/**
	 * 每页显示的博客数
	 */
	@Value("${count_of_blog_in_page}")
	private int countOfBlogInPage;

	
	/**
	 * 查看文章
	 * @param id 文章的id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/articles/{id}")
	public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request)throws Exception{
		ModelAndView mav = new ModelAndView();
		Blog blog = blogService.findById(id);
		
		// 访问量加1
		blog.setCountOfClick(blog.getCountOfClick()+1);
		blogService.update(new Blog(blog.getId(),
									blog.getUpdateTime(),
									blog.getCountOfClick()));// 更新数据库数据

		
		// 关键字处理空格后存入mav
		mav.addObject("keywords", getKeyWords(blog.getKeywords()));
		// 添加完整文章内容
		mav.addObject("blog",blog);
		// 加入上一篇和下一篇的pageCode html代码
		mav.addObject("pageCode", this.getUpAndDownPageCode(blogService.getLastBlog(id), blogService.getNextBlog(id), request.getServletContext().getContextPath()));
		// 页面标签上的标题
		mav.addObject("pageTitle", blog.getTitle()+"-MyBlog");
		// mainTemp公共模板jsp中的mainPage模块引入
		mav.addObject("mainPage", "foreground/blog/view.jsp");
		// 设置返回视图名(配置的时候配置的以jsp结尾的)
		mav.setViewName("mainTemp");// 转发
		return mav;
	}

	/**
	 * 根据关键字查询相关博客信息，支持上下分页
	 * @param q
	 * @param page 当前页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/q")
	public ModelAndView search(@RequestParam(value="q", required=false)String q,
							   @RequestParam(value="page",required=false)String page,
							   HttpServletRequest request)throws Exception{
		ModelAndView mav = new ModelAndView();

		if (StringUtil.isEmpty(page)) {
			page="1";
		}

		List<Blog> blogList = blogIndex.searchBlog(q);
		// 处理下toIndex，最后一页不一定有countOfBlogInPage这么多篇博文
		Integer toIndex = blogList.size() >= Integer.parseInt(page)*countOfBlogInPage ?
				Integer.parseInt(page)*countOfBlogInPage :
				blogList.size();;
		// 将该页的blog设置进去
		mav.addObject("blogList", blogList.subList((Integer.parseInt(page)-1)*countOfBlogInPage, toIndex));
		mav.addObject("pageCode",
				genUpAndDownPageCode(Integer.parseInt(page),
									blogList.size(),
									q, countOfBlogInPage,
									request.getServletContext().getContextPath()));
		mav.addObject("q", q);// 回显查询条件
		// 页面标签上的标题
		mav.addObject("pageTitle", "搜索关键字'"+q+"'结果页面");
		mav.addObject("resultTotal", blogList.size());// 查出的总记录数
		// mainTemp公共模板jsp中的mainPage模块引入
		mav.addObject("mainPage", "foreground/blog/searchResult.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}

	/**
	 * 将数据库中关键字存入List里(数据库中村的keyWords里有空格)
	 * @param keywords
	 * @return
	 */
	private Object getKeyWords(String keywords){
		if (StringUtil.isNotEmpty(keywords)) {
			String [] arr = keywords.split(" ");
			return StringUtil.filterEmpty(Arrays.asList(arr));
		} else {
			return null;
		}
	}
	
	/**
	 * 获取上一篇博客和下一篇博客的pageCode
	 * @param lastBlog
	 * @param nextBlog
	 * @param projectContext 项目路径
	 * @return
	 */
	private String getUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
		StringBuilder pageCode = new StringBuilder();
		if (lastBlog == null || lastBlog.getId() == null) {
			pageCode.append("<p>上一篇：没有了</p>");
		} else {
			pageCode.append("<p>上一篇：<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}
			
		if (nextBlog == null || nextBlog.getId() == null) {
			pageCode.append("<p>下一篇：没有了</p>");
		} else {
			pageCode.append("<p>下一篇：<a href='"+projectContext+"/blog/articles/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}

	/**
	 * 获取上一页，下一页代码 
	 * @param page 当前页
	 * @param totalNum	总页数
	 * @param q	查询关键字
	 * @param countOfBlogInPage 每页显示页数的上限
	 * @param projectContext 
	 * @return
	 */
	private String genUpAndDownPageCode(int page, int totalNum, String q, int countOfBlogInPage, String projectContext) {
		// 计算总页数
		long totalPage = totalNum%countOfBlogInPage == 0 ?
				totalNum/countOfBlogInPage :
				totalNum/countOfBlogInPage + 1;
		StringBuilder pageCode = new StringBuilder();
		if (totalPage == 0) {
			return "";
		} else {
			pageCode.append("<nav>");
			pageCode.append("<ul class='pager'>");
			if (page > 1) {
				pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page-1)+"&q="+q+"'>上一页</a></li>");
			} else {
				pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
			}
			if (page < totalPage) {
				pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page+1)+"&q="+q+"'>下一页</a></li>");
			} else {
				pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
			}
			pageCode.append("</ul>");
			pageCode.append("</nav>");
		}
		return pageCode.toString();
	}
}
