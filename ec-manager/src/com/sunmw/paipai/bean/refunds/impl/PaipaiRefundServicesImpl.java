package com.sunmw.paipai.bean.refunds.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.paipai.bean.PaipaiApiForJson;
import com.sunmw.paipai.bean.refunds.PaipaiRefundServices;
import com.sunmw.paipai.entity.PpRefund;
import com.sunmw.web.bean.refund.RefundServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebUtil;

public class PaipaiRefundServicesImpl extends HibernateDaoSupport implements
		PaipaiRefundServices {

	@Override
	public Map getDealRefundDetailInfo(Map param) {
		PaipaiApiForJson paipaiApiForJson = (PaipaiApiForJson) GetBeanServlet
				.getBean("paipaiApiForJson");
		Map result = paipaiApiForJson.getDealDetail(param);
		if (result.get("Flag").equals("success")) {
			savePpRefund(param, result);
			return WebUtil.toMap("Flag", "success");
		} else
			return result;
	}

	@Override
	public Map getDealRefundInfoList(Map param) {
		PaipaiApiForJson paipaiApiForJson = (PaipaiApiForJson) GetBeanServlet
				.getBean("paipaiApiForJson");
		Map pm = new HashMap();
		pm.put("StoreId", param.get("StoreId"));
		pm.put("userRole", "0");
		pm.put("timeType", param.get("timeType"));
		pm.put("timeBegin", param.get("timeBegin"));
		pm.put("timeEnd", param.get("timeEnd"));
		pm.put("pageSize", 20);
		int pageIndex = 1;
		while (true) {
			pm.put("pageIndex", pageIndex);
			Map result = paipaiApiForJson.getDealRefundInfoList(pm);
			if (result.get("Flag").equals("success")) {
				List<Map> rhList = (List) result.get("dealList");
				if (WebUtil.isNullForList(rhList))
					break;
				for (Map oh : rhList) {
					savePpRefund(param, oh);
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

	private void savePpRefund(Map param, Map result) {
		List<PpRefund> rhList = this.getHibernateTemplate().find(
				"from PpRefund where dealCode = ? and StoreId = ?",
				new Object[] { result.get("dealCode"), param.get("StoreId") });
		Map<Integer, PpRefund> rhMap = new HashMap();
		if (!WebUtil.isNullForList(rhList)) {
			for (PpRefund pr : rhList) {
				rhMap.put(pr.getTradeRefundId(), pr);
			}
		}
		Session session = this.getSession();
		try {
			RefundServices refundServices = (RefundServices) GetBeanServlet
					.getBean("refundServices");
			Transaction tx = session.beginTransaction();
			String dealCode = (String) result.get("dealCode");
			List<Map> refundList = (List) result.get("itemList");
			if (!WebUtil.isNullForList(refundList)) {
				for (Map rf : refundList) {
					Map info = (Map) rf.get("refundInfo");
					if (info == null)
						continue;
					Map updateRefundResult = refundServices.updateSingleRefundFromPp(info,
							(Integer) param.get("StoreId"));
					PpRefund rh = rhMap.get(rf.get("tradeRefundId"));
					if (rh == null) {
						rh = new PpRefund();
						rh.setStoreId((Integer) param.get("StoreId"));
					}
					rh.setUpdateTime(new Date());
					// 如果订单成功更新，UpdateFlag标记为E
					if (updateRefundResult.get("Flag").equals("ERROR"))
						rh.setUpdateFlag("D");
					else
						rh.setUpdateFlag("E");
					rh.setBuyerConsignmentDesc((String) info
							.get("buyerConsignmentDesc"));
					if (WebUtil.isNotNull(info.get("buyerConsignmentTime"))&&WebUtil.toDateForString(result.get(
					"buyerConsignmentTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
						rh.setBuyerConsignmentTime(WebUtil.toDateForString(
								result.get("buyerConsignmentTime").toString(),
								"yyyy-MM-dd HH:mm:ss"));
					rh.setBuyerConsignmentWuliu((String) info
							.get("buyerConsignmentWuliu"));
					rh.setDealCode((String) info.get("dealCode"));
					if (WebUtil.isNotNull(info.get("dealCreateTime"))&&WebUtil.toDateForString(result.get(
					"dealCreateTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
						rh.setDealCreateTime(WebUtil.toDateForString(result
								.get("dealCreateTime").toString(),
								"yyyy-MM-dd HH:mm:ss"));
					rh.setDealSubCode((String) info.get("dealSubCode"));
					rh.setItemCode((String) rf.get("itemCode"));
					rh.setItemCodeHistory((String) rf.get("itemCodeHistory"));
					rh.setItemLocalCode((String) rf.get("itemLocalCode"));
					if (WebUtil.isNotNull(info.get("lastUpdateTime"))&&WebUtil.toDateForString(result.get(
					"lastUpdateTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
						rh.setLastUpdateTime(WebUtil.toDateForString(result
								.get("lastUpdateTime").toString(),
								"yyyy-MM-dd HH:mm:ss"));
					rh.setPreRefundState((String) info.get("preRefundState"));
					rh.setRefDealCode(dealCode);
					rh
							.setRefDealDetailLink((String) info
									.get("dealDetailLink"));
					if (WebUtil.isNotNull(info.get("refundEndTime"))&&WebUtil.toDateForString(result.get(
					"refundEndTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
						rh.setRefundEndTime(WebUtil.toDateForString(result.get(
								"refundEndTime").toString(),
								"yyyy-MM-dd HH:mm:ss"));
					rh.setRefundItemState((String) info.get("refundItemState"));
					rh.setRefundReasonDesc((String) info
							.get("refundReasonDesc"));
					rh.setRefundReasonType((String) info
							.get("refundReasonType"));
					rh.setRefundReqitemFlag((String) info
							.get("refundReqitemFlag"));
					if (WebUtil.isNotNull(info.get("refundReqTime"))&&WebUtil.toDateForString(result.get(
					"refundReqTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
						rh.setRefundReqTime(WebUtil.toDateForString(result.get(
								"refundReqTime").toString(),
								"yyyy-MM-dd HH:mm:ss"));
					rh.setRefundState((String) info.get("refundState"));
					rh.setRefundStateDesc((String) info.get("refundStateDesc"));
					rh.setRefundToBuyer((String) info.get("refundToBuyer"));
					if (WebUtil.isNotNull(info.get("refundToBuyerNum")))
						rh.setRefundToBuyerNum(new Integer(info.get(
								"refundToBuyerNum").toString()));
					rh.setRefundToSeller((String) info.get("refundToSeller"));
					if (WebUtil.isNotNull(info.get("sellerAgreeGivebackTime"))&&WebUtil.toDateForString(result.get(
					"sellerAgreeGivebackTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
						rh.setSellerAgreeGivebackTime(WebUtil.toDateForString(
								result.get("sellerAgreeGivebackTime")
										.toString(), "yyyy-MM-dd HH:mm:ss"));
					rh.setSellerAgreeItemMsg((String) info
							.get("sellerAgreeItemMsg"));
					rh.setSellerAgreeMsg((String) info.get("sellerAgreeMsg"));
					rh.setSellerRefundAddr((String) info
							.get("sellerRefundAddr"));
					if (WebUtil.isNotNull(info.get("sellerRefuseTime"))&&WebUtil.toDateForString(result.get(
					"sellerRefuseTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
						rh.setSellerRefuseTime(WebUtil.toDateForString(result
								.get("sellerRefuseTime").toString(),
								"yyyy-MM-dd HH:mm:ss"));
					rh.setSellerUin((String) info.get("sellerUin"));
					rh.setStockAttr((String) rf.get("stockAttr"));
					rh.setStockLocalCode((String) rf.get("stockLocalCode"));
					if (WebUtil.isNotNull(info.get("timeoutItemFlag")))
						rh.setTimeoutItemFlag(new Integer(info.get(
								"timeoutItemFlag").toString()));
					rh.setTradePropertyMask((String) info
							.get("tradePropertymask"));
					if (WebUtil.isNotNull(info.get("tradeRefundId")))
						rh.setTradeRefundId(new Integer(info.get(
								"tradeRefundId").toString()));
					tx.commit();
				}
			}

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
	}
}
