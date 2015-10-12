package com.wucl.stdmis.module;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.cache.DictCache;
import com.wucl.stdmis.model.DictTypeModel;
import com.wucl.stdmis.service.IDictTypeService;

/**
 * 业务字典类型管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/dicttypemgr")
@IocBean
public class DictTypeModule {

	// private static Log log = Logs.get();

	@Inject
	private IDictTypeService dictTypeService = null;
	
	@Inject
	private DictCache dictCache = null;

	@At("/listWithPage")
	public ModelListWithPagingWrapper<DictTypeModel> listWithPage(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<DictTypeModel> modelList = dictTypeService.listWithPage(pageIndex,
				pageSize, sortField, sortOrder, key);
		int size = dictTypeService.count(key);

		return new ModelListWithPagingWrapper<DictTypeModel>(modelList, size);
	}
	
	@At("/reload")
	public void reload() {
		dictCache.update();
	}

}
