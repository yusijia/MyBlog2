package com.ysj.myblog.util;

/**
 * 分页工具类
 * @author ysj
 *
 */
public class PageUtil {

	/**
	 * 生成分页代码
	 * @param targetUrl 目标地址
	 * @param totalNum 总记录数
	 * @param currentPage 当前页
	 * @param pageSize 每页大小
	 * @return
	 */
	public static String genPagination(String targetUrl,long totalNum,int currentPage,int pageSize,String param){
		// 总页数
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0){
			return "未查到数据";
		}else{
			StringBuffer pageCode=new StringBuffer();
			// 注意param存了本次查询用到的条件参数(日期, 类型等)，生成分页代码时会将这些param参数也设置上
			pageCode.append("<li><a href='"+targetUrl+"?page=1&"+param+"'>首页</a></li>");
			if(currentPage>1){
				pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>上一页</a></li>");			
			}else{
				pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>上一页</a></li>");		
			}
			// 显示[currentPage - 2, currentPage + 2]范围的页码
			for(int i=currentPage-2;i<=currentPage+2;i++){
				if(i<1||i>totalPage){// 不符合
					continue;
				}
				if(i==currentPage){// 当前页
					pageCode.append("<li class='active'><a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a></li>");	
				}else{
					pageCode.append("<li><a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a></li>");	
				}
			}
			if(currentPage<totalPage){
				pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>下一页</a></li>");		
			}else{
				pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>下一页</a></li>");	
			}
			pageCode.append("<li><a href='"+targetUrl+"?page="+totalPage+"&"+param+"'>尾页</a></li>");
			return pageCode.toString();
		}
	}
	

	
	
}
