package com.wucl.stdmis.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.wucl.stdmis.model.IPFilterModel;
import com.wucl.stdmis.util.NetUtil;

public class VisitorIPFilter implements Filter {
	
	private static Log log = Logs.get();

	private static List<String> blackIPList = new ArrayList<String>();
	private static List<String> whiteIPList = new ArrayList<String>();
	public static boolean onlyAccessFlag = false;
	public static boolean isInitialized = false;

	public static void update() {
		Ioc ioc = new NutIoc(new JsonLoader("ioc.js"));
		Dao dao = ioc.get(NutDao.class, "dao");
		List<IPFilterModel> list = dao.query(IPFilterModel.class, null);;
		for (IPFilterModel iPFilterModel : list) {
			if (iPFilterModel.getIPType() == 0) {
				blackIPList.add(iPFilterModel.getIPAdress());
			} else if (iPFilterModel.getIPType() == 1) {
				whiteIPList.add(iPFilterModel.getIPAdress());
			}
		}
		if (log.isInfoEnabled()) {
			log.infof("the ipfilter data update success.");
		}
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		if (!isInitialized) {
			update();
			isInitialized = true;
		}
		HttpServletRequest request = (HttpServletRequest) arg0;
		String ip = NetUtil.getIpAddr(request);
		if (onlyAccessFlag) {
			if (!whiteIPList.contains(ip)) {
				request.setAttribute("msg", "IP非法，请与系统管理员联系。");
				request.getRequestDispatcher("/msg.jsp").forward(arg0, arg1);
			}
		} else {
			if (blackIPList.contains(ip)) {
				request.setAttribute("msg", "IP被加入了系统黑名单，请与系统管理员联系。");
				request.getRequestDispatcher("/msg.jsp").forward(arg0, arg1);
			}
		}
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
