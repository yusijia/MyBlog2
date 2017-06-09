package com.ysj.myblog.entity;

import javax.validation.constraints.Min;

/**
 * 友情链接实体
 * @author ysj
 *
 */
public class Link {

	/*与数据库表有关联的属性*/
	private Integer id; 		// 编号
	private String linkName; 	// 链接名称
	private String linkUrl;		// 链接地址
	@Min(value = 1, message = "排列等级最小为1")
	private Integer orderNo; 	// 排序序号 从小到大排序
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "Link{" +
				"id=" + id +
				", linkName='" + linkName + '\'' +
				", linkUrl='" + linkUrl + '\'' +
				", orderNo=" + orderNo +
				'}';
	}
}
