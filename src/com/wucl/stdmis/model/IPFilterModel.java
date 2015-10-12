package com.wucl.stdmis.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("W_IPFilter")
public class IPFilterModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column("IPFilterID")
	private int iPFilterID;

	@Column("IPAdress")
	private String iPAdress;

	@Column("IPType")
	private int iPType;

	@Column("AddTime")
	private Timestamp addTime;

	@Column("Notes")
	private String notes;

	public void setIPFilterID(int iPFilterID) {
		this.iPFilterID = iPFilterID;
	}

	public int getIPFilterID() {
		return this.iPFilterID;
	}

	public void setIPAdress(String iPAdress) {
		this.iPAdress = iPAdress;
	}

	public String getIPAdress() {
		return this.iPAdress;
	}

	public void setIPType(int iPType) {
		this.iPType = iPType;
	}

	public int getIPType() {
		return this.iPType;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Timestamp getAddTime() {
		return this.addTime;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return this.notes;
	}

}