package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.UserRoleModel;
/**
 * 用户角色管理数据层服务接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface IUserRoleService {
	
	public List<UserRoleModel> get(int userID);
	
	public boolean insert(UserRoleModel userRoleModel);
	
	public boolean delete(int userID); 
}
