package com.wucl.stdmis.module;

import java.util.ArrayList;
import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.cache.AuthorityCache;
import com.wucl.stdmis.model.ResourcesModel;
import com.wucl.stdmis.model.RoleResourcesModel;
import com.wucl.stdmis.service.IResourcesService;
import com.wucl.stdmis.service.IRoleResourcesService;
/**
 * 角色资源管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/roleResourcesMgr")
@IocBean
public class RoleResourcesModule {

	private static Log log = Logs.get();

	@Inject
	private IRoleResourcesService roleResourcesService = null;
	
	@Inject
	private IResourcesService resourcesService = null;


	@At("/addRoleResourcesModel")
	public void addRoleResourcesModel(@Param("roleID") int roleID,
			@Param("autoIDs") String autoIDs) {
		if (autoIDs == null || autoIDs.trim().length() == 0) {
			return;
		}
		String[] ids = autoIDs.split(",");
		for (String autoID : ids) {
			RoleResourcesModel roleResourcesModel = new RoleResourcesModel();
			roleResourcesModel.setRoleID(roleID);
			roleResourcesModel.setAutoID(Integer.parseInt(autoID));
			try {
				roleResourcesService.insert(roleResourcesModel);
				AuthorityCache.reset();
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	@At("/delUserRoleByRoleID")
	public void delUserRoleByRoleID(@Param("roleID") int roleID) {
		try {
			roleResourcesService.delete(roleID);
			AuthorityCache.reset();
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	@At("/getResourcesByRoleID")
	public List<ResourcesModel> getResourcesByRoleID(int roleID) {
		List<RoleResourcesModel> roleResourcesModelList = roleResourcesService.get(roleID);
		List<ResourcesModel> resourcesModelList = new ArrayList<ResourcesModel>();
		for (RoleResourcesModel roleResourcesModel : roleResourcesModelList) {
			ResourcesModel resourcesModel = resourcesService.get(roleResourcesModel.getAutoID());
			resourcesModel.setNotes("");
			resourcesModel.setRequestUrl("");
			resourcesModelList.add(resourcesModel);
		}
		return resourcesModelList;
	}

}
