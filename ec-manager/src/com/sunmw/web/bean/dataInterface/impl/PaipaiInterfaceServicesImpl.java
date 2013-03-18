package com.sunmw.web.bean.dataInterface.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.paipai.bean.order.PaipaiOrderServices;
import com.sunmw.paipai.bean.refunds.PaipaiRefundServices;
import com.sunmw.taobao.bean.order.TBOrderServices;
import com.sunmw.web.bean.dataInterface.PaipaiInterfaceServices;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebUtil;

public class PaipaiInterfaceServicesImpl extends HibernateDaoSupport implements
		PaipaiInterfaceServices {

	@Override
	public Map paipaiOrderGet(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【paipai淘宝订单下载】开始执行");
		PaipaiOrderServices paipaiOrderServices = (PaipaiOrderServices) GetBeanServlet
		.getBean("paipaiOrderServices");
		Map ppParam = new HashMap();
		ppParam.put("StoreId", param.get("StoreId"));
		if(WebUtil.isNotNull(param.get("historyDeal")))
			ppParam.put("historyDeal", param.get("historyDeal").toString());
		else
			ppParam.put("historyDeal", "0");
		if(WebUtil.isNotNull(param.get("timeType")))
			ppParam.put("timeType", param.get("timeType").toString());
		else
			ppParam.put("timeType", "CREATE");
		Date date = new Date();
		if(WebUtil.isNotNull(param.get("timeBegin")))
			ppParam.put("timeBegin", param.get("timeBegin"));
		else
			ppParam.put("timeBegin", WebUtil.beforeMonthDate(date, 3)+" 00:00:00");
		if(WebUtil.isNotNull(param.get("timeEnd")))
			ppParam.put("timeEnd", param.get("timeEnd"));
		else
			ppParam.put("timeEnd", WebUtil.formatDateString(date, "yyyy-MM-dd HH:mm:ss"));
		if(WebUtil.isNotNull(param.get("dealState")))
			ppParam.put("dealState", param.get("dealState"));
		if(WebUtil.isNotNull(param.get("itemCode")))
			ppParam.put("itemCode", param.get("itemCode"));
		if(WebUtil.isNotNull(param.get("itemNameKey")))
			ppParam.put("itemNameKey", param.get("itemNameKey"));
		if(WebUtil.isNotNull(param.get("dealCode")))
			ppParam.put("dealCode", param.get("dealCode"));
		if(WebUtil.isNotNull(param.get("orderDesc")))
			ppParam.put("orderDesc", param.get("orderDesc"));
		if(WebUtil.isNotNull(param.get("listItem")))
			ppParam.put("listItem", param.get("listItem"));
		else
			ppParam.put("listItem", "1");
		if(WebUtil.isNotNull(param.get("dealNoteType")))
			ppParam.put("dealNoteType", param.get("dealNoteType"));		
		if(WebUtil.isNotNull(param.get("dealRateState")))
			ppParam.put("dealRateState", param.get("dealRateState"));
		Map result = paipaiOrderServices.sellerSearchDealList(ppParam);
		logger.info("接口【paipai淘宝订单下载】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map paipaiOrderIncrement(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【paipai增量订单下载】开始执行");
		PaipaiOrderServices paipaiOrderServices = (PaipaiOrderServices) GetBeanServlet
				.getBean("paipaiOrderServices");
		Map ppParam = new HashMap();
		ppParam.put("StoreId", param.get("StoreId"));
		ppParam.put("historyDeal", "0");
		ppParam.put("timeType", "UPDATE");
		ppParam.put("timeBegin", WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss"));
		ppParam.put("timeEnd", WebUtil.formatDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		ppParam.put("listItem", "1");
		//ppParam.put("dealState", param.get("dealState"));
		Map result = paipaiOrderServices.sellerSearchDealList(ppParam);
		logger.info("接口【paipai增量订单下载】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map paipaiOrderInfoGet(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【paipai详细信息】开始执行");
		PaipaiOrderServices paipaiOrderServices = (PaipaiOrderServices) GetBeanServlet
				.getBean("paipaiOrderServices");
		Map ppParam = new HashMap();
		ppParam.put("StoreId", param.get("StoreId"));
		ppParam.put("dealCode", param.get("dealCode"));
		ppParam.put("listItem", "1");
		Map result = paipaiOrderServices.getDealDetail(ppParam);
		logger.info("接口【paipai详细信息】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map paipaiRefundGet(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【paipai详细信息】开始执行");
		PaipaiRefundServices paipaiRefundServices = (PaipaiRefundServices) GetBeanServlet
				.getBean("paipaiRefundServices");
		Map ppParam = new HashMap();
		ppParam.put("StoreId", param.get("StoreId"));
		if(WebUtil.isNotNull(param.get("userRole")))
			ppParam.put("userRole", param.get("userRole"));
		else
			ppParam.put("userRole", "0");
		if(WebUtil.isNotNull(param.get("timeType")))
			ppParam.put("timeType", param.get("timeType"));
		else
			ppParam.put("timeType", "CREATE");
		Date date = new Date();
		if(WebUtil.isNotNull(param.get("timeBegin")))
			ppParam.put("timeBegin", param.get("timeBegin"));
		else
			ppParam.put("timeBegin", WebUtil.beforeMonthDate(date, 3)+" 00:00:00");
		if(WebUtil.isNotNull(param.get("timeEnd")))
			ppParam.put("timeEnd", param.get("timeEnd"));
		else
			ppParam.put("timeEnd", WebUtil.formatDateString(date, "yyyy-MM-dd HH:mm:ss"));
		Map result = paipaiRefundServices.getDealRefundInfoList(ppParam);
		logger.info("接口【paipai详细信息】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map paipaiRefundIncrement(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【paipai详细信息】开始执行");
		PaipaiRefundServices paipaiRefundServices = (PaipaiRefundServices) GetBeanServlet
				.getBean("paipaiRefundServices");
		Map ppParam = new HashMap();
		ppParam.put("StoreId", param.get("StoreId"));
		ppParam.put("userRole", "0");
		ppParam.put("timeType", "UPDATE");
		ppParam.put("timeBegin", WebUtil.formatDateString((Date)param.get("LastExecTime"), "yyyy-MM-dd HH:mm:ss"));
		ppParam.put("timeEnd", WebUtil.formatDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Map result = paipaiRefundServices.getDealRefundInfoList(ppParam);
		logger.info("接口【paipai详细信息】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map paipaiRefundInfoGet(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【paipai详细信息】开始执行");
		PaipaiRefundServices paipaiRefundServices = (PaipaiRefundServices) GetBeanServlet
				.getBean("paipaiRefundServices");
		Map ppParam = new HashMap();
		ppParam.put("StoreId", param.get("StoreId"));
		ppParam.put("dealCode", param.get("dealCode"));
		if(WebUtil.isNotNull(param.get("userRole")))
			ppParam.put("userRole", param.get("userRole"));
		else
			ppParam.put("userRole", "0");
		Map result = paipaiRefundServices.getDealRefundDetailInfo(ppParam);
		logger.info("接口【paipai详细信息】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map paipaiDeliverySend(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【paipai上传发货信息】开始执行");
		Map result = new HashMap();
		OrderServices orderServices = (OrderServices) GetBeanServlet
				.getBean("orderServices");
		// 查询已发货状态订单
		Map m = orderServices.ppDeliveryOrder(param);
		if (m.get("Flag").equals("error")) {
			result.putAll(m);
			return result;
		}
		// 上传发货信息到淘宝
		List deliveryList = (List) m.get("DeliveryList");
		if (WebUtil.isNullForList(deliveryList)) {
			result.put("Flag", "success");
			result.put("Message", "无需要上传的发货信息");
			return result;
		}
		PaipaiOrderServices paipaiOrderServices = (PaipaiOrderServices) GetBeanServlet
		.getBean("paipaiOrderServices");
		m = paipaiOrderServices.sellerConsignDealItem(WebUtil.toMap("StoreId", param
				.get("StoreId"), "LogisticsList", deliveryList));
		if (m.get("Flag").equals("error")) {
			result.putAll(m);
			return result;
		}
		// 更新本地订单状态（发货成功或发货失败）
		m = orderServices.updatePpDeliveryStatus(WebUtil.toMap("StoreId", param
				.get("StoreId"), "SuccessOrderNo", m.get("SuccessOrderNo"),
				"ErrorOrderNo", param.get("ErrorOrderNo")));
		if (m.get("Flag").equals("error")) {
			result.putAll(m);
			return result;
		}
		result.put("Flag", "success");
		logger.info("接口【paipai上传发货信息】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

}
