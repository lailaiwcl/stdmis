package com.wucl.stdmis.module;
/**
 * json格式包装类,包装单个模型
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public class SingleModelWrapper<T> {
	private T obj;

	public SingleModelWrapper(T obj) {
		this.obj = obj;
	}

	public SingleModelWrapper() {
		super();
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

}
