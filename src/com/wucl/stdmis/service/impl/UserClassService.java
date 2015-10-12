package com.wucl.stdmis.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.wucl.stdmis.model.UserClassModel;
import com.wucl.stdmis.service.IUserClassService;
/**
 * 用户班级管理数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "userClassService")
public class UserClassService implements IUserClassService {

	@Inject
	private Dao dao = null;

	public boolean insert(UserClassModel userClassModel) {
		if (userClassModel == null) {
			return false;
		}
		dao.insert(userClassModel);
		return true;
	}

	public boolean delete(int userID) {
		List<UserClassModel> userClassList = getUserClassByUserID(userID);
		for (UserClassModel userClassModel : userClassList) {
			dao.delete(userClassModel);
		}
		return true;
	}

	public List<UserClassModel> getUserClassByUserID(int userID) {
		List<UserClassModel> userClassList = dao.query(UserClassModel.class,
				Cnd.where("userID", "=", userID));
		return userClassList;
	}

}
