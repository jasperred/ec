package com.sunmw.web.bean.financial;

import java.util.Map;

public interface AlipayFinancialServices {
	
	/**
	 * 导入支付宝财务报表
	 * @param param
	 * @return
	 */
	public Map importAlipayFinancialReportCsv(Map param);
	
	/**
	 * 查询淘宝对账主表
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchTaobaoAccountHead(Map param, int currentPage, int pageRow);
	
	/**
	 * 生成淘宝对账报表
	 * @param param
	 * @return
	 */
	public Map taobaoAccountReport(Map param);
	
	/**
	 * 报表信息
	 * @param accountId
	 * @return
	 */
	public Map taobaoAccountHeadInfo(Map param);
	
	/**
	 * 报表明细
	 * @param param
	 * @return
	 */
	public Map searchTaobaoAccountDetail(Map param);
	
	/**
	 * 报表退款信息
	 * @param param
	 * @return
	 */
	public Map searchTaobaoAccountRefund(Map param);
	
	/**
	 * 支付宝明细
	 * @param param
	 * @return
	 */
	public Map searchAlipayFinancialDetail(Map param);
}
