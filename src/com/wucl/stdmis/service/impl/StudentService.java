package com.wucl.stdmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.wucl.stdmis.model.StudentModel;
import com.wucl.stdmis.service.IStudentService;

/**
 * 学生数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "studentService")
public class StudentService implements IStudentService {

	@Inject
	private Dao dao = null;

	public int count(String key) {
		Cnd cnd = null;
		if (key == null || "".equals(key.trim())) {
			cnd = Cnd.where("delFlag", "=", 0);
		} else {
			cnd = Cnd.where("studentName", "like", "%" + key + "%").and(
					"delFlag", "=", 0);
		}
		return dao.count(StudentModel.class, cnd);
	}

	public int count(String key, String deptNo) {
		Cnd cnd = null;
		if (Strings.isBlank(key) && Strings.isBlank(deptNo)) {
			cnd = Cnd.where("delFlag", "=", 0);
		} else if (!Strings.isBlank(key) && Strings.isBlank(deptNo)) {
			cnd = Cnd.where("StudentName", "like", "%" + key + "%").and(
					"delFlag", "=", 0);
		} else if (Strings.isBlank(key) && !Strings.isBlank(deptNo)) {
			cnd = Cnd.where("deptNo", "=", deptNo).and("delFlag", "=", 0);
		} else if (!Strings.isBlank(key) && !Strings.isBlank(deptNo)) {
			cnd = Cnd.where("deptNo", "=", deptNo).and("StudentName", "like",
					"%" + key + "%").and("delFlag", "=", 0);
		}
		return dao.count(StudentModel.class, cnd);
	}

	public boolean delete(String studentNo) {
		StudentModel model = new StudentModel();
		model.setStudentNo(studentNo);
		model.setDelFlag(1);
		int count = dao.updateIgnoreNull(model);
		if (count == 1) {
			return true;
		}
		return false;
	}

	public StudentModel get(String studentNo) {
		StudentModel model = null;
		try {
			model = dao.fetch(StudentModel.class, Cnd.where("studentNo", "=",
					studentNo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	public boolean insert(StudentModel model) {
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

	public List<StudentModel> listWithPageByDeptNo(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key, String deptNo) {
		List<StudentModel> modelList = new ArrayList<StudentModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照学生学号降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "asc";
			sortField = "studentNo";
		}
		if ("asc".equalsIgnoreCase(sortOrder)) {
			if (!Strings.isBlank(key) && !Strings.isBlank(deptNo)) {
				modelList = dao.query(StudentModel.class, Cnd.where(
						"studentName", "like", "%" + key + "%").and("deptNo",
						"=", deptNo).and("delFlag", "=", 0).asc(sortField),
						pager);
			} else if (!Strings.isBlank(key) && Strings.isBlank(deptNo)) {
				modelList = dao.query(StudentModel.class, Cnd.where(
						"studentName", "like", "%" + key + "%").and("delFlag",
								"=", 0).asc(sortField), pager);
			} else if (Strings.isBlank(key) && !Strings.isBlank(deptNo)) {
				modelList = dao.query(StudentModel.class, Cnd.where("deptNo",
						"=", deptNo).and("delFlag", "=", 0).asc(sortField),
						pager);
			} else {
				modelList = dao.query(StudentModel.class, Cnd.where("delFlag",
						"=", 0).asc(sortField), pager);
			}

		} else {
			if (!Strings.isBlank(key) && !Strings.isBlank(deptNo)) {
				modelList = dao.query(StudentModel.class, Cnd.where(
						"studentName", "like", "%" + key + "%").and("deptNo",
						"=", deptNo).and("delFlag", "=", 0).desc(sortField),
						pager);
			} else if (!Strings.isBlank(key) && Strings.isBlank(deptNo)) {
				modelList = dao.query(StudentModel.class, Cnd.where(
						"studentName", "like", "%" + key + "%").and("delFlag",
								"=", 0).desc(sortField), pager);

			} else if (Strings.isBlank(key) && !Strings.isBlank(deptNo)) {
				modelList = dao.query(StudentModel.class, Cnd.where("deptNo",
						"=", deptNo).and("delFlag", "=", 0).desc(sortField),
						pager);
			} else {
				modelList = dao.query(StudentModel.class, Cnd.where("delFlag",
						"=", 0).desc(sortField), pager);
			}
		}
		return modelList;
	}

	public List<StudentModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<StudentModel> modelList = new ArrayList<StudentModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照学生学号降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "asc";
			sortField = "studentNo";
		}
		if ("asc".equalsIgnoreCase(sortOrder)) {
			if (!Strings.isBlank(key)) {
				modelList = dao.query(StudentModel.class, Cnd.where(
						"studentName", "like", "%" + key + "%").and("delFlag",
						"=", 0).asc(sortField), pager);
			} else {
				modelList = dao.query(StudentModel.class, Cnd.where("delFlag",
						"=", 0).asc(sortField), pager);
			}

		} else {
			if (!Strings.isBlank(key)) {
				modelList = dao.query(StudentModel.class, Cnd.where(
						"studentName", "like", "%" + key + "%").and("delFlag",
						"=", 0).desc(sortField), pager);
			} else {
				modelList = dao.query(StudentModel.class, Cnd.where("delFlag",
						"=", 0).desc(sortField), pager);
			}

		}
		return modelList;
	}

	public boolean modify(StudentModel model) {
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
