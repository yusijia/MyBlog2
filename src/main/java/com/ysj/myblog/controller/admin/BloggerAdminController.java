package com.ysj.myblog.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysj.myblog.entity.Blogger;
import com.ysj.myblog.entity.ResultObject;
import com.ysj.myblog.entity.ResultStatusCode;
import com.ysj.myblog.service.BloggerService;
import com.ysj.myblog.util.CryptographyUtil;
import com.ysj.myblog.util.DateUtil;
import com.ysj.myblog.util.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 博主后台管理Controller层
 * @author ysj
 *
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

	@Resource
	private BloggerService bloggerService;

	// json转换器
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 查询博主信息
	 * @throws Exception
	 */
	@RequestMapping("/find")
	@ResponseBody
	public Object find()throws Exception{
		Blogger blogger = bloggerService.find();
		return blogger;
	}
	
	/**
	 * 修改博主信息
	 * @param imageFile
	 * @param blogger
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void save(@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
					 @Valid Blogger blogger,
					 HttpServletRequest request,
					 HttpServletResponse response)throws Exception{
		// 处理上传图片
		if (imageFile != null && !imageFile.isEmpty()) {
			// 获取服务器根路径
			String filePath = request.getServletContext().getRealPath("/");
			// 获得新的文件名(当前日期)
			String imageName = DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
			// 写入路径里
			imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
			// blogger对象里保存文件名
			blogger.setImageName(imageName);
		}

		StringBuffer result = new StringBuffer();
		int resultTotal = bloggerService.update(blogger);
		if (resultTotal > 0) {
			result.append("<script language='javascript'>alert('修改成功！');</script>");
		} else {
			result.append("<script language='javascript'>alert('修改失败！');</script>");
		}
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 修改博主密码
	 * @param newPassword
	 * @param response
	 * @throws Exception
	 */
	@PostMapping("/modifyPassword")
	@ResponseBody
	public Object modifyPassword(String newPassword,
								HttpServletResponse response)throws Exception{
		Blogger blogger = new Blogger();
		// 注意BloggerController里的登入方法里md5的salt也是ysj
		blogger.setPassword(CryptographyUtil.md5(newPassword,"ysj"));
		int resultTotal = bloggerService.update(blogger);

		Map<String, Object> data = new HashMap<String, Object>();
		if (resultTotal > 0) {
			data.put("success", true);
		} else {
			data.put("success", false);
		}
		return new ResultObject(ResultStatusCode.REQUEST_SUCCESS.getCode(), ResultStatusCode.REQUEST_SUCCESS.getMessage(), data);
	}

	/**
	 * 注销
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/logout")
	public String logout()throws Exception{
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
}
