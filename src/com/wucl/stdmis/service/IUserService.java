package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.UserModel;
/**
 * 用户管理数据层服务接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface IUserService {

	public List<UserModel> listUsersWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public UserModel get(String userName);

	public UserModel getUserByID(int userID);

	public boolean delete(int userID);

	public boolean modify(UserModel user);

	public boolean insert(UserModel user);

	public int count(String key);

}
