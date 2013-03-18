package com.sunmw.web.bean.data;

import java.util.List;
import java.util.Map;

public interface RefundTaskServices {
	
	/**
	 * 导出退货单
	 * @param storeId
	 * @return
	 */
	public List exportRefund(int storeId);
	
	/**
	 * 更新退货单状态为退款中
	 * @param orderNos
	 * @return
	 */
	public String updateRefundWmsStatus(String orderNos);
	
	/**
	 * 更新退货单的返回状态,成功状态更新为REFUND_COMPLETE,取消状态更新为REFUND_CLOSED
	 * @param m
	 * @return
	 */
	public String updateRefundErpStatus(List<Map> l);

}
