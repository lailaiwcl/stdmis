package com.wucl.stdmis.model;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("W_DictTypeInfo")
public class DictTypeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column("DictTypeID")
	@Name
	private String dictTypeID;

	@Column("ParentDictTypeID")
	private String parentDictTypeID;

	@Column("DictTypeName")
	private String dictTypeName;

	public void setDictTypeID(String dictTypeID) {
		this.dictTypeID = dictTypeID;
	}

	public String getDictTypeID() {
		return this.dictTypeID;
	}

	public void setParentDictTypeID(String parentDictTypeID) {
		this.parentDictTypeID = parentDictTypeID;
	}

	public String getParentDictTypeID() {
		return this.parentDictTypeID;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	public String getDictTypeName() {
		return this.dictTypeName;
	}

}