package com.wucl.stdmis.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

import javax.servlet.http.HttpServletRequest;

public class NetUtil {
	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取远程Mac地址
	 * 
	 * @param ip
	 * @return
	 */
	public static String getMACAddress(String ip) {
		String str = "";
		//String macAddress = "";
		String aa = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream(),"utf-8");
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				// Pattern macPattern = Pattern
				// .compile("([0-9A-Fa-f]{2})(-[0-9A-Fa-f]{2}){5}");
				// Matcher macMatcher;

				if (str != null) {
					aa =aa +"<br/>" +str;
					String head = "MAC Address";
//					String head1 = "MAC Address";
//					String head2 = "MAC 地址";
//					if (str.indexOf(head1) > 0) {
//						head = head1;
//					}
//					if (str.indexOf(head2) > 0) {
//						head = head2;
//					}
					if (str.indexOf(head) > 1) {
						System.out.println(str);
//						macAddress = str.substring(
//								str.indexOf(head.length()) + 3, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return aa;
	}

	/**
	 * 获取本机Mac地址，地址返回格式xx-xx-xx-xx-xx
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getLocalMACAddress() throws Exception {
		InetAddress ia = InetAddress.getLocalHost();// 获取本地IP对象
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			String s = Integer.toHexString(mac[i] & 0xFF);
			sb.append(s.length() == 1 ? 0 + s : s);
		}

		return sb.toString().toUpperCase();
	}

}
