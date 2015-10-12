package com.wucl.stdmis.service;

import java.util.List;

import com.wucl.stdmis.model.ResourcesModel;

/**
 * 资源管理数据层接口
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
public interface IResourcesService {
	public List<ResourcesModel> listAll();

	public ResourcesModel get(int autoID);

	public ResourcesModel getByResourcesID(String resourcesID);

	public List<ResourcesModel> listWithPage(int pageIndex, int pageSize,
			String sortField, String sortOrder, String key);

	public List<ResourcesModel> listWithPageByParent(int pageIndex,
			int pageSize, String sortField, String sortOrder, String key,
			String parentResourcesID);

	public List<ResourcesModel> getChild(String resourcesID);

	public ResourcesModel getParent(String resourcesID);

	public boolean delete(int autoID);

	public boolean modify(ResourcesModel resourcesModel);

	public boolean insert(ResourcesModel resourcesModel);

	public int count(String key);

	public int count(String key, String parentResourcesID);

}
