package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.DictEntryModel;

/**
 * 业务字典实体数据层接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface IDictEntryService {

	public DictEntryModel getEntryByID(int dictEntryID);

	public List<DictEntryModel> getEntryByType(String dictTypeID);

	public List<DictEntryModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public boolean delete(int dictEntryID);

	public boolean modify(DictEntryModel model);

	public boolean insert(DictEntryModel model);

	public int count(String key);
}
