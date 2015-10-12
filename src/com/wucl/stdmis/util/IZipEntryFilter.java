
package com.wucl.stdmis.util;

import java.util.zip.ZipEntry;

/**
 * zipEntry过滤器接口
 *
 * @author wucl(lailaiwcl@gmail.com)
 *
 */
public interface IZipEntryFilter {
	boolean accept(ZipEntry zipEntry);
}