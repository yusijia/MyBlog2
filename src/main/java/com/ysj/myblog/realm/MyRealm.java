package com.ysj.myblog.realm;

import com.ysj.myblog.entity.Blogger;
import com.ysj.myblog.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;




/**
 * 自定义Realm
 * @author ysj
 *
 */
public class MyRealm extends AuthorizingRealm{

	@Resource
	private BloggerService bloggerService;
	
	/**
	 *  为当前的登入的用户授予角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		return null;
	}

	/**
	 *  验证当前登入的用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取用户输入的用户名
		String userName = (String)token.getPrincipal();
		// 根据用户输入过来的用户名从数据库里获取该用户的相关信息
		Blogger blogger = bloggerService.getByUserName(userName);
		// 如果有这个用户名
		if (blogger != null) {
			// 把当前用户信息存到session中
			SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger);
			// 用于验证用户传来的用户名和密码
			// 将该用户正确的用户名和密码封装到AuthenticationInfo里给底层封装成token, 用于在Controller里subject.login(用户token)里和用户传来的信息比较
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(blogger.getUserName(), blogger.getPassword(), "xxx");
			return authcInfo;
		} else {
			return null;
		}
	}

}
