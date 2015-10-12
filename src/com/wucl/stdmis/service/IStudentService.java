package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.StudentModel;

/**
 * 学生数据层接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface IStudentService {

	public StudentModel get(String studentNo);

	public List<StudentModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public List<StudentModel> listWithPageByDeptNo(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key, String deptNo);

	public boolean delete(String studentNo);

	public boolean modify(StudentModel model);

	public boolean insert(StudentModel model);

	public int count(String key);

	public int count(String key, String deptNo);

}
