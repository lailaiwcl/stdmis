package com.wucl.stdmis.module;

import java.util.List;
/**
 * json格式包装类,封装分页信息
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public class ModelListWithPagingWrapper<T> {
	private int total;
	private List<T> data = null;

	public ModelListWithPagingWrapper(List<T> modelList, int total) {
		this.data = modelList;
		this.total = total;
	}

	public ModelListWithPagingWrapper(List<T> modelList) {
		this.data = modelList;
		this.total = modelList.size();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
