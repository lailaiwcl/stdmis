package com.wucl.stdmis.model;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("W_DictEntryInfo")
public class DictEntryModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column("DictEntryID")
	@Id
	private int dictEntryID;

	@Column("ParentDictEntryID")
	private int parentDictEntryID;

	@Column("DictEntryName")
	private String dictEntryName;

	@Column("DictEntryValue")
	private String dictEntryValue;

	@Column("DictTypeID")
	private String dictTypeID;
	
	@Column("SortField")
	private int sortField;

	public void setDictEntryID(int dictEntryID) {
		this.dictEntryID = dictEntryID;
	}

	public int getDictEntryID() {
		return this.dictEntryID;
	}

	public void setParentDictEntryID(int parentDictEntryID) {
		this.parentDictEntryID = parentDictEntryID;
	}

	public int getParentDictEntryID() {
		return this.parentDictEntryID;
	}

	public void setDictEntryName(String dictEntryName) {
		this.dictEntryName = dictEntryName;
	}

	public String getDictEntryName() {
		return this.dictEntryName;
	}

	public String getDictEntryValue() {
		return dictEntryValue;
	}

	public void setDictEntryValue(String dictEntryValue) {
		this.dictEntryValue = dictEntryValue;
	}

	public void setDictTypeID(String dictTypeID) {
		this.dictTypeID = dictTypeID;
	}

	public String getDictTypeID() {
		return this.dictTypeID;
	}

	public int getSortField() {
		return sortField;
	}

	public void setSortField(int sortField) {
		this.sortField = sortField;
	}

	@Override
	public String toString() {
		return "DictEntryModel [dictEntryID=" + dictEntryID
				+ ", dictEntryName=" + dictEntryName + ", dictEntryValue="
				+ dictEntryValue + ", dictTypeID=" + dictTypeID
				+ ", parentDictEntryID=" + parentDictEntryID + ", sortField="
				+ sortField + "]";
	}
	
	
	
}