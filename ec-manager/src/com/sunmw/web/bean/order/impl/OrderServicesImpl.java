package com.sunmw.web.bean.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.entity.TbOrder;
import com.sunmw.taobao.entity.TbPromotionDetail;
import com.sunmw.taobao.entity.TbTrade;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.entity.CheckRepeatNo;
import com.sunmw.web.entity.No;
import com.sunmw.web.entity.OrderHead;
import com.sunmw.web.entity.OrderItem;
import com.sunmw.web.entity.OrderNote;
import com.sunmw.web.entity.OrderNotePK;
import com.sunmw.web.entity.OrderPayment;
import com.sunmw.web.entity.OrderPromotion;
import com.sunmw.web.entity.OrderTaobaoinfo;
import com.sunmw.web.entity.StatusItem;
import com.sunmw.web.entity.Store;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class OrderServicesImpl extends HibernateDaoSupport implements
		OrderServices {

	static Logger logger = Logger.getLogger(OrderServicesImpl.class);

	public Map newOrder(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("Flag", "ERROR");
			return result;
		}

		long l = System.currentTimeMillis();
		Date date = new Date();
		List<Map> toList = (List) param.get("OrderItem");
		OrderHead oh = null;
		oh = new OrderHead();
		// 订单来源为手工订单
		oh.setOrigin("MANUAL");
		// 订单状态设置为未审核
		oh.setOrderStatus("START");

		BigDecimal orderAmt = new BigDecimal(0);// 合计明细金额
		// 检查订单明细中的skuCd是否存在
		if (toList != null) {
			for (Map to : toList) {
				if (to.get("Price") != null && to.get("Num") != null)
					orderAmt = orderAmt.add(new BigDecimal(to.get("Price")
							.toString()).multiply(new BigDecimal(to.get("Num")
							.toString())));

			}
		}
		oh.setOrderNo(genericOrderNo("MCOD", 6, "yyyyMMdd"));
		oh.setOrderType("ORDER");
		oh.setCtime(date);
		oh.setStoreId((Integer) param.get("StoreId"));
		oh.setSellerNick((String) param.get("SellerNick"));
		// 支付时间--手工订单都应该是已付款的订单
		oh.setPayTime((Date) param.get("PayTime"));
		// 收货地址
		oh.setReceiverAddress((String) param.get("ReceiverAddress"));
		oh.setReceiverCity((String) param.get("ReceiverCity"));
		oh.setReceiverDistrict((String) param.get("ReceiverDistrict"));
		oh.setReceiverMobile((String) param.get("ReceiverMobile"));
		oh.setReceiverName((String) param.get("ReceiverName"));
		oh.setReceiverState((String) param.get("ReceiverState"));
		oh.setReceiverTel((String) param.get("ReceiverPhone"));
		oh.setReceiverZip((String) param.get("ReceiverZip"));
		oh.setBuyerEmail((String) param.get("BuyerEmail"));
		oh.setBuyerNick((String) param.get("BuyerNick"));
		// 订单商品总金额计算，明细合计
		oh.setOrderAmt(WebUtil.round(orderAmt));
		// 订单商品金额
		if (WebUtil.isNotNull(param.get("TotalFee")))
			oh.setTotalFee((BigDecimal) (param.get("TotalFee")));
		else
			oh.setTotalFee(new BigDecimal(0));
		// 订单支付金额
		if (WebUtil.isNotNull(param.get("Payment")))
			oh.setPayment((BigDecimal) (param.get("Payment")));
		else
			oh.setPayment(new BigDecimal(0));
		// 邮费
		if (WebUtil.isNotNull(param.get("PostFee")))
			oh.setPostFee((BigDecimal) (param.get("PostFee")));
		else
			oh.setPostFee(new BigDecimal(0));
		// 折扣
		if (WebUtil.isNotNull(param.get("DiscountFee")))
			oh.setDiscount((BigDecimal) (param.get("DiscountFee")));
		else
			oh.setDiscount(new BigDecimal(0));
		// 积分
		if (param.get("PointFee") != null)
			oh.setPaymentPoint((Integer) param.get("PointFee"));
		else
			oh.setPaymentPoint(0);
		// 获得积分
		if (param.get("ObtainPoint") != null)
			oh.setObtainPoint((Integer) param.get("ObtainPoint"));
		else
			oh.setObtainPoint(0);
		// VIP
		if (param.get("CustId") != null)
			oh.setCustId((Integer) param.get("CustId"));
		// 订单时间取支付时间
		oh.setOrderDate((Date) param.get("PayTime"));
		oh.setRequestDate(date);
		this.getHibernateTemplate().save(oh);
		Integer orderHeadId = oh.getId();
		// 订单信息结束
		// 订单备注
		if (!WebUtil.isNull(param.get("BuyerMemo"))) {
			OrderNote note = new OrderNote();
			OrderNotePK notePK = new OrderNotePK();
			notePK.setNoteType("BUYER_MEMO");
			notePK.setOrderHeadId(orderHeadId);
			note.setId(notePK);
			note.setNote((String) param.get("BuyerMemo"));
			note.setCtime(date);
			this.getHibernateTemplate().save(note);

		}
		if (!WebUtil.isNull(param.get("BuyerMessage"))) {
			OrderNote note = new OrderNote();
			OrderNotePK notePK = new OrderNotePK();
			notePK.setNoteType("BUYER_MESSAGE");
			notePK.setOrderHeadId(orderHeadId);
			note.setId(notePK);
			note.setNote((String) param.get("BuyerMessage"));
			note.setCtime(date);
			this.getHibernateTemplate().save(note);

		}
		if (!WebUtil.isNull(param.get("SellerMemo"))) {
			OrderNote note = new OrderNote();
			OrderNotePK notePK = new OrderNotePK();
			notePK.setNoteType("SELLER_MEMO");
			notePK.setOrderHeadId(orderHeadId);
			note.setId(notePK);
			note.setNote((String) param.get("SellerMemo"));
			note.setCtime(date);
			this.getHibernateTemplate().save(note);

		}
		if (!WebUtil.isNull(param.get("TradeMemo"))) {
			OrderNote note = new OrderNote();
			OrderNotePK notePK = new OrderNotePK();
			notePK.setNoteType("TRADE_MEMO");
			notePK.setOrderHeadId(orderHeadId);
			note.setId(notePK);
			note.setNote((String) param.get("TradeMemo"));
			note.setCtime(date);
			this.getHibernateTemplate().save(note);

		}
		// 支付货款
		BigDecimal amt = oh.getPayment().subtract(oh.getPostFee());
		// 订单明细
		if (toList != null) {
			// 计算明细总额
			BigDecimal detailAmt = new BigDecimal(0);
			for (Map to : toList) {
				if (WebUtil.isNotNull(to.get("TotalFee")))
					detailAmt = detailAmt
							.add((BigDecimal) (to.get("TotalFee")));
			}
			// 已计算的整单折扣金额
			BigDecimal detailCount = new BigDecimal(0);

			int r = 1;
			for (Map to : toList) {
				OrderItem oi = new OrderItem();
				oi.setCtime(date);
				oi.setItemCd((String) to.get("ItemCd"));
				oi.setName((String) to.get("Title"));
				oi.setOrderHeadId(orderHeadId);
				//oi.setOrderItemStatus("");
				//oi.setOrigOrderItemNo((String) to.get("Oid"));
				//oi.setOrigSkuId((String) to.get("SkuId"));
				if (WebUtil.isNotNull(to.get("Num")))
					oi.setQty((BigDecimal) (to.get("Num")));
				if (WebUtil.isNotNull(to.get("Price")))
					oi.setPrice((BigDecimal) (to.get("Price")));
				if (WebUtil.isNotNull(to.get("Num")))
					oi.setReqQty((BigDecimal) (to.get("Num")));
				if (WebUtil.isNotNull(to.get("AdjustFee")))
					oi.setAdjustFee((BigDecimal) (to.get("AdjustFee")));
				if (WebUtil.isNotNull(to.get("DiscountFee")))
					oi
							.setDiscountPriceAmt((BigDecimal) (to
									.get("DiscountFee")));

				oi.setSkuCd((String) to.get("SkuCd"));
				// 销售属性处理[拖鞋尺码:其它;颜色分类:天蓝色;规格:中号]
				oi.setSkuProp1((String) to.get("SkuProp1"));
				oi.setSkuProp2((String) to.get("SkuProp2"));
				oi.setSkuProp3((String) to.get("SkuProp3"));
				
				if (WebUtil.isNotNull(to.get("TotalFee")))
					oi.setSubTotal((BigDecimal) to.get("TotalFee"));
				// 折扣价格=销售价格-明细金额/明细数量
				oi.setDiscountPrice(oi.getPrice().subtract(
						oi.getSubTotal().divide(oi.getReqQty(), 2,
								BigDecimal.ROUND_HALF_UP)));
				// 折扣小计=销售价格*明细数量-明细金额
				oi.setDiscountPriceAmt(oi.getPrice().multiply(oi.getReqQty())
						.subtract(oi.getSubTotal()));
				// 扣除OrderHead折扣计算
				if (r == toList.size()) {
					// 商品实际总额=支付货款-已计算的整单折扣金额
					oi.setRealPriceAmt(amt.subtract(detailCount));
					// 商品实际单价=商品实际总额/明细数量
					oi.setRealPrice(oi.getRealPriceAmt().divide(oi.getReqQty(),
							2, BigDecimal.ROUND_HALF_UP));
				} else {
					// 商品实际总额=支付货款*（明细金额/明细总额）
					BigDecimal c = amt.multiply(oi.getSubTotal().divide(
							detailAmt, 2, BigDecimal.ROUND_HALF_UP));
					oi.setRealPriceAmt(c);
					// 商品实际单价=商品实际总额/明细数量
					oi.setRealPrice(c.divide(oi.getReqQty(), 2,
							BigDecimal.ROUND_HALF_UP));
				}
				this.getHibernateTemplate().save(oi);

				// 已计算的整单折扣金额
				if (oi.getRealPriceAmt() != null)
					detailCount = detailCount.add(oi.getRealPriceAmt());
				r++;
			}
		}
		result.put("Flag", "SUCCESS");

		return result;
	}

	public Map searchOrder(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		// 字段:订单ID、订单号、订单状态、淘宝订单号、淘宝订单状态、淘宝订单日期、运单号、店铺ID、订单金额、订单状态名称、店铺名称、买家昵称、支付时间
		String fields = "select oh.id,oh.OrderNo,oh.OrderStatus,oh.OrigOrderNo,oh.OrigOrderStatus,oh.RequestDate,oh.ReceiveField4,oh.StoreId,oh.OrderAmt,si.Description,oh.BuyerNick,oh.PayTime";
		StringBuffer hql = new StringBuffer(
				" from OrderHead oh, StatusItem si where oh.OrderType = 'ORDER' and oh.OrderStatus = si.StatusCode and si.StatusTypeId = 'ORDER'");
		StringBuffer con = new StringBuffer();
		// 分公司权限判断
		if (WebUtil.isNull(ul.getUserType())
				|| !ul.getUserType().equals("SYSTEM")) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.CompanyId = " + ul.getCompanyId());
		}
		if (WebUtil.isNotNull(param.get("storeId"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.StoreId = '" + param.get("storeId") + "'");
		}
		if (WebUtil.isNotNull(param.get("tbStatus"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrigOrderStatus = '" + param.get("tbStatus") + "'");
		}
		if (WebUtil.isNotNull(param.get("erpStatus"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrderStatus = '" + param.get("erpStatus") + "'");
		}
		if (WebUtil.isNotNull(param.get("receiveMessage"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.ReceiveField1 = '" + param.get("receiveMessage")
					+ "'");
		}
		if (WebUtil.isNotNull(param.get("orderFromDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.RequestDate >= '" + param.get("orderFromDate")
					+ "'");
		}
		if (WebUtil.isNotNull(param.get("orderEndDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.RequestDate < '" + param.get("orderEndDate") + "'");
		}
		if (WebUtil.isNotNull(param.get("deliveryFromDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.DeliveryDate >= '" + param.get("deliveryFromDate")
					+ "'");
		}
		if (WebUtil.isNotNull(param.get("deliveryEndDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.DeliveryDate < '" + param.get("deliveryEndDate")
					+ "'");
		}
		if (WebUtil.isNotNull(param.get("ORDER_NO"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrderNo like '%" + param.get("ORDER_NO") + "%'");
		}
		if (WebUtil.isNotNull(param.get("TB_ORDER_NO"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrigOrderNo like '%" + param.get("TB_ORDER_NO")
					+ "%'");
		}
		if (WebUtil.isNotNull(param.get("TB_SHIP_NO"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.ReceiveField4 like '%" + param.get("TB_SHIP_NO")
					+ "%'");
		}
		if (WebUtil.isNotNull(param.get("BUYER_NICK"))) {
			if (con.length() > 0)
				con.append(" and ");
			con
					.append(" oh.BuyerNick like '%" + param.get("BUYER_NICK")
							+ "%'");
		}
		if (WebUtil.isNotNull(param.get("RECEIVER_NAME"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.ReceiverName like '%" + param.get("RECEIVER_NAME")
					+ "%'");
		}
		if (WebUtil.isNotNull(param.get("RECEIVER_TEL"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" (oh.ReceiverMobile like '%"
					+ param.get("RECEIVER_TEL")
					+ "%' or oh.ReceiverTel like '%"
					+ param.get("RECEIVER_TEL") + "%')");
		}
		if (WebUtil.isNotNull(param.get("BUYER_ALIPAY_NO"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.PayNo like '%" + param.get("BUYER_ALIPAY_NO")
					+ "%'");
		}
		if (con.length() > 0)
			hql.append(" and " + con.toString());
		hql.append(" order by oh.RequestDate desc");
		List<Long> countList = this.getHibernateTemplate().find(
				"select count(*) " + hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery(hql.toString());
		if (pageRow > 0) {
			q.setFirstResult((currentPage - 1) * pageRow);
			q.setMaxResults(pageRow);
		}
		List<Object[]> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if (l != null && l.size() > 0) {
			Map m = getStatusMap("'TAOBAO_ORDER','PAIPAI_ORDER'");
			// Map storeMap = getStoreMap();
			for (Object[] o : l) {
				OrderHead oh = (OrderHead) o[0];
				// OrderTaobaoinfo ot = (OrderTaobaoinfo) o[1];
				// OrderPayment op = (OrderPayment) o[2];
				StatusItem si = (StatusItem) o[1];
				Map r = new HashMap();
				r.put("orderHeadId", oh.getId());
				r.put("orderNo", oh.getOrderNo());
				r.put("orderStatus", oh.getOrderStatus());
				r.put("origOrderNo", oh.getOrigOrderNo());
				r.put("origOrderStatus", oh.getOrigOrderStatus());
				r.put("requestDate", WebUtil.formatDateString(oh
						.getRequestDate(), "yyyy-MM-dd HH:mm:ss"));
				r.put("shipNo", oh.getPostNo());
				r.put("storeId", oh.getStoreId());
				r.put("orderAmt", oh.getOrderAmt());
				r.put("orderStatusName", si.getDescription());
				r.put("storeName", oh.getSellerNick());
				r.put("buyerNick", oh.getBuyerNick());
				r.put("origOrderStatusName", m.get(oh.getOrigOrderStatus()));
				r.put("payTime", WebUtil.formatDateString(oh.getPayTime(),
						"yyyy-MM-dd HH:mm:ss"));
				r.put("paymentAmt", oh.getPayment());
				r.put("freightAmt", oh.getPostFee());
				r.put("actualTotalAmt", oh.getTotalFee());
				rlist.add(r);
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}

	public Map searchOrderItem(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		String fields = "select oh.id,oh.OrderNo,oh.OrderStatus,oh.OrigOrderNo,oh.OrigOrderStatus,oh.RequestDate,oh.PostNo,oh.StoreId,oh.OrderAmt,oh.BuyerNick,oi.ItemCd,oi.SkuCd,oi.Name,oi.Price,oi.Qty,oh.Payment,oh.TotalFee,oh.PostFee,oh.Discount,oh.PayTime";
		StringBuffer hql = new StringBuffer(
				" from OrderHead oh,OrderItem oi where oh.id = oi.OrderHeadId ");
		StringBuffer con = new StringBuffer();
		// 分公司权限判断
		if (WebUtil.isNull(ul.getUserType())
				|| !ul.getUserType().equals("SYSTEM")) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.CompanyId = " + ul.getCompanyId());
		}
		if (param.get("storeId") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.StoreId = '" + param.get("storeId") + "'");
		}
		if (param.get("tbStatus") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrigOrderStatus = '" + param.get("tbStatus") + "'");
		}
		if (param.get("erpStatus") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrderStatus = '" + param.get("erpStatus") + "'");
		}
		if (param.get("receiveMessage") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.ReceiveField1 = '" + param.get("receiveMessage")
					+ "'");
		}
		if (param.get("orderFromDate") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.RequestDate >= '" + param.get("orderFromDate")
					+ "'");
		}
		if (param.get("orderEndDate") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.RequestDate < '" + param.get("orderEndDate") + "'");
		}
		if (param.get("ORDER_NO") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrderNo like '%" + param.get("ORDER_NO") + "%'");
		}
		if (param.get("TB_ORDER_NO") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrigOrderNo like '%" + param.get("TB_ORDER_NO")
					+ "%'");
		}
		if (param.get("TB_SHIP_NO") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.ReceiveField2 like '%" + param.get("TB_SHIP_NO")
					+ "%'");
		}
		if (param.get("BUYER_NICK") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con
					.append(" oh.BuyerNick like '%" + param.get("BUYER_NICK")
							+ "%'");
		}
		if (param.get("RECEIVER_NAME") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.ReceiverName like '%" + param.get("RECEIVER_NAME")
					+ "%'");
		}
		if (param.get("RECEIVER_TEL") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" (oh.ReceiverMobile like '%"
					+ param.get("RECEIVER_TEL")
					+ "%' or oh.ReceiverPhone like '%"
					+ param.get("RECEIVER_TEL") + "%')");
		}
		if (param.get("BUYER_ALIPAY_NO") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.PayNo like '%" + param.get("BUYER_ALIPAY_NO")
					+ "%'");
		}
		if (param.get("itemCd") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oi.ItemCd like '%" + param.get("itemCd") + "%'");
		}
		if (param.get("skuCd") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oi.SkuCd like '%" + param.get("skuCd") + "%'");
		}
		if (param.get("itemName") != null) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oi.Name like '%" + param.get("itemName") + "%'");
		}
		if (con.length() > 0)
			hql.append(" and " + con.toString());
		hql.append(" order by oh.RequestDate desc,oi.id");
		List<Long> countList = this.getHibernateTemplate().find(
				"select count(*) " + hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery(fields + hql.toString());
		if (pageRow > 0) {
			q.setFirstResult((currentPage - 1) * pageRow);
			q.setMaxResults(pageRow);
		}
		List<Object[]> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if (l != null && l.size() > 0) {
			Map m = getStatusMap("'TAOBAO_ORDER','ORDER'");
			Map storeMap = getStoreMap();
			for (Object[] o : l) {
				Map r = new HashMap();
				r.put("orderHeadId", o[0]);
				r.put("orderNo", o[1]);
				r.put("orderStatus", o[2]);
				r.put("origOrderNo", o[3]);
				r.put("origOrderStatus", o[4]);
				r.put("requestDate", WebUtil.formatDateString((Date) o[5],
						"yyyy-MM-dd HH:mm:ss"));
				r.put("shipNo", o[6]);
				r.put("storeId", o[7]);
				r.put("orderAmt", o[8]);
				r.put("orderStatusName", m.get(o[2]));
				if (storeMap != null)
					r.put("storeName", storeMap.get(o[7]));
				r.put("buyerNick", o[9]);
				r.put("origOrderStatusName", m.get(o[4]));
				// r.put("payTime", WebUtil.formatDateString((Date)o[10],
				// "yyyy-MM-dd HH:mm:ss"));
				r.put("itemCd", o[10]);
				r.put("skuCd", o[11]);
				r.put("itemName", o[12]);
				r.put("itemPrice", o[13]);
				r.put("itemQty", o[14]);
				r.put("payment", o[15]);
				r.put("totalFee", o[16]);
				r.put("postFee", o[17]);
				r.put("discount", o[18]);
				r.put("payTime", WebUtil.formatDateString((Date) o[19],
						"yyyy-MM-dd HH:mm:ss"));
				rlist.add(r);
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}

	// 得到状态
	private Map getStatusMap(String type) {
		Map m = new HashMap();
		List<StatusItem> l = this.getHibernateTemplate().find(
				"from StatusItem where StatusTypeId in (" + type + ")");
		for (StatusItem si : l) {
			m.put(si.getStatusCode(), si.getDescription());
		}
		return m;
	}

	// 得到店铺
	private Map getStoreMap() {
		Map m = new HashMap();
		List<Store> l = this.getHibernateTemplate().find("from Store");
		for (Store s : l) {
			m.put(s.getId().toString(), s.getStoreName());
		}
		return m;
	}

	public String auditedOrder(int orderHeadId) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (l == null || l.size() == 0)
			return "订单未找到";
		OrderHead oh = l.get(0);
		if (!oh.getOrderStatus().equals("START"))
			return "订单状态已改变";
		oh.setOrderStatus("AUDITED");
		this.getHibernateTemplate().update(oh);
		return "success";
	}

	public String auditedOrders(String orderHeadIds) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where OrderStatus = 'START' and id in ("
						+ orderHeadIds + ")");
		if (l == null || l.size() == 0)
			return "订单未找到";
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		for (OrderHead oh : l) {
			oh.setOrderStatus("AUDITED");
			session.update(oh);
		}
		tx.commit();
		session.close();
		return "success";
	}

	public String receiveOrder(int orderHeadId, String receiveMessage) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (l == null || l.size() == 0)
			return "订单未找到";
		OrderHead oh = l.get(0);
		if (!oh.getOrderStatus().equals("AUDITED"))
			return "订单状态已改变";
		oh.setOrderStatus("START");
		oh.setReceiveField1(receiveMessage);
		this.getHibernateTemplate().update(oh);
		return "success";
	}

	public String receiveOrders(String orderHeadIds, String receiveMessage) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where OrderStatus = 'AUDITED' and id in ("
						+ orderHeadIds + ")");
		if (l == null || l.size() == 0)
			return "订单未找到";
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		for (OrderHead oh : l) {
			oh.setOrderStatus("START");
			oh.setReceiveField1(receiveMessage);
			session.update(oh);
		}
		tx.commit();
		session.close();
		return "success";
	}

	public Map getOrderInfo(int orderHeadId) {
		Map result = new HashMap();
		List<OrderHead> orderHeadList = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (orderHeadList == null || orderHeadList.size() == 0)
			return null;
		result.put("OrderHead", orderHeadList.get(0));
		List<StatusItem> silist = this.getHibernateTemplate().find(
				"from StatusItem where (StatusTypeId = 'ORDER' and StatusCode = '"
						+ orderHeadList.get(0).getOrderStatus()
						+ "') or (StatusCode = '"
						+ orderHeadList.get(0).getOrigOrderStatus() + "')");
		if (silist != null) {
			for (StatusItem si : silist) {
				if (si.getStatusTypeId().equals("ORDER"))
					result.put("OrderStatusDesc", si.getDescription());
				else
					// (si.getStatusTypeId().equals("TAOBAO_ORDER"))
					result.put("OrigOrderStatusDesc", si.getDescription());
			}
		}
		// List<OrderTaobaoinfo> otList = this.getHibernateTemplate().find(
		// "from OrderTaobaoinfo where id = " + orderHeadId);
		// if (otList != null) {
		// result.put("OrderTaobaoinfo", otList.get(0));
		// }
		// List<OrderPayment> opList = this.getHibernateTemplate().find(
		// "from OrderPayment where id = " + orderHeadId);
		// if (opList != null) {
		// result.put("OrderPayment", opList.get(0));
		// }
		List<OrderNote> onList = this.getHibernateTemplate().find(
				"from OrderNote where id.OrderHeadId = " + orderHeadId);
		if (onList != null) {
			for (OrderNote on : onList) {
				result.put(on.getId().getNoteType(), on.getNote());
			}
		}
		List<OrderItem> orderItemList = this.getHibernateTemplate().find(
				"from OrderItem where OrderHeadId = " + orderHeadId);
		result.put("OrderItem", orderItemList);
		return result;
	}

	public Map getOrderInfoByTbOrderNo(String tbOrderNo) {
		List<OrderHead> orderHeadList = this.getHibernateTemplate().find(
				"from OrderHead where OrigOrderNo = '" + tbOrderNo + "'");
		if (orderHeadList == null || orderHeadList.size() == 0
				|| orderHeadList.get(0) == null)
			return null;
		Map m = getOrderInfo(orderHeadList.get(0).getId());
		return m;
	}

	public String saveOrderNote(int orderHeadId, String noteType, String note) {
		List<OrderNote> l = this.getHibernateTemplate().find(
				"from OrderNote where id.OrderHeadId = " + orderHeadId
						+ " and id.NoteType = '" + noteType + "'");
		OrderNote on = null;
		if (l != null && l.size() > 0) {
			on = l.get(0);
			on.setMtime(new Date());
			on.setNote(note);
			this.getHibernateTemplate().update(on);
		} else {
			on = new OrderNote();
			OrderNotePK onpk = new OrderNotePK();
			onpk.setNoteType(noteType);
			onpk.setOrderHeadId(orderHeadId);
			on.setId(onpk);
			on.setCtime(new Date());
			on.setNote(note);
			this.getHibernateTemplate().save(on);
		}
		return "success";
	}

	public String updateReveiverAddress(int orderHeadId,
			Map<String, String> param) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (l == null || l.size() == 0)
			return "error";
		OrderHead oh = l.get(0);
		oh.setReceiverName(param.get("ReceiverName"));
		oh.setReceiverState(param.get("ReceiverState"));
		oh.setReceiverCity(param.get("ReceiverCity"));
		oh.setReceiverDistrict(param.get("ReceiverDistrict"));
		oh.setReceiverAddress(param.get("ReceiverAddress"));
		oh.setReceiverZip(param.get("ReceiverZip"));
		oh.setReceiverMobile(param.get("ReceiverMobile"));
		oh.setReceiverTel(param.get("ReceiverPhone"));
		this.getHibernateTemplate().update(oh);
		return "success";
	}

	// 订单取消
	public String cancelOrder(int orderHeadId) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (l == null || l.size() == 0)
			return "error";
		OrderHead oh = l.get(0);
		// 只有未审核状态才可以取消订单
		if (!oh.getOrderStatus().equals("START"))
			return "error";
		oh.setOrderStatus("CANCEL");
		this.getHibernateTemplate().update(oh);
		return "success";
	}

	// 订单恢复
	public String recoverOrder(int orderHeadId) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (l == null || l.size() == 0)
			return "error";
		OrderHead oh = l.get(0);
		// 只有取消状态才可以恢复订单
		if (!oh.getOrderStatus().equals("CANCEL"))
			return "error";
		oh.setOrderStatus("START");
		this.getHibernateTemplate().update(oh);
		return "success";
	}

	// 保存发票信息
	public String saveInvoice(int orderHeadId, String invoice,
			String invoiceMessage) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (l == null || l.size() == 0)
			return "error";
		OrderHead oh = l.get(0);
		if (invoice == null || invoice.trim().length() == 0)
			invoice = "0";
		oh.setInvoiceType(invoice);
		oh.setInvoiceContent(invoiceMessage);
		this.getHibernateTemplate().update(oh);
		return "success";
	}

	// 商品删除
	// 商品更换

	public synchronized String updateOrderFromTb(List<Map> tbOrderList) {
		if (tbOrderList == null || tbOrderList.size() == 0)
			return null;
		StringBuffer tbOrderNos = new StringBuffer();
		Session session = this.getSession();
		// 店铺列表
		List<Store> storeList = session.createQuery("from Store").list();
		Map<Integer, Integer> storeMap = new HashMap();
		if (!WebUtil.isNullForList(storeList)) {
			for (Store s : storeList)
				storeMap.put(s.getId(), s.getCompanyId());
		}
		for (Map m : tbOrderList) {
			long l = System.currentTimeMillis();
			// 订单保留判断
			// A 订单留言
			// B 地址不全
			// C 手工保留
			// D 联系方式不全
			// E 系统错误
			// F 退款联系中
			// G 收件人没有
			// H 其它原因
			// I 订单未支付
			// J 订单没有明细
			Transaction tx = session.beginTransaction();
			Date date = new Date();
			TbTrade trade = (TbTrade) m.get("Trade");
			List<TbOrder> toList = (List) m.get("Order");
			List<TbPromotionDetail> pdList = (List) m.get("OrderPromotion");
			List<OrderHead> ohl = session.createQuery(
					"from OrderHead where OrigOrderNo = '"
							+ trade.getTid().longValue() + "'").list();
			OrderHead oh = null;
			if (ohl == null || ohl.size() == 0) {
				oh = new OrderHead();
				oh.setOrigin("TAOBAO");
			} else
				oh = ohl.get(0);
			boolean isNew = true;
			if (oh.getId() != null)
				isNew = false;
			// 订单保留原因处理
			if (!WebUtil.isNull(trade.getBuyerMessage()))
				oh.setReceiveField1("A");
			if (WebUtil.isNull(trade.getReceiverState())
					|| WebUtil.isNull(trade.getReceiverCity())
					|| WebUtil.isNull(trade.getReceiverDistrict())
					|| WebUtil.isNull(trade.getReceiverAddress()))
				oh.setReceiveField1("B");
			if (WebUtil.isNull(trade.getReceiverMobile())
					&& WebUtil.isNull(trade.getReceiverPhone()))
				oh.setReceiveField1("D");
			if (WebUtil.isNull(trade.getReceiverName()))
				oh.setReceiveField1("G");
			String tradeStatus = trade.getStatus();
			if (tradeStatus.equals("WAIT_BUYER_PAY")
					|| tradeStatus.equals("TRADE_NO_CREATE_PAY"))
				oh.setReceiveField1("I");
			if (toList == null || toList.size() == 0)
				oh.setReceiveField1("J");
			// 订单状态处理
			// START 未审核 订单未支付或有订单留言
			// AUDITED 已审核 正常订单
			// WMS 进入WMS 订单导出
			// CANCEL 订单取消 主动取消订单
			// CLOSED 订单关闭 淘宝关闭订单
			// COMPLETE 已完成 交易成功或卖家已发货
			// '关闭'状态订单
			if (tradeStatus.equals("TRADE_CLOSED")
					|| tradeStatus.equals("TRADE_CLOSED_BY_TAOBAO"))
				oh.setOrderStatus("CLOSED");
			// 卖家已发货,已发货状态
			if (tradeStatus.equals("WAIT_BUYER_CONFIRM_GOODS"))
				oh.setOrderStatus("DELIVERY");
			// 交易成功,订单完成
			if (tradeStatus.equals("TRADE_FINISHED"))
				oh.setOrderStatus("COMPLETE");
			// 订单未处理时才更新订单状态(除'关闭'状态订单)
			if (WebUtil.isNull(oh.getOrderStatus())
					|| oh.getOrderStatus().equals("START")) {
				oh.setOrderStatus("AUDITED");
				if (tradeStatus.equals("WAIT_BUYER_PAY")
						|| tradeStatus.equals("TRADE_NO_CREATE_PAY") ||
						// 已付款状态但保留原因不是未支付，订单状态设置为‘未审核’
						(tradeStatus.equals("WAIT_SELLER_SEND_GOODS")
								&& WebUtil.isNotNull(oh.getReceiveField1()) && !oh
								.getReceiveField1().equals("I"))) {
					oh.setOrderStatus("START");
				}
			}

			BigDecimal orderAmt = new BigDecimal(0);// 合计明细金额
			// 检查订单明细中的skuCd是否存在
			if (toList != null) {
				for (TbOrder to : toList) {
					if (to.getPrice() != null && to.getNum() != null)
						orderAmt = orderAmt.add(to.getPrice().multiply(
								to.getNum()));
					if (to.getOuterSkuId() == null
							|| to.getOuterSkuId().trim().length() == 0) {
						if (tradeStatus.equals("WAIT_BUYER_PAY")
								|| tradeStatus.equals("TRADE_NO_CREATE_PAY")
								|| !WebUtil.isNull(oh.getReceiveField1()))
							oh.setOrderStatus("START");
						oh.setReceiveField1("E");
						break;
					}
				}
			}
			oh.setOrigOrderNo("" + trade.getTid().longValue());
			oh.setOrigOrderType("TAOBAO");
			oh.setOrigOrderStatus(tradeStatus);
			if (WebUtil.isNull(oh.getOrderNo()))
				oh.setOrderNo(genericOrderNo("TBOD", 6, "yyyyMMdd"));
			if (WebUtil.isNull(oh.getOrderType()))
				oh.setOrderType("ORDER");
			if (WebUtil.isNull(oh.getId()))
				oh.setCtime(date);
			else
				oh.setMtime(date);
			oh.setBuyerNick(trade.getBuyerNick());
			oh.setSellerNick(trade.getSellerNick());
			// 发货时间
			if (WebUtil.isNotNull(trade.getConsignTime())
					&& WebUtil.isNull(oh.getDeliveryDate())) {
				oh.setDeliveryDate(trade.getConsignTime());
			}
			// 支付时间
			oh.setPayTime(trade.getPayTime());
			// 支付帐号
			oh.setPayNo(trade.getAlipayNo());
			// 收货地址
			oh.setReceiverAddress(trade.getReceiverAddress());
			oh.setReceiverCity(trade.getReceiverCity());
			oh.setReceiverDistrict(trade.getReceiverDistrict());
			oh.setReceiverMobile(trade.getReceiverMobile());
			oh.setReceiverName(trade.getReceiverName());
			oh.setReceiverState(trade.getReceiverState());
			oh.setReceiverTel(trade.getReceiverPhone());
			oh.setReceiverZip(trade.getReceiverZip());
			oh.setBuyerEmail(trade.getBuyerEmail());
			// 发票
			oh.setInvoiceContent(trade.getInvoiceName());
			if (WebUtil.isNull(oh.getStoreId())) {
				oh.setStoreId(trade.getStoreId());
				oh.setCompanyId(storeMap.get(trade.getStoreId()));
			}
			// 订单商品总金额计算，明细合计
			oh.setOrderAmt(WebUtil.round(orderAmt));
			// 订单商品金额
			oh.setTotalFee(trade.getTotalFee());
			// 订单支付金额
			oh.setPayment(trade.getPayment());
			// 邮费
			oh.setPostFee(trade.getPostFee());
			// 折扣
			oh.setDiscount(trade.getDiscountFee());
			// 积分
			if (trade.getPointFee() != null)
				oh.setPaymentPoint(trade.getPointFee().intValue());
			else
				oh.setPaymentPoint(0);
			if (WebUtil.isNull(oh.getId())) {
				// 订单时间取支付时间
				oh.setOrderDate(trade.getPayTime());
				oh.setRequestDate(trade.getCreated());
			}
			if (isNew)
				session.save(oh);
			else
				session.update(oh);
			Integer orderHeadId = oh.getId();
			// 订单信息结束
			// 订单金额
			OrderPayment op = new OrderPayment();
			op.setActualTotalAmt(trade.getTotalFee());
			op.setPaymentAmt(trade.getPayment());
			op.setFreightAmt(trade.getPostFee());
			op.setId(orderHeadId);
			if (isNew)
				session.save(op);
			else
				session.merge(op);
			// 订单备注
			if (!WebUtil.isNull(trade.getBuyerMemo())) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("BUYER_MEMO");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(trade.getBuyerMemo());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}

			}
			if (!WebUtil.isNull(trade.getBuyerMessage())) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("BUYER_MESSAGE");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(trade.getBuyerMessage());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}
			}
			if (!WebUtil.isNull(trade.getSellerMemo())) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("SELLER_MEMO");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(trade.getSellerMemo());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}
			}
			if (!WebUtil.isNull(trade.getTradeMemo())) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("TRADE_MEMO");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(trade.getTradeMemo());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}
			}

			// 淘宝信息
			List<OrderTaobaoinfo> otList = session.createQuery(
					"from OrderTaobaoinfo where id = " + orderHeadId).list();
			OrderTaobaoinfo ot = null;
			if (WebUtil.isNullForList(otList)) {
				ot = new OrderTaobaoinfo();
			} else
				ot = otList.get(0);
			ot.setId(orderHeadId);
			ot.setAdjustFee(trade.getAdjustFee());
			ot.setAlipayNo(trade.getAlipayNo());
			ot.setAlipayUrl(trade.getAlipayUrl());
			ot.setAvailableConfirmFee(trade.getAvailableConfirmFee());
			ot.setBuyerAlipayNo(trade.getBuyerAlipayNo());
			ot.setBuyerEmail(trade.getBuyerEmail());
			ot.setBuyerFlag(trade.getBuyerFlag());
			ot.setBuyerMemo(trade.getBuyerMemo());
			ot.setBuyerMessage(trade.getBuyerMessage());
			ot.setBuyerNick(trade.getBuyerNick());
			ot.setBuyerObtainPointFee(trade.getBuyerObtainPointFee());
			ot.setBuyerRate(trade.isBuyerRate());
			ot.setCodFee(trade.getCodFee());
			ot.setCodStatus(trade.getCodStatus());
			ot.setCommissionFee(trade.getCommissionFee());
			ot.setConsignTime(trade.getConsignTime());
			ot.setCreated(trade.getCreated());
			ot.setDiscountFee(trade.getDiscountFee());
			ot.setEndTime(trade.getEndTime());
			ot.setHasPostFee(trade.isHasPostFee());
			ot.setIid(trade.getIid());
			ot.setInvoiceName(trade.getInvoiceName());
			ot.setIs3d(trade.isIs3d());
			ot.setModified(trade.getModified());
			ot.setNum(trade.getNum());
			ot.setPayment(trade.getPayment());
			ot.setPayTime(trade.getPayTime());
			ot.setPicPath(trade.getPicPath());
			ot.setPointFee(trade.getPointFee());
			ot.setPostFee(trade.getPostFee());
			ot.setPrice(trade.getPrice());
			ot.setPromotion(trade.getPromotion());
			ot.setRealPointFee(trade.getRealPointFee());
			ot.setReceivedPayment(trade.getReceivedPayment());
			// 只有新增和未审核及地址为空时可以更新收货地址
			if (isNew || oh.getOrderStatus().equals("START")
					|| WebUtil.isNull(ot.getReceiverAddress())) {
				ot.setReceiverAddress(trade.getReceiverAddress());
				ot.setReceiverCity(trade.getReceiverCity());
				ot.setReceiverDistrict(trade.getReceiverDistrict());
				ot.setReceiverMobile(trade.getReceiverMobile());
				ot.setReceiverName(trade.getReceiverName());
				ot.setReceiverPhone(trade.getReceiverPhone());
				ot.setReceiverState(trade.getReceiverState());
				ot.setReceiverZip(trade.getReceiverZip());
			}
			ot.setSellerAlipayNo(trade.getSellerAlipayNo());
			ot.setSellerEmail(trade.getSellerEmail());
			ot.setSellerFlag(trade.getSellerFlag());
			ot.setSellerMemo(trade.getSellerMemo());
			ot.setSellerMobile(trade.getSellerMobile());
			ot.setSellerName(trade.getSellerName());
			ot.setSellerNick(trade.getSellerNick());
			ot.setSellerPhone(trade.getSellerPhone());
			ot.setSellerRate(trade.isSellerRate());
			ot.setShippingType(trade.getShippingType());
			ot.setSnapshot(trade.getSnapshot());
			ot.setSnapshotUrl(trade.getSnapshotUrl());
			ot.setStatus(tradeStatus);
			ot.setTimeoutActionTime(trade.getTimeoutActionTime());
			ot.setTitle(trade.getTitle());
			ot.setTotalFee(trade.getTotalFee());
			ot.setTradeFrom(trade.getTradeFrom());
			ot.setTradeMemo(trade.getTradeMemo());
			if (isNew) {
				ot.setCtime(date);
				session.save(ot);
			} else {
				ot.setMtime(date);
				session.update(ot);
			}
			// 促销使用，系统ID和淘宝ID
			Map tbIdMap = new HashMap();

			// 订单明细
			if (toList != null) {
				StringBuffer oids = new StringBuffer();
				for (TbOrder to : toList) {
					if (oids.length() > 0)
						oids.append(",");
					oids.append("'" + to.getOid().longValue() + "'");
				}
				List<OrderItem> oil = session.createQuery(
						"from OrderItem where OrderHeadId = " + orderHeadId
								+ " and  OrigOrderItemNo in ("
								+ oids.toString() + ")").list();
				Map<String, OrderItem> oim = new HashMap();
				if (oil != null) {
					for (OrderItem oi : oil) {
						oim.put(oi.getOrigOrderItemNo(), oi);
					}
				}
				for (TbOrder to : toList) {
					OrderItem oi = oim.get("" + to.getOid().longValue());
					if (oi == null) {
						oi = new OrderItem();
						oi.setCtime(date);
						oi.setItemCd(to.getOuterIid());
						oi.setName(to.getTitle());
						oi.setOrderHeadId(orderHeadId);
						oi.setOrderItemStatus("");
						oi.setOrigOrderItemNo("" + to.getOid().longValue());
						oi.setOrigSkuId(to.getSkuId());
						oi.setQty(to.getNum());
						oi.setPrice(to.getPrice());
						oi.setReqQty(to.getNum());
						oi.setSkuCd(to.getOuterSkuId());
						// 销售属性处理[拖鞋尺码:其它;颜色分类:天蓝色;规格:中号]
						if (WebUtil.isNotNull(to.getSkuPropertiesName())) {
							String[] props = to.getSkuPropertiesName().split(
									";");
							for (String prop : props) {
								String[] ps = prop.split(":");
								if (ps.length == 2) {
									if (ps[0].indexOf("颜色") >= 0) {
										oi.setSkuProp1(ps[1]);
									} else if (ps[0].indexOf("尺码") >= 0) {
										oi.setSkuProp2(ps[1]);
									} else {
										oi.setSkuProp3(ps[1]);
									}
								}
							}
						}
						// oi.setSkuProp1(to.getSkuPropertiesName());
						oi.setSubTotal(to.getTotalFee());
						session.save(oi);
					} else {
						oi.setMtime(date);
						// oi.setSkuProp1(to.getSkuPropertiesName());
						// 销售属性处理[拖鞋尺码:其它;颜色分类:天蓝色;规格:中号]
						if (WebUtil.isNotNull(to.getSkuPropertiesName())) {
							String[] props = to.getSkuPropertiesName().split(
									";");
							for (String prop : props) {
								String[] ps = prop.split(":");
								if (ps.length == 2) {
									if (ps[0].indexOf("颜色") >= 0) {
										oi.setSkuProp1(ps[1]);
									} else if (ps[0].indexOf("尺码") >= 0) {
										oi.setSkuProp2(ps[1]);
									} else {
										oi.setSkuProp3(ps[1]);
									}
								}
							}
						}
						oi.setName(to.getTitle());
						session.update(oi);
					}
					tbIdMap.put(oi.getOrigOrderItemNo(), oi.getId());

				}
			}

			// 促销
			if (pdList != null) {
				for (TbPromotionDetail pd : pdList) {
					OrderPromotion opm = new OrderPromotion();
					// 订单促销
					if (WebUtil.isNotNull(pd.getTradeId())
							&& WebUtil.isNotNull(pd.getTbPromotionDetailId())
							&& pd.getTradeId().longValue() == pd
									.getTbPromotionDetailId()) {
						opm.setId(orderHeadId);
						opm.setType("ORDER_HEAD");
					}
					// 明细促销
					else {
						// 不在明细中的促销不处理
						if (WebUtil.isNull(pd.getTbPromotionDetailId()))
							continue;
						if (WebUtil.isNull(tbIdMap.get(pd
								.getTbPromotionDetailId().toString())))
							continue;
						opm.setId((Integer) tbIdMap.get(pd
								.getTbPromotionDetailId().toString()));
						opm.setType("ORDER_ITEM");
					}
					opm.setDiscountFee(pd.getDiscountFee());
					opm.setGiftItemName(pd.getGiftItemName());
					opm.setPromotionName(pd.getPromotionName());
					opm.setOrigId(pd.getTbPromotionDetailId().toString());
					session.merge(opm);
				}
			}

			tx.commit();
			if (tbOrderNos.length() > 0)
				tbOrderNos.append(",");
			tbOrderNos.append(oh.getOrigOrderNo());

			long c = System.currentTimeMillis();
			logger.info("time:" + (c - l));
		}
		session.close();
		return tbOrderNos.toString();
	}

	private String genericOrderNo(String prefix, int len, String format) {
		Date d = new Date();
		String date = WebUtil.formatDateString(d, format);
		No no = new No();
		no.setTime(d);
		this.getHibernateTemplate().save(no);
		return prefix + date + WebUtil.addPrefix("" + no.getId(), len, "0");
		// List l = this.getHibernateTemplate().find(
		// "select max(substring(OrderNo,"
		// + (prefix.length() + date.length() + 1)
		// + ")) from OrderHead where OrderNo like '" + prefix
		// + date + "%'");
		// if (l == null || l.size() == 0 || l.get(0) == null) {
		// return prefix + date + WebUtil.addPrefix("1", len, "0");
		// } else {
		// return prefix
		// + date
		// + WebUtil.addPrefix(""
		// + (Integer.parseInt(l.get(0).toString()) + 1), len,
		// "0");
		// }
	}

	@Override
	public Map tbDeliveryOrder(Map param) {
		Map result = new HashMap();
		// 发货状态为已发货，原订单类型为淘宝的订单
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID是空");
			return result;
		}
		List<Object[]> deliveryList = this
				.getHibernateTemplate()
				.find(
						"select OrigOrderNo,PostCompany,PostNo from OrderHead where StoreId = ? and OrderType = ? and OrigOrderType = ? and OrderStatus = ?",
						new Object[] { storeId, "ORDER", "TAOBAO", "DELIVERY" });
		if (!WebUtil.isNullForList(deliveryList)) {
			List<Map> l = new ArrayList();
			for (Object[] obj : deliveryList) {
				Map m = new HashMap();
				m.put("TaobaoNo", obj[0]);
				m.put("CompanyCode", obj[1]);
				m.put("OutSid", obj[2]);
				l.add(m);
			}
			result.put("DeliveryList", l);
		}
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map updateTbDeliveryStatus(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID是空");
			return result;
		}
		String successTaobaoNos = (String) param.get("SuccessOrderNo");
		String errorTaobaoNos = (String) param.get("ErrorOrderNo");
		if (WebUtil.isNotNull(successTaobaoNos)) {
			this.getHibernateTemplate().bulkUpdate(
					"update OrderHead set OrderStatus = ? where StoreId = "
							+ storeId + " and OrigOrderNo in ("
							+ successTaobaoNos + ")", "COMPLETE");
		}
		if (WebUtil.isNotNull(errorTaobaoNos)) {
			this.getHibernateTemplate().bulkUpdate(
					"update OrderHead set OrderStatus = ? where StoreId = "
							+ storeId + " and OrigOrderNo in ("
							+ errorTaobaoNos + ")", "DELIVERY_FAILURE");
		}
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map ppDeliveryOrder(Map param) {
		Map result = new HashMap();
		// 发货状态为已发货，原订单类型为淘宝的订单
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID是空");
			return result;
		}
		List<Object[]> deliveryList = this
				.getHibernateTemplate()
				.find(
						"select OrigOrderNo,PostCompany,PostNo from OrderHead where StoreId = ? and OrderType = ? and OrigOrderType = ? and OrderStatus = ?",
						new Object[] { storeId, "ORDER", "PAIPAI", "DELIVERY" });
		if (!WebUtil.isNullForList(deliveryList)) {
			List<Map> l = new ArrayList();
			for (Object[] obj : deliveryList) {
				Map m = new HashMap();
				m.put("dealCode", obj[0]);
				m.put("logisticsName", obj[1]);
				m.put("logisticsCode", obj[2]);
				l.add(m);
			}
			result.put("DeliveryList", l);
		}
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map updatePpDeliveryStatus(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID是空");
			return result;
		}
		String successTaobaoNos = (String) param.get("SuccessOrderNo");
		String errorTaobaoNos = (String) param.get("ErrorOrderNo");
		if (WebUtil.isNotNull(successTaobaoNos)) {
			this.getHibernateTemplate().bulkUpdate(
					"update OrderHead set OrderStatus = ? where StoreId = "
							+ storeId + " and OrigOrderNo in ("
							+ successTaobaoNos + ")", "COMPLETE");
		}
		if (WebUtil.isNotNull(errorTaobaoNos)) {
			this.getHibernateTemplate().bulkUpdate(
					"update OrderHead set OrderStatus = ? where StoreId = "
							+ storeId + " and OrigOrderNo in ("
							+ errorTaobaoNos + ")", "DELIVERY_FAILURE");
		}
		result.put("Flag", "success");
		return result;
	}

	public Map exportDeliveryRequest(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID是空");
			return result;
		}
		// 已审核状态的订单
		List<Object[]> orderList = this
				.getHibernateTemplate()
				.find(
						"from OrderHead oh,OrderItem oi where oh.id = oi.OrderHeadId and oh.OrderStatus = ? and oh.StoreId = ? and oh.OrderType = ? order by oh.OrderNo,oi.id",
						new Object[] { "AUDITED", storeId, "ORDER" });
		if (WebUtil.isNullForList(orderList)) {
			result.put("Flag", "success");
			return result;
		}
		// 更新订单状态为进入WMS
		StringBuffer orderIds = new StringBuffer();
		Map<Integer, Integer> m = new HashMap();
		List l = new ArrayList();
		for (Object[] obj : orderList) {
			OrderHead oh = (OrderHead) obj[0];
			OrderItem oi = (OrderItem) obj[1];
			if (m.get(oh.getId()) == null) {
				if (orderIds.length() > 0)
					orderIds.append(",");
				orderIds.append(oh.getId());
				m.put(oh.getId(), oh.getId());
			}
		}
		// 订单备注信息
		List<OrderNote> noteList = this.getHibernateTemplate().find(
				"from OrderNote where id.OrderHeadId in ("
						+ orderIds.toString() + ")");
		Map<Integer, String> noteMap = new HashMap();
		if (!WebUtil.isNullForList(noteList)) {
			for (OrderNote on : noteList) {
				String s = noteMap.get(on.getId().getOrderHeadId());
				if (s == null)
					s = "";
				s = s + ";" + on.getNote();
				noteMap.put(on.getId().getOrderHeadId(), s);
			}
		}
		for (Object[] obj : orderList) {
			OrderHead oh = (OrderHead) obj[0];
			OrderItem oi = (OrderItem) obj[1];
			Map o = new HashMap();
			o.put("StoreId", oh.getStoreId());
			o.put("Flag", 1);// 出库FLAG,1：出库，0：退货
			o.put("OrderNo", oh.getOrderNo());
			o.put("OrigOrderNo", oh.getOrigOrderNo());
			o.put("BuyerNick", oh.getBuyerNick());
			o.put("TotalFee", oh.getTotalFee());
			o.put("PostFee", oh.getPostFee());
			o.put("PaymentPoint", oh.getPaymentPoint());
			o.put("OrderStatus", 1);// 订单状态:1:待出库/0:取消出库
			o.put("ReceiverName", oh.getReceiverName());
			o.put("ReceiverState", oh.getReceiverState());
			o.put("ReceiverCity", oh.getReceiverCity());
			o.put("ReceiverDistrict", oh.getReceiverDistrict());
			o.put("ReceiverZip", oh.getReceiverZip());
			o.put("ReceiverAddress", oh.getReceiverAddress());
			o.put("PostMethod", oh.getPostMethod());
			o.put("ReceiverMobile", oh.getReceiverMobile());
			o.put("ReceiverTel", oh.getReceiverTel());
			o.put("OrderDate", oh.getOrderDate());
			o.put("Memo", noteMap.get(oh.getId()));
			// 订单明细
			o.put("OrderItemId", oi.getId());
			o.put("Title", oi.getName());
			o.put("SkuCd", oi.getSkuCd());
			o.put("ColorName", oi.getSkuProp1());
			o.put("SizeName", oi.getSkuProp2());
			o.put("Price", oi.getPrice());
			o.put("Qty", oi.getReqQty());
			o.put("SubTotal", oi.getSubTotal());
			l.add(o);
		}
		this.getHibernateTemplate().bulkUpdate(
				"update OrderHead set OrderStatus = 'WMS' where id in ("
						+ orderIds.toString()
						+ ") and OrderStatus = ? and OrderType = ?",
				new Object[] { "AUDITED", "ORDER" });
		result.put("OrderList", l);
		return result;
	}

	public Map importWmsDelivery(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			return result;
		}
		List<Map> wmsList = (List) param.get("DeliveryResultList");
		if (WebUtil.isNullForList(wmsList))
			return result;
		Map<String, Integer> check = new HashMap();
		for (Map<String, String> m : wmsList) {
			// 根据出库状态更新订单，出库成功或出库失败
			String wmsStatus = m.get("WmsStatus");
			String orderNo = m.get("OrderNo");
			String origOrderNo = m.get("OrigOrderNo");
			String postCompany = m.get("PostCompany");
			String postNo = m.get("PostNo");
			String postNos = m.get("PostNos");
			String deliveryDate = m.get("DeliveryDate");
			if (WebUtil.isNull(deliveryDate)) {
				deliveryDate = WebUtil.formatDateString(new Date(),
						"yyyy-MM-dd HH:mm:ss");
			}
			String wmsFee = m.get("WmsFee");
			String memo = m.get("Memo");
			String orderItemId = m.get("OrderItemId");
			String skuCd = m.get("SkuCd");
			String qty = m.get("Qty");
			List<OrderHead> ol = this.getHibernateTemplate().find(
					"from OrderHead where OrigOrderNo = ? and OrderType = ?",
					new Object[] { origOrderNo, "ORDER" });
			if (WebUtil.isNullForList(ol))
				continue;
			OrderHead oh = ol.get(0);
			// 出库成功时更新明细的出库数据
			if (wmsStatus.equals("1")) {
				if (check.get(origOrderNo) == null) {
					check.put(origOrderNo, oh.getId());
					if (WebUtil.isNotNull(deliveryDate))
						oh.setDeliveryDate(WebUtil.toDateForString(
								deliveryDate, "yyyy-MM-dd HH:mm:ss"));
					if (WebUtil.isNotNull(postCompany))
						oh.setPostCompany(postCompany);
					if (WebUtil.isNotNull(postNo))
						oh.setPostNo(postNo);
					if (WebUtil.isNotNull(postNos))
						oh.setPostNos(postNos);
					if (WebUtil.isNotNull(wmsFee))
						oh.setReceiveField2(wmsFee);
					if (WebUtil.isNotNull(memo))
						oh.setReceiveField3(memo);
					if (oh.getOrderStatus() != null
							&& oh.getOrderStatus().equals("WMS"))
						oh.setOrderStatus("DELIVERY");
					this.getHibernateTemplate().update(oh);
					if (WebUtil.isNotNull(orderItemId)
							&& WebUtil.isNotNull(skuCd)
							&& WebUtil.isNotNull(qty)) {
						this
								.getHibernateTemplate()
								.bulkUpdate(
										"update OrderItem set RefQty = "
												+ qty
												+ ",Qty = "
												+ qty
												+ " where SkuCd = ? and OrderHeadId = ?",
										new Object[] { skuCd,
												check.get(origOrderNo) });
					} else {
						this
								.getHibernateTemplate()
								.bulkUpdate(
										"update OrderItem set RefQty = ReqQty where OrderHeadId = ?",
										check.get(origOrderNo));
					}
				} else {
					if (WebUtil.isNotNull(orderItemId)
							&& WebUtil.isNotNull(skuCd)
							&& WebUtil.isNotNull(qty)) {
						this
								.getHibernateTemplate()
								.bulkUpdate(
										"update OrderItem set RefQty = "
												+ qty
												+ ",Qty = "
												+ qty
												+ " where SkuCd = ? and OrderHeadId = ?",
										new Object[] { skuCd,
												check.get(origOrderNo) });
					} else {
						this
								.getHibernateTemplate()
								.bulkUpdate(
										"update OrderItem set RefQty = ReqQty where OrderHeadId = ?",
										check.get(origOrderNo));
					}
				}
			}
			// 出库失败
			else {
				if (check.get(origOrderNo) == null) {
					check.put(origOrderNo, ol.get(0).getId());
					this
							.getHibernateTemplate()
							.bulkUpdate(
									"update OrderHead set OrderStatus = 'DELIVERY_FAILURE' where OrderStatus = ? and OrigOrderNo = ? and OrderType = ?",
									new Object[] { "WMS", origOrderNo, "ORDER" });
				}
			}
		}
		return result;
	}

	public Map exportReturnRequest(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID是空");
			return result;
		}
		// 已审核状态的订单
		List<Object[]> orderList = this
				.getHibernateTemplate()
				.find(
						"from OrderHead oh,OrderItem oi where oh.id = oi.OrderHeadId and oh.OrderStatus = ? and oh.StoreId = ? and oh.OrderType = ? order by oh.OrderNo,oi.id",
						new Object[] { "REFUND_AUDITED", storeId, "REFUND" });
		if (WebUtil.isNullForList(orderList)) {
			result.put("Flag", "success");
			return result;
		}
		// 更新订单状态为进入WMS
		StringBuffer orderIds = new StringBuffer();
		Map<Integer, Integer> m = new HashMap();
		List l = new ArrayList();
		for (Object[] obj : orderList) {
			OrderHead oh = (OrderHead) obj[0];
			OrderItem oi = (OrderItem) obj[1];
			if (m.get(oh.getId()) == null) {
				if (orderIds.length() > 0)
					orderIds.append(",");
				orderIds.append(oh.getId());
				m.put(oh.getId(), oh.getId());
			}
		}
		// 订单备注信息
		List<OrderNote> noteList = this.getHibernateTemplate().find(
				"from OrderNote where id.OrderHeadId in ("
						+ orderIds.toString() + ")");
		Map<Integer, String> noteMap = new HashMap();
		if (!WebUtil.isNullForList(noteList)) {
			for (OrderNote on : noteList) {
				String s = noteMap.get(on.getId().getOrderHeadId());
				if (s == null)
					s = "";
				s = s + "," + on.getNote();
			}
		}
		for (Object[] obj : orderList) {
			OrderHead oh = (OrderHead) obj[0];
			OrderItem oi = (OrderItem) obj[1];
			Map o = new HashMap();
			o.put("StoreId", oh.getStoreId());
			o.put("Flag", 1);// 出库FLAG,1：出库，0：退货
			o.put("OrderNo", oh.getOrderNo());
			o.put("OrigOrderNo", oh.getOrigOrderNo());
			o.put("OrderStatus", 1);// 订单状态:1:待出库/0:取消出库
			o.put("TotalFee", oh.getTotalFee());
			o.put("PostFee", oh.getPostFee());
			o.put("BuyerNick", oh.getBuyerNick());
			o.put("PaymentPoint", oh.getPaymentPoint());
			o.put("ReceiverName", oh.getReceiverName());
			o.put("ReceiverState", oh.getReceiverState());
			o.put("ReceiverCity", oh.getReceiverCity());
			o.put("ReceiverDistrict", oh.getReceiverDistrict());
			o.put("ReceiverZip", oh.getReceiverZip());
			o.put("ReceiverAddress", oh.getReceiverAddress());
			o.put("PostMethod", oh.getPostMethod());
			o.put("ReceiverMobile", oh.getReceiverMobile());
			o.put("ReceiverTel", oh.getReceiverTel());
			o.put("OrderDate", oh.getOrderDate());
			o.put("Memo", noteMap.get(oh.getId()));
			// 订单明细
			o.put("OrderItemId", oi.getId());
			o.put("Title", oi.getName());
			o.put("SkuCd", oi.getSkuCd());
			o.put("ColorName", oi.getSkuProp1());
			o.put("SizeName", oi.getSkuProp2());
			o.put("Price", oi.getPrice());
			o.put("Qty", oi.getReqQty());
			o.put("SubTotal", oi.getSubTotal());
			l.add(o);
		}
		this.getHibernateTemplate().bulkUpdate(
				"update OrderHead set OrderStatus = 'REFUND_PROCESS' where id in ("
						+ orderIds.toString()
						+ ") and OrderStatus = ? and OrderType = ?",
				new Object[] { "REFUND_AUDITED", "REFUND" });
		result.put("ReturnResultList", l);
		return result;
	}

	public Map importWmsReturn(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			return result;
		}
		List<Map> wmsList = (List) param.get("ReturnResultList");
		if (WebUtil.isNullForList(wmsList))
			return result;
		Map<String, Integer> check = new HashMap();
		for (Map<String, String> m : wmsList) {
			// 根据出库状态更新订单，出库成功或出库失败
			String wmsStatus = m.get("WmsStatus");
			String orderNo = m.get("OrderNo");
			String origOrderNo = m.get("OrigOrderNo");
			String postCompany = m.get("PostCompany");
			String postNo = m.get("PostNo");
			String postNos = m.get("PostNos");
			String deliveryDate = m.get("DeliveryDate");
			String wmsFee = m.get("WmsFee");
			String memo = m.get("Memo");
			String orderItemId = m.get("OrderItemId");
			String skuCd = m.get("SkuCd");
			String qty = m.get("Qty");
			List<OrderHead> ol = this
					.getHibernateTemplate()
					.find(
							"from OrderHead where OrderStatus = ? and OrigOrderNo = ? and OrderType = ?",
							new Object[] { "REFUND_PROCESS", origOrderNo,
									"REFUND" });
			if (WebUtil.isNullForList(ol))
				continue;
			// 出库成功时更新明细的出库数据
			if (wmsStatus.equals("1")) {
				if (check.get(origOrderNo) == null) {
					check.put(origOrderNo, ol.get(0).getId());
					this
							.getHibernateTemplate()
							.bulkUpdate(
									"update OrderHead set OrderStatus = 'REFUND_COMPLETE',DeliveryDate = '"
											+ deliveryDate
											+ "',PostCompany = '"
											+ postCompany
											+ "',PostNo = '"
											+ postNo
											+ "',PostNos = '"
											+ postNos
											+ "',ReceiveField2 = '"
											+ wmsFee
											+ "',ReceiveField3 = '"
											+ memo
											+ "' where OrderStatus = ? and OrigOrderNo = ? and OrderType = ?",
									new Object[] { "REFUND_PROCESS",
											origOrderNo, "REFUND" });
					if (WebUtil.isNotNull(orderItemId)
							&& WebUtil.isNotNull(skuCd)
							&& WebUtil.isNotNull(qty)) {
						this
								.getHibernateTemplate()
								.bulkUpdate(
										"update OrderItem set RefQty = "
												+ qty
												+ ",Qty = "
												+ qty
												+ " where id = ? and SkuCd = ? and OrderHeadId = ?",
										new Object[] {
												new Integer(orderItemId),
												skuCd, check.get(origOrderNo) });
					} else {
						this
								.getHibernateTemplate()
								.bulkUpdate(
										"update OrderItem set RefQty = ReqQty where OrderHeadId = ?",
										check.get(origOrderNo));
					}
				} else {
					if (WebUtil.isNotNull(orderItemId)
							&& WebUtil.isNotNull(skuCd)
							&& WebUtil.isNotNull(qty)) {
						this
								.getHibernateTemplate()
								.bulkUpdate(
										"update OrderItem set RefQty = "
												+ qty
												+ ",Qty = "
												+ qty
												+ " where id = ? and SkuCd = ? and OrderHeadId = ?",
										new Object[] {
												new Integer(orderItemId),
												skuCd, check.get(origOrderNo) });
					} else {
						this
								.getHibernateTemplate()
								.bulkUpdate(
										"update OrderItem set RefQty = ReqQty where OrderHeadId = ?",
										check.get(origOrderNo));
					}
				}
			}
			// 出库失败
			else {
				if (check.get(origOrderNo) == null) {
					check.put(origOrderNo, ol.get(0).getId());
					this
							.getHibernateTemplate()
							.bulkUpdate(
									"update OrderHead set OrderStatus = 'REFUND_CLOSED' where OrderStatus = ? and OrigOrderNo = ? and OrderType = ?",
									new Object[] { "REFUND_PROCESS",
											origOrderNo, "REFUND" });
				}
			}
		}
		return result;
	}

	// 单个订单从Taobao同步，在下载淘宝订单时就调用，提高速度
	public Map updateSingleOrderFromTb(Map trade, Integer storeId) {
		Map result = new HashMap();
		try {
			// Session session = this.getSession();
			long l = System.currentTimeMillis();
			// 订单保留判断
			// A 订单留言
			// B 地址不全
			// C 手工保留
			// D 联系方式不全
			// E 系统错误
			// F 退款联系中
			// G 收件人没有
			// H 其它原因
			// I 订单未支付
			// J 订单没有明细
			// Transaction tx = session.beginTransaction();
			Date date = new Date();
			List<Map> toList = (List) trade.get("Orders");
			List<Map> pdList = (List) trade.get("PromotionDetails");
			List<OrderHead> ohl = this.getHibernateTemplate().find(
					"from OrderHead where OrigOrderNo = '" + trade.get("Tid")
							+ "'");
			OrderHead oh = null;
			if (ohl == null || ohl.size() == 0) {
				oh = new OrderHead();
				oh.setOrigin("TAOBAO");
			} else
				oh = ohl.get(0);
			boolean isNew = true;
			if (oh.getId() != null)
				isNew = false;
			// 订单保留原因处理
			if (!WebUtil.isNull(trade.get("BuyerMessage")))
				oh.setReceiveField1("A");
			if (WebUtil.isNull(trade.get("ReceiverState"))
					|| WebUtil.isNull(trade.get("ReceiverCity"))
					|| WebUtil.isNull(trade.get("ReceiverDistrict"))
					|| WebUtil.isNull(trade.get("ReceiverAddress")))
				oh.setReceiveField1("B");
			if (WebUtil.isNull(trade.get("ReceiverMobile"))
					&& WebUtil.isNull(trade.get("ReceiverPhone")))
				oh.setReceiveField1("D");
			if (WebUtil.isNull(trade.get("ReceiverName")))
				oh.setReceiveField1("G");
			String tradeStatus = (String) trade.get("Status");
			if (tradeStatus.equals("WAIT_BUYER_PAY")
					|| tradeStatus.equals("TRADE_NO_CREATE_PAY"))
				oh.setReceiveField1("I");
			if (toList == null || toList.size() == 0)
				oh.setReceiveField1("J");
			// 订单状态处理
			// START 未审核 订单未支付或有订单留言
			// AUDITED 已审核 正常订单
			// WMS 进入WMS 订单导出
			// CANCEL 订单取消 主动取消订单
			// CLOSED 订单关闭 淘宝关闭订单
			// COMPLETE 已完成 交易成功或卖家已发货
			// '关闭'状态订单
			if (tradeStatus.equals("TRADE_CLOSED")
					|| tradeStatus.equals("TRADE_CLOSED_BY_TAOBAO"))
				oh.setOrderStatus("CLOSED");
			// 卖家已发货,已发货状态
			if (tradeStatus.equals("WAIT_BUYER_CONFIRM_GOODS")
					|| tradeStatus.equals("WAIT_BUYER_CONFIRM_GOODS_ACOUNTED"))// 分销使用，已付款（已分账），已发货。只对代销分账支持
				oh.setOrderStatus("DELIVERY");
			// 交易成功,订单完成
			if (tradeStatus.equals("TRADE_FINISHED"))
				oh.setOrderStatus("COMPLETE");
			// 订单未处理时才更新订单状态(除'关闭'状态订单)
			if (WebUtil.isNull(oh.getOrderStatus())
					|| oh.getOrderStatus().equals("START")) {
				oh.setOrderStatus("AUDITED");
				if (tradeStatus.equals("WAIT_BUYER_PAY")
						|| tradeStatus.equals("TRADE_NO_CREATE_PAY")
						|| tradeStatus
								.equals("WAIT_SELLER_SEND_GOODS_ACOUNTED") || // 分销使用，已付款（已分账），待发货。只对代销分账支持
						// 已付款状态但保留原因不是未支付，订单状态设置为‘未审核’
						(tradeStatus.equals("WAIT_SELLER_SEND_GOODS")
								&& WebUtil.isNotNull(oh.getReceiveField1()) && !oh
								.getReceiveField1().equals("I"))) {
					oh.setOrderStatus("START");
				}
			}

			BigDecimal orderAmt = new BigDecimal(0);// 合计明细金额
			// 检查订单明细中的skuCd是否存在
			if (toList != null) {
				for (Map to : toList) {
					if (to.get("Price") != null && to.get("Num") != null)
						orderAmt = orderAmt.add(new BigDecimal(to.get("Price")
								.toString()).multiply(new BigDecimal(to.get(
								"Num").toString())));
					if (to.get("OuterSkuId") == null
							|| to.get("OuterSkuId").toString().trim().length() == 0) {
						if (tradeStatus.equals("WAIT_BUYER_PAY")
								|| tradeStatus.equals("TRADE_NO_CREATE_PAY")
								|| !WebUtil.isNull(oh.getReceiveField1()))
							oh.setOrderStatus("START");
						oh.setReceiveField1("E");
						continue;
					}
				}
			}
			oh.setOrigOrderNo((String) trade.get("Tid"));
			oh.setOrigOrderType((String) trade.get("OrigOrderType"));
			oh.setOrigOrderStatus(tradeStatus);
			if (WebUtil.isNull(oh.getOrderNo()))
				oh.setOrderNo(genericOrderNo("TBOD", 6, "yyyyMMdd"));
			if (WebUtil.isNull(oh.getOrderType()))
				oh.setOrderType("ORDER");
			if (WebUtil.isNull(oh.getId()))
				oh.setCtime(date);
			else
				oh.setMtime(date);
			oh.setBuyerNick((String) trade.get("BuyerNick"));
			oh.setSellerNick((String) trade.get("SellerNick"));
			// 发货时间
			if (WebUtil.isNotNull(trade.get("ConsignTime"))
					&& WebUtil.isNull(oh.getDeliveryDate())) {
				oh.setDeliveryDate((Date) trade.get("ConsignTime"));
			}
			// 支付时间
			oh.setPayTime((Date) trade.get("PayTime"));
			// 支付帐号
			oh.setPayNo((String) trade.get("AlipayNo"));
			// 收货地址
			oh.setReceiverAddress((String) trade.get("ReceiverAddress"));
			oh.setReceiverCity((String) trade.get("ReceiverCity"));
			oh.setReceiverDistrict((String) trade.get("ReceiverDistrict"));
			oh.setReceiverMobile((String) trade.get("ReceiverMobile"));
			oh.setReceiverName((String) trade.get("ReceiverName"));
			oh.setReceiverState((String) trade.get("ReceiverState"));
			oh.setReceiverTel((String) trade.get("ReceiverPhone"));
			oh.setReceiverZip((String) trade.get("ReceiverZip"));
			oh.setBuyerEmail((String) trade.get("BuyerEmail"));
			// 发票
			oh.setInvoiceContent((String) trade.get("InvoiceName"));
			if (WebUtil.isNull(oh.getStoreId())) {
				oh.setStoreId(storeId);
				List<Integer> sl = this.getHibernateTemplate().find(
						"select CompanyId from Store where id = " + storeId);
				if (!WebUtil.isNullForList(sl))
					oh.setCompanyId(sl.get(0));
			}
			// 订单商品总金额计算，明细合计
			oh.setOrderAmt(WebUtil.round(orderAmt));
			// 订单商品金额
			if (WebUtil.isNotNull(trade.get("TotalFee")))
				oh.setTotalFee((BigDecimal) (trade.get("TotalFee")));
			else
				oh.setTotalFee(new BigDecimal(0));
			// 订单支付金额
			if (WebUtil.isNotNull(trade.get("Payment")))
				oh.setPayment((BigDecimal) (trade.get("Payment")));
			else
				oh.setPayment(new BigDecimal(0));
			// 邮费
			if (WebUtil.isNotNull(trade.get("PostFee")))
				oh.setPostFee((BigDecimal) (trade.get("PostFee")));
			else
				oh.setPostFee(new BigDecimal(0));
			// 折扣
			if (WebUtil.isNotNull(trade.get("DiscountFee")))
				oh.setDiscount((BigDecimal) (trade.get("DiscountFee")));
			else
				oh.setDiscount(new BigDecimal(0));
			// 积分
			if (trade.get("PointFee") != null)
				oh.setPaymentPoint((Integer) trade.get("PointFee"));
			else
				oh.setPaymentPoint(0);
			// 获得的积分
			if (trade.get("ObtainPoint") != null)
				oh.setObtainPoint((Integer) trade.get("ObtainPoint"));
			else
				oh.setObtainPoint(0);
			// 订单时间取支付时间
			oh.setOrderDate((Date) trade.get("PayTime"));
			if (WebUtil.isNull(oh.getId())) {
				oh.setRequestDate((Date) trade.get("Created"));
			}
			if (isNew) {
				// 此处用于防止重复单号
				CheckRepeatNo crn = new CheckRepeatNo(oh.getOrigOrderNo(),
						"TBOD");
				this.getHibernateTemplate().save(crn);
				this.getHibernateTemplate().save(oh);
			} else
				this.getHibernateTemplate().update(oh);
			Integer orderHeadId = oh.getId();
			// 订单信息结束
			// 订单备注
			if (!WebUtil.isNull(trade.get("BuyerMemo"))) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("BUYER_MEMO");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote((String) trade.get("BuyerMemo"));
				if (isNew) {
					note.setCtime(date);
					this.getHibernateTemplate().save(note);
				} else {
					note.setMtime(date);
					this.getHibernateTemplate().merge(note);
				}

			}
			if (!WebUtil.isNull(trade.get("BuyerMessage"))) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("BUYER_MESSAGE");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote((String) trade.get("BuyerMessage"));
				if (isNew) {
					note.setCtime(date);
					this.getHibernateTemplate().save(note);
				} else {
					note.setMtime(date);
					this.getHibernateTemplate().merge(note);
				}
			}
			if (!WebUtil.isNull(trade.get("SellerMemo"))) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("SELLER_MEMO");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote((String) trade.get("SellerMemo"));
				if (isNew) {
					note.setCtime(date);
					this.getHibernateTemplate().save(note);
				} else {
					note.setMtime(date);
					this.getHibernateTemplate().merge(note);
				}
			}
			if (!WebUtil.isNull(trade.get("TradeMemo"))) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("TRADE_MEMO");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote((String) trade.get("TradeMemo"));
				if (isNew) {
					note.setCtime(date);
					this.getHibernateTemplate().save(note);
				} else {
					note.setMtime(date);
					this.getHibernateTemplate().merge(note);
				}
			}
			// 促销使用，系统ID和淘宝ID
			Map tbIdMap = new HashMap();
			// 支付货款
			BigDecimal amt = oh.getPayment().subtract(oh.getPostFee());
			// 订单明细
			if (toList != null) {
				StringBuffer oids = new StringBuffer();
				for (Map to : toList) {
					if (oids.length() > 0)
						oids.append(",");
					oids.append("'" + to.get("Oid") + "'");
				}
				List<OrderItem> oil = this.getHibernateTemplate().find(
						"from OrderItem where OrderHeadId = " + orderHeadId
								+ " and  OrigOrderItemNo in ("
								+ oids.toString() + ")");
				Map<String, OrderItem> oim = new HashMap();
				if (oil != null) {
					for (OrderItem oi : oil) {
						oim.put(oi.getOrigOrderItemNo(), oi);
					}
				}
				// 计算明细总额
				BigDecimal detailAmt = new BigDecimal(0);
				for (Map to : toList) {
					if (WebUtil.isNotNull(to.get("TotalFee")))
						detailAmt = detailAmt.add((BigDecimal) (to
								.get("TotalFee")));
				}
				// 已计算的整单折扣金额
				BigDecimal detailCount = new BigDecimal(0);

				int r = 1;
				for (Map to : toList) {
					OrderItem oi = oim.get(to.get("Oid"));
					if (oi == null) {
						oi = new OrderItem();
						oi.setCtime(date);
						oi.setItemCd((String) to.get("OuterIid"));
						oi.setName((String) to.get("Title"));
						oi.setOrderHeadId(orderHeadId);
						oi.setOrderItemStatus("");
						oi.setOrigOrderItemNo((String) to.get("Oid"));
						oi.setOrigSkuId((String) to.get("SkuId"));
						if (WebUtil.isNotNull(to.get("Num")))
							oi.setQty((BigDecimal) (to.get("Num")));
						if (WebUtil.isNotNull(to.get("Price")))
							oi.setPrice((BigDecimal) (to.get("Price")));
						if (WebUtil.isNotNull(to.get("Num")))
							oi.setReqQty((BigDecimal) (to.get("Num")));
						if (WebUtil.isNotNull(to.get("AdjustFee")))
							oi.setAdjustFee((BigDecimal) (to.get("AdjustFee")));
						if (WebUtil.isNotNull(to.get("DiscountFee")))
							oi.setDiscountPriceAmt((BigDecimal) (to
									.get("DiscountFee")));

						oi.setSkuCd((String) to.get("OuterSkuId"));
						// 销售属性处理[拖鞋尺码:其它;颜色分类:天蓝色;规格:中号]
						if (WebUtil.isNotNull(to.get("SkuPropertiesName"))) {
							String[] props = to.get("SkuPropertiesName")
									.toString().split(";");
							for (String prop : props) {
								String[] ps = prop.split(":");
								if (ps.length == 2) {
									if (ps[0].indexOf("颜色") >= 0) {
										oi.setSkuProp1(ps[1]);
									} else if (ps[0].indexOf("尺码") >= 0) {
										oi.setSkuProp2(ps[1]);
									} else {
										oi.setSkuProp3(ps[1]);
									}
								}
							}
						}
						// oi.setSkuProp1(to.getSkuPropertiesName());
						if (WebUtil.isNotNull(to.get("TotalFee")))
							oi.setSubTotal((BigDecimal) to.get("TotalFee"));
						// 折扣价格=销售价格-明细金额/明细数量
						oi.setDiscountPrice(oi.getPrice().subtract(
								oi.getSubTotal().divide(oi.getReqQty(), 2,
										BigDecimal.ROUND_HALF_UP)));
						// 折扣小计=销售价格*明细数量-明细金额
						oi.setDiscountPriceAmt(oi.getPrice().multiply(
								oi.getReqQty()).subtract(oi.getSubTotal()));
						// 扣除OrderHead折扣计算
						if (r == toList.size()) {
							// 商品实际总额=支付货款-已计算的整单折扣金额
							oi.setRealPriceAmt(amt.subtract(detailCount));
							// 商品实际单价=商品实际总额/明细数量
							oi.setRealPrice(oi.getRealPriceAmt()
									.divide(oi.getReqQty(), 2,
											BigDecimal.ROUND_HALF_UP));
						} else {
							// 商品实际总额=支付货款*（明细金额/明细总额）
							BigDecimal c = amt.multiply(oi.getSubTotal()
									.divide(detailAmt, 2,
											BigDecimal.ROUND_HALF_UP));
							oi.setRealPriceAmt(c);
							// 商品实际单价=商品实际总额/明细数量
							oi.setRealPrice(c.divide(oi.getReqQty(), 2,
									BigDecimal.ROUND_HALF_UP));
						}
						this.getHibernateTemplate().save(oi);
					} else {
						oi.setMtime(date);
						// oi.setSkuProp1(to.getSkuPropertiesName());
						// 销售属性处理[拖鞋尺码:其它;颜色分类:天蓝色;规格:中号]
						if (WebUtil.isNotNull(to.get("SkuPropertiesName"))) {
							String[] props = to.get("SkuPropertiesName")
									.toString().split(";");
							for (String prop : props) {
								String[] ps = prop.split(":");
								if (ps.length == 2) {
									if (ps[0].indexOf("颜色") >= 0) {
										oi.setSkuProp1(ps[1]);
									} else if (ps[0].indexOf("尺码") >= 0) {
										oi.setSkuProp2(ps[1]);
									} else {
										oi.setSkuProp3(ps[1]);
									}
								}
							}
						}
						oi.setName((String) to.get("Title"));
						this.getHibernateTemplate().update(oi);
					}
					tbIdMap.put(oi.getOrigOrderItemNo(), oi.getId());
					// 已计算的整单折扣金额
					if (oi.getRealPriceAmt() != null)
						detailCount = detailCount.add(oi.getRealPriceAmt());
					r++;
				}
			}

			// 促销
			if (pdList != null) {
				for (Map pd : pdList) {
					OrderPromotion opm = new OrderPromotion();
					// 订单促销
					if (WebUtil.isNotNull(pd.get("Id"))
							&& pd.get("Id") == trade.get("Tid")) {
						opm.setId(orderHeadId);
						opm.setType("ORDER_HEAD");
					}
					// 明细促销
					else {
						// 不在明细中的促销不处理
						if (WebUtil.isNull(pd.get("Id")))
							continue;
						if (WebUtil
								.isNull(tbIdMap.get(pd.get("Id").toString())))
							continue;
						opm.setId((Integer) tbIdMap
								.get(pd.get("Id").toString()));
						opm.setType("ORDER_ITEM");
					}
					if (WebUtil.isNotNull(pd.get("DiscountFee")))
						opm.setDiscountFee((BigDecimal) pd.get("DiscountFee"));
					else
						opm.setDiscountFee(new BigDecimal(0));
					opm.setGiftItemName((String) pd.get("GiftItemName"));
					opm.setPromotionName((String) pd.get("PromotionName"));
					opm.setOrigId(pd.get("Id").toString());
					this.getHibernateTemplate().merge(opm);
				}
			}

			// tx.commit();

			long c = System.currentTimeMillis();
			logger.info("time:" + (c - l));
			// session.close();
			result.put("Flag", "SUCCESS");
			return result;
		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("Flag", "ERROR");
		return result;
	}

	@Override
	public Map updateSingleOrderFromPp(Map order, Integer storeId) {
		Map result = new HashMap();
		try {
			Session session = this.getSession();
			long l = System.currentTimeMillis();
			// 订单保留判断
			// A 订单留言
			// B 地址不全
			// C 手工保留
			// D 联系方式不全
			// E 系统错误
			// F 退款联系中
			// G 收件人没有
			// H 其它原因
			// I 订单未支付
			// J 订单没有明细
			Transaction tx = session.beginTransaction();
			Date date = new Date();
			List<Map> toList = (List) order.get("itemList");
			List<OrderHead> ohl = session.createQuery(
					"from OrderHead where OrigOrderNo = '"
							+ order.get("dealCode") + "'").list();
			OrderHead oh = null;
			if (ohl == null || ohl.size() == 0) {
				oh = new OrderHead();
				oh.setOrigin("PAIPAI");
			} else
				oh = ohl.get(0);
			boolean isNew = true;
			if (oh.getId() != null)
				isNew = false;
			// 订单保留原因处理
			if (!WebUtil.isNull(order.get("dealNote")))
				oh.setReceiveField1("A");
			if (WebUtil.isNull(order.get("receiverAddress")))
				oh.setReceiveField1("B");
			if (WebUtil.isNull(order.get("receiverMobile"))
					&& WebUtil.isNull(order.get("receiverPhone")))
				oh.setReceiveField1("D");
			if (WebUtil.isNull(order.get("receiverName")))
				oh.setReceiveField1("G");
			if (order.get("dealState").equals("DS_WAIT_BUYER_PAY")
					|| order.get("dealState").equals("DS_UNKNOWN"))
				oh.setReceiveField1("I");
			if (WebUtil.isNullForList(toList))
				oh.setReceiveField1("J");
			// 订单状态处理
			// START 未审核 订单未支付或有订单留言
			// AUDITED 已审核 正常订单
			// WMS 进入WMS 订单导出
			// CANCEL 订单取消 主动取消订单
			// CLOSED 订单关闭 淘宝关闭订单
			// COMPLETE 已完成 交易成功或卖家已发货
			// '关闭'状态订单
			if (order.get("dealState").equals("DS_DEAL_CANCELLED")
					|| order.get("dealState").equals("DS_CLOSED"))
				oh.setOrderStatus("CLOSED");
			// 卖家已发货,已发货状态
			if (order.get("dealState").equals("DS_WAIT_BUYER_RECEIVE"))
				oh.setOrderStatus("DELIVERY");
			// 交易成功,订单完成
			if (order.get("dealState").equals("DS_DEAL_END_NORMAL")
					|| order.get("dealState").equals("DS_BUYER_EVALUATED"))
				oh.setOrderStatus("COMPLETE");
			// 订单未处理时才更新订单状态(除'关闭'状态订单)
			if (WebUtil.isNull(oh.getOrderStatus())
					|| oh.getOrderStatus().equals("START")) {
				oh.setOrderStatus("AUDITED");
				if (order.get("dealState").equals("DS_WAIT_BUYER_PAY") ||
				// 已付款状态但保留原因不是未支付，订单状态设置为‘未审核’
						(order.get("dealState").equals(
								"DS_WAIT_SELLER_DELIVERY")
								&& WebUtil.isNotNull(oh.getReceiveField1()) && !oh
								.getReceiveField1().equals("I"))) {
					oh.setOrderStatus("START");
				}
			}

			BigDecimal orderAmt = new BigDecimal(0);// 合计明细金额=明细原价*数量
			BigDecimal orderItemTotalFee = new BigDecimal(0);// 合计明细金额应支付金额=明细原价*数量
			// 检查订单明细中的skuCd是否存在
			if (toList != null) {
				for (Map to : toList) {
					if (WebUtil.isNotNull(to.get("itemDealPrice"))
							&& WebUtil.isNotNull(to.get("itemDealCount"))) {
						orderAmt = orderAmt.add(new BigDecimal(to.get(
								"itemDealPrice").toString()).divide(
								new BigDecimal(100), 2,
								BigDecimal.ROUND_HALF_UP).multiply(
								new BigDecimal(to.get("itemDealCount")
										.toString())));
						orderItemTotalFee = orderItemTotalFee
								.add(new BigDecimal(to.get("itemDealPrice")
										.toString()).divide(
										new BigDecimal(100), 2,
										BigDecimal.ROUND_HALF_UP).multiply(
										new BigDecimal(to.get("itemDealCount")
												.toString())));
					}
					if (WebUtil.isNotNull(to.get("itemDiscountFee")))
						orderItemTotalFee = orderItemTotalFee
								.subtract(new BigDecimal(to.get(
										"itemDiscountFee").toString()).divide(
										new BigDecimal(100), 2,
										BigDecimal.ROUND_HALF_UP));
					if (WebUtil.isNotNull(to.get("itemAdjustPrice")))
						orderItemTotalFee = orderItemTotalFee
								.add(new BigDecimal(to.get("itemAdjustPrice")
										.toString()).divide(
										new BigDecimal(100), 2,
										BigDecimal.ROUND_HALF_UP));
					if (WebUtil.isNull(to.get("itemLocalCode"))) {
						if (order.get("dealState").equals("DS_WAIT_BUYER_PAY")
								|| WebUtil.isNotNull(oh.getReceiveField1()))
							oh.setOrderStatus("START");
						oh.setReceiveField1("E");
						// break;
					}
				}
			}
			oh.setOrigOrderNo((String) order.get("dealCode"));
			oh.setOrigOrderType("PAIPAI");
			oh.setOrigOrderStatus((String) order.get("dealState"));
			if (WebUtil.isNull(oh.getOrderNo()))
				oh.setOrderNo(genericOrderNo("PPOD", 6, "yyyyMMdd"));
			if (WebUtil.isNull(oh.getOrderType()))
				oh.setOrderType("ORDER");
			if (WebUtil.isNull(oh.getId()))
				oh.setCtime(date);
			else
				oh.setMtime(date);
			oh.setBuyerNick((String) order.get("buyerName"));
			// 发货时间
			if (WebUtil.isNotNull(order.get("sellerConsignmentTime"))
					&& WebUtil.toDateForString(
							order.get("sellerConsignmentTime").toString(),
							"yyyy-MM-dd HH:mm:ss").after(
							WebUtil.toDateForString("1970-01-01 08:00:00",
									"yyyy-MM-dd HH:mm:ss"))
					&& WebUtil.isNull(oh.getDeliveryDate())) {
				oh.setDeliveryDate(WebUtil.toDateForString(order.get(
						"sellerConsignmentTime").toString(),
						"yyyy-MM-dd HH:mm:ss"));
			}
			// 支付时间
			if (WebUtil.isNotNull(order.get("payTime"))
					&& WebUtil.toDateForString(order.get("payTime").toString(),
							"yyyy-MM-dd HH:mm:ss").after(
							WebUtil.toDateForString("1970-01-01 08:00:00",
									"yyyy-MM-dd HH:mm:ss")))
				oh.setPayTime(WebUtil.toDateForString(order.get("payTime")
						.toString(), "yyyy-MM-dd HH:mm:ss"));
			// 支付帐号
			oh.setPayNo((String) order.get("tenpayCode"));
			// 收货地址
			String address = (String) order.get("receiverAddress");
			if (WebUtil.isNotNull(address)) {
				String[] as = address.split(" ");
				if (as.length == 3) {
					oh.setReceiverState(as[0]);
					oh.setReceiverCity(as[0]);
					oh.setReceiverDistrict(as[1]);
					oh.setReceiverAddress(as[2]);
				} else if (as.length > 3) {
					oh.setReceiverState(as[0]);
					oh.setReceiverCity(as[1]);
					oh.setReceiverDistrict(as[2]);
					StringBuffer ss = new StringBuffer();
					for (int i = 3; i < as.length; i++) {
						ss.append(as[i]);
					}
					oh.setReceiverAddress(ss.toString());
				}

			}

			oh.setReceiverMobile((String) order.get("receiverMobile"));
			oh.setReceiverName((String) order.get("receiverName"));
			oh.setReceiverTel((String) order.get("receiverPhone"));
			oh.setReceiverZip((String) order.get("receiverPostcode"));
			if (WebUtil.isNotNull(order.get("buyerUin")))
				oh.setBuyerEmail(order.get("buyerUin").toString());
			// 发票
			oh.setInvoiceContent((String) order.get("invoiceTitle"));
			if (WebUtil.isNull(oh.getStoreId())) {
				oh.setStoreId(storeId);
				List<Object[]> sl = session.createQuery(
						"select CompanyId,StoreName from Store where id = "
								+ storeId).list();
				if (!WebUtil.isNullForList(sl)) {
					if (WebUtil.isNotNull(sl.get(0)[0]))
						oh.setCompanyId(new Integer(sl.get(0)[0].toString()));
					oh.setSellerNick((String) sl.get(0)[1]);
				}
			}
			// 订单商品总金额计算，明细合计
			oh.setOrderAmt(orderAmt);
			oh.setTotalFee(orderAmt);
			// 订单支付金额
			BigDecimal payment = orderItemTotalFee;
			if (WebUtil.isNotNull(order.get("couponFee")))
				payment = payment.add(new BigDecimal(order.get("couponFee")
						.toString()).divide(new BigDecimal(100), 2,
						BigDecimal.ROUND_HALF_UP));
			if (WebUtil.isNotNull(order.get("freight")))
				payment = payment.add(new BigDecimal(order.get("freight")
						.toString()).divide(new BigDecimal(100), 2,
						BigDecimal.ROUND_HALF_UP));
			oh.setPayment(payment);
			// 邮费
			if (WebUtil.isNotNull(order.get("freight")))
				oh.setPostFee(new BigDecimal(order.get("freight").toString())
						.divide(new BigDecimal(100), 2,
								BigDecimal.ROUND_HALF_UP));
			else
				oh.setPostFee(new BigDecimal(0));
			// 折扣
			if (WebUtil.isNotNull(order.get("couponFee")))
				oh
						.setDiscount(new BigDecimal(order.get("couponFee")
								.toString()).divide(new BigDecimal(100), 2,
								BigDecimal.ROUND_HALF_UP).multiply(
								new BigDecimal(-1)));
			else
				oh.setDiscount(new BigDecimal(0));
			// 积分
			if (WebUtil.isNotNull(order.get("dealPayFeePoint")))
				oh.setPaymentPoint(new Integer(order.get("dealPayFeePoint")
						.toString()));
			else
				oh.setPaymentPoint(0);
			// 订单时间取支付时间
			if (WebUtil.isNotNull(order.get("payTime"))
					&& WebUtil.toDateForString(order.get("payTime").toString(),
							"yyyy-MM-dd HH:mm:ss").after(
							WebUtil.toDateForString("1970-01-01 08:00:00",
									"yyyy-MM-dd HH:mm:ss")))
				oh.setOrderDate(WebUtil.toDateForString(order.get("payTime")
						.toString(), "yyyy-MM-dd HH:mm:ss"));
			if (WebUtil.isNull(oh.getId())) {
				if (WebUtil.isNotNull(order.get("createTime"))
						&& WebUtil.toDateForString(
								order.get("createTime").toString(),
								"yyyy-MM-dd HH:mm:ss").after(
								WebUtil.toDateForString("1970-01-01 08:00:00",
										"yyyy-MM-dd HH:mm:ss")))
					oh.setRequestDate(WebUtil.toDateForString(order.get(
							"createTime").toString(), "yyyy-MM-dd HH:mm:ss"));
			}
			if (isNew) {
				// 此处用于防止重复单号
				CheckRepeatNo crn = new CheckRepeatNo(oh.getOrigOrderNo(),
						"PPOD");
				this.getHibernateTemplate().save(crn);
				session.save(oh);
			} else
				session.update(oh);
			Integer orderHeadId = oh.getId();
			// 订单信息结束
			// 订单备注
			if (WebUtil.isNotNull(order.get("buyerRemark"))) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("BUYER_MEMO");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(order.get("buyerRemark").toString());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}

			}

			// 促销使用，系统ID和淘宝ID
			Map tbIdMap = new HashMap();
			// 支付货款
			BigDecimal amt = oh.getPayment().subtract(oh.getPostFee());
			// 订单明细
			if (toList != null) {
				List<OrderItem> oil = session.createQuery(
						"from OrderItem where OrderHeadId = " + orderHeadId)
						.list();
				Map<String, OrderItem> oim = new HashMap();
				if (oil != null) {
					for (OrderItem oi : oil) {
						oim.put(oi.getOrigOrderItemNo(), oi);
					}
				}
				// 已计算的整单折扣金额
				BigDecimal detailCount = new BigDecimal(0);

				int r = 1;
				for (Map to : toList) {
					OrderItem oi = oim.get(to.get("dealSubCode"));
					if (oi == null) {
						oi = new OrderItem();
						oi.setCtime(date);
						oi.setItemCd((String) to.get("itemLocalCode"));
						oi.setName((String) to.get("itemName"));
						oi.setOrderHeadId(orderHeadId);
						oi.setOrderItemStatus((String) to.get("refundState"));
						oi.setOrigOrderItemNo((String) to.get("dealSubCode"));
						oi.setOrigSkuId((String) to.get("stockAttr"));
						if (WebUtil.isNotNull(to.get("itemDealCount")))
							oi.setQty(new BigDecimal(to.get("itemDealCount")
									.toString()));
						if (WebUtil.isNotNull(to.get("itemDealPrice")))
							oi.setPrice(new BigDecimal(to.get("itemDealPrice")
									.toString()).divide(new BigDecimal(100), 2,
									BigDecimal.ROUND_HALF_UP));
						if (WebUtil.isNotNull(to.get("itemDealCount")))
							oi.setReqQty(new BigDecimal(to.get("itemDealCount")
									.toString()));
						if (WebUtil.isNotNull(to.get("itemDiscountFee")))
							oi.setDiscountPriceAmt(new BigDecimal(to.get(
									"itemDiscountFee").toString()).divide(
									new BigDecimal(100), 2,
									BigDecimal.ROUND_HALF_UP));
						if (WebUtil.isNotNull(to.get("itemAdjustPrice")))
							oi.setAdjustFee(new BigDecimal(to.get(
									"itemAdjustPrice").toString()).divide(
									new BigDecimal(100), 2,
									BigDecimal.ROUND_HALF_UP));

						oi.setSkuCd((String) to.get("stockLocalCode"));
						// 销售属性处理[拖鞋尺码:其它;颜色分类:天蓝色;规格:中号]
						if (WebUtil.isNotNull(to.get("stockAttr"))) {
							String[] props = to.get("stockAttr").toString()
									.split("\\|");
							for (String prop : props) {
								String[] ps = prop.split(":");
								if (ps.length == 2) {
									if (ps[0].indexOf("颜色") >= 0) {
										oi.setSkuProp1(ps[1]);
									} else if (ps[0].indexOf("尺码") >= 0) {
										oi.setSkuProp2(ps[1]);
									} else {
										oi.setSkuProp3(ps[1]);
									}
								}
							}
						}
						// 计算明细合计
						BigDecimal subTotal = new BigDecimal(0);
						if (WebUtil.isNotNull(to.get("itemDealPrice"))
								&& WebUtil.isNotNull(to.get("itemDealCount"))) {
							subTotal = subTotal.add(new BigDecimal(to.get(
									"itemDealPrice").toString()).divide(
									new BigDecimal(100), 2,
									BigDecimal.ROUND_HALF_UP).multiply(
									new BigDecimal(to.get("itemDealCount")
											.toString())));
						}
						if (WebUtil.isNotNull(to.get("itemDiscountFee")))
							subTotal = subTotal.subtract(new BigDecimal(to.get(
									"itemDiscountFee").toString()).divide(
									new BigDecimal(100), 2,
									BigDecimal.ROUND_HALF_UP));
						if (WebUtil.isNotNull(to.get("itemAdjustPrice")))
							subTotal = subTotal.add(new BigDecimal(to.get(
									"itemAdjustPrice").toString()).divide(
									new BigDecimal(100), 2,
									BigDecimal.ROUND_HALF_UP));
						oi.setSubTotal(subTotal);
						// 折扣价格=销售价格-明细金额/明细数量
						if (oi.getDiscountPriceAmt() != null)
							oi.setDiscountPrice(oi.getDiscountPriceAmt()
									.divide(oi.getReqQty(), 2,
											BigDecimal.ROUND_HALF_UP));
						// 扣除OrderHead折扣计算
						if (r == toList.size()) {
							// 商品实际总额=支付货款-已计算的整单折扣金额
							oi.setRealPriceAmt(amt.subtract(detailCount));
							// 商品实际单价=商品实际总额/明细数量
							oi.setRealPrice(oi.getRealPriceAmt()
									.divide(oi.getReqQty(), 2,
											BigDecimal.ROUND_HALF_UP));
						} else {
							// 商品实际总额=支付货款*（明细金额/明细总额）
							BigDecimal c = amt.multiply(oi.getSubTotal()
									.divide(orderItemTotalFee, 2,
											BigDecimal.ROUND_HALF_UP));
							oi.setRealPriceAmt(c);
							// 商品实际单价=商品实际总额/明细数量
							oi.setRealPrice(c.divide(oi.getReqQty(), 2,
									BigDecimal.ROUND_HALF_UP));
						}
						session.save(oi);
					} else {
						oi.setMtime(date);
						// oi.setSkuProp1(to.getSkuPropertiesName());
						// 销售属性处理[拖鞋尺码:其它;颜色分类:天蓝色;规格:中号]
						if (WebUtil.isNotNull(to.get("stockAttr"))) {
							String[] props = to.get("stockAttr").toString()
									.split("|");
							for (String prop : props) {
								String[] ps = prop.split(":");
								if (ps.length == 2) {
									if (ps[0].indexOf("颜色") >= 0) {
										oi.setSkuProp1(ps[1]);
									} else if (ps[0].indexOf("尺码") >= 0) {
										oi.setSkuProp2(ps[1]);
									} else {
										oi.setSkuProp3(ps[1]);
									}
								}
							}
						}
						oi.setName((String) to.get("itemName"));
						session.update(oi);
					}
					tbIdMap.put(oi.getOrigOrderItemNo(), oi.getId());
					// 已计算的整单折扣金额
					if (oi.getRealPriceAmt() != null)
						detailCount = detailCount.add(oi.getRealPriceAmt());
					r++;
				}
			}
			tx.commit();

			long c = System.currentTimeMillis();
			logger.info("time:" + (c - l));
			session.close();
			result.put("Flag", "SUCCESS");
			return result;
		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("Flag", "ERROR");
		return result;
	}

}
