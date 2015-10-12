package com.wucl.stdmis.tag;

import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.wucl.stdmis.model.UserModel;

public class SessionTag  extends TagSupport {

	private static final long serialVersionUID = -1068846496279888318L;
	
	private String sessionName = "has_login";
	
	private String fieldName;
	
	@Override
	public int doStartTag() throws JspException {
		if (sessionName == null || sessionName.trim().length() == 0) {
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
		try {
			String outString = "";
			if("userName".equalsIgnoreCase(fieldName)){
				outString = user.getUserName();
			}
			if("userID".equalsIgnoreCase(fieldName)){
				outString = user.getUserID() + "";
			}
			if("userEmail".equalsIgnoreCase(fieldName)){
				outString = user.getUserEmail();
			}
			if("loginTimes".equalsIgnoreCase(fieldName)){
				outString = user.getLoginTimes() + "";
			}
			if("lastLoginTime".equalsIgnoreCase(fieldName)){
				if(user.getLastLoginTime() != null){
					SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 ");
					outString = format.format(user.getLastLoginTime());
				}
			}
			if("userType".equalsIgnoreCase(fieldName)){
				outString = user.getUserType() + "";
			}
			pageContext.getOut().write(outString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	


}
