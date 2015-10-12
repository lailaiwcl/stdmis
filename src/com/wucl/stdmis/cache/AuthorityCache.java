package com.wucl.stdmis.cache;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.nutz.dao.Sqls;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

/**
 * 权限缓存管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public class AuthorityCache {

	/**
	 * 缓存数目
	 */
	public static int cacheAmount = 2;

	/**
	 * 缓存时间
	 */
	//public int cacheTime = 1000 * 60 * 60 * 24 * 30;

	public static Map<Integer, List<String>> cacheMap = new HashMap<Integer, List<String>>();

	public static Queue<Integer> queue = new LinkedBlockingQueue<Integer>(cacheAmount);

	//public static AuthorityCache cache = new AuthorityCache();

	private static Log log = Logs.get();
	
	private static String queueLock = "";
	
	private static String mapLock = "";
	
	public static NutDao dao = null;
	
	static {
		Ioc ioc = new NutIoc(new JsonLoader("ioc.js"));
		dao = ioc.get(NutDao.class, "dao");
	}
	
	public static void reset(){
		if(cacheMap != null){
			cacheMap.clear();
			queue.clear();
			if (log.isInfoEnabled()) {
				log.infof("reset authority cache.");
			}
		}
	}

	public List<String> uptate(final int userID) {
		List<String> userPrivileges = cacheMap.get(userID);
		if (userPrivileges == null) {
			final List<String> currentUserPrivileges = getUserPrivileges(userID);
			if (queue.size() >= cacheAmount) {
				Trans.exec(new Atom() {
					public void run() {
						int head;
						synchronized (queueLock) {
							head = queue.poll();
							queue.add(userID);
						}
						synchronized (mapLock) {
							cacheMap.remove(head);
							cacheMap.put(userID, currentUserPrivileges);
						}
						if (log.isInfoEnabled()) {
							log.infof("removes the head element [%d] of cache queue and cache map,and insert the element [%d] into cache queue and cache map.",
											head, userID);
							log.infof("the current cache queue size [%d],the  current cache map size [%d] .",
									queue.size(), cacheMap.size());
						}
					}
				});
			} else {
				Trans.exec(new Atom() {
					public void run() {
						synchronized (queueLock) {
							queue.add(userID);
						}
						synchronized (mapLock) {
						    cacheMap.put(userID, currentUserPrivileges);
						}
						if (log.isInfoEnabled()) {
							log.infof("inserts the element [%d] into cache queue and cahe map.",userID);
							log.infof("the current cache queue size [%d],the  current cache map size [%d] .",
									queue.size(), cacheMap.size());
						}
					}
				});
			}
			return currentUserPrivileges;
		} else {
			return userPrivileges;
		}
	}

	public List<String> getUserPrivileges(int userID) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("select distinct rs.ResourcesID rid");
		sqlStr
				.append(" from W_UserRole ur,W_RoleInfo r,W_RoleResource rr,W_Resources rs");
		sqlStr.append(" where ur.RoleID = r.RoleID");
		sqlStr.append(" and r.RoleID=rr.RoleID");
		sqlStr.append(" and rr.AutoID=rs.AutoID");
		sqlStr.append(" and r.DelFlag = 0");
		sqlStr.append(" and rs.DelFlag = 0 ");
		sqlStr.append(" and ur.UserID = ");
		sqlStr.append(userID);
		Sql sql = Sqls.create(sqlStr.toString());
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql)
					throws SQLException {
				List<String> list = new ArrayList<String>();
				while (rs.next())
					list.add(rs.getString("rid"));
				return list;
			}
		});
		dao.execute(sql);
		List<String> list = sql.getList(String.class);
		if (log.isInfoEnabled()) {
			log.infof("select the user [%d] of the privileges from database.",userID);
		}
		return list;
	}

	public static void main(String[] args) {
		AuthorityCache a = new AuthorityCache();
		for (String b : a.getUserPrivileges(17)) {
			System.out.println(b);
		}
	}

}
