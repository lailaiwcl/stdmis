package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.OperationModel;

public interface IOperationService {
	public OperationModel get(int operationID);

	public List<OperationModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public boolean delete(int operationID);

	public boolean modify(OperationModel model);

	public boolean insert(OperationModel model);

	public int count(String key);

}
