package com.wucl.stdmis.module;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.model.IPFilterModel;
import com.wucl.stdmis.service.IIPFilterService;

/**
 * IP过滤管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/ipfiltermgr")
@IocBean
public class IPFilterModule {
	
	@Inject
	private IIPFilterService iPFilterService = null;
	
	@At("/getBlackIPList")
	public ModelListWithPagingWrapper<IPFilterModel> getBlackIPList(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<IPFilterModel> modelList = iPFilterService.listWithPage(pageIndex, pageSize, sortField, sortOrder, key, 0);
		int size = iPFilterService.count(key, 0);
		return new ModelListWithPagingWrapper<IPFilterModel>(modelList, size);
	}
	
	@At("/getWhiteIPList")
	public ModelListWithPagingWrapper<IPFilterModel> getWhiteIPList(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<IPFilterModel> modelList = iPFilterService.listWithPage(pageIndex, pageSize, sortField, sortOrder, key, 1);
		int size = iPFilterService.count(key, 1);
		return new ModelListWithPagingWrapper<IPFilterModel>(modelList, size);
	}

}
