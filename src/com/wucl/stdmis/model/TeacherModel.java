package com.wucl.stdmis.model;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("W_TeacherInfo")
public class TeacherModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column("TeacherNo")
	@Name
	private String teacherNo;

	@Column("TeacherName")
	private String teacherName;

	@Column("TeacherSex")
	private int teacherSex;

	@Column("TeacherMobile")
	private String teacherMobile;

	@Column("TeacherEmail")
	private String teacherEmail;

	@Column("TeacherProfessional")
	private int teacherProfessional;

	@Column("TeacherOfficePhone")
	private String teacherOfficePhone;

	@Column("TeacherFamilyPhone")
	private String teacherFamilyPhone;

	@Column("TeacherFamilyAdress")
	private String teacherFamilyAdress;

	@Column("TeacherPoltics")
	private int teacherPoltics;

	@Column("TeacherCardID")
	private String teacherCardID;

	@Column("PhotoUrl")
	private String photoUrl;

	@Column("DelFlag")
	private int delFlag;

	@Column("DeptNo")
	private String deptNo;

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public String getTeacherNo() {
		return this.teacherNo;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherName() {
		return this.teacherName;
	}

	public void setTeacherSex(int teacherSex) {
		this.teacherSex = teacherSex;
	}

	public int getTeacherSex() {
		return this.teacherSex;
	}

	public void setTeacherMobile(String teacherMobile) {
		this.teacherMobile = teacherMobile;
	}

	public String getTeacherMobile() {
		return this.teacherMobile;
	}

	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}

	public String getTeacherEmail() {
		return this.teacherEmail;
	}

	public void setTeacherProfessional(int teacherProfessional) {
		this.teacherProfessional = teacherProfessional;
	}

	public int getTeacherProfessional() {
		return this.teacherProfessional;
	}

	public void setTeacherOfficePhone(String teacherOfficePhone) {
		this.teacherOfficePhone = teacherOfficePhone;
	}

	public String getTeacherOfficePhone() {
		return this.teacherOfficePhone;
	}

	public void setTeacherFamilyPhone(String teacherFamilyPhone) {
		this.teacherFamilyPhone = teacherFamilyPhone;
	}

	public String getTeacherFamilyPhone() {
		return this.teacherFamilyPhone;
	}

	public void setTeacherFamilyAdress(String teacherFamilyAdress) {
		this.teacherFamilyAdress = teacherFamilyAdress;
	}

	public String getTeacherFamilyAdress() {
		return this.teacherFamilyAdress;
	}

	public void setTeacherPoltics(int teacherPoltics) {
		this.teacherPoltics = teacherPoltics;
	}

	public int getTeacherPoltics() {
		return this.teacherPoltics;
	}

	public void setTeacherCardID(String teacherCardID) {
		this.teacherCardID = teacherCardID;
	}

	public String getTeacherCardID() {
		return this.teacherCardID;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getDelFlag() {
		return this.delFlag;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

}