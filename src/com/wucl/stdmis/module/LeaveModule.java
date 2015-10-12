package com.wucl.stdmis.module;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.model.LeaveModel;
import com.wucl.stdmis.service.ILeaveService;

/**
 * 请假管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/leavemgr")
@IocBean
public class LeaveModule {
	private static Log log = Logs.get();

	public static String HAS_LOGIN = "has_login";

	@Inject
	private ILeaveService leaveService = null;

	@At("/getByUserID")
	public ModelListWithPagingWrapper<LeaveModel> getByUserID(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key,
			@Param("userName") String userName) {
		List<LeaveModel> modelList = leaveService.getByUserID(pageIndex,
				pageSize, sortField, sortOrder, "senderUserName", userName);
		int size = leaveService.count("senderUserName", userName);

		return new ModelListWithPagingWrapper<LeaveModel>(modelList, size);
	}

	@At("/getByReceiverUserID")
	public ModelListWithPagingWrapper<LeaveModel> getByReceiverUserID(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key,
			@Param("receiverUserName") String receiverUserName) {
		List<LeaveModel> modelList = leaveService.getByReceiverUserID(
				pageIndex, pageSize, sortField, sortOrder, "receiverUserName",
				receiverUserName);
		int size = leaveService.count("receiverUserName", receiverUserName);

		return new ModelListWithPagingWrapper<LeaveModel>(modelList, size);
	}

	@At("/applyLeave")
	public void applyLeave(@Param("::model.") LeaveModel model,
			HttpServletRequest request) {
		model.setApplyTime(new Date(System.currentTimeMillis()));
		try {
			leaveService.insert(model);
		} catch (Exception e) {
			log.error(e);
		}
	}

	@At("/getModel")
	public Map<String, LeaveModel> getModel(@Param("id") int leaveID,
			HttpServletRequest request) {
		LeaveModel model = null;
		try {
			model = leaveService.getByID(leaveID);
		} catch (Exception e) {
			log.error(e);
		}
		Map<String, LeaveModel> map = new HashMap<String, LeaveModel>();
		map.put("model", model);
		return map;
	}

	@At("/edit")
	public void editModel(@Param("::model.") LeaveModel model,
			HttpServletRequest request) {
		if (model == null) {
			return;
		}
		try {
			leaveService.modify(model);
			if (log.isInfoEnabled()) {
				log.infof("the leave [%s] verify success.", model.getLeaveID());
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@At("/del")
	public void delModel(@Param("ids") String ids, HttpServletRequest request) {
		if (ids == null || ids.trim().length() == 0) {
			return;
		}
		String[] id = ids.split(",");
		for (String leaveID : id) {
			try {
				leaveService.delete(Integer.parseInt(leaveID));
				if (log.isInfoEnabled()) {
					log.infof("the student [%s] delete success.", leaveID);
				}
			} catch (Exception e) {
				log.error(e);
			}

		}
	}
}
