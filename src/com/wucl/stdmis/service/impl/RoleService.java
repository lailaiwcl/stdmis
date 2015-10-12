package com.wucl.stdmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.wucl.stdmis.model.RoleModel;
import com.wucl.stdmis.service.IRoleService;
/**
 * 角色管理数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "roleService")
public class RoleService implements IRoleService {

	@Inject
	private Dao dao = null;

	public RoleModel get(int roleID) {
		RoleModel roleModel = dao.fetch(RoleModel.class, roleID);
		return roleModel;
	}

	public List<RoleModel> listAll() {
		List<RoleModel> roleModelList = new ArrayList<RoleModel>();
		roleModelList = dao
				.query(RoleModel.class, Cnd.where("delFlag", "=", 0));
		return roleModelList;
	}

	public List<RoleModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<RoleModel> roleModelList = new ArrayList<RoleModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照用户的创建时间降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "asc";
			sortField = "roleID";
		}
		if ("asc".equalsIgnoreCase(sortOrder)) {
			if (key != null && key.trim().length() != 0) {
				roleModelList = dao.query(RoleModel.class, Cnd.where(
						"roleName", "like", "%" + key + "%").and("delFlag",
						"=", 0).asc(sortField), pager);
			} else {
				roleModelList = dao.query(RoleModel.class, Cnd.where("delFlag",
						"=", 0).asc(sortField), pager);
			}

		} else {
			if (key != null && key.trim().length() != 0) {
				roleModelList = dao.query(RoleModel.class, Cnd.where(
						"roleName", "like", "%" + key + "%").and("delFlag",
						"=", 0).desc(sortField), pager);
			} else {
				roleModelList = dao.query(RoleModel.class, Cnd.where("delFlag",
						"=", 0).desc(sortField), pager);
			}

		}
		return roleModelList;
	}

	public int count(String key) {
		Cnd cnd = null;
		if (key == null || "".equals(key.trim())) {
			cnd = Cnd.where("delFlag", "=", 0);
		} else {
			cnd = Cnd.where("roleName", "like", "%" + key + "%").and("delFlag",
					"=", 0);
		}
		return dao.count(RoleModel.class, cnd);
	}

	public boolean insert(RoleModel role) {
		if (role == null) {
			return false;
		}
		dao.insert(role);
		return false;
	}

	public boolean delete(int roleID) {
		RoleModel role = new RoleModel();
		role.setRoleID(roleID);
		role.setDelFlag(1);
		int count = dao.updateIgnoreNull(role);
		if (count == 1) {
			return true;
		}
		return false;
	}

	public boolean modify(RoleModel role) {
		if (role == null) {
			return false;
		}
		try {
			dao.updateIgnoreNull(role);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
