package com.wucl.stdmis.model;

import java.sql.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
/**
 * 用户模型类
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@Table("W_AdminInfo")
public class UserModel implements java.io.Serializable {
	private static final long serialVersionUID = -3503960765797462771L;
	@Column("UserID")
	@Id
	private int userID;
	
	@Column("UserName")
	private String userName;
	
	@Column("Passwords")
	private String passWords;
	
	@Column("UserType")
	private int userType;
	
	@Column("LoginTimes")
	private int loginTimes;
	
	@Column("RecentLoginTime")
	private Date recentLoginTime;
	
	
	private Date lastLoginTime;
	
	@Column("UserMail")
	private String userEmail;
	
	@Column("RegisterDate")
	private Date registerDate;
	
	@Column("UserState")
	private int userState;
	
	@Column("Notes")
	private String notes;
	
	@Column("DelFlag")
	private int delFlag;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWords() {
		return passWords;
	}

	public void setPassWords(String passWords) {
		this.passWords = passWords;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public int getUserState() {
		return userState;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Date getRecentLoginTime() {
		return recentLoginTime;
	}

	public void setRecentLoginTime(Date recentLoginTime) {
		this.recentLoginTime = recentLoginTime;
	}
	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setUserState(int userState) {
		this.userState = userState;
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
