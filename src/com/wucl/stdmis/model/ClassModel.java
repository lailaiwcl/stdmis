package com.wucl.stdmis.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
/**
 * 班级模型
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@Table("W_ClassInfo")
public class ClassModel implements java.io.Serializable {

	private static final long serialVersionUID = -7190350242593978214L;

	@Column("ClassID")
	@Name
	private String classID;
	@Column("ParentClassID")
	private String parentClassID;
	@Column("ClassName")
	private String className;
	@Column("ShortClassName")
	private String shortClassName;
	@Column("Notes")
	private String notes;
	@Column("DelFlag")
	private int delFlag;
	public String getClassID() {
		return classID;
	}
	public void setClassID(String classID) {
		this.classID = classID;
	}
	public String getParentClassID() {
		return parentClassID;
	}
	public void setParentClassID(String parentClassID) {
		this.parentClassID = parentClassID;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getShortClassName() {
		return shortClassName;
	}
	public void setShortClassName(String shortClassName) {
		this.shortClassName = shortClassName;
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
