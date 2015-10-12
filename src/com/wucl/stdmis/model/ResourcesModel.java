package com.wucl.stdmis.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
/**
 * 资源模型
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@Table("W_Resources")
public class ResourcesModel implements java.io.Serializable {

	private static final long serialVersionUID = -3216592024263999741L;

	@Column("AutoID")
	@Id
	private int autoID;

	@Column("ResourcesID")
	private String resourcesID;

	@Column("ParentResourcesID")
	private String parentResourcesID;

	@Column("ResourcesName")
	private String resourcesName;

	@Column("RequestUrl")
	private String requestUrl;

	@Column("Notes")
	private String notes;

	@Column("DelFlag")
	private int delFlag;

	public int getAutoID() {
		return autoID;
	}

	public void setAutoID(int autoID) {
		this.autoID = autoID;
	}

	public String getResourcesID() {
		return resourcesID;
	}

	public void setResourcesID(String resourcesID) {
		this.resourcesID = resourcesID;
	}

	public String getParentResourcesID() {
		return parentResourcesID;
	}

	public void setParentResourcesID(String parentResourcesID) {
		this.parentResourcesID = parentResourcesID;
	}

	public String getResourcesName() {
		return resourcesName;
	}

	public void setResourcesName(String resourcesName) {
		this.resourcesName = resourcesName;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
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
