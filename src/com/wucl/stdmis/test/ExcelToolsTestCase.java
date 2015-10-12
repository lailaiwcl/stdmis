package com.wucl.stdmis.test;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;

import com.wucl.stdmis.util.ExcelUtil;

public class ExcelToolsTestCase {
	public static void main(String[] args) {
		// Map<String, List<UserModel>> dataMap = new HashMap<String,
		// List<UserModel>>();
		// List<UserModel> taskList = new ArrayList<UserModel>();
		// for (int i = 1; i < 11; i++) {
		// UserModel user = new UserModel();
		// user.setUserID(i);
		// user.setUserName("wucl_" + i);
		// user.setRegisterDate(new Date(System.currentTimeMillis()));
		// user.setUserType(1);
		// user.setUserEmail("lailaiwcl@163.com");
		// user.setUserState(0);
		// user.setNotes("");
		// taskList.add(user);
		// }
		// dataMap.put("user", taskList);
		// ExcelTools.exportToExcelByFreemarker(dataMap,"D:/data","UserInfo_Template.xml","D:/data/output/UserInfo.xls");
		try {
			List<List<List<String>>> list = ExcelUtil.getWorkbookData(
					"d:/data/ClassTemplate.xls", 0, 0);
			List<List<String>> sheet = list.get(0);
			for (int i = 0; i < sheet.size(); i++) {
				for (int j = 0; j < sheet.get(i).size(); j++) {
					System.out.print(sheet.get(i).get(j) + "  ");
				}
				System.out.println();
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
