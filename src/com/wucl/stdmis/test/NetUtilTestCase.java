package com.wucl.stdmis.test;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import com.wucl.stdmis.util.NetUtil;

@At("/net")
@IocBean
public class NetUtilTestCase {
	@At("/getIP")
	public void importExcel(HttpServletRequest request,HttpServletResponse response) {
		String ip = NetUtil.getIpAddr(request);
		String mac = NetUtil.getMACAddress(ip);
		try {
			Writer out = response.getWriter();
			out.write("ip = " + ip + "          ");
			out.write("mac = <br/>" + mac);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
