package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.TeacherModel;

/**
 * 教师数据层接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface ITeacherService {
	
	public List<TeacherModel> listAll();
	
	public TeacherModel get(String teacherNo);

	public List<TeacherModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);
	
	public List<TeacherModel> listWithPageAndDeptNo(String deptNo);

	public boolean delete(String teacherNo);

	public boolean modify(TeacherModel model);

	public boolean insert(TeacherModel model);

	public int count(String key);
}
