package com.ysj.myblog.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class User {

	private Integer id;
	
	@NotEmpty
	@Size(min=2, max=10, message="用户名要在2到10位")
	private String userName;
	
	@NotEmpty 
	@Size(min=2, max=10, message="密码要在2到10位")
	private String password;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
