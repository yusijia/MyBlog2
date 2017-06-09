package com.ysj.myblog.controller;

import com.ysj.myblog.entity.Blog;
import com.ysj.myblog.entity.PageBean;
import com.ysj.myblog.service.BlogService;
import com.ysj.myblog.util.PageUtil;
import com.ysj.myblog.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 主页Controller
 * @author ysj
 *
 */
@Controller
@RequestMapping("/") // 根目录
public class IndexController {

	@Resource
	private BlogService blogService;

	/**
	 * 每页显示的博客数
	 */
	@Value("${count_of_blog_in_page}")
	private int countOfBlogInPage;

	/**
	 * 转发到请求主页,在本地是通过'http://localhost:8080/' 访问
	 * @return
	 */
	@RequestMapping("/")
	public String guide() {
		return "forward:/guide.jsp";
	}

	/**
	 * 请求主页
	 * 第一次访问时没有page, typeId releaseDateStr属性，所以required=false
	 * @param page 访问第几页
	 * @param typeId 主页侧边栏按文章类型查询出文章
	 * @param releaseDateStr 主页侧边栏按文章发布日期查询出文章
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value="page",required=false)String page,
			@RequestParam(value="typeId",required=false)String typeId,
			@RequestParam(value="releaseDateStr",required=false)String releaseDateStr,
			HttpServletRequest request)throws Exception{
		
		ModelAndView mav = new ModelAndView();

		// 第一次访问时page为空，自动设置page为1，访问首页
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), countOfBlogInPage);
		
		// 用于封装下面blogService.list方法需要的查询blog的条件信息: start, size
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getCountOfBlogInPage());
		map.put("typeId", typeId);// 点击侧边栏的按文章类别中的类别会传来typeId,限定查出该类别下的文章
		map.put("releaseDateStr", releaseDateStr);// 点击侧边栏的按文章发布时间中的类别会传来releaseDateStr,限定查出该时间下的文章
		
		List<Blog> blogList = blogService.list(map);// 根据分页条件和查询条件查询出当前页的博客

		// 侧边栏按日期，类别的查询条件
		// 如果点击了按文章类别查询则typeId会再次出现在URI中(看PageUtil.genPagination源码就知道了)，再点击某
		// 页时，在这里再将typeId取出拼接到param中可继续查询该文章类型下的第几页的文章，releaseDateStr同理
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeId", typeId);
		param.put("releaseDateStr", releaseDateStr);
		
		// 文章中的图用jsoup做成缩略图并保存到blogList里
		getSmallPic(blogList);
		// 文章列表
		mav.addObject("blogList", blogList);
		// 文章列表分页html代码
		mav.addObject("pageCode",
				PageUtil.genPagination(
						request.getContextPath()+"/index.html",
						blogService.getTotal(map),
						Integer.parseInt(page),
						countOfBlogInPage,// 这里可以扩展成加载配置文件里的countOfBlogInPage参数来自定义
						param));
		// 页面标签上的标题
		mav.addObject("pageTitle", "MyBlog");
		// mainTemp公共模板jsp中的mainPage模块引入
		mav.addObject("mainPage", "foreground/blog/list.jsp");
		// 设置返回视图名(配置的时候配置的以jsp结尾的)
		mav.setViewName("mainTemp");// 转发
		return mav;
	}

	/**
	 * menu中的源码下载页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public ModelAndView download()throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.addObject("pageTitle", "本站源码下载页面 MyBlog");
		mav.addObject("mainPage", "foreground/system/download.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * 提取文章中的图用jsoup做出缩略图
	 * @param blogList
	 */
	private void getSmallPic(List<Blog> blogList) {
		// 提取博客内容中的图处理成缩略图
		for (Blog blog : blogList) {
			List<String> imageList = blog.getImageList();// 存该文章的缩略图
			String blogInfo = blog.getContent();		 // 存该文章的内容
			Document doc = Jsoup.parse(blogInfo);		 // 解析文章内容
			// Elements jpgs = doc.select("img[src$=.jpg]");// 选择器提取img元素后缀为jpg的图
			Elements imgs = doc.select("img"); // 选择器提取img元素
			for (int i = 0; i < imgs.size(); i++) {
				Element img = imgs.get(i);
				imageList.add(img.toString());
				if (i == 2) { // 控制最多只显示三种缩略图
					break;
				}
			}
		}
	}
	
	
}
