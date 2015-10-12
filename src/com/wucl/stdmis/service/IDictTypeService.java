package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.DictTypeModel;

public interface IDictTypeService {

	public DictTypeModel get(String dictTypeID);
	
	public List<DictTypeModel> listAll();

	public List<DictTypeModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public boolean delete(String dictTypeID);

	public boolean modify(DictTypeModel model);

	public boolean insert(DictTypeModel model);

	public int count(String key);

}
