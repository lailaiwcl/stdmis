package com.wucl.stdmis.test;

import java.lang.reflect.Field;

public class ReflectTestCase {
	private String aa;
	private int bb;
	public static void main(String[] args) throws Exception, IllegalAccessException {
		Field[] field = ReflectTestCase.class.getDeclaredFields();
		//for (int i = 0; i < field.length; i++) {
			field[0].setAccessible(true);
			field[0].set(String.class, "11");
		//}
	}
	public String getAa() {
		return aa;
	}
	public void setAa(String aa) {
		this.aa = aa;
	}
	public int getBb() {
		return bb;
	}
	public void setBb(int bb) {
		this.bb = bb;
	}

}
