package com.wucl.stdmis.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

/**
 * 用户角色模型类
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@Table("W_UserRole")
@PK( { "userID", "roleID" })
public class UserRoleModel implements java.io.Serializable {

	private static final long serialVersionUID = -6673497140464971302L;

	@Column("UserID")
	private int userID;

	@Column("RoleID")
	private int roleID;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

}
