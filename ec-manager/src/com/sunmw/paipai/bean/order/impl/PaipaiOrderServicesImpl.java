package com.sunmw.paipai.bean.order.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.paipai.bean.PaipaiApiForJson;
import com.sunmw.paipai.bean.order.PaipaiOrderServices;
import com.sunmw.paipai.entity.PpOrderHead;
import com.sunmw.paipai.entity.PpOrderItem;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebUtil;

public class PaipaiOrderServicesImpl extends HibernateDaoSupport implements
		PaipaiOrderServices {

	static Logger logger = Logger.getLogger(PaipaiOrderServicesImpl.class);

	@Override
	public Map getDealDetail(Map param) {
		PaipaiApiForJson paipaiApiForJson = (PaipaiApiForJson) GetBeanServlet
				.getBean("paipaiApiForJson");
		Map result = paipaiApiForJson.getDealDetail(param);
		if (result.get("Flag").equals("success")) {
			savePpOrderHead(param, result);
			return WebUtil.toMap("Flag", "success");
		} else
			return result;
	}

	@Override
	public Map sellerSearchDealList(Map param) {
		PaipaiApiForJson paipaiApiForJson = (PaipaiApiForJson) GetBeanServlet
				.getBean("paipaiApiForJson");
		Map pm = new HashMap();
		pm.put("StoreId", param.get("StoreId"));
		pm.put("historyDeal", param.get("historyDeal"));
		pm.put("timeType", param.get("timeType"));
		pm.put("timeBegin", param.get("timeBegin"));
		pm.put("timeEnd", param.get("timeEnd"));
		pm.put("dealState", param.get("dealState"));
		pm.put("listItem", "1");
		pm.put("pageSize", 20);
		int pageIndex = 1;
		while (true) {
			pm.put("pageIndex", pageIndex);
			Map result = paipaiApiForJson.sellerSearchDealList(pm);
			if (result.get("Flag").equals("success")) {
				List<Map> ohList = (List) result.get("dealList");
				if (WebUtil.isNullForList(ohList))
					break;
				for (Map oh : ohList) {
					savePpOrderHead(param, oh);
				}
				if (Integer.parseInt(result.get("pageTotal").toString()) <= Integer
						.parseInt(result.get("pageIndex").toString()))
					break;
				pageIndex++;
			} else
				return result;
		}
		return WebUtil.toMap("Flag", "success");
	}

	private void savePpOrderHead(Map param, Map result) {
		List<PpOrderHead> ohList = this.getHibernateTemplate().find(
				"from PpOrderHead where DealCode = ? and StoreId = ?",
				new Object[] { result.get("dealCode"), param.get("StoreId") });
		Session session = this.getSession();
		try {
			OrderServices orderServices = (OrderServices) GetBeanServlet
					.getBean("orderServices");
			Map updateOrderResult = orderServices.updateSingleOrderFromPp(result,
					(Integer) param.get("StoreId"));
			Transaction tx = session.beginTransaction();
			PpOrderHead oh = null;
			if (WebUtil.isNullForList(ohList)) {
				oh = new PpOrderHead();
				oh.setStoreId((Integer) param.get("StoreId"));
			} else {
				oh = ohList.get(0);
			}
			oh.setUpdateTime(new Date());
			// 如果订单成功更新，UpdateFlag标记为E
			if (updateOrderResult.get("Flag").equals("ERROR"))
				oh.setUpdateFlag("D");
			else
				oh.setUpdateFlag("E");
			oh.setBuyerName((String) result.get("buyerName"));
			oh.setBuyerRemark((String) result.get("buyerRemark"));
			if(WebUtil.isNotNull(result.get("buyerUin")))
			oh.setBuyerUin( result.get("buyerUin").toString());
			oh.setComboInfo((String) result.get("comboInfo"));
			if (WebUtil.isNotNull(result.get("couponFee")))
				oh.setCouponFee(new BigDecimal(result.get("couponFee")
						.toString()));
			if (WebUtil.isNotNull(result.get("createTime"))&&WebUtil.toDateForString(result.get(
			"createTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
				oh.setCreateTime(WebUtil.toDateForString(result.get(
						"createTime").toString(), "yyyy-MM-dd HH:mm:ss"));
			oh.setDealCode((String) result.get("dealCode"));
			oh.setDealDesc((String) result.get("dealDesc"));
			oh.setDealDetailLink((String) result.get("dealDetailLink"));
			if (WebUtil.isNotNull(result.get("dealEndTime"))&&WebUtil.toDateForString(result.get(
			"dealEndTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
				oh.setDealEndTime(WebUtil.toDateForString(result.get(
						"dealEndTime").toString(), "yyyy-MM-dd HH:mm:ss"));
			oh.setDealNote((String) result.get("dealNote"));
			oh.setDealNoteType((String) result.get("dealNoteType"));
			if (WebUtil.isNotNull(result.get("dealPayFeePoint")))
				oh.setDealPayFeePoint(new BigDecimal(result.get(
						"dealPayFeePoint").toString()));
			if (WebUtil.isNotNull(result.get("dealPayFeeTicket")))
				oh.setDealPayFeeTicket(new BigDecimal(result.get(
						"dealPayFeeTicket").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
			if (WebUtil.isNotNull(result.get("dealPayFeeTotal")))
				oh.setDealPayFeeTotal(new BigDecimal(result.get(
						"dealPayFeeTotal").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
			oh.setDealPayType((String) result.get("dealPayType"));
			oh.setDealPayTypeDesc((String) result.get("dealPayTypeDesc"));
			oh.setDealRateState((String) result.get("dealRateState"));
			oh.setDealRateStateDesc((String) result.get("dealRateStateDesc"));
			oh.setDealState((String) result.get("dealState"));
			oh.setDealStateDesc((String) result.get("dealStateDesc"));
			oh.setDealType((String) result.get("dealType"));
			oh.setDealTypeDesc((String) result.get("dealTypeDesc"));
			if (WebUtil.isNotNull(result.get("expectArrivalTime"))&&WebUtil.toDateForString(result.get(
			"expectArrivalTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
				oh
						.setExpectArrivalTime(WebUtil.toDateForString(result
								.get("expectArrivalTime").toString(),
								"yyyy-MM-dd HH:mm:ss"));
			if (WebUtil.isNotNull(result.get("freight")))
				oh.setFreight(new BigDecimal(result.get("freight").toString()));
			if (WebUtil.isNotNull(result.get("hasInvoice")))
				oh.setHasInvoice(new Integer(result.get("hasInvoice")
						.toString()));
			oh.setInvoiceContent((String) result.get("invoiceContent"));
			oh.setInvoiceTitle((String) result.get("invoiceTitle"));
			if (WebUtil.isNotNull(result.get("lastUpdateTime"))&&WebUtil.toDateForString(result.get(
			"lastUpdateTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
				oh.setLastUpdateTime(WebUtil.toDateForString(result.get(
						"lastUpdateTime").toString(), "yyyy-MM-dd HH:mm:ss"));
			if (WebUtil.isNotNull(result.get("payReturnTime"))&&WebUtil.toDateForString(result.get(
			"payReturnTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
				oh.setPayReturnTime(WebUtil.toDateForString(result.get(
						"payReturnTime").toString(), "yyyy-MM-dd HH:mm:ss"));
			if (WebUtil.isNotNull(result.get("payTime"))&&WebUtil.toDateForString(result.get(
			"payTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
				oh.setPayTime(WebUtil.toDateForString(result.get("payTime")
						.toString(), "yyyy-MM-dd HH:mm:ss"));
			oh.setPropertyMask((String) result.get("propertymask"));
			oh.setReceiverAddress((String) result.get("receiverAddress"));
			oh.setReceiverMobile((String) result.get("receiverMobile"));
			oh.setReceiverName((String) result.get("receiverName"));
			oh.setReceiverPhone((String) result.get("receiverPhone"));
			oh.setReceiverPostcode((String) result.get("receiverPostcode"));
			if (WebUtil.isNotNull(result.get("recvfeeReturnTime"))&&WebUtil.toDateForString(result.get(
			"recvfeeReturnTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
				oh
						.setRecvfeeReturnTime(WebUtil.toDateForString(result
								.get("recvfeeReturnTime").toString(),
								"yyyy-MM-dd HH:mm:ss"));
			if (WebUtil.isNotNull(result.get("recvfeeTime"))&&WebUtil.toDateForString(result.get(
			"recvfeeTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
				oh.setRecvfeeTime(WebUtil.toDateForString(result.get(
						"recvfeeTime").toString(), "yyyy-MM-dd HH:mm:ss"));
			if (WebUtil.isNotNull(result.get("sellerConsignmentTime"))&&WebUtil.toDateForString(result.get(
			"sellerConsignmentTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
				oh.setSellerConsignmentTime(WebUtil.toDateForString(result.get(
						"sellerConsignmentTime").toString(),
						"yyyy-MM-dd HH:mm:ss"));
			oh.setSellerCrm((String) result.get("sellerCrm"));
			oh.setSellerName((String) result.get("sellerName"));
			if (WebUtil.isNotNull(result.get("sellerRecvRefund")))
				oh.setSellerRecvRefund(new BigDecimal(result.get(
						"sellerRecvRefund").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
			if (WebUtil.isNotNull(result.get("buyerRecvRefund")))
				oh.setBuyerRecvRefund(new BigDecimal(result.get(
						"buyerRecvRefund").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
			if(WebUtil.isNotNull(result.get("sellerUin")))
			oh.setSellerUin( result.get("sellerUin").toString());
			if (WebUtil.isNotNull(result.get("shippingfeeCalc")))
				oh.setShippingfeeCalc(new BigDecimal(result.get(
						"shippingfeeCalc").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
			oh.setTenpayCode((String) result.get("tenpayCode"));
			if (WebUtil.isNotNull(result.get("totalCash")))
				oh.setTotalCash(new BigDecimal(result.get("totalCash")
						.toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
			oh.setTransportType((String) result.get("transportType"));
			oh.setTransportTypeDesc((String) result.get("transportTypeDesc"));
			if (WebUtil.isNotNull(result.get("whoPayShippingfee")))
				oh.setWhoPayShippingfee(new Integer(result.get(
						"whoPayShippingfee").toString()));
			oh.setWuliuId((String) result.get("wuliuId"));
			if (oh.getId() == null)
				session.save(oh);
			else
				session.update(oh);
			List<Map> itemList = (List) result.get("itemList");
			if (!WebUtil.isNullForList(itemList)) {
				List<PpOrderItem> oiList = this.getHibernateTemplate().find(
						"from PpOrderItem where DealCode = ? and StoreId = ?",
						new Object[] { result.get("dealCode"),
								param.get("StoreId") });
				Map<String, PpOrderItem> oiMap = new HashMap();
				if (!WebUtil.isNullForList(oiList)) {
					for (PpOrderItem oi : oiList) {
						oiMap.put(oi.getDealSubCode(), oi);
					}
				}
				for (Map item : itemList) {
					PpOrderItem oi = oiMap.get(item.get("dealSubCode"));
					if (oi == null) {
						oi = new PpOrderItem();
						oi.setDealCode((String) result.get("dealCode"));
						oi.setStoreId((Integer) param.get("StoreId"));
					}

					// 如果订单成功更新，UpdateFlag标记为E
					if (updateOrderResult.get("Flag").equals("ERROR"))
						oi.setUpdateFlag("D");
					else
						oi.setUpdateFlag("E");
					oi.setUpdateTime(new Date());
					oi.setAccount((String) item.get("account"));
					oi.setAvailableAction((String) item
							.get("availableAction"));
					oi.setDealSubCode((String) item.get("dealSubCode"));
					if (WebUtil.isNotNull(item.get("itemAdjustPrice")))
						oi.setItemAdjustPrice(new BigDecimal(item.get(
								"itemAdjustPrice").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
					oi.setItemCode((String) item.get("itemCode"));
					oi.setItemCodeHistory((String) item
							.get("itemCodeHistory"));
					if (WebUtil.isNotNull(item.get("itemDealCount")))
						oi.setItemDealCount(new Integer(item.get(
								"itemDealCount").toString()));
					if (WebUtil.isNotNull(item.get("itemDealPrice")))
						oi.setItemDealPrice(new BigDecimal(item.get(
								"itemDealPrice").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
					oi.setItemDetailLink((String) item.get("itemDetailLink"));
					if (WebUtil.isNotNull(item.get("itemDiscountFee")))
						oi.setItemDiscountFee(new BigDecimal(item.get(
								"itemDiscountFee").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
					oi.setItemFlag((String) item.get("itemFlag"));
					oi.setItemLocalCode((String) item.get("itemLocalCode"));
					oi.setItemName((String) item.get("itemName"));
					oi.setItemPic80((String) item.get("itemPic80"));
					if (WebUtil.isNotNull(item.get("itemRetailPrice")))
						oi.setItemRetailPrice(new BigDecimal(item.get(
								"itemRetailPrice").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
					oi.setRefundState((String) item.get("refundState"));
					oi.setRefundStateDesc((String) item
							.get("refundStateDesc"));
					oi.setStockAttr((String) item.get("stockAttr"));
					oi.setStockLocalCode((String) item.get("stockLocalCode"));
					oi.setTradePropertyMask((String) item
							.get("tradePropertymask"));
					if (oi.getId() == null)
						session.save(oi);
					else
						session.update(oi);
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
	}

	@Override
	public Map sellerConsignDealItem(Map param) {
		PaipaiApiForJson paipaiApiForJson = (PaipaiApiForJson) GetBeanServlet
				.getBean("paipaiApiForJson");
		List<Map> logisticsList = (List) param.get("LogisticsList");
		if (WebUtil.isNullForList(logisticsList))
			return WebUtil.toMap("Flag", "success");
		StringBuffer successDealCodes = new StringBuffer();// 成功的订单号
		StringBuffer errorDealCodes = new StringBuffer();// 失败的订单号
		for (Map m : logisticsList) {
			Map result = paipaiApiForJson.sellerConsignDealItem(m);
			if (result.get("Flag").equals("success")) {
				if (successDealCodes.length() > 0)
					successDealCodes.append(",");
				successDealCodes.append("'" + m.get("dealCode") + "'");
			} else {
				if (errorDealCodes.length() > 0)
					errorDealCodes.append(",");
				errorDealCodes.append("'" + m.get("dealCode") + "'");
			}
		}
		return WebUtil.toMap("Flag", "success", "SuccessDealCodes",
				successDealCodes.toString(), "ErrorDealCodes", errorDealCodes
						.toString());
	}

	@Override
	public Map sellerSignDealPreparing(Map param) {
		PaipaiApiForJson paipaiApiForJson = (PaipaiApiForJson) GetBeanServlet
				.getBean("paipaiApiForJson");
		List<String> dealList = (List) param.get("DealList");
		if (WebUtil.isNullForList(dealList))
			return WebUtil.toMap("Flag", "success");
		StringBuffer successDealCodes = new StringBuffer();// 成功的订单号
		StringBuffer errorDealCodes = new StringBuffer();// 失败的订单号
		for (String dealCode : dealList) {
			Map result = paipaiApiForJson.sellerSignDealPreparing(WebUtil.toMap("dealCode", dealCode));
			if (result.get("Flag").equals("success")) {
				if (successDealCodes.length() > 0)
					successDealCodes.append(",");
				successDealCodes.append("'" + dealCode + "'");
			} else {
				if (errorDealCodes.length() > 0)
					errorDealCodes.append(",");
				errorDealCodes.append("'" + dealCode + "'");
			}
		}
		return WebUtil.toMap("Flag", "success", "SuccessDealCodes",
				successDealCodes.toString(), "ErrorDealCodes", errorDealCodes
						.toString());
	}

}
