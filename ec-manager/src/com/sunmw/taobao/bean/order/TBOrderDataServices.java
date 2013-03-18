package com.sunmw.taobao.bean.order;

import com.taobao.api.domain.Trade;

public interface TBOrderDataServices {
	
	/**
	 * 插入Tid
	 * @param tid
	 */
	public void insertTid(Long tid);
	
	/**
	 * 更新Trade信息
	 * @param t
	 */
	public void updateTrade(Trade t,Integer storeId);

}
