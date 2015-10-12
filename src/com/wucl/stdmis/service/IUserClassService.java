package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.UserClassModel;
/**
 * 用户班级管理数据层服务接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface IUserClassService {
	public List<UserClassModel> getUserClassByUserID(int userID);
	
	public boolean insert(UserClassModel userClassModel);
	
	public boolean delete(int userID); 
}
