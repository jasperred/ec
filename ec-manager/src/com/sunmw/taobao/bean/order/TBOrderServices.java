package com.sunmw.taobao.bean.order;

import java.util.List;
import java.util.Map;

public interface TBOrderServices {

	/**
	 * 增量下载订单
	 * @param storeId
	 * @param params
	 */
	public Map taobaoTradesSoldIncrementGet(Map params);
	/**
	 * 增量下载订单
	 * @param storeId
	 * @param params
	 */
	public Map taobaoTradesSoldIncrementVGet(Map params);
	
	/**
	 * 全部订单
	 * @param storeId
	 * @param params
	 */
	public Map taobaoTradesSoldGet(Map params);
	
	/**
	 * 订单的全部信息
	 * @param storeId
	 * @param params
	 */
	public Map taobaoTradeFullinfoGet(Map params);
	
	/**
	 * 上传物流信息
	 * @param logisticsList
	 * @param storeId
	 * @return String
	 */
	public Map taobaoDeliverySend(Map params);
	
	/**
	 * 淘宝漏单检查
	 * @param param
	 * @return
	 */
	public Map taobaoCheckOrderMiss(Map param);
	
}