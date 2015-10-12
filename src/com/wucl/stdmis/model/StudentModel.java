package com.wucl.stdmis.model;

import java.io.Serializable;
import java.sql.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 学生模型类
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@Table("W_StudentInfo")
public class StudentModel implements Serializable {

	private static final long serialVersionUID = -2070566961573905970L;

	@Column("StudentNo")
	@Name
	private String studentNo;

	@Column("StudentName")
	private String studentName;

	@Column("StudentSex")
	private Integer studentSex;

	@Column("StudentMobile")
	private String studentMobile;

	@Column("StudentEmail")
	private String studentEmail;

	@Column("StudentQQ")
	private String studentQQ;

	@Column("StudentDormitory")
	private String studentDormitory;

	@Column("StudentFamilyPhone")
	private String studentFamilyPhone;

	@Column("StudentFamilyAdress")
	private String studentFamilyAdress;

	@Column("StudentCardID")
	private String studentCardID;

	@Column("StudentBankNo")
	private String studentBankNo;

	@Column("StudentPolitics")
	private Integer studentPolitics;

	@Column("StudentGraduateTime")
	private Date studentGraduateTime;

	@Column("JoinDate")
	private Date joinDate;

	@Column("SchoolArea")
	private String schoolArea;

	@Column("PhotoUrl")
	private String photoUrl;

	@Column("DelFlag")
	private Integer delFlag;

	@Column("DeptNo")
	private String deptNo;

	@Column("TeacherNo")
	private String teacherNo;

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getStudentSex() {
		return studentSex;
	}

	public void setStudentSex(Integer studentSex) {
		this.studentSex = studentSex;
	}

	public String getStudentMobile() {
		return studentMobile;
	}

	public void setStudentMobile(String studentMobile) {
		this.studentMobile = studentMobile;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public String getStudentQQ() {
		return studentQQ;
	}

	public void setStudentQQ(String studentQQ) {
		this.studentQQ = studentQQ;
	}

	public String getStudentDormitory() {
		return studentDormitory;
	}

	public void setStudentDormitory(String studentDormitory) {
		this.studentDormitory = studentDormitory;
	}

	public String getStudentFamilyPhone() {
		return studentFamilyPhone;
	}

	public void setStudentFamilyPhone(String studentFamilyPhone) {
		this.studentFamilyPhone = studentFamilyPhone;
	}

	public String getStudentFamilyAdress() {
		return studentFamilyAdress;
	}

	public void setStudentFamilyAdress(String studentFamilyAdress) {
		this.studentFamilyAdress = studentFamilyAdress;
	}

	public String getStudentCardID() {
		return studentCardID;
	}

	public void setStudentCardID(String studentCardID) {
		this.studentCardID = studentCardID;
	}

	public String getStudentBankNo() {
		return studentBankNo;
	}

	public void setStudentBankNo(String studentBankNo) {
		this.studentBankNo = studentBankNo;
	}

	public Integer getStudentPolitics() {
		return studentPolitics;
	}

	public void setStudentPolitics(Integer studentPolitics) {
		this.studentPolitics = studentPolitics;
	}

	public Date getStudentGraduateTime() {
		return studentGraduateTime;
	}

	public void setStudentGraduateTime(Date studentGraduateTime) {
		this.studentGraduateTime = studentGraduateTime;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getSchoolArea() {
		return schoolArea;
	}

	public void setSchoolArea(String schoolArea) {
		this.schoolArea = schoolArea;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getTeacherNo() {
		return teacherNo;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}


}