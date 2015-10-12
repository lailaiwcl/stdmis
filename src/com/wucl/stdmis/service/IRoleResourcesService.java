package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.RoleResourcesModel;
/**
 * 角色资源管理数据层接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface IRoleResourcesService {
	public List<RoleResourcesModel> get(int roleID);

	public boolean insert(RoleResourcesModel roleResourcesModel);

	public boolean delete(int roleID);
}
