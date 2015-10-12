package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.IPFilterModel;

public interface IIPFilterService {
	public List<IPFilterModel> listAll(int type);

	public IPFilterModel get(int id);

	public List<IPFilterModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key, int type);

	public boolean delete(int id);

	public boolean modify(IPFilterModel model);

	public boolean insert(IPFilterModel model);

	public int count(String key, int type);
}
