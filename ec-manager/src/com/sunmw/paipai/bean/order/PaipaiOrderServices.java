package com.sunmw.paipai.bean.order;

import java.util.Map;

public interface PaipaiOrderServices {
	/**
	 * 卖家查找订单列表接口
	 * @param param
	 * @return
	 */
	public Map sellerSearchDealList(Map param);
	
	/**
	 * 卖家查寻单个订单详情信息接口
	 * @param param
	 * @return
	 */
	public Map getDealDetail(Map param);
	
	/**
	 * 卖家标记订单配货中
	 * @param param
	 * @return
	 */
	public Map sellerSignDealPreparing(Map param);
	
	/**
	 * 卖家标记订单发货
	 * @param param
	 * @return
	 */
	public Map sellerConsignDealItem(Map param);
}
