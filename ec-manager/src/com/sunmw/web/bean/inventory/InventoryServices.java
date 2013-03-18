package com.sunmw.web.bean.inventory;

import java.util.List;
import java.util.Map;

public interface InventoryServices {
	
	/**
	 * 库存处理,库存和库存日志同步处理
	 * @param skuList
	 * @param allowNegativeInv
	 * @return
	 */
	public Map inventoryProcess(Map param);
	
	/**
	 * 清空库存和库存日志
	 * @param storeId
	 */
	public void clearInventory(int storeId);
	
	/**
	 * 根据店铺得到仓库ID
	 * @param storeId
	 * @return
	 */
	public Integer getWhId(int storeId);
	
	/**
	 * 根据店铺得到需要更新到淘宝的库存
	 * @param storeId
	 * @return
	 */
	public List<Map> getTbUpdateInventory(int storeId);
	
	/**
	 * 处理淘宝订单的库存
	 * @param storeId
	 */
	public void tbOrderInventoryProcess(int storeId);
	
	/**
	 * 查询库存
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchInventory(Map param, int currentPage, int pageRow);
	
	/**
	 * 根据库存规则更新库存，以仓库为单位，更新到各个店铺
	 * 库存更新到UnitSku表中
	 * @param param
	 * @return
	 */
	public Map updateInvToUnitSku(Map param);
	
	/**
	 * 获得店铺的可更新库存
	 * @param param
	 * @return
	 */
	public Map getAvailableInvOfShop(Map param);
	
	/**
	 * 更新店铺库存标记
	 * @param param
	 * @return
	 */
	public Map updateInvFlagOfShop(Map param);

}
