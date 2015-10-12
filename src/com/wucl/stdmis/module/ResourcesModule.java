package com.wucl.stdmis.module;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.model.ResourcesModel;
import com.wucl.stdmis.service.IResourcesService;

/**
 * 资源管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/resourcesmgr")
@IocBean
public class ResourcesModule {
	private static Log log = Logs.get();

	@Inject
	private IResourcesService resourcesService = null;

	@At("/listAll")
	public List<ResourcesModel> listAll() {
		return resourcesService.listAll();
	}

	@At("/listWithPage")
	public ModelListWithPagingWrapper<ResourcesModel> listWithPage(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<ResourcesModel> modelList = resourcesService.listWithPage(
				pageIndex, pageSize, sortField, sortOrder, key);
		int size = resourcesService.count(key);

		return new ModelListWithPagingWrapper<ResourcesModel>(modelList, size);
	}

	@At("/listWithPageByParent")
	public ModelListWithPagingWrapper<ResourcesModel> listWithPageByParent(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key,
			@Param("parentResourcesID") String parentResourcesID) {
		List<ResourcesModel> modelList = resourcesService.listWithPageByParent(
				pageIndex, pageSize, sortField, sortOrder, key,
				parentResourcesID);
		int size = resourcesService.count(key, parentResourcesID);

		return new ModelListWithPagingWrapper<ResourcesModel>(modelList, size);
	}

	@At("/getChildModel")
	public ModelListWithPagingWrapper<ResourcesModel> getChildModel(
			@Param("key") String resourcesID, HttpServletRequest request) {
		List<ResourcesModel> modelList = null;
		try {
			modelList = resourcesService.getChild(resourcesID);
		} catch (Exception e) {
			log.error(e);
		}
		return new ModelListWithPagingWrapper<ResourcesModel>(modelList, 0);
	}

	@At("/getModel")
	public ResourcesModel getModel(@Param("id") int autoID,
			HttpServletRequest request) {
		ResourcesModel model = null;
		try {
			model = resourcesService.get(autoID);
		} catch (Exception e) {
			log.error(e);
		}
		return model;
	}

	@At("/getByResourcesID")
	public ResourcesModel getByResourcesID(
			@Param("resourcesID") String resourcesID) {
		ResourcesModel model = null;
		try {
			model = resourcesService.getByResourcesID(resourcesID);
		} catch (Exception e) {
			log.error(e);
		}
		return model;
	}

}
