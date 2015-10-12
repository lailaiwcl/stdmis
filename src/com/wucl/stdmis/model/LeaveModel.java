package com.wucl.stdmis.model;

import java.io.Serializable;
import java.sql.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("W_LeaveInfo")
public class LeaveModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column("LeaveID")
	@Id
	private int leaveID;

	@Column("SenderUserName")
	private String senderUserName;

	@Column("ReceiverUserName")
	private String receiverUserName;

	@Column("ApplyTime")
	private Date applyTime;

	@Column("LeaveName")
	private String leaveName;

	@Column("StartTime")
	private Date startTime;

	@Column("ExpiredTime")
	private Date expiredTime;

	@Column("LeaveText")
	private String leaveText;

	@Column("LeaveState")
	private int leaveState;

	@Column("ReplyText")
	private String replyText;

	@Column("DelFlag")
	private int delFlag;

	public void setLeaveID(int leaveID) {
		this.leaveID = leaveID;
	}

	public int getLeaveID() {
		return this.leaveID;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public String getSenderUserName() {
		return this.senderUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getReceiverUserName() {
		return this.receiverUserName;
	}

	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}

	public String getLeaveName() {
		return this.leaveName;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Date getExpiredTime() {
		return this.expiredTime;
	}

	public void setLeaveText(String leaveText) {
		this.leaveText = leaveText;
	}

	public String getLeaveText() {
		return this.leaveText;
	}

	public void setLeaveState(int leaveState) {
		this.leaveState = leaveState;
	}

	public int getLeaveState() {
		return this.leaveState;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public String getReplyText() {
		return this.replyText;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getDelFlag() {
		return this.delFlag;
	}
}