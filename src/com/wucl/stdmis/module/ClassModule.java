package com.wucl.stdmis.module;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.read.biff.BiffException;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.wucl.stdmis.model.ClassModel;
import com.wucl.stdmis.service.IClassService;
import com.wucl.stdmis.util.ExcelUtil;

/**
 * 班级管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/classmgr")
@IocBean
public class ClassModule {
	private static Log log = Logs.get();

	@Inject
	private IClassService classService = null;

	@At("/listClass")
	public List<ClassModel> listClass() {
		return classService.listAll();
	}

	@At("/importFromExcel")
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:myUpload" })
	@Ok("void")
	public void importExcel(@Param("excelFile") File excelFile,
			@Param("ignoreHead") int beginRow,
			@Param("importOneSheet") int importOneSheet,
			HttpServletRequest request) {
		List<List<List<String>>> workBookData = null;
		int totalCount = 0;
		int successImportCount = 0;
		String ret = "";
		try {
			workBookData = ExcelUtil.getWorkbookData(excelFile, beginRow, 0);
			for (List<List<String>> sheetData : workBookData) {
				totalCount += sheetData.size();
				for (List<String> rowData : sheetData) {
					for (int i = rowData.size(); i < 5; i++) {
						rowData.add("");
					}
					ClassModel classModel = new ClassModel();
					classModel.setClassID(rowData.get(0));
					classModel.setParentClassID(rowData.get(1));
					classModel.setClassName(rowData.get(2));
					classModel.setShortClassName(rowData.get(3));
					classModel.setNotes(rowData.get(4));
					classModel.setDelFlag(0);
					try {
						classService.insert(classModel);
						successImportCount++;
					} catch (Exception e) {
						log.error(e);
					}
				}
				// 如果只导入第一张工作簿，结束循环
				if (importOneSheet == 1) {
					break;
				}
			}
			ret = "共导入 " + totalCount + " 条班级信息数据<br/>成功导入  "
					+ successImportCount + " 条<br/>失败 "
					+ (totalCount - successImportCount) + " 条<br/> 失败信息请查看日志";

			if (log.isInfoEnabled()) {
				log.info(ret);
			}

		} catch (BiffException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return;
	}

	@At("/listClassWithPage")
	public ModelListWithPagingWrapper<ClassModel> listUsers(
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize,
			@Param("sortField") String sortField,
			@Param("sortOrder") String sortOrder, @Param("key") String key) {
		List<ClassModel> classList = classService.listWithPage(pageIndex,
				pageSize, sortField, sortOrder, key);
		int size = classService.count(key);

		return new ModelListWithPagingWrapper<ClassModel>(classList, size);
	}

	@At("/getModel")
	public ClassModel getModel(@Param("id") String classID,
			HttpServletRequest request) {
		ClassModel model = null;
		try {
			model = classService.get(classID);
		} catch (Exception e) {
			log.error(e);
		}
		return model;
	}

}
