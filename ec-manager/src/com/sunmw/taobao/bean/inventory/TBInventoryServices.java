package com.sunmw.taobao.bean.inventory;

import java.util.List;
import java.util.Map;

public interface TBInventoryServices {
	
	/**
	 * 淘宝Item或Sku库存更新
	 * @param storeId
	 * @param params
	 */
	public void taobaoItemQuantityUpdate(int storeId, Map params);

	/**
	 * 通过ERP库存更新淘宝库存
	 * @param storeId
	 * @param invList
	 * @param invUpdateFlag
	 */
	public void updateTaobaoInventory(int storeId, List<Map> invList,String invUpdateFlag);
	
	/**
	 * 通过ERP库存更新淘宝库存,ERP中无库存则更新淘宝库存为0
	 * @param storeId
	 * @param invList
	 */
	public void updateTaobaoInventoryForTBItem(int storeId, List<Map> invList);
}
