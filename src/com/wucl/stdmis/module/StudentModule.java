package com.wucl.stdmis.module;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.wucl.stdmis.model.StudentModel;
import com.wucl.stdmis.service.IStudentService;

/**
 * 学生管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/studentmgr")
@IocBean
public class StudentModule {
	private static Log log = Logs.get();

	public static String HAS_LOGIN = "has_login";

	@Inject
	private IStudentService studentService = null;

	@At("/listWithPage")
	public ModelListWithPagingWrapper<StudentModel> listWithPage(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<StudentModel> modelList = studentService.listWithPage(pageIndex,
				pageSize, sortField, sortOrder, key);
		int size = studentService.count(key);

		return new ModelListWithPagingWrapper<StudentModel>(modelList, size);
	}

	@At("/listWithPageByDeptNo")
	public ModelListWithPagingWrapper<StudentModel> listWithPageByDeptNo(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key,
			@Param("deptNo") String deptNo) {
		List<StudentModel> modelList = studentService.listWithPageByDeptNo(
				pageIndex, pageSize, sortField, sortOrder, key, deptNo);
		int size = studentService.count(key, deptNo);

		return new ModelListWithPagingWrapper<StudentModel>(modelList, size);
	}

	@At("/getModel")
	public Map<String, StudentModel> getModel(@Param("id") String studentNo,
			HttpServletRequest request,ServletContext context) {
		StudentModel model = null;
		try {
			model = studentService.get(studentNo);
			if(model.getPhotoUrl() == null || !(new File(context.getRealPath("/"),model.getPhotoUrl())).exists()){
				model.setPhotoUrl("upload/studentImages/default.jpg");
			}
		} catch (Exception e) {
			log.error(e);
		}
		Map<String, StudentModel> map = new HashMap<String, StudentModel>();
		map.put("model", model);
		return map;
	}

	@At("/add")
	public void addModel(@Param("::model.") StudentModel model,
			HttpServletRequest request) {
		try {
			studentService.insert(model);
			if (log.isInfoEnabled()) {
				log.info("add student success.");
			}
		} catch (Exception e) {
			log.error("add student failed.", e);
		}

	}

	@SuppressWarnings("unchecked")
	@At("/uploadPhoto")
	// @Ok("jsp:/student/uploadPhoto")
	@Ok("void")
	public void uploadImage(ServletContext context, HttpServletRequest request) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		fileUpload.setFileSizeMax(4194304);// 上传文件大小不能超过4M
		fileUpload.setHeaderEncoding("utf-8");
		try {
			// 每一个FileItem对象包含 一个表单域的信息可以通过访问该对象的方法来获得相应的数据
			List<FileItem> items = fileUpload.parseRequest(request);
			String studentNo = "null";
			for (FileItem item : items) {
				// 普通的表单域
				if (item.isFormField()) {
					if ("studentNo".equalsIgnoreCase(item.getFieldName())) {
						studentNo = item.getString();
					}
				}
			}
			for (FileItem item : items) {
				// 文件上传域
				if (!item.isFormField()) {
					String fileName = item.getName();
					String extFileName = fileName.substring(fileName
							.lastIndexOf('.') + 1);
					if (fileName == null || fileName.trim().length() == 0) {
						return;
					}
					if ("jpg".equalsIgnoreCase(extFileName)
							|| "jpeg".equalsIgnoreCase(extFileName)
							|| "png".equalsIgnoreCase(extFileName)
							|| "gif".equalsIgnoreCase(extFileName)
							|| "bmp".equalsIgnoreCase(extFileName)) {
						String contextPath = context.getRealPath("/");
						String appPath = "/upload/studentImages/" + studentNo
								+ "/";
						File photoPath = new File(contextPath + appPath);
						if (!photoPath.exists()) {
							photoPath.mkdirs();
						}
						fileName = System.currentTimeMillis() + "." + extFileName;
						item.write(new File(photoPath, fileName));
						// 更新数据库记录
						StudentModel model = new StudentModel();
						model.setStudentNo(studentNo);
						model.setPhotoUrl(appPath + fileName);
						studentService.modify(model);
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@At("/edit")
	public void editModel(@Param("::model.") StudentModel model,
			HttpServletRequest request) {
		if (model == null) {
			return;
		}
		String ret = "";
		try {
			studentService.modify(model);
			ret = "保存学生信息成功。";
			if (log.isInfoEnabled()) {
				log.infof("the student [%s] edite success.", model
						.getStudentNo());
			}
		} catch (Exception e) {
			ret = "保存学生信息失败！";
			if (log.isInfoEnabled()) {
				log.infof("the student [%s] edite failed.", model
						.getStudentNo());
			}
		}
		request.setAttribute("msg", ret);

	}

	@At("/del")
	public void delModel(@Param("ids") String ids, HttpServletRequest request) {
		String ret = "";
		if (ids == null || ids.trim().length() == 0) {
			return;
		}
		String[] id = ids.split(",");
		for (String studentNo : id) {
			try {
				studentService.delete(studentNo);
				ret = "删除学生成功。";
				if (log.isInfoEnabled()) {
					log.infof("the student [%s] delete success.", studentNo);
				}
			} catch (Exception e) {
				ret = "删除学生失败。";
				log.error(e);
			}

		}
		request.setAttribute("msg", ret);

	}
}
