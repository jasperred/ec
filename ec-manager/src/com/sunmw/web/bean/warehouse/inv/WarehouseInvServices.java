package com.sunmw.web.bean.warehouse.inv;

import java.util.Map;

public interface WarehouseInvServices {
	
	/**
	 * 库存查询
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchInventory(Map param, int currentPage, int pageRow);

}
