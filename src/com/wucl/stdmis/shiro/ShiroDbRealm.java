package com.wucl.stdmis.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.nutz.lang.Lang;

import com.wucl.stdmis.model.RoleResourcesModel;
import com.wucl.stdmis.model.UserModel;
import com.wucl.stdmis.model.UserRoleModel;
import com.wucl.stdmis.service.IRoleResourcesService;
import com.wucl.stdmis.service.IUserRoleService;
import com.wucl.stdmis.service.IUserService;
import com.wucl.stdmis.service.impl.RoleResourcesService;
import com.wucl.stdmis.service.impl.UserRoleService;
import com.wucl.stdmis.service.impl.UserService;

public class ShiroDbRealm extends AuthorizingRealm {

	private IUserService userService = null;

	// @Inject
	// private IRoleService roleService;

	private IUserRoleService userRoleService;

	IRoleResourcesService roleResourcesService;

	private String[] loaderStr = { "*org.nutz.ioc.loader.json.JsonLoader",
			"ioc.js", "upload.json",
			"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
			"com.wucl.stdmis" };

	public ShiroDbRealm() {
		Ioc ioc = null;
		try {
			ioc = new NutIoc(new ComboIocLoader(loaderStr));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (Lang.isEmpty(userRoleService)) {
			userService = ioc.get(UserService.class, "userService");
			userRoleService = ioc.get(UserRoleService.class, "userRoleService");
			roleResourcesService = ioc.get(RoleResourcesService.class,
					"roleResourcesService");

		}
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		UserModel user = userService.get(token.getUsername());
		System.out.println(user.getPassWords());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getUserName(), user
					.getPassWords(), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String userName = (String) principals.fromRealm(getName()).iterator()
				.next();
		UserModel user = userService.get(userName);
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<UserRoleModel> userRoleList = userRoleService.get(user
					.getUserID());

			// 获取用户角色信息
			List<String> roles = new ArrayList<String>();
			if (userRoleList != null && userRoleList.size() > 0) {
				for (UserRoleModel userRole : userRoleList) {
					roles.add(userRole.getRoleID() + "");
				}
			}
			System.out.println(roles);
			info.addRoles(roles);
			// 获取用户资源信息
			List<Integer> roleIDs = new ArrayList<Integer>();
			for (UserRoleModel userRole : userRoleList) {
				roleIDs.add(userRole.getRoleID());
			}
			List<RoleResourcesModel> roleResources = null;
			for (int roleID : roleIDs) {
				roleResources = roleResourcesService.get(roleID);
			}
			List<String> resources = new ArrayList<String>();
			if (roleResources != null && roleResources.size() > 0) {
				for (RoleResourcesModel roleResource : roleResources)
					resources.add(roleResource.getAutoID() + "");
			}
			info.addStringPermissions(resources);
			return info;
		} else {
			return null;
		}
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	public IUserService getUserService() {
		if (Lang.isEmpty(userService)) {
			Ioc ioc = null;
			try {
				ioc = new NutIoc(new ComboIocLoader(loaderStr));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			userService = ioc.get(UserService.class);
		}
		return userService;
	}

	public IUserRoleService getUserRoleService() {
		if (Lang.isEmpty(userRoleService)) {
			Ioc ioc = null;
			try {
				ioc = new NutIoc(new ComboIocLoader(loaderStr));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			userRoleService = ioc.get(UserRoleService.class);
		}
		return userRoleService;
	}

	public IRoleResourcesService getRoleResourcesService() {
		if (Lang.isEmpty(roleResourcesService)) {
			Ioc ioc = null;
			try {
				ioc = new NutIoc(new ComboIocLoader(loaderStr));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			roleResourcesService = ioc.get(RoleResourcesService.class);
		}
		return roleResourcesService;
	}

}