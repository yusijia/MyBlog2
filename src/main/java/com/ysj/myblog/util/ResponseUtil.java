package com.ysj.myblog.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;


/**
 * 主要用于将后台生成的json对象传到页面去，虽然SpringMVC自带了自动帮忙转化的，但还有不灵活
 * 建议用下面这个工具类处理后台json传到前台去
 * @author ysj
 *
 */
public class ResponseUtil {

	public static void write(HttpServletResponse response,Object o)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		out.println(o.toString());
		out.flush();
		out.close();
	}
}
