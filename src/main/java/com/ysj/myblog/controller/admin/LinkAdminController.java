package com.ysj.myblog.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysj.myblog.entity.Link;
import com.ysj.myblog.entity.PageBean;
import com.ysj.myblog.entity.ResultObject;
import com.ysj.myblog.entity.ResultStatusCode;
import com.ysj.myblog.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
	@ResponseBody
	public Object list(@RequestParam(value="page",required=false)String page,
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
		return resultMap;
		/*String result = mapper.writeValueAsString(resultMap);
		ResponseUtil.write(response, result);*/
	}
	
	/**
	 * 添加或者修改友情链接信息
	 * @param link
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid Link link,
					 HttpServletResponse response)throws Exception{
		int resultTotal;
		if (link.getId() == null) {
			resultTotal = linkService.add(link);
		} else {
			resultTotal = linkService.update(link);
		}

		Map<String,Object> data = new HashMap<String,Object>();
		if(resultTotal > 0){
			data.put("success", true);
		}else{
			data.put("success", false);
		}
		return new ResultObject(ResultStatusCode.REQUEST_SUCCESS.getCode(), ResultStatusCode.REQUEST_SUCCESS.getMessage(), data);
	}
	
	/**
	 * 删除友情链接信息
	 * @param ids 从前台传来的要删除的link的id(逗号分隔)
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam(value="ids", required=false)String ids,
						  HttpServletResponse response)throws Exception{
		String [] idsStr = ids.split(",");

		for(int i = 0; i < idsStr.length; i++){
			linkService.delete(Integer.parseInt(idsStr[i]));				
		}

		Map<String,Object> data = new HashMap<String,Object>();
		data.put("success", true);
		return new ResultObject(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getMessage(), data);
	}
}
