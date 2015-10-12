package com.wucl.stdmis.test;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import com.wucl.stdmis.service.IUserRoleService;
import com.wucl.stdmis.service.impl.UserRoleService;

public class MainTest {

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		// Ioc ioc = new NutIoc(new JsonLoader("ioc.js"));
		// SimpleDataSource dataSource = ioc.get(SimpleDataSource.class,
		// "dataSource");
		// NutDao dao = ioc.get(NutDao.class, "dao");
		// System.out.println(dataSource);
		// ResourcesModel userModel = null;
		// try {
		// userModel = dao.fetch(ResourcesModel.class, Cnd.where("ResourcesID",
		// "=", 1));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println(userModel.getResourcesName());
		// System.out.println(dao.query(ResourcesModel.class,
		// Cnd.where("delFlag", "=", 0)).size());

		String[] loaderStr = { "*org.nutz.ioc.loader.json.JsonLoader",
				"ioc.js", "upload.json",
				"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
				"com.wucl.stdmis" };
		Ioc ioc = new NutIoc(new ComboIocLoader(loaderStr));
		IUserRoleService userRoleService = ioc.get(UserRoleService.class,
				"userRoleService");
		System.out.println(userRoleService.get(17).get(0).getRoleID());
		// System.out.println(Strings.isEmpty(" "));

	}

}
