package com.wucl.stdmis.util;

import javax.servlet.ServletContext;

public class FileUploadPathUtil {

	private ServletContext context;

	public String getPath(String path) {
		return context.getRealPath(path);
	}
}
