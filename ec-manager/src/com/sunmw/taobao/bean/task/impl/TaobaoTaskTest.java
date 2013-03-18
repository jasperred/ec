package com.sunmw.taobao.bean.task.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.task.TaobaoTaskServices;
import com.sunmw.web.util.WebUtil;

public class TaobaoTaskTest extends HibernateDaoSupport implements
		TaobaoTaskServices {
	static Logger logger = Logger.getLogger(TaobaoTaskServices.class);

	@Override
	public Map downloadTbCat(Map param) {
		logger.info("downloadTbCat:[storeId:"+param.get("StoreId")+",LastExecTime:"+WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss")+"]");
		Map result = new HashMap();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map downloadTbLogisticsCompany(Map param) {
		logger.info("downloadTbLogisticsCompany:[storeId:"+param.get("StoreId")+",LastExecTime:"+WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss")+"]");
		Map result = new HashMap();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map taobaoItemDownload(Map param) {
		logger.info("taobaoItemDownload:[storeId:"+param.get("StoreId")+",LastExecTime:"+WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss")+"]");
		Map result = new HashMap();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map taobaoItemQuantityUpdate(Map param) {
		logger.info("taobaoItemQuantityUpdate:[storeId:"+param.get("StoreId")+",LastExecTime:"+WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss")+"]");
		Map result = new HashMap();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map taobaoProductsGet(Map param) {
		logger.info("taobaoProductsGet:[storeId:"+param.get("StoreId")+",LastExecTime:"+WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss")+"]");
		Map result = new HashMap();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map taobaoRefundsReceiveGet(Map param) {
		logger.info("taobaoRefundsReceiveGet:[storeId:"+param.get("StoreId")+",LastExecTime:"+WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss")+"]");
		Map result = new HashMap();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map taobaoSellercatsListGet(Map param) {
		logger.info("taobaoSellercatsListGet:[storeId:"+param.get("StoreId")+",LastExecTime:"+WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss")+"]");
		Map result = new HashMap();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map taobaoSkusPriceUpdate(Map param) {
		logger.info("taobaoSkusPriceUpdate:[storeId:"+param.get("StoreId")+",LastExecTime:"+WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss")+"]");
		Map result = new HashMap();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map taobaoTradesSoldGet(Map param) {
		logger.info("taobaoTradesSoldGet:[storeId:"+param.get("StoreId")+",LastExecTime:"+WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss")+"]");
		Map result = new HashMap();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map taobaoTradesSoldIncrement(Map param) {
		logger.info("taobaoTradesSoldIncrement:[storeId:"+param.get("StoreId")+",LastExecTime:"+WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss")+"]");
		Map result = new HashMap();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map dailyTaobaoRefundReport(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map dailyTaobaoSaleReport(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoDeliverySend(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

}
