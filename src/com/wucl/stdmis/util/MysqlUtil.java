package com.wucl.stdmis.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.nutz.lang.Strings;

/**
 * mysql操作工具类
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public class MysqlUtil {

	/**
	 * 备份数据库到SQL
	 * 
	 * @param commadPath
	 *            命令行路径
	 * @param hostName
	 *            主机名称
	 * @param dataBaseName
	 *            数据库名称
	 * @param username
	 *            数据库用户名
	 * @param password
	 *            数据库密码
	 * @param outSqlFile
	 *            输出文件路径
	 * @throws IOException
	 */
	public static void backup(String commadPath, String hostName,
			String dataBaseName, String username, String password,
			String outSqlFile) throws IOException {
		// 对路径中的空格处理
		//commadPath = commadPath.replace(" ", "\" \"");
		outSqlFile = outSqlFile.replace(" ", "\" \"");
		if (Strings.isBlank(hostName)) {
			hostName = "127.0.0.1";
		}
		String command = null;
		StringBuilder sb = new StringBuilder();
		sb.append(commadPath);
		sb.append("mysqldump ");
		sb.append("-u");
		sb.append(username);
		sb.append(" ");
		sb.append("-p");
		sb.append(password);
		sb.append(" ");
		sb.append("-h ");
		sb.append(hostName);
		sb.append(" ");
		sb.append("--opt ");
		sb.append(dataBaseName);
		sb.append(" > ");
		sb.append(outSqlFile);
		command = sb.toString();
		String osType = System.getProperty("os.name").toLowerCase(Locale.US);
		if (osType.indexOf("windows") >= 0) {
			Runtime.getRuntime().exec("cmd /c " + command);
		} else {
			Runtime.getRuntime().exec(new String[] { "sh", "-c", command });
		}
	}

	/**
	 * 
	 * @param commadPath
	 *            命令行路径
	 * @param hostName
	 *            主机名称
	 * @param dataBaseName
	 *            数据库名称
	 * @param username
	 *            数据库用户名
	 * @param password
	 *            数据库密码
	 * @param sqlFile
	 *            加载sql文件路径
	 * @throws IOException
	 */
	public static void load(String commadPath, String hostName,
			String dataBaseName, String username, String password,
			String sqlFile) throws IOException {
		File file = new File(sqlFile);
		if (file == null || !file.isFile()) {
			throw new FileNotFoundException();
		}
		// 对路径中的空格处理
		commadPath = commadPath.replace(" ", "\" \"");
		String command = commadPath + "mysql -u" + username + " -p"
				+ password + " " + dataBaseName + " < " + sqlFile;
		try {
			Process process = null;
			String osType = System.getProperty("os.name").toLowerCase(Locale.US);
			if (osType.indexOf("windows") >= 0) {
				process = Runtime.getRuntime().exec("cmd /c " + command);
			} else {
				process = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });
			}
			OutputStream out = process.getOutputStream();
			String line = null;
			String outStr = null;
			StringBuffer sb = new StringBuffer("");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "utf8"));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\r\n");
			}
			outStr = sb.toString();
			OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
			writer.flush();
			out.close();
			br.close();
			writer.close();
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedEncodingException(e.getMessage());
		} catch (IOException e) {
			throw new IOException(e);
		}

	}

	public static void main(String[] args) {
		try {
			// backup("D:/Program Files", "localhost", "stdmis", "root", "root",
			// "d:/stdmis.sql");
			load("D:/Program Files/", "localhost", "testload", "root", "root",
					"D:/Program Files/mysql_stdmis(20140531143501).sql");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
