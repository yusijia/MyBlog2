package com.ysj.myblog.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 博客实体
 * @author ysj
 *
 */
public class Blog {
	/*与数据库表有关联的属性*/
	private Integer id; 		// 编号
	@NotNull(message = "标题不能为空")
	@Size(min = 1, max = 20, message = "标题为1到20位字符")
	private String title; 		// 博客标题
	private String summary; 	// 摘要
	private Date createTime; 	// 发布日期
	private Date updateTime;	// 更新日期
	private Integer countOfClick;// 查看次数
	private Integer countOfReply; 	// 回复次数
	@NotNull(message = "内容不能为空")
	@Size(min = 1, message = "内容最少为一个字符")
	private String content; 	// 博客内容
	private String keywords; 	// 关键字  空格隔开

	@NotNull(message = "博客类别不能为空")
	@Valid // 嵌套数据验证
	private BlogType blogType; 	// 博客类型，作为外键关联t_blogtype的主键

	/*与数据库表无关联的属性*/
	private String contentNoTag; // 博客内容，无网页标签 Lucene分词用到
	private Integer blogCount; 	// 博客数量 非博客实际属性 主要是 根据发布日期归档查询数量时用到
	private String releaseDateStr; // 格式化发布日期的字符串 只取年和月
	private List<String> imageList = new LinkedList<String>(); // 博客里存在的图片，主要用于列表展示的缩略图

	public Blog() {	}

	public Blog(Integer id, Date updateTime, Integer countOfClick) {
		this.id = id;
		this.updateTime = updateTime;
		this.countOfClick = countOfClick;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BlogType getBlogType() {
		return blogType;
	}
	public void setBlogType(BlogType blogType) {
		this.blogType = blogType;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	public String getContentNoTag() {
		return contentNoTag;
	}
	public void setContentNoTag(String contentNoTag) {
		this.contentNoTag = contentNoTag;
	}
	public Integer getCountOfReply() {
		return countOfReply;
	}
	public void setCountOfReply(Integer countOfReply) {
		this.countOfReply = countOfReply;
	}
	public Integer getCountOfClick() {
		return countOfClick;
	}
	public void setCountOfClick(Integer countOfClick) {
		this.countOfClick = countOfClick;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Blog{" +
				"id=" + id +
				", title='" + title + '\'' +
				", summary='" + summary + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", countOfClick=" + countOfClick +
				", countOfReply=" + countOfReply +
				", content='" + content + '\'' +
				", keywords='" + keywords + '\'' +
				", blogType=" + blogType +
				", contentNoTag='" + contentNoTag + '\'' +
				", blogCount=" + blogCount +
				", releaseDateStr='" + releaseDateStr + '\'' +
				", imageList=" + imageList +
				'}';
	}
}
