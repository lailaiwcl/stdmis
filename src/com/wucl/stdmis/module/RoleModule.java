package com.wucl.stdmis.module;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.model.RoleModel;
import com.wucl.stdmis.service.IRoleService;

/**
 * 角色管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/rolemgr")
@IocBean
public class RoleModule {

	private static Log log = Logs.get();

	@Inject
	private IRoleService roleService = null;

	@At("/listrole")
	public List<RoleModel> listAllRole() {
		return roleService.listAll();
	}

	@At("/listRolesWithPage")
	public ModelListWithPagingWrapper<RoleModel> listRolesWithPage(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<RoleModel> roleModelList = roleService.listWithPage(pageIndex,
				pageSize, sortField, sortOrder, key);
		int size = roleService.count(key);
		return new ModelListWithPagingWrapper<RoleModel>(roleModelList, size);
	}

	@At("/getRole")
	public SingleModelWrapper<RoleModel> getRole(@Param("id") int roleID,
			HttpServletRequest request) {
		RoleModel role = null;
		try {
			role = roleService.get(roleID);
		} catch (Exception e) {
			log.error(e);
		}
		return new SingleModelWrapper<RoleModel>(role);

	}

	@At("/addRole")
	public void addRole(@Param("::role.") RoleModel roleModel,
			HttpServletRequest request) {
		String ret = "";
		try {
			roleService.insert(roleModel);
			ret = "添加角色成功。";
		} catch (Exception e) {
			ret = "添加角色失败！";
		}
		request.setAttribute("msg", ret);
	}

	@At("/delRole")
	public void delRole(@Param("ids") String ids, HttpServletRequest request) {
		String ret = "";
		if (ids == null || ids.trim().length() == 0) {
			return;
		}
		String[] id = ids.split(",");
		for (String roleID : id) {
			try {
				roleService.delete(Integer.parseInt(roleID));
				ret = "删除角色成功。";
				if (log.isInfoEnabled()) {
					log.infof("the role [%s] delete success.", roleID);
				}
			} catch (Exception e) {
				ret = "删除角色失败！";
				if (log.isInfoEnabled()) {
					log.infof("the role [%s] delete failed.", roleID);
				}
			}

		}
		request.setAttribute("msg", ret);
	}

	@At("/editRole")
	public void editRole(@Param("::obj.") RoleModel role,
			HttpServletRequest request) {
		if (role == null) {
			return;
		}
		String ret = "";
		try {
			roleService.modify(role);
			ret = "保存角色成功。";
			if (log.isInfoEnabled()) {
				log.infof("the role [%d] edite success.", role.getRoleID());
			}
		} catch (Exception e) {
			ret = "保存角色失败！";
			if (log.isInfoEnabled()) {
				log.infof("the role [%d] edite failed.", role.getRoleID());
			}
		}
		request.setAttribute("msg", ret);

	}

}
