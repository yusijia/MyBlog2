package com.ysj.myblog.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 * @author 
 *
 */
public class StringUtil {

	/**
	 * 判断是否是空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if((str != null) && !"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 格式化模糊查询
	 * @param str
	 * @return
	 */
	public static String formatLike(String str){
		if(isNotEmpty(str)){
			return "%"+str+"%";
		}else{
			return null;
		}
	}
	
	/**
	 * 过滤掉集合里的空值null或""
	 * @param list
	 * @return
	 */
	public static List<String> filterEmpty(List<String> list){
		List<String> resultList = new ArrayList<String>();
		for(String item : list){
			if(isNotEmpty(item)){
				resultList.add(item);
			}
		}
		return resultList;
	}

}
