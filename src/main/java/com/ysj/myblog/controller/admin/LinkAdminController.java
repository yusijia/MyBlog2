package com.ysj.myblog.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysj.myblog.entity.Link;
import com.ysj.myblog.entity.PageBean;
import com.ysj.myblog.service.LinkService;
import com.ysj.myblog.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员友情链接Controller层
 * @author ysj
 *
 */
@Controller
@RequestMapping("/admin/link")
public class LinkAdminController {

	@Resource
	private LinkService linkService;

	// json转换器
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 分页查询友情链接信息
	 * @param page 当前是第几页
	 * @param rows 该页最多显示多少条记录
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void list(@RequestParam(value="page",required=false)String page,
					@RequestParam(value="rows",required=false)String rows,
					HttpServletResponse response)throws Exception{
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getCountOfBlogInPage());
		// 分页查询
		List<Link> linkList = linkService.list(map);
		Long total = linkService.getTotal(map);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// easyUI分页组件需要的两个参数
		resultMap.put("rows", linkList);
		resultMap.put("total", total);
		String result = mapper.writeValueAsString(resultMap);
		// System.out.println(result);
		// 返回给AJAX方法, 所以该方法为void，同下
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 添加或者修改友情链接信息
	 * @param link
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(Link link, HttpServletResponse response)throws Exception{
		int resultTotal;
		if (link.getId() == null) {
			resultTotal = linkService.add(link);
		} else {
			resultTotal = linkService.update(link);
		}

		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(resultTotal > 0){
			resultMap.put("success", true);
		}else{
			resultMap.put("success", false);
		}
		String result = mapper.writeValueAsString(resultMap);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 友情链接信息删除
	 * @param ids 从前台传来的要删除的link的id(逗号分隔)
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="ids", required=false)String ids,
						  HttpServletResponse response)throws Exception{
		String [] idsStr = ids.split(",");

		Map<String,Object> resultMap = new HashMap<String,Object>();
		for(int i = 0; i < idsStr.length; i++){
			linkService.delete(Integer.parseInt(idsStr[i]));				
		}
		resultMap.put("success", true);
		String result = mapper.writeValueAsString(resultMap);
		ResponseUtil.write(response, result);
	}
}
