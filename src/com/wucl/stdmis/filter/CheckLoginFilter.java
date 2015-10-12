package com.wucl.stdmis.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.RequestPath;

import com.wucl.stdmis.module.UserModule;

/**
 * 系统认证监测过滤器
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public class CheckLoginFilter implements Filter {
	protected FilterConfig filterConfig = null;
	private static final String IGNORE = "^.+\\.(png|gif|jpg|js|css|jpeg|swf|ico)$";

	private Pattern ignorePtn;

	public void init(FilterConfig config) throws ServletException {
		String regx = Strings.sNull(config.getInitParameter("ignore"), IGNORE);
		if (!"null".equalsIgnoreCase(regx)) {
			ignorePtn = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		}
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		// 如果没有登录过，则回到登录界面
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpSession session = request.getSession();
		if (session != null
				&& session.getAttribute(UserModule.HAS_LOGIN) != null) {
			chain.doFilter(arg0, arg1);
		} else {
			// 如果是登录请求或登录界面，则忽略，否则回到登录界面
			RequestPath path = Mvcs.getRequestPathObject(request);
			String url = path.getUrl();
			if (null == ignorePtn || !ignorePtn.matcher(url).find()) {
				if (url.indexOf("login") != -1) {
					chain.doFilter(arg0, arg1);
				} else {
					request.getRequestDispatcher("/login.jsp").forward(arg0,
							arg1);
				}
			} else {
				chain.doFilter(arg0, arg1);
			}
		}
	}
}
