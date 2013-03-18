package com.sunmw.web.bean.report;

import java.util.List;

public interface ReportServices {
	
	/**
	 * 查询销售报表
	 * @param storeId
	 * @param fromDate
	 * @param endDate
	 * @param orderStatus
	 * @return
	 */
	public List searchSaleReport(int storeId,String fromDate,String endDate,String fromPayTime, String endPayTime,String orderStatus,String orderType);
	
	/**
	 * 按SKU销售报表
	 * @param storeId
	 * @param fromDate
	 * @param endDate
	 * @param orderStatus
	 * @return
	 */
	public List searchSaleReportBySku(int storeId,String fromDate,String endDate,String fromPayTime, String endPayTime,String orderStatus,String orderType);
	
	/**
	 * 按省份销售报表
	 * @param storeId
	 * @param fromDate
	 * @param endDate
	 * @param orderStatus
	 * @return
	 */
	public List searchSaleReportByState(int storeId,String fromDate,String endDate,String fromPayTime, String endPayTime,String orderStatus,String orderType);
	
	/**
	 * 退款报表
	 * @param storeId
	 * @param fromDate
	 * @param endDate
	 * @param orderStatus
	 * @return
	 */
	public List searchRefundReport(int storeId,String fromDate,String endDate,String completeFromDate,String completeEndDate,String orderStatus,String orderType);
	
	/**
	 * 退款SKU别报表
	 * @param storeId
	 * @param fromDate
	 * @param endDate
	 * @param orderStatus
	 * @return
	 */
	public List searchRefundReportBySku(int storeId,String fromDate,String endDate,String completeFromDate,String completeEndDate,String orderStatus,String orderType);

}
