package com.wucl.stdmis;

import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.wucl.stdmis.filter.AntiInjectionFilter;
import com.wucl.stdmis.filter.OperationRecorderFilter;

/**
 * Nutz框架入口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@Modules(scanPackage = true)
@Ok("json")
@Fail("json")
@Filters( {
		@By(type = AntiInjectionFilter.class, args = { "ioc:antiInjectionFilter" }),
		@By(type = OperationRecorderFilter.class, args = { "ioc:operationRecorderFilter" }) })
@Encoding(input = "UTF-8", output = "UTF-8")
@IocBy(type = ComboIocProvider.class, args = {
		"*org.nutz.ioc.loader.json.JsonLoader", "ioc.js", "upload.json",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
		"com.wucl.stdmis" })
public class MainModule {

}
