package com.sunmw.paipai.bean.refunds;

import java.util.Map;

public interface PaipaiRefundServices {
	
	/**
	 * 查询订单的退款信息列表
	 * @param param
	 * @return
	 */
	public Map getDealRefundInfoList(Map param);
	
	/**
	 * 查询订单退款信息详情
	 * @param param
	 * @return
	 */
	public Map getDealRefundDetailInfo(Map param);
}
