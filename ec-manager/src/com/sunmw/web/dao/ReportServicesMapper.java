package com.sunmw.web.dao;

import java.util.List;
import java.util.Map;

public interface ReportServicesMapper {
	
    List<Map> selectSaleOrderByExample(Map param);
    List<Map> selectRefundOrderByExample(Map param);
    List<Map> selectDailySaleAndRefundReportByExample(Map param);
}