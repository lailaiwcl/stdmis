package com.wucl.stdmis.model;

import java.io.Serializable;
import java.sql.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("W_AggressionLogInfo")
public class AggressionLogModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column("LogID")
	@Id
	private int logID;

	@Column("RequstUrl")
	private String requstUrl;

	@Column("AggressionIP")
	private String aggressionIP;

	@Column("AggressionMac")
	private String aggressionMac;

	@Column("AggressionType")
	private int aggressionType;

	@Column("AggressionTime")
	private Date aggressionTime;

	@Column("AggressionDetail")
	private String aggressionDetail;

	public void setLogID(int logID) {
		this.logID = logID;
	}

	public int getLogID() {
		return this.logID;
	}

	public void setRequstUrl(String requstUrl) {
		this.requstUrl = requstUrl;
	}

	public String getRequstUrl() {
		return this.requstUrl;
	}

	public void setAggressionIP(String aggressionIP) {
		this.aggressionIP = aggressionIP;
	}

	public String getAggressionIP() {
		return this.aggressionIP;
	}

	public void setAggressionMac(String aggressionMac) {
		this.aggressionMac = aggressionMac;
	}

	public String getAggressionMac() {
		return this.aggressionMac;
	}

	public void setAggressionType(int aggressionType) {
		this.aggressionType = aggressionType;
	}

	public int getAggressionType() {
		return this.aggressionType;
	}

	public void setAggressionTime(Date aggressionTime) {
		this.aggressionTime = aggressionTime;
	}

	public Date getAggressionTime() {
		return this.aggressionTime;
	}

	public void setAggressionDetail(String aggressionDetail) {
		this.aggressionDetail = aggressionDetail;
	}

	public String getAggressionDetail() {
		return this.aggressionDetail;
	}

}