package com.wucl.stdmis.filter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.RequestPath;
import org.nutz.mvc.View;

import com.wucl.stdmis.model.OperationModel;
import com.wucl.stdmis.model.UserModel;
import com.wucl.stdmis.module.UserModule;
import com.wucl.stdmis.service.IOperationService;
import com.wucl.stdmis.util.NetUtil;
/**
 * 系统操作日志记录器
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "operationRecorderFilter")
public class OperationRecorderFilter implements ActionFilter {

	public static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put("aggressionlogmgr", "入侵防护日志管理");
		map.put("backupandrecoverymgr", "系统备份和恢复管理");
		map.put("deptmgr", "部门管理");
		map.put("dictentrymgr", "业务字典管理");
		map.put("dicttypemg", "业务字典管理");
		map.put("leavemgr", "请假管理");
		map.put("resourcesmgr", "资源管理");
		map.put("rolemgr", "角色管理");
		map.put("studentmgr", "学生管理");
		map.put("teachermgr", "教师管理管理");
		map.put("roleResourcesMgr", "角色资源管理");
		map.put("usermgr", "用户管理");
		map.put("userrolemgr", "用户角色管理");
	}

	@Inject
	IOperationService operationService = null;

	public View match(ActionContext actionContext) {
		HttpServletRequest request = actionContext.getRequest();
		RequestPath path = Mvcs.getRequestPathObject(request);
		String url = path.getUrl();
		if (url.indexOf("/get") != -1 || url.indexOf("/list") != -1
				|| url.indexOf("/login") != -1) {
			return null;
		}
		String modulePath = url.substring(1);
		modulePath = modulePath.substring(0, modulePath.indexOf("/"));
		HttpSession session = request.getSession();
		int userID = -1;
		if (session != null) {
			Object obj = session.getAttribute(UserModule.HAS_LOGIN);
			if (obj instanceof UserModel) {
				userID = ((UserModel) obj).getUserID();
			}
		}
		OperationModel model = new OperationModel();
		model.setOperationDateTime(new Timestamp(System.currentTimeMillis()));
		model.setOperationIP(NetUtil.getIpAddr(request));
		model.setRequestUrl(url);
		model.setUserID(userID);
		model.setOperationContent(map.get(modulePath));
		operationService.insert(model);
		return null;
	}
}
