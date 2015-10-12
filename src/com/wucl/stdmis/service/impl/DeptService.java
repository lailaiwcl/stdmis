package com.wucl.stdmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.DaoException;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.wucl.stdmis.model.DeptModel;
import com.wucl.stdmis.model.StudentModel;
import com.wucl.stdmis.service.IDeptService;

/**
 * 部门管理数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "deptService")
public class DeptService implements IDeptService {

	@Inject
	private Dao dao = null;

	public int count(String key) {
		Cnd cnd = null;
		if (Strings.isBlank(key)) {
			cnd = Cnd.where("delFlag", "=", 0);
		} else {
			cnd = Cnd.where("deptName", "like", "%" + key + "%").and("delFlag",
					"=", 0);
		}
		int count;
		try {
			count = dao.count(StudentModel.class, cnd);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return count;
	}

	public boolean delete(String deptNo) {
		// TODO Auto-generated method stub
		return false;
	}

	public DeptModel get(String deptNo) {
		DeptModel model;
		try {
			model = dao.fetch(DeptModel.class, deptNo);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return model;
	}

	public List<DeptModel> getChild(String parentDeptNo) {
		List<DeptModel> modelList = new ArrayList<DeptModel>();
		try {
			if (Strings.isBlank(parentDeptNo)) {
				modelList = dao.query(DeptModel.class, Cnd.where(
						"parentDeptNo", "=", null).and("delFlag", "=", 0));
			} else {
				modelList = dao.query(DeptModel.class, Cnd.where(
						"parentDeptNo", "=", parentDeptNo).and("delFlag", "=",
						0));
			}
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return modelList;
	}

	public DeptModel getParent(String deptNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean insert(DeptModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<DeptModel> listAll() {
		List<DeptModel> modelList = new ArrayList<DeptModel>();
		try {
			modelList = dao
					.query(DeptModel.class, Cnd.where("delFlag", "=", 0));
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return modelList;
	}

	public List<DeptModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<DeptModel> modelList = new ArrayList<DeptModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照学生学号降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "asc";
			sortField = "parentDeptNo";
		}
		try {
			if ("asc".equalsIgnoreCase(sortOrder)) {
				if (key != null && key.trim().length() != 0) {
					modelList = dao.query(DeptModel.class, Cnd.where(
							"deptName", "like", "%" + key + "%").and("delFlag",
							"=", 0).asc(sortField), pager);
				} else {
					modelList = dao.query(DeptModel.class, Cnd.where("delFlag",
							"=", 0).asc(sortField), pager);
				}

			} else {
				if (key != null && key.trim().length() != 0) {
					modelList = dao.query(DeptModel.class, Cnd.where(
							"deptName", "like", "%" + key + "%").and("delFlag",
							"=", 0).desc(sortField), pager);
				} else {
					modelList = dao.query(DeptModel.class, Cnd.where("delFlag",
							"=", 0).desc(sortField), pager);
				}

			}
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return modelList;
	}

	public boolean modify(DeptModel model) {
		// TODO Auto-generated method stub
		return false;
	}

}
