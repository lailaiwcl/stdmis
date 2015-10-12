package com.wucl.stdmis.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;
/**
 * 角色资源模型
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@Table("W_RoleResource")
@PK({"roleID", "autoID"})
public class RoleResourcesModel implements java.io.Serializable {
	
	
	private static final long serialVersionUID = 7389402059668480382L;

	@Column("AutoID")
	private int autoID;
	
	@Column("RoleID")
	private int roleID;

	public int getAutoID() {
		return autoID;
	}

	public void setAutoID(int autoID) {
		this.autoID = autoID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	
	
	

}
