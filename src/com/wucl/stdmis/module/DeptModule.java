package com.wucl.stdmis.module;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.model.DeptModel;
import com.wucl.stdmis.service.IDeptService;

/**
 * 部门管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/deptmgr")
@IocBean
public class DeptModule {
	private static Log log = Logs.get();

	@Inject
	private IDeptService deptService = null;

	@At("/listAll")
	public List<DeptModel> listClass() {
		return deptService.listAll();
	}

	@At("/listWithPage")
	public ModelListWithPagingWrapper<DeptModel> listWithPage(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<DeptModel> modelList = deptService.listWithPage(pageIndex,
				pageSize, sortField, sortOrder, key);
		int size = deptService.count(key);

		return new ModelListWithPagingWrapper<DeptModel>(modelList, size);
	}

	@At("/listByDeptNo")
	public List<DeptModel> listByDeptNo() {
		return deptService.listAll();
	}

	@At("/getChildModel")
	public ModelListWithPagingWrapper<DeptModel> getChildModel(
			@Param("key") String parentDeptNo, HttpServletRequest request) {
		List<DeptModel> modelList = null;
		try {
			modelList = deptService.getChild(parentDeptNo);
		} catch (Exception e) {
			log.error(e);
		}
		return new ModelListWithPagingWrapper<DeptModel>(modelList, 0);
	}

	@At("/getModel")
	public DeptModel getModel(@Param("id") String deptNo,
			HttpServletRequest request) {
		DeptModel model = null;
		try {
			model = deptService.get(deptNo);
		} catch (Exception e) {
			log.error(e);
		}
		return model;
	}
}
