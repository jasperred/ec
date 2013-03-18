package com.sunmw.web.dao.report;

import java.util.List;
import java.util.Map;

import com.sunmw.web.domain.report.ExpandTaobaoAccountHeadExample;
import com.sunmw.web.domain.report.TaobaoAccountHead;

public interface FinancialServicesMapper {

	/**
	 * 分页查询
	 * @param example
	 * @return
	 */
	List<TaobaoAccountHead> selectTaobaoAccountHeadByExample(
			ExpandTaobaoAccountHeadExample example);

	/**
	 * 更新TaobaoAccountDetail的支付宝总额、差异和时间
	 * @param param
	 * @return
	 */
	void updateTaobaoAccountDetailAlipayFee(Map param);

	/**
	 * 更新TaobaoAccountDetail的支付宝信用卡费用
	 * @param param
	 * @return
	 */
	void updateTaobaoAccountDetailCreditCardFee(Map param);

	/**
	 * 更新TaobaoAccountDetail的支付宝积分
	 * @param param
	 * @return
	 */
	void updateTaobaoAccountDetailPointFee(Map param);

	/**
	 * 更新TaobaoAccountDetail的支付宝佣金
	 * @param param
	 * @return
	 */
	void updateTaobaoAccountDetailTbCommissionFee(Map param);

	/**
	 * 更新TaobaoAccountDetail的淘宝客佣金
	 * @param param
	 * @return
	 */
	void updateTaobaoAccountDetailTbkeCommissionFee(Map param);
	/**
	 * 更新TaobaoAccountDetail的当月退款
	 * @param param
	 * @return
	 */
	void updateTaobaoAccountDetailCurrentMonthRefund(Map param);
	/**
	 * 更新TaobaoAccountDetail的次月退款
	 * @param param
	 * @return
	 */
	void updateTaobaoAccountDetailOtherMonthRefund(Map param);
	
	/**
	 * 查询明细合计金额
	 * @param param
	 * @return
	 */
	Map selectTaobaoAccountDetailAmount(Map param);

	/**
	 * 查询支付宝未匹配金额
	 * @param param
	 * @return
	 */
	List<Map> selectAlipayOtherAmount(Map param);
}