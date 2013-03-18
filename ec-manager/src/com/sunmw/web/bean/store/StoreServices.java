package com.sunmw.web.bean.store;

import java.util.Map;

public interface StoreServices {

	/**
	 * 查询店铺
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchStore(Map param, int currentPage, int pageRow);
	
	/**
	 * 保存店铺
	 * @param param
	 * @return
	 */
	public Map saveStore(Map param);
	
	/**
	 * 店铺信息
	 * @param param
	 * @return
	 */
	public Map storeInfo(Map param);
	
	/**
	 * 删除店铺
	 * @param taskDefineId
	 * @return
	 */
	public Map deleteStore(Map param);
}
