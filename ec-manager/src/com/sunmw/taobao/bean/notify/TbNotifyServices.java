package com.sunmw.taobao.bean.notify;

import java.util.Map;

public interface TbNotifyServices {
	/**
	 * 获取一个appkey的哪些用户丢失了消息 
	 * @param param
	 * @return
	 */
	public Map taobaoCometDiscardInfo(Map param);

	/**
	 * 开通主动通知业务的APP可以通过该接口获取用户的交易和评价变更通知信息
	 * @param param
	 * @return
	 */
	public Map taobaoIncrementTradesGet(Map param);

	/**
	 * 开通主动通知业务的APP可以通过该接口获取用户的退款变更通知信息
	 * @param param
	 * @return
	 */
	public Map taobaoIncrementRefundsGet(Map param);

	/**
	 * 开通主动通知业务的APP可以通过该接口获取商品变更通知信息
	 * @param param
	 * @return
	 */
	public Map taobaoIncrementItemsGet(Map param);

}
