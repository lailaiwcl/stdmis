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

import com.wucl.stdmis.model.IPFilterModel;
import com.wucl.stdmis.service.IIPFilterService;

/**
 * IP访问规则管理数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "iPFilterService")
public class IPFilterService implements IIPFilterService {

	@Inject
	private Dao dao = null;

	public int count(String key, int type) {
		Cnd cnd = null;
		if (!Strings.isBlank(key)) {
			cnd = Cnd.where("iPType", "=", type).and("iPAdress", "like",
					"%" + key + "%");
		}
		int count;
		try {
			count = dao.count(IPFilterModel.class, cnd);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return count;
	}

	public boolean delete(int id) {
		try {
			dao.delete(IPFilterModel.class, id);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return true;
	}

	public IPFilterModel get(int id) {
		IPFilterModel model = null;
		try {
			model = dao.fetch(IPFilterModel.class, Cnd.where("iPFilterID", "=",
					id));
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return model;
	}

	public boolean insert(IPFilterModel model) {
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

	public List<IPFilterModel> listAll(int type) {
		List<IPFilterModel> modelList = new ArrayList<IPFilterModel>();
		Cnd cnd = null;
		if (type != -1) {
			cnd = Cnd.where("iPType", "=", type);
		}
		try {
			modelList = dao.query(IPFilterModel.class, cnd);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return modelList;
	}

	public List<IPFilterModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key, int type) {
		List<IPFilterModel> modelList = new ArrayList<IPFilterModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "desc";
			sortField = "iPFilterID";
		}
		try {
			if ("asc".equalsIgnoreCase(sortOrder)) {
				if (key != null && key.trim().length() != 0) {
					modelList = dao.query(IPFilterModel.class, Cnd.where(
							"iPType", "=", type).and("iPAdress", "like",
							"%" + key + "%").asc(sortField), pager);
				} else {
					modelList = dao.query(IPFilterModel.class, Cnd.where(
							"iPType", "=", type).asc(sortField), pager);
				}

			} else {
				if (key != null && key.trim().length() != 0) {
					modelList = dao.query(IPFilterModel.class, Cnd.where(
							"iPType", "=", type).and("iPAdress", "like",
							"%" + key + "%").desc(sortField), pager);
				} else {
					modelList = dao.query(IPFilterModel.class, Cnd.where(
							"iPType", "=", type).desc(sortField), pager);
				}

			}
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return modelList;
	}

	public boolean modify(IPFilterModel model) {
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
