package com.wucl.stdmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.DaoException;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.wucl.stdmis.model.UserModel;
import com.wucl.stdmis.service.IUserService;

/**
 * 用户管理数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "userService")
public class UserService implements IUserService {

	@Inject
	private Dao dao = null;

	public boolean delete(int userID) {
		UserModel user = new UserModel();
		user.setUserID(userID);
		user.setDelFlag(1);
		int count = dao.updateIgnoreNull(user);
		if (count == 1) {
			return true;
		}
		return false;
	}

	public UserModel get(String userName) {
		UserModel userModel = null;
		try {
			userModel = dao.fetch(UserModel.class, Cnd.where("userName", "=",
					userName).and("delFlag", "=", 0));
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return userModel;
	}

	public UserModel getUserByID(int userID) {
		UserModel userModel = null;
		try {
			userModel = dao.fetch(UserModel.class, Cnd.where("userID", "=",
					userID).and("delFlag", "=", 0));
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return userModel;
	}

	public List<UserModel> listUsersWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<UserModel> userModelList = new ArrayList<UserModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照用户的创建时间降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "desc";
			sortField = "userID";
		}
		if ("asc".equalsIgnoreCase(sortOrder)) {
			if (key != null && key.trim().length() != 0) {
				userModelList = dao.query(UserModel.class, Cnd.where(
						"userName", "like", "%" + key + "%").and("delFlag",
						"=", 0).asc(sortField), pager);
			} else {
				userModelList = dao.query(UserModel.class, Cnd.where("delFlag",
						"=", 0).asc(sortField), pager);
			}

		} else {
			if (key != null && key.trim().length() != 0) {
				userModelList = dao.query(UserModel.class, Cnd.where(
						"userName", "like", "%" + key + "%").and("delFlag",
						"=", 0).desc(sortField), pager);
			} else {
				userModelList = dao.query(UserModel.class, Cnd.where("delFlag",
						"=", 0).desc(sortField), pager);
			}

		}
		return userModelList;
	}

	public boolean modify(UserModel user) {
		if (user == null) {
			return false;
		}
		try {
			dao.updateIgnoreNull(user);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return true;
	}

	public boolean insert(UserModel user) {
		if (user == null) {
			return false;
		}
		try {
			dao.insert(user);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return true;
	}

	public int count(String key) {
		Cnd cnd = null;
		if (key == null || "".equals(key.trim())) {
			cnd = Cnd.where("delFlag", "=", 0);
		} else {
			cnd = Cnd.where("UserName", "like", "%" + key + "%").and("delFlag",
					"=", 0);
		}
		return dao.count(UserModel.class, cnd);
	}
}
