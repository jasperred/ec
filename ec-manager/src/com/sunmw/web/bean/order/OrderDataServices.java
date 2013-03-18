package com.sunmw.web.bean.order;

import java.util.Map;

public interface OrderDataServices {
	
	/**
	 * 插入一个空订单
	 * @param origOrderNo
	 */
	public void insertNullOrder(String origOrderNo,String prefix);
	
	/**
	 * 更新订单信息
	 * @param param
	 */
	public void updateOrder(Map param,Integer storeId);

}
