package com.wucl.stdmis.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
/**
 * 角色模型
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@Table("W_RoleInfo")
public class RoleModel implements java.io.Serializable {

	private static final long serialVersionUID = 8331836537930445273L;

	@Column("RoleID")
	@Id
	private int roleID;

	@Column("RoleName")
	private String roleName;

	@Column("Notes")
	private String notes;

	@Column("DelFlag")
	private int delFlag;

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

}
