package com.wucl.stdmis.model;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("W_DeptInfo")
public class DeptModel implements Serializable {

	private static final long serialVersionUID = -3602137644153987211L;

	@Column("DeptNo")
	@Name
	private String deptNo;

	@Column("ParentDeptNo")
	private String parentDeptNo;

	@Column("DeptName")
	private String deptName;

	@Column("DeptShortName")
	private String deptShortName;

	@Column("DeptType")
	private String deptType;

	@Column("DeptTel")
	private String deptTel;

	@Column("DeptLoc")
	private String deptLoc;

	@Column("DeptUrl")
	private String deptUrl;

	@Column("Notes")
	private String notes;

	@Column("DelFlag")
	private int delFlag;

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setParentDeptNo(String parentDeptNo) {
		this.parentDeptNo = parentDeptNo;
	}

	public String getParentDeptNo() {
		return this.parentDeptNo;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}

	public String getDeptShortName() {
		return this.deptShortName;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getDeptType() {
		return this.deptType;
	}

	public void setDeptTel(String deptTel) {
		this.deptTel = deptTel;
	}

	public String getDeptTel() {
		return this.deptTel;
	}

	public void setDeptLoc(String deptLoc) {
		this.deptLoc = deptLoc;
	}

	public String getDeptLoc() {
		return this.deptLoc;
	}

	public void setDeptUrl(String deptUrl) {
		this.deptUrl = deptUrl;
	}

	public String getDeptUrl() {
		return this.deptUrl;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getDelFlag() {
		return this.delFlag;
	}

}