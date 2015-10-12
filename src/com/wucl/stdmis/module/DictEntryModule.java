package com.wucl.stdmis.module;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.cache.DictCache;
import com.wucl.stdmis.model.DictEntryModel;
import com.wucl.stdmis.service.IDictEntryService;

/**
 * 业务字典实体管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/dictentrymgr")
@IocBean
public class DictEntryModule {
	private static Log log = Logs.get();

	public static String HAS_LOGIN = "has_login";

	@Inject
	private IDictEntryService dictEntryService = null;

	@Inject
	private DictCache dictCache = null;

	@At("/getModelByType")
	public List<DictEntryModel> getModelByType(@Param("type") String dictTypeID) {
		List<DictEntryModel> modelList = null;
		try {
			modelList = dictCache.getDictByType(dictTypeID);
		} catch (Exception e) {
			log.error("select dict entry error.", e);
		}
		return modelList;
	}

	@At("/save")
	public void save(@Param("data") List<DictEntryModel> modelList,
			HttpServletRequest request) {
		DictEntryModel model = new DictEntryModel();
		System.out.println(modelList.size());
		try {
			if (model.getDictEntryID() <= 0) {
				dictEntryService.insert(model);
			} else {
				dictEntryService.modify(model);
			}
			if (log.isInfoEnabled()) {
				log.info("add dictEntry success.");
			}
		} catch (Exception e) {
			log.error("add dictEntry failed.", e);
		}
	}

	@At("/save")
	public void sa(@Param("data.dictEntryID") int dictEntryID,
			@Param("data.dictEntryName") String dictEntryName,
			@Param("data.dictEntryValue") String dictEntryValue,
			@Param("data.sortField") int sortField,
			@Param("data.dictTypeID") String dictTypeID,
			HttpServletRequest request) {

	}
}
