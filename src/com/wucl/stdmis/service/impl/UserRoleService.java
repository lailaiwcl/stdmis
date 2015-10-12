package com.wucl.stdmis.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.wucl.stdmis.model.UserRoleModel;
import com.wucl.stdmis.service.IUserRoleService;
/**
 * 用户角色管理数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "userRoleService")
public class UserRoleService implements IUserRoleService {

	@Inject
	private Dao dao = null;

	public boolean insert(UserRoleModel userRoleModel) {
		if (userRoleModel == null) {
			return false;
		}
		dao.insert(userRoleModel);
		return true;
	}

	public boolean delete(int userID) {
		List<UserRoleModel> userRoleList = get(userID);
		for (UserRoleModel userRoleModel : userRoleList) {
			dao.delete(userRoleModel);
		}
		return true;
	}

	public List<UserRoleModel> get(int userID) {
		List<UserRoleModel> userRoleList = dao.query(UserRoleModel.class, Cnd
				.where("userID", "=", userID));
		return userRoleList;
	}

}
