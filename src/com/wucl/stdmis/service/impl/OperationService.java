package com.wucl.stdmis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.DaoException;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.wucl.stdmis.model.OperationModel;
import com.wucl.stdmis.service.IOperationService;

/**
 * 系统操作记录数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "operationService")
public class OperationService implements IOperationService {

	@Inject
	private Dao dao = null;

	public int count(String key) {
		Cnd cnd = null;
		if (!Strings.isBlank(key)) {
			cnd = Cnd.where("operationContent", "like", "%" + key + "%");
		}
		int count;
		try {
			count = dao.count(OperationModel.class, cnd);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return count;
	}

	public boolean delete(int operationID) {
		try {
			dao.delete(OperationModel.class, operationID);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return true;
	}

	public OperationModel get(int operationID) {
		OperationModel model = null;
		try {
			model = dao.fetch(OperationModel.class, Cnd.where("operationID",
					"=", operationID));
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return model;
	}

	public boolean insert(OperationModel model) {
		if (model == null) {
			return false;
		}
		try {
			dao.insert(model);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return true;
	}

	public List<OperationModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<OperationModel> modelList = new ArrayList<OperationModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照学生学号降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "desc";
			sortField = "operationID";
		}
		try {
			if ("asc".equalsIgnoreCase(sortOrder)) {
				if (key != null && key.trim().length() != 0) {
					modelList = dao.query(OperationModel.class, Cnd.where(
							"operationContent", "like", "%" + key + "%").asc(
							sortField), pager);
				} else {
					modelList = dao.query(OperationModel.class, Cnd.cri().asc(
							sortField), pager);
				}

			} else {
				if (key != null && key.trim().length() != 0) {
					modelList = dao.query(OperationModel.class, Cnd.where(
							"operationContent", "like", "%" + key + "%").desc(
							sortField), pager);
				} else {
					modelList = dao.query(OperationModel.class, Cnd.cri().desc(
							sortField), pager);
				}

			}
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return modelList;
	}

	public boolean modify(OperationModel model) {
		if (model == null) {
			return false;
		}
		try {
			dao.updateIgnoreNull(model);
			return true;
		} catch (DaoException e) {
			throw new DaoException(e);
		}
	}

}
