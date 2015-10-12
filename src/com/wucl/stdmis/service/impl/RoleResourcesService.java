package com.wucl.stdmis.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.wucl.stdmis.model.RoleResourcesModel;
import com.wucl.stdmis.service.IRoleResourcesService;
/**
 * 角色资源管理数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "roleResourcesService")
public class RoleResourcesService implements IRoleResourcesService {

	@Inject
	private Dao dao = null;

	public boolean delete(int roleID) {
		List<RoleResourcesModel> roleResourcesModelList = get(roleID);
		for (RoleResourcesModel roleResourcesMode : roleResourcesModelList) {
			dao.delete(roleResourcesMode);
		}
		return true;
	}

	public List<RoleResourcesModel> get(int roleID) {
		List<RoleResourcesModel> roleResourcesModelList = dao.query(
				RoleResourcesModel.class, Cnd.where("roleID", "=", roleID));
		return roleResourcesModelList;
	}

	public boolean insert(RoleResourcesModel roleResourcesModel) {
		if (roleResourcesModel == null) {
			return false;
		}
		dao.insert(roleResourcesModel);
		return true;
	}

}
