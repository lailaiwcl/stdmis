package com.wucl.stdmis.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.DaoException;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.wucl.stdmis.model.DictEntryModel;
import com.wucl.stdmis.service.IDictEntryService;

/**
 * 业务字典实体数据层实现
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "dictEntryService")
public class DictEntryService implements IDictEntryService {

	@Inject
	private Dao dao = null;

	public int count(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean delete(int dictEntryID) {
		// TODO Auto-generated method stub
		return false;
	}

	public DictEntryModel getEntryByID(int dictEntryID) {
		DictEntryModel model = null;
		try {
			model = dao.fetch(DictEntryModel.class, Cnd.where("dictEntryID",
					"=", dictEntryID));
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return model;
	}

	public List<DictEntryModel> getEntryByType(String dictTypeID) {
		List<DictEntryModel> modelList = null;
		try {
			modelList = dao.query(DictEntryModel.class, Cnd.where("dictTypeID",
					"=", dictTypeID).asc("sortField"));
		} catch (DaoException e) {
			throw new DaoException(e);
		}
		return modelList;
	}

	public boolean insert(DictEntryModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<DictEntryModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean modify(DictEntryModel model) {
		// TODO Auto-generated method stub
		return false;
	}

}
