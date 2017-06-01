package com.ysj.myblog.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysj.myblog.entity.BlogType;
import com.ysj.myblog.entity.PageBean;
import com.ysj.myblog.service.BlogService;
import com.ysj.myblog.service.BlogTypeService;
import com.ysj.myblog.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {
	
	@Resource
	private BlogTypeService blogTypeService;
	
	@Resource
	private BlogService blogService;

	// json转换器
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 分页查询博客类别信息
	 * @param page
	 * @param rows
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void list(@RequestParam(value="page",required=false)String page,
					@RequestParam(value="rows",required=false)String rows,
					HttpServletResponse response)throws Exception{
		// 分页用的
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		// 分页查询list时传入的map封装了start和size
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getCountOfBlogInPage());

		// 查询出博客类型列表
		List<BlogType> blogTypeList = blogTypeService.list(map);
		Long total = blogTypeService.getTotal(map);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// easyUI分页组件需要的两个参数
		resultMap.put("rows", blogTypeList);
		resultMap.put("total", total);

		String result = mapper.writeValueAsString(resultMap);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 添加或者修改博客类别信息
	 * @param blogType
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(BlogType blogType, HttpServletResponse response)throws Exception{
		int resultTotal;
		// 选择添加或修改操作
		if(blogType.getId() == null){
			resultTotal = blogTypeService.add(blogType);
		}else{
			resultTotal = blogTypeService.update(blogType);
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
	 * 博客类别信息删除
	 * @param ids
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="ids", required=false)String ids,
							HttpServletResponse response)throws Exception{
		String[] idsStr = ids.split(",");
		// 因为类别下有文章而删除失败的博客类别ID
		List<Integer> blogTypeId = new ArrayList<Integer>();
		// 因为类别下有文章而删除失败的标志
		boolean isOK = true;
		
		for (int i = 0; i < idsStr.length; i++) {
			if (blogService.getBlogByTypeId(Integer.parseInt(idsStr[i])) > 0) {
				isOK = false;
				blogTypeId.add(Integer.parseInt(idsStr[i]));
			} else {
				blogTypeService.delete(Integer.parseInt(idsStr[i]));				
			}
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (isOK == false) {
			resultMap.put("exist", "博客类别下有博客，不能删除！");
			resultMap.put("blogTypeId", blogTypeId);
		}
		resultMap.put("success", true);

		String result = mapper.writeValueAsString(resultMap);
		ResponseUtil.write(response, result);
	}
	
}
