package com.ysj.myblog.entity;

/**
 * 分页Model类
 * @author 
 *
 */
public class PageBean {

	private int page; 		// 第几页
	private int countOfBlogInPage; 	// 每页记录数
	private int start; 		// 起始页
	
	
	public PageBean(int page, int countOfBlogInPage) {
		super();
		this.page = page;
		this.countOfBlogInPage = countOfBlogInPage;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getCountOfBlogInPage() {
		return countOfBlogInPage;
	}
	public void setCountOfBlogInPage(int countOfBlogInPage) {
		this.countOfBlogInPage = countOfBlogInPage;
	}

	public int getStart() {
		return (page-1)*countOfBlogInPage;
	}

	@Override
	public String toString() {
		return "PageBean{" +
				"page=" + page +
				", countOfBlogInPage=" + countOfBlogInPage +
				", start=" + start +
				'}';
	}
}
