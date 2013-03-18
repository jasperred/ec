package com.sunmw.web.bean.in;

import java.util.Map;

public interface InventoryInServices {
	
	/**
	 * 入库单查询
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchInventoryInOrder(Map param, int currentPage, int pageRow);
	
	/**
	 * 入库单信息，不包括明细
	 * @param orderHeadId
	 * @return
	 */
	public Map getInventoryInInfo(int orderHeadId);
	
	/**
	 * 入库单明细
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map getInventoryInOrderItem(Map param, int currentPage, int pageRow);

}
