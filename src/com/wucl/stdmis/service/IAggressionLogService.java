package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.AggressionLogModel;

public interface IAggressionLogService {
	public AggressionLogModel get(int logID);

	public List<AggressionLogModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public boolean delete(int logID);

	public boolean modify(AggressionLogModel model);

	public boolean insert(AggressionLogModel model);

	public int count(String key);

}
