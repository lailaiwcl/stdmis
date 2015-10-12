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

import com.wucl.stdmis.model.AggressionLogModel;
import com.wucl.stdmis.service.IAggressionLogService;

/**
 * 入侵防护日志数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "aggressionLogService")
public class AggressionLogService implements IAggressionLogService {

	@Inject
	private Dao dao = null;

	public int count(String key) {
		Cnd cnd = null;
		if (!Strings.isBlank(key)) {
			cnd = Cnd.where("aggressionDetail", "like", "%" + key + "%");
		}
		int count;
		try {
			count = dao.count(AggressionLogModel.class, cnd);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return count;
	}

	public boolean delete(int logID) {
		try {
			dao.delete(AggressionLogModel.class, logID);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return true;
	}

	public AggressionLogModel get(int logID) {
		AggressionLogModel model = null;
		try {
			model = dao.fetch(AggressionLogModel.class, Cnd.where("logID", "=",
					logID));
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return model;
	}

	public boolean insert(AggressionLogModel model) {
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

	public List<AggressionLogModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<AggressionLogModel> modelList = new ArrayList<AggressionLogModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		// 如果没有传入排序字段，则默认按照学生学号降序排列
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "desc";
			sortField = "logID";
		}
		try {
			if ("asc".equalsIgnoreCase(sortOrder)) {
				if (key != null && key.trim().length() != 0) {
					modelList = dao.query(AggressionLogModel.class, Cnd.where(
							"aggressionDetail", "like", "%" + key + "%").asc(
							sortField), pager);
				} else {
					modelList = dao.query(AggressionLogModel.class, Cnd.cri()
							.asc(sortField), pager);
				}

			} else {
				if (key != null && key.trim().length() != 0) {
					modelList = dao.query(AggressionLogModel.class, Cnd.where(
							"aggressionDetail", "like", "%" + key + "%").desc(
							sortField), pager);
				} else {
					modelList = dao.query(AggressionLogModel.class, Cnd.cri()
							.desc(sortField), pager);
				}

			}
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return modelList;
	}

	public boolean modify(AggressionLogModel model) {
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
