package com.wucl.stdmis.filter;

import java.sql.Date;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.RequestPath;
import org.nutz.mvc.View;
import org.nutz.mvc.view.JspView;
import org.nutz.mvc.view.ViewWrapper;

import com.wucl.stdmis.model.AggressionLogModel;
import com.wucl.stdmis.service.IAggressionLogService;
import com.wucl.stdmis.util.NetUtil;
/**
 * 系统入侵防护监测过滤器
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "antiInjectionFilter")
public class AntiInjectionFilter implements ActionFilter {

	protected FilterConfig filterConfig = null;
	private static final String IGNORE = "^.+\\.(png|gif|jpg|js|css|jpeg|swf|ico)$";

	private static Pattern ignorePtn;

	private static final String SQL_SENSITIVE = "and|exec|execute|insert|select|delete|update|drop|master|truncate|"
			+ "declare|sitename|net user|xp_cmdshell|create|"
			+ "table|from|grant|group_concat|column_name|"
			+ "information_schema.columns|table_schema|union|where|count(*)|"
			+ "master|--|like|//";

	private static final String SCRIPT_SENSITIVE = "script|input|table|div|button|span|html";

	private static final View view = new ViewWrapper(new JspView("/msg.jsp"), 1);

	static {
		String regx = Strings.sNull(null, IGNORE);
		if (!"null".equalsIgnoreCase(regx)) {
			ignorePtn = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		}
	}

	@Inject
	IAggressionLogService aggressionLogService = null;

	@SuppressWarnings("unchecked")
	public View match(ActionContext actionContext) {
		HttpServletRequest request = actionContext.getRequest();
		// HttpServletResponse response = actionContext.getResponse();
		RequestPath path = Mvcs.getRequestPathObject(request);
		String url = path.getUrl();
		if (null != ignorePtn && ignorePtn.matcher(url).find()) {
			return null;
		}
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			// 得到参数名
			String name = params.nextElement().toString();
			// 得到参数对应值
			String[] value = request.getParameterValues(name);
			for (int i = 0; i < value.length; i++) {
				String requstValue = value[i];
				if (!Strings.isBlank(requstValue)) {
					requstValue = requstValue.toLowerCase();
				} else {
					continue;
				}
				if (sqlValidate(requstValue)) {
					AggressionLogModel model = new AggressionLogModel();
					model
							.setAggressionTime(new Date(System
									.currentTimeMillis()));
					// sql注入
					model.setAggressionType(0);
					model.setRequstUrl(url);
					model.setAggressionIP(NetUtil.getIpAddr(request));
					model.setAggressionDetail("非法sql注入关键字：" + requstValue);
					aggressionLogService.insert(model);
					request.setAttribute("msg", "非法sql注入关键字：" + requstValue);
					return view;
				}
				if (scriptValidate(requstValue)) {
					AggressionLogModel model = new AggressionLogModel();
					model
							.setAggressionTime(new Date(System
									.currentTimeMillis()));
					// script注入
					model.setAggressionType(1);
					model.setRequstUrl(url);
					model.setAggressionIP(NetUtil.getIpAddr(request));
					model.setAggressionDetail("非法script注入关键字：" + requstValue);
					aggressionLogService.insert(model);
					request.setAttribute("msg", "非法script注入关键字：" + requstValue);
					return view;
				}
			}
		}
		return null;
	}

	// 校验
	private static boolean sqlValidate(String str) {
		String[] sqlStrs = SQL_SENSITIVE.split("\\|");
		for (int i = 0; i < sqlStrs.length; i++) {
			if (str.indexOf(sqlStrs[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	private static boolean scriptValidate(String str) {
		String[] scriptStrs = SCRIPT_SENSITIVE.split("\\|");
		for (int i = 0; i < scriptStrs.length; i++) {
			if (str.indexOf(scriptStrs[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

}
