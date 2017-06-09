package com.ysj.myblog.entity;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 博客类型实体
 * @author ysj
 *
 */
public class BlogType {
	/*与数据库表有关联的属性*/
	// 可以为空，但如果不为空则最小值为0，如果更新blog时选类型是'请选择所属类别...'则对应的值为-1，后台验证时将不通过
	@Min(value = 0, message = "请选择正确的类型名")
	private Integer id; 		// 编号
	@NotNull(message = "博客类型名称不能为空")
	private String typeName; 	// 博客类型名称
	@Min(value = 1, message = "排序序号最小为1")
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
