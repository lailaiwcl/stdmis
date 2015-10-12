package com.wucl.stdmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.wucl.stdmis.model.LeaveModel;
import com.wucl.stdmis.service.ILeaveService;

/**
 * 请假数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "leaveService")
public class LeaveService implements ILeaveService {

	@Inject
	private Dao dao = null;

	public int count(String key, String value) {
		Cnd cnd = null;
		if (key == null || "".equals(key.trim())) {
			cnd = Cnd.where("delFlag", "=", 0);
		} else {
			cnd = Cnd.where(key, "=", key).and("delFlag", "=", 0);
		}
		return dao.count(LeaveModel.class, cnd);
	}

	public boolean delete(int leaveID) {
		LeaveModel model = new LeaveModel();
		model.setLeaveID(leaveID);
		model.setDelFlag(1);
		int count = dao.updateIgnoreNull(model);
		if (count == 1) {
			return true;
		}
		return false;
	}

	public LeaveModel getByID(int leaveID) {
		LeaveModel model = null;
		try {
			model = dao.fetch(LeaveModel.class, Cnd.where("leaveID", "=",
					leaveID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	public List<LeaveModel> getByReceiverUserID(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key,
			String receiverUserID) {
		List<LeaveModel> modelList = listWithPage(pageIndex, pageSize,
				sortField, sortOrder, key, receiverUserID, "=");
		return modelList;
	}

	public List<LeaveModel> getByUserID(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key,
			String senderUserName) {
		List<LeaveModel> modelList = listWithPage(pageIndex, pageSize,
				sortField, sortOrder, key, senderUserName, "=");
		return modelList;
	}

	public boolean insert(LeaveModel model) {
		if (model == null) {
			return false;
		}
		try {
			dao.insert(model);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<LeaveModel> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LeaveModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<LeaveModel> modelList = listWithPage(pageIndex, pageSize,
				sortField, sortOrder, null, null, null);
		return modelList;
	}

	private List<LeaveModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key, String value,
			String op) {
		List<LeaveModel> modelList = new ArrayList<LeaveModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照学生学号降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "asc";
			sortField = "leaveID";
		}
		if ("asc".equalsIgnoreCase(sortOrder)) {
			if (key != null && key.trim().length() != 0) {
				modelList = dao.query(LeaveModel.class, Cnd.where(key, op,
						value).and("delFlag", "=", 0).asc(sortField), pager);
			} else {
				modelList = dao.query(LeaveModel.class, Cnd.where("delFlag",
						"=", 0).asc(sortField), pager);
			}

		} else {
			if (key != null && key.trim().length() != 0) {
				modelList = dao.query(LeaveModel.class, Cnd.where(key, op,
						value).and("delFlag", "=", 0).desc(sortField), pager);
			} else {
				modelList = dao.query(LeaveModel.class, Cnd.where("delFlag",
						"=", 0).desc(sortField), pager);
			}

		}
		return modelList;
	}

	public boolean modify(LeaveModel model) {
		if (model == null) {
			return false;
		}
		try {
			dao.updateIgnoreNull(model);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
