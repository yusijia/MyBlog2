package com.ysj.myblog.entity;


/**
 * 作者实体
 * @author ysj
 *
 */
public class Blogger {

	/*与数据库表有关联的属性*/
	private int id;				// 博主id
	private String userName;	// 用户名
	private String password; 	// 用户密码
	private String profile;		// 用户简介
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
