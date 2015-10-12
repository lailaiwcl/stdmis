package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.LeaveModel;

public interface ILeaveService {
	public List<LeaveModel> listAll();

	public LeaveModel getByID(int leaveID);

	public List<LeaveModel> getByUserID(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key,String userID);

	public List<LeaveModel> getByReceiverUserID(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key,String receiverUserID);

	public List<LeaveModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public boolean delete(int leaveID);

	public boolean modify(LeaveModel model);

	public boolean insert(LeaveModel model);

	public int count(String key, String value);
}
