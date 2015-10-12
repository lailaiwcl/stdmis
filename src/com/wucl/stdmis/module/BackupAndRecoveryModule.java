package com.wucl.stdmis.module;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.wucl.stdmis.util.FileUtil;
import com.wucl.stdmis.util.MysqlUtil;

/**
 * 系统备份和恢复管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@At("/backupandrecoverymgr")
@IocBean
public class BackupAndRecoveryModule {

	private static Log log = Logs.get();

	private String recentSqlFile = "";

	private String recentWorkspaceFile = "";

	private static Properties props = new Properties();
	static {
		try {
			props.load(BackupAndRecoveryModule.class.getClassLoader()
					.getResourceAsStream("backup.properties"));
		} catch (Exception e) {
			log.error(e);
		}
	}

	@At("/backupDatabase")
	@Ok("void")
	public void backupDatabase(HttpServletRequest request,
			ServletContext context) throws IOException {
		String conextRealPath = context.getRealPath("/");
		conextRealPath = FileUtil.normalizeInUnixStyle(conextRealPath);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String outputFileName = "mysql_stdmis-"
				+ format.format(System.currentTimeMillis()) + ".sql";
		String outFileRealPath = conextRealPath
				+ "/WEB-INF/upload/mysqlbackup/" + outputFileName;
		recentSqlFile = outFileRealPath;
		try {
			String remotePath = props.getProperty("backup.remotepath",
					"localhost");
			String databaseName = props.getProperty("backup.databasename");
			String userName = props.getProperty("backup.username", "root");
			String password = props.getProperty("backup.password");
			MysqlUtil.backup("", remotePath,
					databaseName, userName, password, outFileRealPath);
			request.setAttribute("msg", "导出数据库成功。");

		} catch (IOException e) {
			log.error(e);
			request.setAttribute("msg", "导出数据库失败:" + e.getMessage());
			throw new IOException();
		}
	}

	@At("/backupWorkspace")
	@Ok("void")
	public void backupWorkspace(@Param("type") String type,
			HttpServletRequest request, ServletContext context)
			throws IOException {
		String contextRealPath = context.getRealPath("/");
		contextRealPath = FileUtil.normalizeInUnixStyle(contextRealPath);
		final String finalContextPath = contextRealPath;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String outputFileName = "stdmis("
				+ format.format(System.currentTimeMillis()) + ").zip";
		try {
			String backupZipFileDir = props
					.getProperty("workspace.backupzipfiledir");
			if (Strings.isBlank(backupZipFileDir)) {
				throw new IllegalArgumentException(
						"backupZipFileDir can not be null.");
			}
			if (!(new File(backupZipFileDir)).isDirectory()) {
				throw new IllegalArgumentException(
						"backupZipFileDir must be a directory.");
			}
			File zipFile = new File(backupZipFileDir, outputFileName);
			FileFilter filter = new FileFilter() {
				public boolean accept(File pathname) {
					String path = FileUtil.normalizeInUnixStyle(pathname
							.getAbsolutePath());
					if (path.startsWith(finalContextPath + "/upload")
							|| path.startsWith(finalContextPath
									+ "/WEB-INF/upload")) {
						return true;

					}
					return false;
				}
			};
			if ("fully".equalsIgnoreCase(type)) {
				filter = null;
			}
			FileUtil.zip(new File(contextRealPath), zipFile, filter, false);
			recentWorkspaceFile = zipFile.getAbsolutePath();
			request.setAttribute("msg", "工程备份成功。");
		} catch (IOException e) {
			log.error(e);
			throw new IOException();
		}
	}

	@At("/dowloadSqlFile")
	@Ok("void")
	public void dowloadSqlFile(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			FileUtil.downloadFile(new File(recentSqlFile), null, response);
			request.setAttribute("msg", "下载数据库备份文件成功。");
		} catch (Exception e) {
			log.error(e);
			request.setAttribute("msg", "下载数据库文件失败:" + e.getMessage());
		}
	}

	@At("/dowloadWorkspaceFile")
	@Ok("void")
	public void dowloadWorkspaceFile(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			FileUtil
					.downloadFile(new File(recentWorkspaceFile), null, response);
			request.setAttribute("msg", "下载工程备份文件成功。");
		} catch (Exception e) {
			log.error(e);
			request.setAttribute("msg", "下载工程文件失败:" + e.getMessage());
		}
	}

	@At("/loadDatabase")
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:myUpload" })
	@Ok("jsp:/msg.jsp")
	@Fail("jsp:/msg.jsp")
	public void loadDatabase(@Param("sqlFile") File sqlFile,
			HttpServletRequest request, ServletContext context)
			throws IOException {
		String conextRealPath = context.getRealPath("/");
		conextRealPath = FileUtil.normalizeInUnixStyle(conextRealPath);
		// 数据恢复前先备份数据
		backupDatabase(request, context);
		try {
			String remotePath = props.getProperty("load.remotepath",
					"localhost");
			String databaseName = props.getProperty("load.databasename");
			String userName = props.getProperty("load.username", "root");
			String password = props.getProperty("load.password");
			MysqlUtil
					.load("", remotePath,
							databaseName, userName, password, sqlFile
									.getAbsolutePath());
			request.setAttribute("msg", "导入数据文件成功");
		} catch (IOException e) {
			log.error(e);
			request.setAttribute("msg", "导入数据文件失败:" + e.getMessage());
		}
	}

	@At("/recoveryWorkspace")
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:myUpload" })
	@Ok("jsp:/msg.jsp")
	@Fail("jsp:/msg.jsp")
	public void recoveryWorkspace(@Param("workspaceFile") File workspaceFile,
			HttpServletRequest request, ServletContext context)
			throws IOException {
		String conextRealPath = context.getRealPath("/");
		conextRealPath = FileUtil.normalizeInUnixStyle(conextRealPath);
		// 工程重置前先备份数据
		backupWorkspace("user", request, context);
		try {
			FileUtil.unzip(workspaceFile, new File(conextRealPath), null);
			request.setAttribute("msg", "导入数据文件成功");
		} catch (Exception e) {
			log.error(e);
			request.setAttribute("msg", "导入数据文件失败:" + e.getMessage());
		}
	}

}
