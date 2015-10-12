package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.DeptModel;

/**
 * 部门管理数据层接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface IDeptService {
	public List<DeptModel> listAll();

	public DeptModel get(String deptNo);

	public List<DeptModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public List<DeptModel> getChild(String parentDeptNo);

	public DeptModel getParent(String deptNo);

	public boolean delete(String deptNo);

	public boolean modify(DeptModel model);

	public boolean insert(DeptModel model);

	public int count(String key);
}
