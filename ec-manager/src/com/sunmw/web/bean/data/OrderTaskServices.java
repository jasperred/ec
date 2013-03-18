package com.sunmw.web.bean.data;

import java.util.List;
import java.util.Map;

import com.sunmw.web.bean.inventory.InventoryServices;

public interface OrderTaskServices {

	/**
	 * 导出需要出库的订单
	 * @param storeId
	 * @return
	 */
	public List exportOrderToWMS(int storeId);
	
	/**
	 * 更新订单状态为'进入WMS'
	 * @param orderNos
	 * @return
	 */
	public String updateOrderWmsStatus(String orderNos);
	
	/**
	 * 更新订单的物流信息
	 * @param logisticsList
	 * @return
	 */
	public String updateOrderLogistics(List<Map> logisticsList);
	
	/**
	 * 得到需要更新淘宝物流状态的订单
	 * @param storeId
	 * @return
	 */
	public List getToTbLogisticsOrder(int storeId);
	
	/**
	 * 更新ERP订单状态为已完成(淘宝物流状态上传成功后)
	 * @param taobaoNos
	 * @return
	 */
	public String updateOrderLogisticsStatus(String taobaoNos);
}
