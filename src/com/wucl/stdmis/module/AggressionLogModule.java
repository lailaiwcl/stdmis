package com.wucl.stdmis.module;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.model.AggressionLogModel;
import com.wucl.stdmis.service.IAggressionLogService;

/**
 * 入侵防护日志管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/aggressionlogmgr")
@IocBean
public class AggressionLogModule {

	@Inject
	private IAggressionLogService aggressionLogService = null;

	@At("/listWithPage")
	public ModelListWithPagingWrapper<AggressionLogModel> listWithPage(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<AggressionLogModel> modelList = aggressionLogService.listWithPage(
				pageIndex, pageSize, sortField, sortOrder, key);
		int size = aggressionLogService.count(key);

		return new ModelListWithPagingWrapper<AggressionLogModel>(modelList,
				size);
	}
}
