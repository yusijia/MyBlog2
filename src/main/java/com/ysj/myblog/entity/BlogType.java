package com.ysj.myblog.entity;


/**
 * 博客类型实体
 * @author ysj
 *
 */
public class BlogType {
	/*与数据库表有关联的属性*/
	private Integer id; 		// 编号
	private String typeName; 	// 博客类型名称
	private Integer orderNo; 	// 排序序号 从小到大排序

	/*与数据库表无关联的属性*/
	private Integer blogCount; 	// 数量(该属性没有与数据库对应)
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}

	@Override
	public String toString() {
		return "BlogType{" +
				"id=" + id +
				", typeName='" + typeName + '\'' +
				", orderNo=" + orderNo +
				'}';
	}
}
