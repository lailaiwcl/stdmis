package com.wucl.stdmis.module;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.model.TeacherModel;
import com.wucl.stdmis.service.ITeacherService;

/**
 * 教师管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/teachermgr")
@IocBean
public class TeacherModule {

	private static Log log = Logs.get();

	public static String HAS_LOGIN = "has_login";

	@Inject
	private ITeacherService teacherService = null;

	@At("/listWithPage")
	public ModelListWithPagingWrapper<TeacherModel> listWithPage(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<TeacherModel> modelList = teacherService.listWithPage(pageIndex,
				pageSize, sortField, sortOrder, key);
		int size = teacherService.count(key);

		return new ModelListWithPagingWrapper<TeacherModel>(modelList, size);
	}

	@At("/listWithPageAndDeptNo")
	public ModelListWithPagingWrapper<TeacherModel> listWithPageAndDeptNo(
			@Param("key") String key) {
		List<TeacherModel> modelList = teacherService
				.listWithPageAndDeptNo(key);
		int size = teacherService.count(key);

		return new ModelListWithPagingWrapper<TeacherModel>(modelList, size);
	}

	@At("/autocomplete")
	public List<TeacherModel> autoComplete(@Param("key") String key) {
		List<TeacherModel> modelList = teacherService.listWithPage(0, 5, null,
				null, key);
		List<TeacherModel> simpleModelList = new ArrayList<TeacherModel>();
		for (TeacherModel model : modelList) {
			TeacherModel tm = new TeacherModel();
			tm.setTeacherNo(model.getTeacherNo());
			tm.setTeacherName(model.getTeacherName());
			simpleModelList.add(tm);
		}
		return simpleModelList;
	}

	@At("/getModel")
	public TeacherModel getModel(@Param("id") String teacherNo,
			HttpServletRequest request) {
		TeacherModel model = null;
		try {
			model = teacherService.get(teacherNo);
		} catch (Exception e) {
			log.error(e);
		}
		return model;
	}

}
