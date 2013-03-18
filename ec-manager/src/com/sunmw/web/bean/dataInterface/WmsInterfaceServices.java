package com.sunmw.web.bean.dataInterface;

import java.util.Map;

public interface WmsInterfaceServices {
	
	/**
	 * 导出发货指示
	 * @param param
	 * @return
	 */
	public Map exportDeliveryRequest(Map param);
	
	/**
	 * 导入发货实绩
	 * @param param
	 * @return
	 */
	public Map importDeliveryResult(Map param);
	
	/**
	 * 导出退货指示
	 * @param param
	 * @return
	 */
	public Map exportReturnRequest(Map param);
	
	/**
	 * 导入退货实绩
	 * @param param
	 * @return
	 */
	public Map importReturnResult(Map param);
	
	/**
	 * 导入库存
	 * @param param
	 * @return
	 */
	public Map importInventory(Map param);
	
	/**
	 * 导出商品信息
	 * @param param
	 * @return
	 */
	public Map exportItemMaster(Map param);
}
