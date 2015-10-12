package com.wucl.stdmis.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;
/**
 * 用户班级模型
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@Table("W_UserClassInfo")
@PK({"userID", "classID"})
public class UserClassModel implements java.io.Serializable {

	private static final long serialVersionUID = -363237025188878715L;

	@Column("UserID")
	private int userID;
	
	@Column("ClassID")
	private String classID;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

}
