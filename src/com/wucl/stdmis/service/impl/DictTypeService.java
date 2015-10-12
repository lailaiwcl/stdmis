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

import com.wucl.stdmis.model.DictTypeModel;
import com.wucl.stdmis.service.IDictTypeService;

/**
 * 业务字典类型数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "dictTypeService")
public class DictTypeService implements IDictTypeService {

	@Inject
	private Dao dao = null;

	public int count(String key) {
		Cnd cnd = null;
		if (key == null || "".equals(key.trim())) {
			cnd = null;
		} else {
			cnd = Cnd.where("dictTypeName", "like", "%" + key + "%");
		}
		int count;
		try {
			count = dao.count(DictTypeModel.class, cnd);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return count;
	}

	public boolean delete(String dictTypeID) {
		if (Strings.isBlank(dictTypeID)) {
			return false;
		}
		try {
			dao.delete(DictTypeModel.class, dictTypeID);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return true;
	}

	public DictTypeModel get(String dictTypeID) {
		DictTypeModel model = null;
		try {
			model = dao.fetch(DictTypeModel.class, Cnd.where("dictTypeID", "=",
					dictTypeID));
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return model;
	}

	public boolean insert(DictTypeModel model) {
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

	public List<DictTypeModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		List<DictTypeModel> modelList = new ArrayList<DictTypeModel>();
		if (pageSize <= 0) {
			pageSize = 20;
		}
		Pager pager = new Pager();
		pager.setPageSize(pageSize);
		pager.setPageNumber(pageIndex + 1);
		if (sortField == null || sortField.trim().length() == 0) {
			sortOrder = "desc";
			sortField = "dictTypeID";
		}
		try {
			if ("asc".equalsIgnoreCase(sortOrder)) {
				if (!Strings.isBlank(key)) {
					modelList = dao.query(DictTypeModel.class, Cnd.where(
							"dictTypeName", "like", "%" + key + "%").asc(
							sortField), pager);
				} else {
					modelList = dao.query(DictTypeModel.class, Cnd.orderBy()
							.asc(sortField), pager);
				}

			} else {
				if (!Strings.isBlank(key)) {
					modelList = dao.query(DictTypeModel.class, Cnd.where(
							"dictTypeName", "like", "%" + key + "%").desc(
							sortField), pager);
				} else {
					modelList = dao.query(DictTypeModel.class, Cnd.orderBy()
							.desc(sortField), pager);
				}

			}
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return modelList;
	}

	public boolean modify(DictTypeModel model) {
		if (model == null) {
			return false;
		}
		try {
			dao.updateIgnoreNull(model);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return true;
	}

	public List<DictTypeModel> listAll() {
		List<DictTypeModel> modelList = new ArrayList<DictTypeModel>();
		try {
			modelList = dao.query(DictTypeModel.class, null);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return modelList;
	}

}
