package net.ftzcode.component.shiro;

import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.collect.Sets;
import net.ftzcode.component.JsonMethodArgumentResolver;
import net.ftzcode.model.PidUser;
import net.ftzcode.service.PidUserService;

/**
 * Created by WangZiHe on 2017/9/24
 * QQ/WeChat:648830605
 * QQ-Group:368512253
 * Blog:www.520code.net
 * Github:https://github.com/yancheng199287
 */
public class CuteRealm extends AuthorizingRealm {
	
	
	@Autowired
	private PidUserService pidUserService;

	private Logger logger = LoggerFactory.getLogger(JsonMethodArgumentResolver.class);
	/**
	 * 为当前登录成功的用户授予角色和权限
	 * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    	//获取登录用户名
		String userName = (String) principalCollection.getPrimaryPrincipal();
		logger.info("为当前登录用户授权角色和权限:"+userName);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//根据用户名获取用户角色，并赋值给authorizationInfo
		Set<String> defaultRoles=Sets.newHashSet("NormalUser");
		authorizationInfo.setRoles(defaultRoles);
		//根据用户名获取用户权限，并赋值给authorizationInfo
		Set<String> defaultPermissions=Sets.newHashSet("all permission");
		authorizationInfo.setStringPermissions(defaultPermissions);
		return authorizationInfo;
    }

    /**
	 * 验证当前登录的用户
	 * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    	//根据token获取到用户名，根据用户名查询相关信息
		String userId =  authenticationToken.getPrincipal().toString();
		//根据用户名获取用户信息
		//PidUser user = pidUserService.findById(userId);
		logger.info("userId: "+userId);
		PidUser user =new PidUser();
		user.setId(987);user.setUsername("admin");user.setPassword("123456");user.setNickname("认证名字");
		if (user != null) {
			logger.info("doGetAuthenticationInfo 为当前登录用户验证合法身份:"+user.getId()+", username:"+user.getUsername());
			
			//获得认证信息
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getId().toString(),user.getPassword(),user.getNickname());
			return authenticationInfo;
		}
    	return null;
    }
}
