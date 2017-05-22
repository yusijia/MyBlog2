package com.ysj.myblog.controller;

import com.ysj.myblog.entity.Blogger;
import com.ysj.myblog.service.BloggerService;
import com.ysj.myblog.util.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 博主Controller层
 * @author ysj
 *
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Resource
	private BloggerService bloggerService;
	
	/**
	 * 
	 * @param blogger 这个封装了博主的所有信息的对象实例静态化存在了Application中, login.jsp文件中
	 * input标签里是name="userName",也可以是blogger.userName,只不过值栈里只有blogger对象有userName
	 * 和password信息所以springMVC会把这两个从表单里传来的信息存入blogger对象里
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Blogger blogger, HttpServletRequest request){
		// 获取当前登入的用户
		Subject subject = SecurityUtils.getSubject();
		// salt 为 ysj
		// 这里的blogger是封装了用户表单传来的用户名和密码
		// 封装成token
		UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), "ysj"));
		try {
			// 用这里封装的token与shiro底层通过MyRealm类验证身份方法返回的authcInfo封装的token比较
			subject.login(token); // 登录验证		
			return "redirect:/admin/main.jsp"; // 成功时转发到main.jsp
		} catch (Exception e){
			// 登入验证失败
			e.printStackTrace();
			// blogger信息回显到页面上
			request.setAttribute("blogger", blogger);
			// 错误提示信息
			request.setAttribute("errorInfo", "用户名或者密码错误！");
			return "login";
		}
	}
	
	/**
	 * 关于博主
	 * 这里要取数据，所以用ModelAndView
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutMe")
	public ModelAndView aboutMe()throws Exception{
		ModelAndView mav = new ModelAndView();
		// 页面标签上的标题
		mav.addObject("pageTitle", "关于我");
		// mainTemp公共模板jsp中的mainPage模块引入
		mav.addObject("mainPage", "foreground/blogger/info.jsp");
		// 设置返回视图名(配置的时候配置的以jsp结尾的)
		mav.setViewName("mainTemp");
		return mav;
	}
}
