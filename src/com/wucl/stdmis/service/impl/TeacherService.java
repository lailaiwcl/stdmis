package com.wucl.stdmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.wucl.stdmis.model.TeacherModel;
import com.wucl.stdmis.service.ITeacherService;

/**
 * 教师数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "teacherService")
public class TeacherService implements ITeacherService {

	@Inject
	private Dao dao = null;

	public List<TeacherModel> listAll() {
		List<TeacherModel> modelList = new ArrayList<TeacherModel>();
		modelList = dao.query(TeacherModel.class, Cnd.where("delFlag", "=", 0));
		return modelList;
	}

	public int count(String key) {
		Cnd cnd = null;
		if (key == null || "".equals(key.trim())) {
			cnd = Cnd.where("delFlag", "=", 0);
		} else {
			cnd = Cnd.where("teacherName", "like", "%" + key + "%").and(
					"delFlag", "=", 0);
		}
		return dao.count(TeacherModel.class, cnd);
	}

	public boolean delete(String teacherNo) {
		TeacherModel model = new TeacherModel();
		model.setTeacherNo(teacherNo);
		model.setDelFlag(1);
		int count = dao.updateIgnoreNull(model);
		if (count == 1) {
			return true;
		}
		return false;
	}

	public TeacherModel get(String teacherNo) {
		TeacherModel model = null;
		try {
			model = dao.fetch(TeacherModel.class, Cnd.where("teacherNo", "=",
					teacherNo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	public boolean insert(TeacherModel model) {
		if (model == null) {
			return false;
		}
		try {
			dao.insert(model);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<TeacherModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<TeacherModel> modelList = new ArrayList<TeacherModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照学生学号降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "asc";
			sortField = "teacherNo";
		}
		if ("asc".equalsIgnoreCase(sortOrder)) {
			if (key != null && key.trim().length() != 0) {
				modelList = dao.query(TeacherModel.class, Cnd.where(
						"teacherName", "like", "%" + key + "%").and("delFlag",
						"=", 0).asc(sortField), pager);
			} else {
				modelList = dao.query(TeacherModel.class, Cnd.where("delFlag",
						"=", 0).asc(sortField), pager);
			}

		} else {
			if (key != null && key.trim().length() != 0) {
				modelList = dao.query(TeacherModel.class, Cnd.where(
						"teacherName", "like", "%" + key + "%").and("delFlag",
						"=", 0).desc(sortField), pager);
			} else {
				modelList = dao.query(TeacherModel.class, Cnd.where("delFlag",
						"=", 0).desc(sortField), pager);
			}

		}
		return modelList;
	}

	public List<TeacherModel> listWithPageAndDeptNo(String deptNo) {
		List<TeacherModel> modelList = new ArrayList<TeacherModel>();
		int pageSize = 10;
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(1);
		String sortOrder = "asc";
		String sortField = "teacherNo";
		if ("asc".equalsIgnoreCase(sortOrder)) {
			if (deptNo != null && deptNo.trim().length() != 0) {
				modelList = dao.query(TeacherModel.class, Cnd.where("deptNo",
						"=", deptNo).and("delFlag", "=", 0).asc(sortField),
						pager);
			} else {
				modelList = dao.query(TeacherModel.class, Cnd.where("delFlag",
						"=", 0).asc(sortField), pager);
			}

		} else {
			if (deptNo != null && deptNo.trim().length() != 0) {
				modelList = dao.query(TeacherModel.class, Cnd.where("deptNo",
						"=", deptNo).and("delFlag", "=", 0).desc(sortField),
						pager);
			} else {
				modelList = dao.query(TeacherModel.class, Cnd.where("delFlag",
						"=", 0).desc(sortField), pager);
			}

		}
		return modelList;

	}

	public boolean modify(TeacherModel model) {
		if (model == null) {
			return false;
		}
		try {
			dao.updateIgnoreNull(model);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
