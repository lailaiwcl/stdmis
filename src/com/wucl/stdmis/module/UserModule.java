package com.wucl.stdmis.module;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.DaoException;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.RequestPath;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.filter.OperationRecorderFilter;
import com.wucl.stdmis.model.OperationModel;
import com.wucl.stdmis.model.UserModel;
import com.wucl.stdmis.service.IOperationService;
import com.wucl.stdmis.service.IUserService;
import com.wucl.stdmis.util.Crypto;
import com.wucl.stdmis.util.ExcelUtil;
import com.wucl.stdmis.util.NetUtil;

/**
 * 用户管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/usermgr")
@IocBean
public class UserModule {
	private static Log log = Logs.get();

	public static String HAS_LOGIN = "has_login";

	@Inject
	private IUserService userService = null;

	@Inject
	IOperationService operationService = null;

	@At("/login")
	public int login(@Param("userName") String userName,
			@Param("passwords") String passwords, HttpSession session,
			HttpServletRequest request) {
		if (Strings.isBlank(userName) || Strings.isBlank(passwords)) {
			log.error("The username and password should not be empty.", null);
			return -1;
		} else {
			UserModel model = null;
			try {
				model = userService.get(userName.toString());
			} catch (DaoException e) {
				log.error(e);
				return -1;
			}
			if (model == null) {
				if (log.isInfoEnabled()) {
					log.infof("userName [%s] do not exist.", userName);
				}
				return 1;
			}
			if (!Crypto.validatePassword(model.getPassWords(), passwords)) {
				if (log.isInfoEnabled()) {
					log.infof("the user [%s] password do not correct.",
							userName);
				}
				return 2;
			}
			if (Crypto.validatePassword(model.getPassWords(), passwords)
					&& model.getUserState() == 1) {
				if (log.isInfoEnabled()) {
					log.infof(
							"the user [%s] has locked,do not allow to login.",
							userName);
				}
				return 3;
			}
			model.setLastLoginTime(model.getRecentLoginTime());
			int loginTimes = model.getLoginTimes();
			model.setLoginTimes(++loginTimes);
			model.setRecentLoginTime(new Date(System.currentTimeMillis()));
			userService.modify(model);
			model.setPassWords("");
			model.setNotes("");
			model.setUserEmail("");
			model.setRegisterDate(null);
			session.setAttribute(HAS_LOGIN, model);
			// session最大失效时间一个小时
			session.setMaxInactiveInterval(3600);

			OperationModel operationModel = new OperationModel();
			operationModel.setOperationDateTime(new Timestamp(System
					.currentTimeMillis()));
			operationModel.setOperationIP(NetUtil.getIpAddr(request));
			RequestPath path = Mvcs.getRequestPathObject(request);
			String url = path.getUrl();
			operationModel.setRequestUrl(url);
			operationModel.setUserID(model.getUserID());
			String modulePath = url.substring(1);
			modulePath = modulePath.substring(0, modulePath.indexOf("/"));
			operationModel.setOperationContent(OperationRecorderFilter.map
					.get(modulePath));
			operationService.insert(operationModel);

			if (log.isInfoEnabled()) {
				log.infof("the user [%s] login success.", userName);
			}
			return 0;
		}
	}

	@At("/logout")
	public void logout(HttpSession session) {
		if (session == null) {
			return;
		}
		UserModel model = (UserModel) session.getAttribute(HAS_LOGIN);
		if (model != null) {
			log.debugf("The user[%s] request to logout.", model.getUserName());
		}
		session.invalidate();
	}

	@At("/listUsers")
	public ModelListWithPagingWrapper<UserModel> listUsers(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<UserModel> userList = userService.listUsersWithPage(pageIndex,
				pageSize, sortField, sortOrder, key);
		int size = userService.count(key);

		// 清除密码信息
		List<UserModel> list = new ArrayList<UserModel>();
		for (UserModel user : userList) {
			user.setPassWords("");
			list.add(user);
		}

		return new ModelListWithPagingWrapper<UserModel>(userList, size);
	}

	@At("/changePwd")
	public int changePwd(@Param("userID") int userID,
			@Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd) {
		if (oldPwd == null || newPwd == null) {
			return -1;
		}
		UserModel user = userService.getUserByID(userID);
		if (user == null) {
			return -1;
		}
		if (user.getPassWords().equals(Crypto.generatePassword(oldPwd))) {
			user.setPassWords(Crypto.generatePassword(newPwd));
			userService.modify(user);
			return 1;
		}
		if (!user.getPassWords().equals(Crypto.generatePassword(oldPwd))) {
			return 0;
		}
		return 0;
	}

	@At("/addUser")
	public void addUser(@Param("::usr.") UserModel user,
			HttpServletRequest request) {
		try {
			String pwd = user.getPassWords();
			user.setPassWords(Crypto.generatePassword(pwd));
			user.setRegisterDate(new Date(System.currentTimeMillis()));
			user.setUserState(0);
			userService.insert(user);
			if (log.isInfoEnabled()) {
				log.info("add user success.");
			}
		} catch (Exception e) {
			log.error("add user failed.", e);
		}

	}

	@At("/delUser")
	public void delUser(@Param("ids") String ids, HttpServletRequest request) {
		String ret = "";
		if (ids == null || ids.trim().length() == 0) {
			return;
		}
		String[] id = ids.split(",");
		for (String userID : id) {
			try {
				if ("sysadmin".equalsIgnoreCase(userService.getUserByID(
						Integer.parseInt(userID)).getUserName())) {
					ret = "内置超级管理员用户[ sysadmin ]不能被删除。";
					log
							.infof("the super admin  [ sysadmin ] can not be deleted.");
					return;
				}
				userService.delete(Integer.parseInt(userID));
				ret = "删除管理员成功。";
				if (log.isInfoEnabled()) {
					log.infof("the user [%s] delete success.", userID);
				}
			} catch (Exception e) {
				ret = "删除管理员失败。";
				log.error("delete user [" + userID + "] failed.", e);
			}

		}
		request.setAttribute("msg", ret);

	}

	@At("/getUser")
	public SingleModelWrapper<UserModel> getUser(@Param("id") int userID,
			HttpServletRequest request) {
		String ret = "";
		UserModel user = null;
		try {
			user = userService.getUserByID(userID);
			user.setPassWords("");
			ret = "查询管理员成功。";
		} catch (Exception e) {
			ret = "查询管理员失败！";
		}
		request.setAttribute("msg", ret);
		return new SingleModelWrapper<UserModel>(user);

	}

	@At("/editUser")
	public void editUser(@Param("::obj.") UserModel user,
			HttpServletRequest request) {
		if (user == null) {
			return;
		}
		String ret = "";
		try {
			UserModel u = userService.getUserByID(user.getUserID());
			if (user.getPassWords() == null || "".equals(user.getPassWords())) {
				user.setPassWords(u.getPassWords());
			} else {
				user.setPassWords(Crypto.generatePassword(user.getPassWords()));
			}
			userService.modify(user);
			ret = "保存管理员成功。";
			if (log.isInfoEnabled()) {
				log.infof("the user [%s] edite success.", user.getUserID());
			}
		} catch (Exception e) {
			ret = "保存管理员失败！";
			if (log.isInfoEnabled()) {
				log.infof("the user [%s] edite failed.", user.getUserID());
			}
		}
		request.setAttribute("msg", ret);

	}

	@At("/lockUser")
	public void lockUser(@Param("ids") String ids, HttpServletRequest request) {
		String ret = "";
		if (ids == null || ids.trim().length() == 0) {
			return;
		}
		String[] id = ids.split(",");
		for (String userID : id) {
			try {
				UserModel user = userService.getUserByID(Integer
						.parseInt(userID));
				user.setUserState(1);
				userService.modify(user);
				ret = "锁定管理员成功。";
				if (log.isInfoEnabled()) {
					log.infof("the user [%s] lock success.", userID);
				}
			} catch (Exception e) {
				ret = "锁定管理员失败！";
				if (log.isInfoEnabled()) {
					log.infof("the user [%s] lock success.", userID);
				}
			}
		}
		request.setAttribute("msg", ret);
	}

	@At("/unlockUser")
	public void unlockUser(@Param("ids") String ids, HttpServletRequest request) {
		String ret = "";
		if (ids == null || ids.trim().length() == 0) {
			return;
		}
		String[] id = ids.split(",");
		for (String userID : id) {
			try {
				UserModel user = userService.getUserByID(Integer
						.parseInt(userID));
				user.setUserState(0);
				userService.modify(user);
				ret = "解锁管理员成功。";
				if (log.isInfoEnabled()) {
					log.infof("the user [%s] unlock success.", userID);
				}
			} catch (Exception e) {
				ret = "解锁管理员失败！";
				if (log.isInfoEnabled()) {
					log.infof("the user [%s] unlock success.", userID);
				}
			}
		}
		request.setAttribute("msg", ret);

	}

	@At("/exportExcel")
	@Ok("void")
	public void exportExcel(HttpServletResponse response) {
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"yyyyMMddHHmmss");
			String filename = "users_" + formatter.format(new java.util.Date())
					+ ".xls";
			String[] headText = { "编号", "用户名", "用户类型", "邮箱", "注册日期", "用户状态",
					"备注" };
			List<UserModel> userList = userService.listUsersWithPage(0,
					Integer.MAX_VALUE, "userID", "asc", null);
			List<List<String>> dataText = new ArrayList<List<String>>();
			for (UserModel user : userList) {
				List<String> singleData = new ArrayList<String>();
				singleData.add(user.getUserID() + "");
				singleData.add(user.getUserName());
				String userType = "系统管理员";
				if (user.getUserType() == 1) {
					userType = "班级管理员";
				}
				singleData.add(userType);
				singleData.add(user.getUserEmail());
				singleData.add(user.getRegisterDate() == null ? "" : user
						.getRegisterDate().toString());
				String userState = "正常";
				if (user.getUserState() == 1) {
					userState = "锁定";
				}
				singleData.add(userState);
				singleData.add(user.getNotes());
				dataText.add(singleData);
			}
			int[] columnWidth = { 6, 15, 12, 20, 12, 8, 20 };
			ExcelUtil.exportToExcelByJExcelApi(response, filename, headText,
					dataText, columnWidth);
			if (log.isInfoEnabled()) {
				log.infof("the excel file [%s] export success.", filename);
			}

		} catch (Exception e) {
			log.error(e);
		}

	}

	@At("/validateUserName")
	public boolean validateUserName(String userName) {
		if (userName == null || userService.get(userName) != null) {
			return false;
		}
		return true;
	}

}
