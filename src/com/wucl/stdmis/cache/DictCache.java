package com.wucl.stdmis.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.wucl.stdmis.model.DictEntryModel;
import com.wucl.stdmis.model.DictTypeModel;
import com.wucl.stdmis.service.IDictEntryService;
import com.wucl.stdmis.service.IDictTypeService;
/**
 * 业务字典缓存管理模块
 * 
 * @author wucl(lailaiwcl@gmail.com)
 * 
 */
@IocBean(name = "dictCache")
public class DictCache {

	private static Log log = Logs.get();

	@Inject
	private IDictTypeService dictTypeService = null;

	@Inject
	private IDictEntryService dictEntryService = null;

	private static Map<String, List<DictEntryModel>> map = new HashMap<String, List<DictEntryModel>>();
	
	private boolean isInitialized = false;

	public void update() {
		synchronized (DictCache.class) {
			map.clear();
		}
		List<DictTypeModel> modelList = dictTypeService.listAll();
		for (DictTypeModel model : modelList) {
			String dictTypeID = model.getDictTypeID();
			synchronized (DictCache.class) {
				map.put(dictTypeID, dictEntryService.getEntryByType(dictTypeID));
			}
		}
		if (log.isInfoEnabled()) {
			log.infof("the dictcache update success.");
		}
	}
	
	public Map<String, List<DictEntryModel>> getDict(){
		if(!isInitialized){
			update();
			isInitialized = true;
			if (log.isInfoEnabled()) {
				log.infof("the dictcache initialized success.");
			}
		}
		return map;
	}
	
	public List<DictEntryModel> getDictByType(String dictType){
		if(!isInitialized){
			update();
			isInitialized = true;
			if (log.isInfoEnabled()) {
				log.infof("the dictcache initialized success.");
			}
		}
		return map.get(dictType);
	}
}
