package com.sunmw.web.bean.dataInterface;

import java.util.Map;

public interface PaipaiInterfaceServices {

	/**
	 * 增量订单
	 * @param param
	 * @return
	 */
	public Map paipaiOrderIncrement(Map param);

	/**
	 * 按日期得到订单,可查历史订单
	 * @param params
	 * @return
	 */
	public Map paipaiOrderGet(Map param);
	
	/**
	 * 单个订单的详细信息
	 * @param param
	 * @return
	 */
	public Map paipaiOrderInfoGet(Map param);
	
	/**
	 * 下载退货信息
	 * @param params
	 * @return
	 */
	public Map paipaiRefundIncrement(Map param);
	
	/**
	 * 下载退货信息
	 * @param params
	 * @return
	 */
	public Map paipaiRefundGet(Map param);
	
	/**
	 * 单个退货信息
	 * @param params
	 * @return
	 */
	public Map paipaiRefundInfoGet(Map params);
	
	/**
	 * 订单发货
	 * @param param
	 * @return
	 */
	public Map paipaiDeliverySend(Map param);
}
