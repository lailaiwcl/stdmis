package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.RoleModel;
/**
 * 角色管理数据层服务接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface IRoleService {

	public List<RoleModel> listAll();

	public List<RoleModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public RoleModel get(int roleID);

	public int count(String key);

	public boolean delete(int roleID);

	public boolean modify(RoleModel role);

	public boolean insert(RoleModel role);

}
