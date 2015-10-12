package com.wucl.stdmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.wucl.stdmis.model.ClassModel;
import com.wucl.stdmis.service.IClassService;

/**
 * 班级管理数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "classService")
public class ClassService implements IClassService {

	@Inject
	private Dao dao = null;

	public boolean insert(ClassModel classes) {
		dao.insert(classes);
		return false;
	}

	public boolean delete(String classID) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ClassModel> getChild(String parentClassID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ClassModel get(String classID) {
		ClassModel calssModel = dao.fetch(ClassModel.class, classID);
		return calssModel;
	}

	public ClassModel getParent(String classID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ClassModel> listAll() {
		List<ClassModel> classModelList = new ArrayList<ClassModel>();
		classModelList = dao.query(ClassModel.class, Cnd.where("delFlag", "=",
				0));
		return classModelList;
	}
	
	public List<ClassModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<ClassModel> classModelList = new ArrayList<ClassModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照用户的创建时间降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "desc";
			sortField = "parentClassID";
		}
		if ("asc".equalsIgnoreCase(sortOrder)) {
			if (key != null && key.trim().length() != 0) {
				classModelList = dao.query(ClassModel.class, Cnd.where(
						"className", "like", "%" + key + "%").and("delFlag",
						"=", 0).asc(sortField), pager);
			} else {
				classModelList = dao.query(ClassModel.class, Cnd.where("delFlag",
						"=", 0).asc(sortField), pager);
			}

		} else {
			if (key != null && key.trim().length() != 0) {
				classModelList = dao.query(ClassModel.class, Cnd.where(
						"className", "like", "%" + key + "%").and("delFlag",
						"=", 0).desc(sortField), pager);
			} else {
				classModelList = dao.query(ClassModel.class, Cnd.where("delFlag",
						"=", 0).desc(sortField), pager);
			}

		}
		return classModelList;
	}

	public boolean modify(ClassModel classes) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int count(String key) {
		Cnd cnd = null;
		if (key == null || "".equals(key.trim())) {
			cnd = Cnd.where("delFlag", "=", 0);
		} else {
			cnd = Cnd.where("className", "like", "%" + key + "%").and("delFlag",
					"=", 0);
		}
		return dao.count(ClassModel.class, cnd);
	}

}
