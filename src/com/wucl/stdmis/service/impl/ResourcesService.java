package com.wucl.stdmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.wucl.stdmis.model.ResourcesModel;
import com.wucl.stdmis.service.IResourcesService;

/**
 * 资源管理数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "resourcesService")
public class ResourcesService implements IResourcesService {

	@Inject
	private Dao dao = null;

	public int count(String key) {
		Cnd cnd = null;
		if (key == null || "".equals(key.trim())) {
			cnd = Cnd.where("delFlag", "=", 0);
		} else {
			cnd = Cnd.where("resourcesName", "like", "%" + key + "%").and(
					"delFlag", "=", 0);
		}
		return dao.count(ResourcesModel.class, cnd);
	}

	public int count(String key, String parentResourcesID) {
		Cnd cnd = null;
		if (Strings.isBlank(key) && Strings.isBlank(parentResourcesID)) {
			cnd = Cnd.where("delFlag", "=", 0);
		} else if (!Strings.isBlank(key) && Strings.isBlank(parentResourcesID)) {
			cnd = Cnd.where("resourcesName", "like", "%" + key + "%").and(
					"delFlag", "=", 0);
		} else if (Strings.isBlank(key) && !Strings.isBlank(parentResourcesID)) {
			cnd = Cnd.where("parentResourcesID", "=", parentResourcesID).and(
					"delFlag", "=", 0);
		} else if (!Strings.isBlank(key) && !Strings.isBlank(parentResourcesID)) {
			cnd = Cnd.where("parentResourcesID", "=", parentResourcesID).and(
					"resourcesName", "like", "%" + key + "%").and("delFlag",
					"=", 0);
		}
		return dao.count(ResourcesModel.class, cnd);
	}

	public boolean delete(int autoID) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ResourcesModel> getChild(String resourcesID) {
		List<ResourcesModel> modelList = new ArrayList<ResourcesModel>();
		if (Strings.isBlank(resourcesID)) {
			modelList = dao.query(ResourcesModel.class, Cnd.where(
					"parentResourcesID", "=", null).and("delFlag", "=", 0));
		} else {
			modelList = dao.query(ResourcesModel.class, Cnd.where(
					"parentResourcesID", "=", resourcesID).and("delFlag", "=",
					0));
		}
		return modelList;
	}

	public ResourcesModel getParent(String resourcesID) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResourcesModel get(int autoID) {
		ResourcesModel resourcesModel = dao.fetch(ResourcesModel.class, autoID);
		return resourcesModel;
	}

	public ResourcesModel getByResourcesID(String resourcesID) {
		ResourcesModel model = null;
		if (Strings.isBlank(resourcesID)) {
			return null;
		} else {
			model = dao.fetch(ResourcesModel.class, Cnd.where("resourcesID",
					"=", resourcesID).and("delFlag", "=", 0));
		}
		return model;
	}

	public boolean insert(ResourcesModel resourcesModel) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ResourcesModel> listAll() {
		List<ResourcesModel> resourcesModelList = new ArrayList<ResourcesModel>();
		resourcesModelList = dao.query(ResourcesModel.class, Cnd.where(
				"delFlag", "=", 0));
		return resourcesModelList;
	}

	public List<ResourcesModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<ResourcesModel> modelList = new ArrayList<ResourcesModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "asc";
			sortField = "parentResourcesID";
		}
		if ("asc".equalsIgnoreCase(sortOrder)) {
			if (key != null && key.trim().length() != 0) {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"resourcesName", "like", "%" + key + "%").and(
						"delFlag", "=", 0).asc(sortField), pager);
			} else {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"delFlag", "=", 0).asc(sortField), pager);
			}

		} else {
			if (key != null && key.trim().length() != 0) {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"resourcesName", "like", "%" + key + "%").and(
						"delFlag", "=", 0).desc(sortField), pager);
			} else {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"delFlag", "=", 0).desc(sortField), pager);
			}

		}
		return modelList;
	}

	public List<ResourcesModel> listWithPageByParent(int pageIndex,
			int pageSize, String sortField, String sortOrder, String key,
			String parentResourcesID) {
		List<ResourcesModel> modelList = new ArrayList<ResourcesModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照学生学号降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "asc";
			sortField = "parentResourcesID";
		}
		if ("asc".equalsIgnoreCase(sortOrder)) {
			if (!Strings.isBlank(key) && !Strings.isBlank(parentResourcesID)) {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"resourcesName", "like", "%" + key + "%").and(
						"parentResourcesID", "=", parentResourcesID).and(
						"delFlag", "=", 0).asc(sortField), pager);
			} else if (!Strings.isBlank(key)
					&& Strings.isBlank(parentResourcesID)) {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"resourcesName", "like", "%" + key + "%").and(
						"delFlag", "=", 0).asc(sortField), pager);
			} else if (Strings.isBlank(key)
					&& !Strings.isBlank(parentResourcesID)) {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"parentResourcesID", "=", parentResourcesID).and(
						"delFlag", "=", 0).asc(sortField), pager);
			} else {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"delFlag", "=", 0).asc(sortField), pager);
			}

		} else {
			if (!Strings.isBlank(key) && !Strings.isBlank(parentResourcesID)) {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"resourcesName", "like", "%" + key + "%").and(
						"parentResourcesID", "=", parentResourcesID).and(
						"delFlag", "=", 0).desc(sortField), pager);
			} else if (!Strings.isBlank(key)
					&& Strings.isBlank(parentResourcesID)) {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"resourcesName", "like", "%" + key + "%").and(
						"delFlag", "=", 0).desc(sortField), pager);

			} else if (Strings.isBlank(key)
					&& !Strings.isBlank(parentResourcesID)) {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"parentResourcesID", "=", parentResourcesID).and(
						"delFlag", "=", 0).desc(sortField), pager);
			} else {
				modelList = dao.query(ResourcesModel.class, Cnd.where(
						"delFlag", "=", 0).desc(sortField), pager);
			}
		}
		return modelList;
	}

	public boolean modify(ResourcesModel resourcesModel) {
		// TODO Auto-generated method stub
		return false;
	}

}
