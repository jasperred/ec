package com.sunmw.web.bean.warehouse.base;

import java.util.Map;

public interface WarehouseBaseServices {

	/**
	 * 查询仓库
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchWarehouse(Map param, int currentPage, int pageRow);
	
	/**
	 * 保存仓库
	 * @param param
	 * @return
	 */
	public Map saveWarehouse(Map param);
	
	/**
	 * 仓库信息
	 * @param param
	 * @return
	 */
	public Map warehouseInfo(Map param);
	
	/**
	 * 删除仓库
	 * @param taskDefineId
	 * @return
	 */
	public Map deleteWarehouse(Map param);
}
