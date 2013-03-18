package com.sunmw.taobao.bean.refunds;

import java.util.List;
import java.util.Map;

import com.taobao.api.TaobaoClient;

public interface TbRefundsServices {

	/**
	 * 从淘宝下载退货信息
	 * @param params
	 * @return
	 */
	public Map taobaoRefundsReceiveGet(Map params);
	
	/**
	 * 从淘宝下载单个退货的详细信息
	  * @param storeId
	 * @param params
	 * @return
	 */
	public Map taobaoRefundGet(Map params);
	
	/**
	 * 获得未更新到ERP的退货信息
	 * @param storeId
	 * @return
	 */
	public List getNotUpdateTBRefund(int storeId);
	
	/**
	 * 更新淘宝退货的更新标记
	 * @param refundIds
	 */
	public void updateTbRefundStatusForImportErp(String refundIds);
}
