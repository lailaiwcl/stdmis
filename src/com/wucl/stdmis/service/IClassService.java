package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.ClassModel;
/**
 * 班级管理数据层接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface IClassService {
	public List<ClassModel> listAll();
	
	public ClassModel get(String classID);

	public List<ClassModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public List<ClassModel> getChild(String parentClassID);

	public ClassModel getParent(String classID);

	public boolean delete(String classID);

	public boolean modify(ClassModel classes);

	public boolean insert(ClassModel classes);

	public int count(String key);

}
