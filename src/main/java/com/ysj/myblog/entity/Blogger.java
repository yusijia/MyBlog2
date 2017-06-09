package com.ysj.myblog.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 作者实体
 * @author ysj
 *
 */
public class Blogger {

	/*与数据库表有关联的属性*/
	private int id;				// 博主id
	@NotNull(message = "用户名不能为NULL")
	private String userName;	// 用户名
	private String password; 	// 用户密码,注意：用了@JsonIgnore,json序列化时忽略该属性
	private String profile;		// 用户简介
	@NotNull(message = "昵称不能为NULL")
	@Size(min = 1, max = 15, message = "要求昵称为1到15位的字符串")
	private String nickName;	// 昵称
	private String sign;		// 个性签名
	private String imageName;	// 头像文件名
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "Blogger{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", profile='" + profile + '\'' +
				", nickName='" + nickName + '\'' +
				", sign='" + sign + '\'' +
				", imageName='" + imageName + '\'' +
				'}';
	}
}
