package com.wucl.stdmis.module;

import java.util.ArrayList;
import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.model.ClassModel;
import com.wucl.stdmis.model.UserClassModel;
import com.wucl.stdmis.service.IClassService;
import com.wucl.stdmis.service.IUserClassService;
/**
 * 用户班级管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/userclassmgr")
@IocBean
public class UserClassModule {
	private static Log log = Logs.get();

	@Inject
	private IUserClassService userClassService = null;

	@Inject
	private IClassService classService = null;

	@At("/getUserClassByUserID")
	public List<UserClassModel> getUserClassByUserID(int userID) {
		List<UserClassModel> userClassModelList = userClassService
				.getUserClassByUserID(userID);
		return userClassModelList;
	}

	@At("/getPermissionUserByUserID")
	public List<ClassModel> getPermissionUserByUserID(int userID) {
		List<UserClassModel> userClassModelList = userClassService
				.getUserClassByUserID(userID);
		List<ClassModel> classModelList = new ArrayList<ClassModel>();
		for (UserClassModel userClassModel : userClassModelList) {
			ClassModel classModel = classService.get(userClassModel
					.getClassID());
			if(classModel == null){
				return null;
			}
			classModel.setNotes("");
			classModelList.add(classModel);
		}
		return classModelList;
	}

	@At("/addUserClass")
	public void addUserClass(@Param("userID") int userID,
			@Param("classIDs") String classIDs) {
		if (classIDs == null || classIDs.trim().length() == 0) {
			return;
		}
		String[] ids = classIDs.split(",");
		for (String classID : ids) {
			UserClassModel userClassModel = new UserClassModel();
			userClassModel.setUserID(userID);
			userClassModel.setClassID(classID);
			try {
				userClassService.insert(userClassModel);
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	@At("/delUserClassByUserID")
	public void delUserClassByUserID(@Param("userID") int userID) {
		try {
			userClassService.delete(userID);
		} catch (Exception e) {
			log.error(e);
		}
	}

}
