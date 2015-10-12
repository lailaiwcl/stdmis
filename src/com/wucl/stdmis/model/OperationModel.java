package com.wucl.stdmis.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("W_OperationInfo")
public class OperationModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column("OperationID")
	private int operationID;

	@Column("OperationContent")
	private String operationContent;

	@Column("OperationIP")
	private String operationIP;

	@Column("RequestUrl")
	private String requestUrl;

	@Column("OperationDateTime")
	private Timestamp operationDateTime;

	@Column("UserID")
	private int userID;

	public void setOperationID(int operationID) {
		this.operationID = operationID;
	}

	public int getOperationID() {
		return this.operationID;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public String getOperationContent() {
		return this.operationContent;
	}

	public void setOperationIP(String operationIP) {
		this.operationIP = operationIP;
	}

	public String getOperationIP() {
		return this.operationIP;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestUrl() {
		return this.requestUrl;
	}

	public void setOperationDateTime(Timestamp operationDateTime) {
		this.operationDateTime = operationDateTime;
	}

	public Timestamp getOperationDateTime() {
		return this.operationDateTime;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getUserID() {
		return this.userID;
	}

}