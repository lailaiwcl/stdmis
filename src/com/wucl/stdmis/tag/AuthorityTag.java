package com.wucl.stdmis.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.wucl.stdmis.cache.AuthorityCache;
import com.wucl.stdmis.model.UserModel;

public class AuthorityTag extends TagSupport {

	private static Log log = Logs.get();

	private static final long serialVersionUID = -1035452506806676659L;

	private String sessionName = "has_login";

	private String resourcesID;

	@Override
	public int doStartTag() throws JspException {
		if (sessionName == null || sessionName.trim().length() == 0) {
			return SKIP_BODY;
		}
		if (resourcesID == null || resourcesID.trim().length() == 0) {
			return SKIP_BODY;
		}
		Object obj = pageContext.getSession().getAttribute(sessionName);
		if (obj == null) {
			return SKIP_BODY;
		}
		if (!(obj instanceof UserModel)) {
			return SKIP_BODY;
		}
		UserModel user = (UserModel) obj;

		// 如果是超级管理员用户，则跳过权限检查，直接拥有所有权限
		if ("sysadmin".equalsIgnoreCase(user.getUserName())) {
			return EVAL_BODY_INCLUDE;
		}
		int userID = user.getUserID();

		AuthorityCache cache = new AuthorityCache();
		cache.uptate(userID);
		Map<Integer, List<String>> cacheMap = AuthorityCache.cacheMap;
		List<String> userPrivileges = cacheMap.get(userID);
		if (userPrivileges == null || userPrivileges.size() == 0) {
			return SKIP_BODY;
		}
		if (userPrivileges.contains(resourcesID)) {
			if (log.isInfoEnabled()) {
				log.infof("the user [" + user.getUserName()
						+ "] load resource [" + resourcesID + "] success.");
			}
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public String getResourcesID() {
		return resourcesID;
	}

	public void setResourcesID(String resourcesID) {
		this.resourcesID = resourcesID;
	}

}
