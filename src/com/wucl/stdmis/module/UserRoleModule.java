package com.wucl.stdmis.module;

import java.util.ArrayList;
import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.cache.AuthorityCache;
import com.wucl.stdmis.model.RoleModel;
import com.wucl.stdmis.model.UserModel;
import com.wucl.stdmis.model.UserRoleModel;
import com.wucl.stdmis.service.IRoleService;
import com.wucl.stdmis.service.IUserRoleService;
import com.wucl.stdmis.service.IUserService;

/**
 * 用户角色管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/userrolemgr")
@IocBean
public class UserRoleModule {

	private static Log log = Logs.get();

	@Inject
	private IUserRoleService userRoleService = null;
	@Inject
	private IUserService userService = null;
	@Inject
	private IRoleService roleService = null;

	@At("/getUserRoleByUserID")
	public List<UserRoleModel> getUserRoleByUserID(int userID) {
		UserModel user = userService.getUserByID(userID);
		//如果是超级管理员用户，返回所有角色
		List<UserRoleModel> userRoleModelList = new ArrayList<UserRoleModel>();
		if (user != null && "sysadmin".equalsIgnoreCase(user.getUserName())) {
			List<RoleModel> roleList = roleService.listAll();
			for (RoleModel roleModel : roleList) {
				UserRoleModel userRole = new UserRoleModel();
				userRole.setUserID(userID);
				userRole.setRoleID(roleModel.getRoleID());
				userRoleModelList.add(userRole);
			}
		} else {
			userRoleModelList = userRoleService.get(userID);
		}
		return userRoleModelList;
	}

	@At("/addUserRole")
	public void addUserRole(@Param("userID") int userID,
			@Param("roleIDs") String roleIDs) {
		if (roleIDs == null || roleIDs.trim().length() == 0) {
			return;
		}
		String[] ids = roleIDs.split(",");
		for (String roleID : ids) {
			UserRoleModel userRoleModel = new UserRoleModel();
			userRoleModel.setUserID(userID);
			userRoleModel.setRoleID(Integer.parseInt(roleID));
			try {
				userRoleService.insert(userRoleModel);
				AuthorityCache.reset();
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	@At("/delUserRoleByUserID")
	public void delUserRoleByUserID(@Param("userID") int userID) {
		try {
			userRoleService.delete(userID);
			AuthorityCache.reset();
		} catch (Exception e) {
			log.error(e);
		}
	}

}
