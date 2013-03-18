package com.sunmw.web.bean.report.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.report.ReportServices;
import com.sunmw.web.entity.OrderHead;
import com.sunmw.web.entity.OrderItem;
import com.sunmw.web.entity.OrderNote;
import com.sunmw.web.entity.OrderTaobaoinfo;
import com.sunmw.web.util.WebUtil;

public class ReportServicesImpl extends HibernateDaoSupport implements
		ReportServices {

	public List searchSaleReport(int storeId, String fromDate, String endDate,String fromPayTime, String endPayTime,
			String orderStatus, String orderType) {
		String hql = "from OrderHead oh where oh.StoreId = " + storeId;
		if(WebUtil.isNotNull(fromDate))
		{
			hql = hql + " and oh.DeliveryDate >= '" + fromDate
				+ "'";
		}
		if(WebUtil.isNotNull(endDate))
		{
			hql = hql + " and oh.DeliveryDate < '" + endDate
			+ "'";
		}
		if(WebUtil.isNotNull(fromDate)||WebUtil.isNotNull(endDate))
		{
			hql = hql + " and (oh.OrderStatus in ("
				+ orderStatus
				+ ") or oh.PostNo is Not null)";
		}
		if(WebUtil.isNotNull(fromPayTime))
		{
			hql = hql + " and oh.PayTime >= '" + fromPayTime
			+ "'";
		}
		if(WebUtil.isNotNull(endPayTime))
		{
			hql = hql + " and oh.PayTime < '" + endPayTime
			+ "'";
		}
		if (WebUtil.isNotNull(orderType))
			hql += " and oh.OrderType = '" + orderType + "'";
		hql += " order by oh.DeliveryDate";
		List<OrderHead> l = this.getHibernateTemplate().find(hql);
		if (l == null || l.size() == 0)
			return null;
		List<Map> result = new ArrayList();
		for (OrderHead oh : l) {
			// OrderHead oh = (OrderHead) obj[0];
			// OrderTaobaoinfo ot = (OrderTaobaoinfo) obj[1];
			Map m = new HashMap();
			m.put("OrderNo", oh.getOrderNo());
			m.put("OrderStatus", oh.getOrderStatus());
			m.put("OrderType", oh.getOrderType());
			m.put("OrigOrderNo", oh.getOrigOrderNo());
			m.put("OrigOrderStatus", oh.getOrigOrderStatus());
			m.put("Origin", oh.getOrigin());
			m.put("StoreId", oh.getStoreId());
			m.put("OrderDate", WebUtil.formatDateString(oh.getOrderDate(),
					"yyyy-MM-dd HH:mm:ss"));
			m.put("PayTime", WebUtil.formatDateString(oh.getPayTime(),
			"yyyy-MM-dd HH:mm:ss"));
			m.put("RequestDate", WebUtil.formatDateString(oh.getRequestDate(),
					"yyyy-MM-dd HH:mm:ss"));
			if (WebUtil.isNotNull(oh.getDeliveryDate()))
				m.put("DeliveryDate", WebUtil.formatDateString(oh
						.getDeliveryDate(), "yyyy-MM-dd HH:mm:ss"));
			// else
			// m.put("DeliveryDate",
			// WebUtil.formatDateString(ot.getConsignTime(),
			// "yyyy-MM-dd HH:mm:ss"));
			m.put("CompleteDate", WebUtil.formatDateString(
					oh.getCompleteDate(), "yyyy-MM-dd HH:mm:ss"));
			m.put("InvoiceId", oh.getInvoiceId());
			m.put("OrderAmt", oh.getOrderAmt());
			m.put("ActualTotalAmt", oh.getTotalFee());
			m.put("PaymentAmt", oh.getPayment());
			m.put("FreightAmt", oh.getPostFee());
			m.put("BuyerNick", oh.getBuyerNick());
			m.put("BuyerAlipayNo", oh.getPayNo());
			m.put("PostNo", oh.getPostNo());
			m.put("ReceiverName", oh.getReceiverName());
			m.put("ReceiverState", oh.getReceiverState());
			m.put("ReceiverCity", oh.getReceiverCity());
			m.put("ReceiverDistrict", oh.getReceiverDistrict());
			m.put("ReceiverAddress", oh.getReceiverAddress());
			m.put("ReceiverZip", oh.getReceiverZip());
			m.put("ReceiverMobile", oh.getReceiverMobile());
			m.put("ReceiverPhone", oh.getReceiverTel());
			List<OrderNote> onList = this.getHibernateTemplate().find(
					"from OrderNote where id.OrderHeadId = " + oh.getId());
			if (onList != null) {
				for (OrderNote on : onList) {
					m.put(on.getId().getNoteType(), on.getNote());
				}
			}
			List<OrderItem> orderItemList = this.getHibernateTemplate().find(
					"from OrderItem where OrderHeadId = " + oh.getId());
			if (orderItemList != null) {
				List<Map> il = new ArrayList();
				BigDecimal payment = null;
				if (oh.getPayment() != null)
					payment = oh.getPayment();
				else
					payment = new BigDecimal(0);
				if (oh.getPostFee() != null)
					payment = payment.subtract(oh.getPostFee());
				BigDecimal orderAmt = oh.getOrderAmt();
				BigDecimal countItemAmt = new BigDecimal(0);
				int c = 0;
				for (OrderItem oi : orderItemList) {
					c++;
					Map i = new HashMap();
					i.put("SkuCd", oi.getSkuCd());
					i.put("ItemCd", oi.getItemCd());
					i.put("Name", oi.getName());
					i.put("SkuProp1", oi.getSkuProp1());
					i.put("SkuProp2", oi.getSkuProp2());
					i.put("SkuProp3", oi.getSkuProp3());
					i.put("Qty", oi.getQty());
					i.put("Price", oi.getPrice());
					i.put("SubTotal", oi.getSubTotal());
					// 计算最终分摊的小计
					if (payment != null) {
						if (c == orderItemList.size()) {
							// 余额给最后一条，（支付金额-运费）-之前明细分摊合计
							i.put("CountTotal", payment.subtract(countItemAmt));
						} else {
							if(orderAmt.doubleValue()==0)
							{
								i.put("CountTotal", new BigDecimal(0));
							}
							else
							{
								// 分摊小计计算，（支付金额-运费）*（（价格*数量）/订单总额）
								BigDecimal im = WebUtil.round(payment.multiply(oi
										.getPrice().multiply(oi.getQty()).divide(
												orderAmt, 2)));
								i.put("CountTotal", im);
								countItemAmt = countItemAmt.add(im);
							}							
						}
					}
					il.add(i);
				}
				m.put("OrderItem", il);
			}
			result.add(m);

		}
		return result;
	}

	public List searchSaleReportBySku(int storeId, String fromDate,
			String endDate,String fromPayTime, String endPayTime, String orderStatus, String orderType) {
		String hql = "select oh.id,oi.ItemCd,oi.SkuCd,oi.Name,oi.Price,oi.Qty,oh.OrderAmt from OrderHead oh,OrderItem oi where oh.id = oi.OrderHeadId and oh.StoreId = "
				+ storeId;

		if(WebUtil.isNotNull(fromDate))
		{
			hql = hql + " and oh.DeliveryDate >= '" + fromDate
				+ "'";
		}
		if(WebUtil.isNotNull(endDate))
		{
			hql = hql + " and oh.DeliveryDate < '" + endDate
			+ "'";
		}
		if(WebUtil.isNotNull(fromDate)||WebUtil.isNotNull(endDate))
		{
			hql = hql + " and (oh.OrderStatus in ("
				+ orderStatus
				+ ") or oh.PostNo is Not null)";
		}
		if(WebUtil.isNotNull(fromPayTime))
		{
			hql = hql + " and oh.PayTime >= '" + fromPayTime
			+ "'";
		}
		if(WebUtil.isNotNull(endPayTime))
		{
			hql = hql + " and oh.PayTime < '" + endPayTime
			+ "'";
		}
		if (WebUtil.isNotNull(orderType))
			hql += " and oh.OrderType = '" + orderType + "'";
		hql += " order by oh.id";
		List<Object[]> l = this.getHibernateTemplate().find(hql);
		if (l == null || l.size() == 0)
			return null;
		List<Map> r = new ArrayList();
		Map<Object, Map> k = new HashMap();
		for (Object[] o : l) {
			Double price = o[4] == null ? 0 : Double.parseDouble(o[4]
					.toString());
			Integer qty = o[5] == null ? 0 : Integer.parseInt(o[5].toString());
			Double amt = o[6] == null ? 0 : Double.parseDouble(o[6].toString());
			String id = o[0].toString();
			Map m = k.get((String)o[1]+o[2]);
			if (m == null) {
				m = new HashMap();
				m.put("ItemCd", o[1]);
				m.put("SkuCd", o[2]);
				m.put("SkuName", o[3]);
				m.put("Price", price);
				m.put("Qty", qty);
				m.put("OrderAmt", amt);
				m.put("Order", 1);
				m.put("OrderHeadId", id);
			} else {
				Double p = (Double) m.get("Price");
				Integer q = (Integer) m.get("Qty");
				Double a = (Double) m.get("OrderAmt");
				Integer order = (Integer) m.get("Order");
				String mid = (String) m.get("OrderHeadId");// 用来判断是否重复同一订单
				if (mid.equals(id)) {
					m.put("Qty", q + qty);
				} else {
					if (price < p)
						m.put("Price", price);
					m.put("Qty", q + qty);
					m.put("OrderAmt", amt + a);
					m.put("Order", order + 1);
				}
				m.put("OrderHeadId", id);
			}
			k.put((String)o[1]+o[2], m);
		}
		Iterator iter = k.keySet().iterator();
		while (iter.hasNext()) {
			r.add(k.get(iter.next()));
		}
		return r;

	}

	public List searchSaleReportByState(int storeId, String fromDate,
			String endDate,String fromPayTime, String endPayTime, String orderStatus, String orderType) {
		String hql = "select oh.id,oh.ReceiverState,oi.Qty,oh.OrderAmt,oh.ReceiveField4,oh.PostFee from OrderHead oh,OrderItem oi where oh.id = oi.OrderHeadId and oh.StoreId = "
				+ storeId;

		if(WebUtil.isNotNull(fromDate))
		{
			hql = hql + " and oh.DeliveryDate >= '" + fromDate
				+ "'";
		}
		if(WebUtil.isNotNull(endDate))
		{
			hql = hql + " and oh.DeliveryDate < '" + endDate
			+ "'";
		}

		if(WebUtil.isNotNull(fromDate)||WebUtil.isNotNull(endDate))
		{
			hql = hql + " and (oh.OrderStatus in ("
				+ orderStatus
				+ ") or oh.PostNo is Not null)";
		}
		if(WebUtil.isNotNull(fromPayTime))
		{
			hql = hql + " and oh.PayTime >= '" + fromPayTime
			+ "'";
		}
		if(WebUtil.isNotNull(endPayTime))
		{
			hql = hql + " and oh.PayTime < '" + endPayTime
			+ "'";
		}
		if (WebUtil.isNotNull(orderType))
			hql += " and oh.OrderType = '" + orderType + "'";
		hql += " order by oh.id";
		List<Object[]> l = this.getHibernateTemplate().find(hql);
		if (l == null || l.size() == 0)
			return null;
		List<Map> r = new ArrayList();
		Map<Object, Map> k = new HashMap();
		for (Object[] o : l) {
			Integer qty = o[2] == null ? 0 : Integer.parseInt(o[2].toString());
			Double postFee = o[5] == null ? 0 : Double.parseDouble(o[5]
					.toString());
			String post = o[4] == null ? "" : (String) o[4];
			Double amt = o[3] == null ? 0 : Double.parseDouble(o[3].toString());
			String id = o[0].toString();
			Map m = k.get(o[1]);
			if (m == null) {
				m = new HashMap();
				m.put("State", o[1]);
				m.put("PostFee", postFee);
				m.put("Qty", qty);
				m.put("OrderAmt", amt);
				m.put("Order", 1);
				m.put("Post", post.split(",").length);
				m.put("OrderHeadId", id);

			} else {
				Double p = (Double) m.get("PostFee");
				Integer q = (Integer) m.get("Qty");
				Double a = (Double) m.get("OrderAmt");
				Integer order = (Integer) m.get("Order");
				Integer pt = (Integer) m.get("Post");
				String mid = (String) m.get("OrderHeadId");// 用来判断是否重复同一订单
				if (mid.equals(id)) {
					m.put("Qty", q + qty);
				} else {
					m.put("PostFee", postFee + p);
					m.put("Qty", q + qty);
					m.put("OrderAmt", amt + a);
					m.put("Order", order + 1);
					m.put("Post", pt + post.split(",").length);
				}
				m.put("OrderHeadId", id);
			}
			k.put(o[1], m);
		}
		Iterator iter = k.keySet().iterator();
		while (iter.hasNext()) {
			r.add(k.get(iter.next()));
		}
		return r;

	}

	public List searchRefundReport(int storeId, String fromDate,
			String endDate, String completeFromDate, String completeEndDate,
			String orderStatus, String orderType) {
		String hql = "from OrderHead oh where oh.OrderStatus in ("
				+ orderStatus + ") and oh.StoreId = '" + storeId + "'";
		if (WebUtil.isNotNull(completeFromDate))
			hql = hql + " and oh.CompleteDate >= '" + completeFromDate + "'";
		if (WebUtil.isNotNull(completeEndDate))
			hql = hql + " and oh.CompleteDate < '" + completeEndDate + "'";
		if (WebUtil.isNotNull(fromDate))
			hql = hql + " and oh.RequestDate >= '" + fromDate + "'";
		if (WebUtil.isNotNull(endDate))
			hql = hql + " and oh.RequestDate < '" + endDate + "'";
		if (WebUtil.isNotNull(orderType))
			hql += " and oh.OrderType = '" + orderType + "'";
		hql += " order by oh.CompleteDate";
		List<OrderHead> l = this.getHibernateTemplate().find(hql);
		if (WebUtil.isNullForList(l))
			return null;
		// 查询退款的淘宝订单号
		StringBuffer tbOrderNo = new StringBuffer();
		for (OrderHead oh : l) {
			if (WebUtil.isNotNull(oh.getRefOrderNo())) {
				if (tbOrderNo.length() > 0)
					tbOrderNo.append(",");
				tbOrderNo.append("'" + oh.getRefOrderNo() + "'");
			}
		}
		// 得到退款的订单信息
		Map<String, OrderHead> tbMap = new HashMap();
		if(tbOrderNo.length()>0)
		{
			List<OrderHead> tbl = this.getHibernateTemplate().find(
					"from OrderHead oh where oh.OrigOrderNo in ("
							+ tbOrderNo.toString() + ")");
			if (tbl != null && tbl.size() > 0) {
				for (OrderHead oh : tbl) {
					// OrderHead oh = (OrderHead) obj[0];
					// OrderTaobaoinfo ot = (OrderTaobaoinfo) obj[1];
					tbMap.put(oh.getOrigOrderNo(), oh);
				}
			}
		}
		
		List<Map> result = new ArrayList();
		for (OrderHead oh : l) {
			
			Map m = new HashMap();
			m.put("OrderNo", oh.getOrderNo());
			m.put("OrderStatus", oh.getOrderStatus());
			m.put("OrderType", oh.getOrderType());
			m.put("OrigOrderNo", oh.getOrigOrderNo());
			m.put("OrigOrderStatus", oh.getOrigOrderStatus());
			m.put("RefOrderNo", oh.getRefOrderNo());
			m.put("Origin", oh.getOrigin());
			m.put("StoreId", oh.getStoreId());
			m.put("OrderDate", WebUtil.formatDateString(oh.getOrderDate(),
					"yyyy-MM-dd HH:mm:ss"));
			m.put("RequestDate", WebUtil.formatDateString(oh.getRequestDate(),
					"yyyy-MM-dd HH:mm:ss"));
			m.put("DeliveryDate", WebUtil.formatDateString(
					oh.getDeliveryDate(), "yyyy-MM-dd HH:mm:ss"));
			m.put("CompleteDate", WebUtil.formatDateString(
					oh.getCompleteDate(), "yyyy-MM-dd HH:mm:ss"));
			m.put("InvoiceId", oh.getInvoiceId());
			m.put("OrderAmt", oh.getTotalFee());
			m.put("ActualTotalAmt", oh.getTotalFee());
			m.put("PaymentAmt", oh.getPayment());
			m.put("FreightAmt", oh.getPostFee());
			m.put("RefundAmt", oh.getOrderAmt());
			// 原订单的收货信息
			OrderHead ooh = tbMap.get(oh.getRefOrderNo());
			if (ooh != null) {
				m.put("BuyerNick", ooh.getBuyerNick());
				m.put("BuyerAlipayNo", ooh.getPayNo());
				m.put("OrigPostNo", ooh.getPostNo());
				m.put("ReceiverName", ooh.getReceiverName());
				m.put("ReceiverState", ooh.getReceiverState());
				m.put("ReceiverCity", ooh.getReceiverCity());
				m.put("ReceiverDistrict", ooh.getReceiverDistrict());
				m.put("ReceiverAddress", ooh.getReceiverAddress());
				m.put("ReceiverZip", ooh.getReceiverZip());
				m.put("ReceiverMobile", ooh.getReceiverMobile());
				m.put("ReceiverPhone", ooh.getReceiverTel());
				//2012-06-18 有发货单号的，但却有退款不退货标识，不生成在报表中
				if(WebUtil.isNotNull(ooh.getPostNo())&&WebUtil.isNotNull(oh.getReceiveField6())&&oh.getReceiveField6().equals("false"))
					continue;
			}

			List<OrderNote> onList = this.getHibernateTemplate().find(
					"from OrderNote where id.OrderHeadId = " + oh.getId());
			if (onList != null) {
				for (OrderNote on : onList) {
					m.put(on.getId().getNoteType(), on.getNote());
				}
			}
			List<OrderItem> orderItemList = this.getHibernateTemplate().find(
					"from OrderItem where OrderHeadId = " + oh.getId());
			if (orderItemList != null) {
				List<Map> il = new ArrayList();
				for (OrderItem oi : orderItemList) {
					Map i = new HashMap();
					i.put("SkuCd", oi.getSkuCd());
					i.put("ItemCd", oi.getItemCd());
					i.put("Name", oi.getName());
					i.put("SkuProp1", oi.getSkuProp1());
					i.put("SkuProp2", oi.getSkuProp2());
					i.put("SkuProp3", oi.getSkuProp3());
					i.put("Qty", oi.getQty());
					i.put("Price", oi.getPrice());
					i.put("SubTotal", oi.getSubTotal());
					il.add(i);
				}
				m.put("OrderItem", il);
			}
			result.add(m);

		}
		return result;
	}

	public List searchRefundReportBySku(int storeId, String fromDate,
			String endDate, String completeFromDate, String completeEndDate,
			String orderStatus, String orderType) {
		String hql = "select oh.id,oi.ItemCd,oi.SkuCd,oi.Name,oi.Price,oi.Qty,oh.OrderAmt from OrderHead oh,OrderItem oi where oh.id = oi.OrderHeadId and oh.OrderStatus in ("
				+ orderStatus + ") and oh.StoreId = '" + storeId + "'";
		if (WebUtil.isNotNull(completeFromDate))
			hql = hql + " and oh.CompleteDate >= '" +completeFromDate + "'";
		if (WebUtil.isNotNull(completeEndDate))
			hql = hql + " and oh.CompleteDate < '" + completeEndDate + "'";
		if (WebUtil.isNotNull(fromDate))
			hql = hql + " and oh.RequestDate >= '" + fromDate + "'";
		if (WebUtil.isNotNull(endDate))
			hql = hql + " and oh.RequestDate < '" + endDate + "'";
		if (WebUtil.isNotNull(orderType))
			hql += " and oh.OrderType = '" + orderType + "'";
		hql += " order by oh.id";
		List<Object[]> l = this.getHibernateTemplate().find(hql);
		if (l == null || l.size() == 0)
			return null;
		List<Map> r = new ArrayList();
		Map<Object, Map> k = new HashMap();
		for (Object[] o : l) {
			Double price = o[4] == null ? 0 : Double.parseDouble(o[4]
					.toString());
			Integer qty = o[5] == null ? 0 : Integer.parseInt(o[5].toString());
			Double amt = o[6] == null ? 0 : Double.parseDouble(o[6].toString());
			String id = o[0].toString();
			Map m = k.get((String)o[1]+o[2]);
			if (m == null) {
				m = new HashMap();
				m.put("ItemCd", o[1]);
				m.put("SkuCd", o[2]);
				m.put("SkuName", o[3]);
				m.put("Price", price);
				m.put("Qty", qty);
				m.put("OrderAmt", amt);
				m.put("Order", 1);
				m.put("OrderHeadId", id);
			} else {
				Double p = (Double) m.get("Price");
				Integer q = (Integer) m.get("Qty");
				Double a = (Double) m.get("OrderAmt");
				Integer order = (Integer) m.get("Order");
				String mid = (String) m.get("OrderHeadId");// 用来判断是否重复同一订单
				if (mid.equals(id)) {
					m.put("Qty", q + qty);
				} else {
					if (price < p)
						m.put("Price", price);
					m.put("Qty", q + qty);
					m.put("OrderAmt", amt + a);
					m.put("Order", order + 1);
				}
				m.put("OrderHeadId", id);
			}
			k.put((String)o[1]+o[2], m);
		}
		Iterator iter = k.keySet().iterator();
		while (iter.hasNext()) {
			r.add(k.get(iter.next()));
		}
		return r;

	}

}
