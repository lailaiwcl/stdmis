package com.wucl.stdmis.module;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.model.OperationModel;
import com.wucl.stdmis.service.IOperationService;

/**
 * 系统操作日志管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/operationmgr")
@IocBean
public class OperationModule {

	@Inject
	IOperationService operationService = null;

	@At("/listWithPage")
	public ModelListWithPagingWrapper<OperationModel> listWithPage(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<OperationModel> modelList = operationService.listWithPage(
				pageIndex, pageSize, sortField, sortOrder, key);
		int size = operationService.count(key);

		return new ModelListWithPagingWrapper<OperationModel>(modelList, size);
	}
}
