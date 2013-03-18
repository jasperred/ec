package com.sunmw.web.bean.refund.impl;

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

import com.sunmw.taobao.entity.TbRefund;
import com.sunmw.web.bean.refund.RefundServices;
import com.sunmw.web.entity.OrderHead;
import com.sunmw.web.entity.OrderItem;
import com.sunmw.web.entity.OrderNote;
import com.sunmw.web.entity.OrderNotePK;
import com.sunmw.web.entity.OrderPayment;
import com.sunmw.web.entity.OrderTaobaoinfo;
import com.sunmw.web.entity.StatusItem;
import com.sunmw.web.entity.Store;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.domain.Refund;

public class RefundServicesImpl extends HibernateDaoSupport implements
		RefundServices {
	static Logger logger = Logger.getLogger(RefundServicesImpl.class);

	public synchronized String updateRefundFromTb(List<TbRefund> l) {
		logger.info("updateRefundFromTb开始执行");
		if (l == null || l.size() == 0 || l.get(0) == null)
			return null;
		StringBuffer refundIds = new StringBuffer();
		Session session = this.getSession();
		// 店铺列表
		List<Store> storeList = session.createQuery("from Store").list();
		Map<Integer, Integer> storeMap = new HashMap();
		if (!WebUtil.isNullForList(storeList)) {
			for (Store s : storeList)
				storeMap.put(s.getId(), s.getCompanyId());
		}
		Date date = new Date();
		for (TbRefund tr : l) {
			Transaction tx = session.beginTransaction();
			List<OrderHead> ol = session.createQuery(
					"from OrderHead where OrigOrderNo = '"
							+ tr.getRefundId().longValue() + "'").list();
			OrderHead oh = null;
			if (ol == null || ol.size() == 0) {
				oh = new OrderHead();
				oh.setOrigin("TAOBAO");
			} else
				oh = ol.get(0);
			boolean isNew = true;
			if (oh.getId() != null)
				isNew = false;
			if (WebUtil.isNull(oh.getOrderNo()))
				oh.setOrderNo(genericOrderNo("TBRO", 6, "yyyyMMdd", session));
			String status = "REFUND_START";
			// 买家已经申请退款，等待卖家同意
			if (tr.getStatus().equals("WAIT_SELLER_AGREE")) {
				status = "REFUND_START";
			}
			// 卖家已经同意退款，等待买家退货
			else if (tr.getStatus().equals("WAIT_BUYER_RETURN_GOODS")) {
				status = "REFUND_AUDITED";
			}
			// 买家已经退货，等待卖家确认收货
			else if (tr.getStatus().equals("WAIT_SELLER_CONFIRM_GOODS")) {
				status = "REFUND_PROCESS";
			}
			// 卖家拒绝退款
			else if (tr.getStatus().equals("SELLER_REFUSE_BUYER")) {
				status = "REFUND_CLOSED";
			}
			// 退款关闭
			else if (tr.getStatus().equals("CLOSED")) {
				status = "REFUND_CLOSED";
			}
			// 退款成功
			else if (tr.getStatus().equals("SUCCESS")) {
				status = "REFUND_COMPLETE";
				// 完成时间
				oh.setCompleteDate(tr.getModified());
			}
			oh.setOrderStatus(status);
			oh.setOrigOrderNo("" + tr.getRefundId().longValue());
			oh.setOrigOrderType("TAOBAO");
			// 退款状态。可选值 WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意)
			// WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货)
			// WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货)
			// SELLER_REFUSE_BUYER(卖家拒绝退款) CLOSED(退款关闭) SUCCESS(退款成功)
			oh.setOrigOrderStatus(tr.getStatus());
			// 货物状态。可选值 BUYER_NOT_RECEIVED (买家未收到货) BUYER_RECEIVED (买家已收到货)
			// BUYER_RETURNED_GOODS (买家已退货)
			oh.setReceiveField5(tr.getGoodStatus());
			// 买家是否需要退货。可选值:true(是),false(否)
			oh.setReceiveField6("" + tr.isHasGoodReturn());
			// 物流公司
			oh.setPostCompany(tr.getCompanyName());
			// 运单号
			oh.setPostNo(tr.getSid());
			// 卖家地址
			// oh.setReceiveField2(tr.getAddress());
			// 引用订单号
			if (WebUtil.isNull(oh.getRefOrderNo())) {
				oh.setRefOrderNo("" + tr.getTid().longValue());
				oh.setRefOrderType("ORDER");
			}
			// 订单类型
			if (WebUtil.isNull(oh.getOrderType()))
				oh.setOrderType("REFUND");
			// 时间记录
			if (WebUtil.isNull(oh.getId()))
				oh.setCtime(date);
			else
				oh.setMtime(date);
			// 店铺
			if (WebUtil.isNull(oh.getStoreId())) {
				oh.setStoreId(tr.getStoreId());
				// 公司
				oh.setCompanyId(storeMap.get(tr.getStoreId()));
			}
			// 订单金额
			oh.setOrderAmt(tr.getRefundFee());
			oh.setPayment(tr.getPayment());
			oh.setTotalFee(tr.getTotalFee());

			if (WebUtil.isNull(oh.getId())) {
				// 生成订单时间
				oh.setOrderDate(date);
				// 淘宝订单时间(退款申请时间)
				oh.setRequestDate(tr.getCreated());
			}
			if (isNew)
				session.save(oh);
			else
				session.update(oh);
			Integer orderHeadId = oh.getId();
			// 订单金额
			OrderPayment op = new OrderPayment();
			op.setActualTotalAmt(tr.getTotalFee());
			op.setPaymentAmt(tr.getPayment());
			op.setRefundAmt(tr.getRefundFee());
			op.setId(orderHeadId);
			if (isNew)
				session.save(op);
			else
				session.merge(op);
			// 退款原因
			if (!WebUtil.isNull(tr.getReason())) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("REFUND_REASON");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(tr.getReason());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}

			}
			// 退款说明
			if (!WebUtil.isNull(tr.getDescription())) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("REFUND_DESC");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(tr.getDescription());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}
			}
			// 淘宝信息
			OrderTaobaoinfo ot = new OrderTaobaoinfo();
			ot.setId(orderHeadId);
			// 支付宝交易号
			ot.setAlipayNo(tr.getAlipayNo());
			// 交易总金额
			ot.setTotalFee(tr.getTotalFee());
			ot.setBuyerNick(tr.getBuyerNick());
			ot.setSellerNick(tr.getSellerNick());
			// 退款申请时间
			ot.setCreated(tr.getCreated());
			// 更新时间
			ot.setModified(tr.getModified());
			// 退款对应的订单交易状态
			ot.setStatus(tr.getOrderStatus());
			// 支付给卖家的金额(交易总金额-退还给买家的金额)
			ot.setPayment(tr.getPayment());
			// 物流方式
			ot.setShippingType(tr.getShippingType());
			if (isNew) {
				ot.setCtime(date);
				session.save(ot);
			} else {
				ot.setMtime(date);
				session.merge(ot);
			}
			// 退货明细,从原淘宝订单中得到
			List<OrderItem> oil = session.createQuery(
					"from OrderItem where OrderHeadId = " + orderHeadId
							+ " and OrigOrderItemNo = '" + tr.getOid() + "'")
					.list();
			List<OrderItem> ooil = session.createQuery(
					"from OrderItem where OrigOrderItemNo = '" + tr.getOid()
							+ "' and SkuCd is not null").list();
			OrderItem oi = null;
			// 新增退款明细
			if (oil == null || oil.size() == 0) {
				oi = new OrderItem();
				oi.setOrderHeadId(orderHeadId);
				// 无法得到原订单明细
				if (ooil == null || ooil.size() == 0) {
					oi.setName(tr.getTitle());
					oi.setQty(new BigDecimal(tr.getNum()));
					oi.setReqQty(new BigDecimal(tr.getNum()));
					oi.setOrigOrderItemNo("" + tr.getOid().longValue());
					oi.setOrigSkuId("" + tr.getNumIid().longValue());
					oi.setPrice(tr.getPrice());
					oi.setSubTotal(new BigDecimal(tr.getNum()
							* tr.getPrice().doubleValue()));
				} else {
					OrderItem ooi = ooil.get(0);
					oi.setName(tr.getTitle());
					oi.setQty(new BigDecimal(tr.getNum()));
					oi.setReqQty(new BigDecimal(tr.getNum()));
					oi.setOrigOrderItemNo("" + tr.getOid().longValue());
					oi.setOrigSkuId("" + tr.getNumIid().longValue());
					oi.setPrice(tr.getPrice());
					oi.setItemCd(ooi.getItemCd());
					oi.setSkuCd(ooi.getSkuCd());
					oi.setSkuProp1(ooi.getSkuProp1());
					oi.setSkuProp2(ooi.getSkuProp2());
					oi.setSkuProp3(ooi.getSkuProp3());
					oi.setOrderItemStatus(ooi.getOrderItemStatus());
					oi.setSubTotal(new BigDecimal(tr.getNum()
							* tr.getPrice().doubleValue()));
				}
				oi.setCtime(date);
				session.save(oi);
			}
			// 用原订单明细更新退货明细
			else {
				oi = oil.get(0);
				if (ooil != null && ooil.size() > 0) {
					OrderItem ooi = ooil.get(0);
					if (oi.getOrderHeadId() == null)
						oi.setOrderHeadId(orderHeadId);
					oi.setItemCd(ooi.getItemCd());
					oi.setSkuCd(ooi.getSkuCd());
					oi.setSkuProp1(ooi.getSkuProp1());
					oi.setSkuProp2(ooi.getSkuProp2());
					oi.setSkuProp3(ooi.getSkuProp3());
					oi.setOrderItemStatus(ooi.getOrderItemStatus());
					oi.setMtime(date);
				}
				session.update(oi);
			}
			tx.commit();
			// 返回退货单ID
			if (refundIds.length() > 0)
				refundIds.append(",");
			refundIds.append(tr.getRefundId());
		}
		session.close();
		logger.info("updateRefundFromTb执行结束");
		return refundIds.toString();
	}

	public synchronized Map updateSingleRefundFromTb(Refund refund,
			Integer storeId) {
		Map result = new HashMap();
		Date date = new Date();
		try {
			Session session = this.getSession();
			Transaction tx = session.beginTransaction();
			List<OrderHead> ol = session.createQuery(
					"from OrderHead where OrigOrderNo = '"
							+ refund.getRefundId().longValue() + "'").list();
			OrderHead oh = null;
			if (ol == null || ol.size() == 0) {
				oh = new OrderHead();
				oh.setOrigin("TAOBAO");
			} else
				oh = ol.get(0);
			boolean isNew = true;
			if (oh.getId() != null)
				isNew = false;
			if (WebUtil.isNull(oh.getOrderNo()))
				oh.setOrderNo(genericOrderNo("TBRO", 6, "yyyyMMdd", session));
			String status = "REFUND_START";
			// 买家已经申请退款，等待卖家同意
			if (refund.getStatus().equals("WAIT_SELLER_AGREE")) {
				status = "REFUND_START";
			}
			// 卖家已经同意退款，等待买家退货
			else if (refund.getStatus().equals("WAIT_BUYER_RETURN_GOODS")) {
				status = "REFUND_AUDITED";
			}
			// 买家已经退货，等待卖家确认收货
			else if (refund.getStatus().equals("WAIT_SELLER_CONFIRM_GOODS")) {
				status = "REFUND_PROCESS";
			}
			// 卖家拒绝退款
			else if (refund.getStatus().equals("SELLER_REFUSE_BUYER")) {
				status = "REFUND_CLOSED";
			}
			// 退款关闭
			else if (refund.getStatus().equals("CLOSED")) {
				status = "REFUND_CLOSED";
			}
			// 退款成功
			else if (refund.getStatus().equals("SUCCESS")) {
				status = "REFUND_COMPLETE";
				// 完成时间
				oh.setCompleteDate(refund.getModified());
			}
			oh.setOrderStatus(status);
			oh.setOrigOrderNo("" + refund.getRefundId().longValue());
			oh.setOrigOrderType("TAOBAO");
			// 退款状态。可选值 WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意)
			// WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货)
			// WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货)
			// SELLER_REFUSE_BUYER(卖家拒绝退款) CLOSED(退款关闭) SUCCESS(退款成功)
			oh.setOrigOrderStatus(refund.getStatus());
			// 货物状态。可选值 BUYER_NOT_RECEIVED (买家未收到货) BUYER_RECEIVED (买家已收到货)
			// BUYER_RETURNED_GOODS (买家已退货)
			oh.setReceiveField5(refund.getGoodStatus());
			// 买家是否需要退货。可选值:true(是),false(否)
			oh.setReceiveField6("" + refund.getHasGoodReturn());
			// 物流公司
			oh.setPostCompany(refund.getCompanyName());
			// 运单号
			oh.setPostNo(refund.getSid());
			// 卖家地址
			// oh.setReceiveField2(refund.getAddress());
			// 引用订单号
			if (WebUtil.isNull(oh.getRefOrderNo())) {
				oh.setRefOrderNo("" + refund.getTid().longValue());
				oh.setRefOrderType("ORDER");
			}
			// 订单类型
			if (WebUtil.isNull(oh.getOrderType()))
				oh.setOrderType("REFUND");
			// 时间记录
			if (WebUtil.isNull(oh.getId()))
				oh.setCtime(date);
			else
				oh.setMtime(date);
			// 店铺
			if (WebUtil.isNull(oh.getStoreId())) {
				oh.setStoreId(storeId);
				// 公司
				List<Integer> sl = session.createQuery(
						"select CompanyId from Store where id = " + storeId)
						.list();
				if (!WebUtil.isNullForList(sl))
					oh.setCompanyId(sl.get(0));
			}
			// 订单金额
			if (WebUtil.isNotNull(refund.getRefundFee()))
				oh.setOrderAmt(new BigDecimal(refund.getRefundFee()));
			if (WebUtil.isNotNull(refund.getPayment()))
				oh.setPayment(new BigDecimal(refund.getPayment()));
			if (WebUtil.isNotNull(refund.getTotalFee()))
				oh.setTotalFee(new BigDecimal(refund.getTotalFee()));

			if (WebUtil.isNull(oh.getId())) {
				// 生成订单时间
				oh.setOrderDate(date);
				// 淘宝订单时间(退款申请时间)
				oh.setRequestDate(refund.getCreated());
			}
			if (isNew)
				session.save(oh);
			else
				session.update(oh);
			Integer orderHeadId = oh.getId();

			// 退款原因
			if (!WebUtil.isNull(refund.getReason())) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("REFUND_REASON");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(refund.getReason());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}

			}
			// 退款说明
			if (!WebUtil.isNull(refund.getDesc())) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("REFUND_DESC");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(refund.getDesc());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}
			}

			// 退货明细,从原淘宝订单中得到
			List<OrderItem> oil = session.createQuery(
					"from OrderItem where OrderHeadId = " + orderHeadId
							+ " and OrigOrderItemNo = '" + refund.getOid()
							+ "'").list();
			List<OrderItem> ooil = session.createQuery(
					"from OrderItem where OrigOrderItemNo = '"
							+ refund.getOid() + "'")
					.list();
			OrderItem oi = null;
			// 新增退款明细
			if (oil == null || oil.size() == 0) {
				oi = new OrderItem();
				oi.setOrderHeadId(orderHeadId);
				// 无法得到原订单明细
				if (ooil == null || ooil.size() == 0) {
					oi.setName(refund.getTitle());
					if (WebUtil.isNotNull(refund.getNum()))
						oi.setQty(new BigDecimal(refund.getNum()));
					else
						oi.setQty(new BigDecimal(0));
					if (WebUtil.isNotNull(refund.getNum()))
						oi.setReqQty(new BigDecimal(refund.getNum()));
					else
						oi.setReqQty(new BigDecimal(0));
					oi.setOrigOrderItemNo("" + refund.getOid().longValue());
					oi.setOrigSkuId("" + refund.getNumIid().longValue());
					if (WebUtil.isNotNull(refund.getPrice()))
						oi.setPrice(new BigDecimal(refund.getPrice()));
					else
						oi.setPrice(new BigDecimal(0));
					oi.setSubTotal(oi.getPrice().multiply(oi.getReqQty()));
				} else {
					OrderItem ooi = ooil.get(0);
					oi.setName(refund.getTitle());
					if (WebUtil.isNotNull(refund.getNum()))
						oi.setQty(new BigDecimal(refund.getNum()));
					else
						oi.setQty(new BigDecimal(0));
					if (WebUtil.isNotNull(refund.getNum()))
						oi.setReqQty(new BigDecimal(refund.getNum()));
					else
						oi.setReqQty(new BigDecimal(0));
					oi.setOrigOrderItemNo("" + refund.getOid().longValue());
					oi.setOrigSkuId("" + refund.getNumIid().longValue());
					if (WebUtil.isNotNull(refund.getPrice()))
						oi.setPrice(new BigDecimal(refund.getPrice()));
					else
						oi.setPrice(new BigDecimal(0));
					oi.setItemCd(ooi.getItemCd());
					oi.setSkuCd(ooi.getSkuCd());
					oi.setSkuProp1(ooi.getSkuProp1());
					oi.setSkuProp2(ooi.getSkuProp2());
					oi.setSkuProp3(ooi.getSkuProp3());
					oi.setOrderItemStatus(ooi.getOrderItemStatus());
					oi.setSubTotal(oi.getPrice().multiply(oi.getReqQty()));
				}
				oi.setCtime(date);
				session.save(oi);
			}
			// 用原订单明细更新退货明细
			else {
				oi = oil.get(0);
				if (ooil != null && ooil.size() > 0) {
					OrderItem ooi = ooil.get(0);
					if (oi.getOrderHeadId() == null)
						oi.setOrderHeadId(orderHeadId);
					oi.setItemCd(ooi.getItemCd());
					oi.setSkuCd(ooi.getSkuCd());
					oi.setSkuProp1(ooi.getSkuProp1());
					oi.setSkuProp2(ooi.getSkuProp2());
					oi.setSkuProp3(ooi.getSkuProp3());
					oi.setOrderItemStatus(ooi.getOrderItemStatus());
					oi.setMtime(date);
				}
				session.update(oi);
			}
			tx.commit();

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

	// 产生订单号
	private String genericOrderNo(String prefix, int len, String format,
			Session session) {
		String date = WebUtil.formatDateString(new Date(), format);
		List l = session.createQuery(
				"select max(substring(OrderNo,"
						+ (prefix.length() + date.length() + 1)
						+ ")) from OrderHead where OrderNo like '" + prefix
						+ date + "%'").list();
		if (l == null || l.size() == 0 || l.get(0) == null) {
			return prefix + date + WebUtil.addPrefix("1", len, "0");
		} else {
			return prefix
					+ date
					+ WebUtil.addPrefix(""
							+ (Integer.parseInt(l.get(0).toString()) + 1), len,
							"0");
		}
	}

	public Map searchRefund(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		// 字段:订单ID、订单号、订单状态、退货单号、退货单状态、退货时间、运输公司、运单号、店铺ID、订单金额、订单状态名称、店铺名称、买家昵称、支付时间、原淘宝订单号、原淘宝订单状态、货物状态、是否需要退货、退款金额、支付金额
		String fields = "select oh.id,oh.OrderNo,oh.OrderStatus,oh.OrigOrderNo,oh.OrigOrderStatus,oh.RequestDate,oh.PostCompany,oh.PostNo,oh.StoreId,oh.OrderAmt,ss.StoreName,oh.BuyerNick,oh.RequestDate,oh.RefOrderNo,oh.ReceiveField5,oh.ReceiveField6,oh.OrderAmt,oh.Payment";
		StringBuffer hql = new StringBuffer(
				" from OrderHead oh, Store ss where oh.OrderType = 'REFUND' and oh.StoreId = ss.id");
		StringBuffer con = new StringBuffer();
		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" oh.CompanyId = "+ul.getCompanyId());
		}
		// 店铺
		if (WebUtil.isNotNull(param.get("storeId"))
				&& Integer.parseInt(param.get("storeId").toString()) > 0) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.StoreId = " + param.get("storeId"));
		}
		// 原淘宝订单状态
		if (WebUtil.isNotNull(param.get("tbStatus"))) {
			if (con.length() > 0)
				con.append(" and ");
			con
					.append(" oh.RefOrderNo in (select OrderNo from OrderHead where OrderType='ORDER' and OrderStatus = '"
							+ param.get("tbStatus") + "')");
		}
		// 系统状态
		if (WebUtil.isNotNull(param.get("erpStatus"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrderStatus = '" + param.get("erpStatus") + "'");
		}
		// 退款状态
		if (WebUtil.isNotNull(param.get("refundStatus"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrigOrderStatu= '" + param.get("refundStatus")
					+ "'");
		}
		// 货物状态
		if (WebUtil.isNotNull(param.get("goodStatus"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.ReceiveField5 = '" + param.get("goodStatus") + "'");
		}
		// 是否需要退货
		if (WebUtil.isNotNull(param.get("hasGoodReturn"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.ReceiveField6 = '" + param.get("hasGoodReturn")
					+ "'");
		}
		// 订单日期
		if (WebUtil.isNotNull(param.get("refundFromDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.RequestDate >= '" + param.get("refundFromDate")
					+ "'");
		}
		if (WebUtil.isNotNull(param.get("refundEndDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con
					.append(" oh.RequestDate < '" + param.get("refundEndDate")
							+ "'");
		}
		// 完成日期
		if (WebUtil.isNotNull(param.get("completeFromDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.CompleteDate >= '" + param.get("completeFromDate")
					+ "'");
		}
		if (WebUtil.isNotNull(param.get("completeEndDate"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.CompleteDate < '" + param.get("completeEndDate")
					+ "'");
		}
		// 系统单号
		if (WebUtil.isNotNull(param.get("ORDER_NO"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrderNo like '%" + param.get("ORDER_NO") + "%'");
		}
		// 退货单号
		if (WebUtil.isNotNull(param.get("TB_REFUND_NO"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.OrigOrderNo like '%" + param.get("TB_REFUND_NO")
					+ "%'");
		}
		// 淘宝订单号
		if (WebUtil.isNotNull(param.get("TB_ORDER_NO"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.RefOrderNo like '%" + param.get("TB_ORDER_NO")
					+ "%'");
		}
		// 运单号
		if (WebUtil.isNotNull(param.get("TB_SHIP_NO"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" oh.PostNo like '%" + param.get("TB_SHIP_NO") + "%'");
		}
		// 买家
		if (WebUtil.isNotNull(param.get("BUYER_NICK"))) {
			if (con.length() > 0)
				con.append(" and ");
			con
					.append(" ot.BuyerNick like '%" + param.get("BUYER_NICK")
							+ "%'");
		}
		// 支付宝
		if (WebUtil.isNotNull(param.get("BUYER_ALIPAY_NO"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" ot.BuyerAlipayNo like '%"
					+ param.get("BUYER_ALIPAY_NO") + "%'");
		}
		if (con.length() > 0)
			hql.append(" and " + con.toString());
		hql.append(" order by oh.RequestDate desc");
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
			Map m = getStatusMap("'TAOBAO_GOOD','TAOBAO_RETURN','REFUND'");
			for (Object[] o : l) {
				Map r = new HashMap();
				r.put("orderHeadId", o[0]);
				r.put("orderNo", o[1]);
				r.put("orderStatus", o[2]);
				r.put("refundNo", o[3]);
				r.put("refundStatus", o[4]);
				r.put("requestDate", WebUtil.formatDateString((Date) o[5],
						"yyyy-MM-dd HH:mm:ss"));
				r.put("companyName", o[6]);
				r.put("shipNo", o[7]);
				r.put("storeId", o[8]);
				r.put("orderAmt", o[9]);
				r.put("orderStatusName", m.get("REFUND" + o[2]));
				r.put("storeName", o[10]);
				r.put("buyerNick", o[11]);
				r.put("refundStatusName", m.get("TAOBAO_RETURN" + o[4]));
				r.put("refundDate", WebUtil.formatDateString((Date) o[12],
						"yyyy-MM-dd HH:mm:ss"));
				r.put("tbOrderNo", o[13]);
				// r.put("tbOrderStatus", o[15]);
				// r.put("tbOrderStatusName", m.get("TAOBAO_ORDER"+o[15]));
				r.put("goodStatus", o[14]);
				r.put("goodStatusName", m.get("TAOBAO_GOOD" + o[14]));
				r.put("hasGoodReturn", o[15]);
				if (o[15] == null) {
					r.put("hasGoodReturnName", "");
				} else {
					r
							.put("hasGoodReturnName",
									o[15].equals("true") ? "是" : "否");
				}
				r.put("payment", o[17]);
				r.put("refundAmt", o[16]);
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
			m.put(si.getStatusTypeId() + si.getStatusCode(), si
					.getDescription());
		}
		return m;
	}

	// 退货单内容
	public Map getRefundInfo(int orderHeadId) {
		Map result = new HashMap();
		// 退货主表
		List<OrderHead> orderHeadList = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (orderHeadList == null || orderHeadList.size() == 0)
			return null;
		OrderHead oh = orderHeadList.get(0);
		result.put("OrderHead", oh);
		// 淘宝订单表
		List<OrderHead> origOrderHeadList = this.getHibernateTemplate().find(
				"from OrderHead where OrigOrderNo = '" + oh.getRefOrderNo()
						+ "'");
		OrderHead origoh = null;
		if (origOrderHeadList != null && origOrderHeadList.size() > 0)
			origoh = origOrderHeadList.get(0);
		result.put("RefOrderHead", origoh);
		// 状态名称
		Map<String, String> statusMap = getStatusMap("'TAOBAO_ORDER','TAOBAO_GOOD','TAOBAO_RETURN','ORDER','REFUND','TB_RETURN_RECEIVE'");
		result.put("orderStatusName", statusMap.get("REFUND"
				+ oh.getOrderStatus()));
		result.put("refundStatusName", statusMap.get("TAOBAO_RETURN"
				+ oh.getOrigOrderStatus()));
		result.put("refundReceiveName", statusMap.get("TB_RETURN_RECEIVE"
				+ oh.getReceiveField1()));
		if (origoh != null)
			result.put("tbOrderStatusName", statusMap.get("TAOBAO_ORDER"
					+ origoh.getOrigOrderStatus()));
		result.put("goodStatusName", statusMap.get("TAOBAO_GOOD"
				+ oh.getReceiveField5()));
		// 淘宝信息
		// List<OrderTaobaoinfo> otList = this.getHibernateTemplate().find(
		// "from OrderTaobaoinfo where id = " + orderHeadId);
		// if (!WebUtil.isNullForList(otList)) {
		// result.put("OrderTaobaoinfo", otList.get(0));
		// result.put("refundDate", WebUtil.formatDateString(otList.get(0)
		// .getCreated(), "yyyy-MM-dd HH:mm"));
		// }
		// 支付信息
		// List<OrderPayment> opList = this.getHibernateTemplate().find(
		// "from OrderPayment where id = " + orderHeadId);
		// if (!WebUtil.isNullForList(opList)) {
		// result.put("OrderPayment", opList.get(0));
		// }
		// 备注
		List<OrderNote> onList = this.getHibernateTemplate().find(
				"from OrderNote where id.OrderHeadId = " + orderHeadId);
		if (onList != null) {
			for (OrderNote on : onList) {
				result.put(on.getId().getNoteType(), on.getNote());
			}
		}
		// 明细
		List<OrderItem> orderItemList = this.getHibernateTemplate().find(
				"from OrderItem where OrderHeadId = " + orderHeadId);
		result.put("OrderItem", orderItemList);
		return result;
	}

	public String auditedRefund(int orderHeadId) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (l == null || l.size() == 0)
			return "订单未找到";
		OrderHead oh = l.get(0);
		if (!oh.getOrderStatus().equals("REFUND_START"))
			return "订单状态已改变";
		oh.setOrderStatus("REFUND_AUDITED");
		this.getHibernateTemplate().update(oh);
		return "success";
	}

	public String auditedRefunds(String orderHeadIds) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where OrderStatus = 'REFUND_START' and id in ("
						+ orderHeadIds + ")");
		if (l == null || l.size() == 0)
			return "订单未找到";
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		for (OrderHead oh : l) {
			oh.setOrderStatus("REFUND_AUDITED");
			session.update(oh);
		}
		tx.commit();
		session.close();
		return "success";
	}

	public String receiveRefund(int orderHeadId, String receiveMessage) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (l == null || l.size() == 0)
			return "订单未找到";
		OrderHead oh = l.get(0);
		if (!oh.getOrderStatus().equals("REFUND_AUDITED"))
			return "订单状态已改变";
		oh.setOrderStatus("REFUND_START");
		oh.setReceiveField1(receiveMessage);
		this.getHibernateTemplate().update(oh);
		return "success";
	}

	public String receiveRefunds(String orderHeadIds, String receiveMessage) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where OrderStatus = 'REFUND_AUDITED' and id in ("
						+ orderHeadIds + ")");
		if (l == null || l.size() == 0)
			return "订单未找到";
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		for (OrderHead oh : l) {
			oh.setOrderStatus("REFUND_START");
			oh.setReceiveField1(receiveMessage);
			session.update(oh);
		}
		tx.commit();
		session.close();
		return "success";
	}

	public Map saveRefund(Map param) {
		Map result = new HashMap();
		try {
			Session session = this.getSession();
			Transaction tx = session.beginTransaction();
			Date date = new Date();
			Integer orderHeadId = (Integer) param.get("OrderHeadId");
			List<OrderHead> ol = session.createQuery(
					"from OrderHead where id = " + orderHeadId).list();
			OrderHead oh = null;
			if (ol == null || ol.size() == 0) {
				oh = new OrderHead();
				oh.setOrigin("ERP");
				oh.setOrderStatus("REFUND_START");
				// 超过7天退货
				// oh.setOrigOrderStatus("SERVEN_REFUND");
				oh.setOrigOrderType("REFUND");
			} else
				oh = ol.get(0);
			boolean isNew = true;
			if (oh.getId() != null)
				isNew = false;
			if (WebUtil.isNull(oh.getOrderNo()))
				oh.setOrderNo(genericOrderNo("TBRO", 6, "yyyyMMdd", session));
			if (WebUtil.isNotNull(param.get("OrigOrderNo")))
				oh.setOrigOrderNo(param.get("OrigOrderNo").toString());
			// 退货状态
			if (WebUtil.isNotNull(param.get("RefundStatus")))
				oh.setOrigOrderStatus(param.get("RefundStatus").toString());
			// 货物状态。可选值 BUYER_NOT_RECEIVED (买家未收到货) BUYER_RECEIVED (买家已收到货)
			// BUYER_RETURNED_GOODS (买家已退货)
			if (WebUtil.isNotNull(param.get("GoodStatus")))
				oh.setReceiveField5(param.get("GoodStatus").toString());
			// 买家是否需要退货。可选值:true(是),false(否)
			if (WebUtil.isNotNull(param.get("HasGoodReturn")))
				oh.setReceiveField6(param.get("HasGoodReturn").toString());
			// 物流公司
			if (WebUtil.isNotNull(param.get("CompanyName")))
				oh.setReceiveField3(param.get("CompanyName").toString());
			// 运单号
			if (WebUtil.isNotNull(param.get("Sid")))
				oh.setReceiveField4(param.get("Sid").toString());
			// 卖家地址
			if (WebUtil.isNotNull(param.get("Address")))
				oh.setReceiveField2(param.get("Address").toString());

			// 卖家地址
			if (WebUtil.isNotNull(param.get("BuyerNick")))
				oh.setBuyerNick(param.get("BuyerNick").toString());
			// 引用订单号
			if (WebUtil.isNull(oh.getRefOrderNo())) {
				oh.setRefOrderNo((String) param.get("RefOrderNo"));
				oh.setRefOrderType("ORDER");
			}
			// 订单类型
			if (WebUtil.isNull(oh.getOrderType()))
				oh.setOrderType("REFUND");
			// 时间记录
			if (WebUtil.isNull(oh.getId()))
				oh.setCtime(date);
			else
				oh.setMtime(date);
			// 店铺
			if (WebUtil.isNull(oh.getStoreId())) {
				oh.setStoreId(new Integer(param.get("WhId").toString()));
				// 公司
				List<Store> sList = this.getHibernateTemplate().find(
						"from Store where id = ?", oh.getStoreId());
				if (!WebUtil.isNullForList(sList)) {
					oh.setCompanyId(sList.get(0).getCompanyId());
				}
			}
			// 订单金额
			if (WebUtil.isNotNull(param.get("TotalFee"))) {
				oh.setTotalFee(new BigDecimal(Double.parseDouble(param.get(
						"TotalFee").toString())));
			}
			if (WebUtil.isNotNull(param.get("PaymentAmt"))) {
				oh.setPayment(new BigDecimal(Double.parseDouble(param.get(
						"PaymentAmt").toString())));
			}
			if (WebUtil.isNotNull(param.get("RefundAmt"))) {
				oh.setOrderAmt(new BigDecimal(Double.parseDouble(param.get(
						"RefundAmt").toString())));
			}

			if (WebUtil.isNull(oh.getId())) {
				// 生成订单时间
				oh.setOrderDate(date);
			}
			// 淘宝订单时间(退款申请时间)
			oh.setRequestDate((Date) param.get("Created"));
			if (isNew)
				session.save(oh);
			else
				session.update(oh);
			orderHeadId = oh.getId();
			// 订单金额
			OrderPayment op = new OrderPayment();
			if (WebUtil.isNotNull(param.get("TotalFee"))) {
				oh.setTotalFee(new BigDecimal(Double.parseDouble(param.get(
						"TotalFee").toString())));
				op.setActualTotalAmt(new BigDecimal(Double.parseDouble(param
						.get("TotalFee").toString())));
			}
			if (WebUtil.isNotNull(param.get("PaymentAmt"))) {
				oh.setPayment(new BigDecimal(Double.parseDouble(param.get(
						"PaymentAmt").toString())));
				op.setPaymentAmt(new BigDecimal(Double.parseDouble(param.get(
						"PaymentAmt").toString())));
			}
			if (WebUtil.isNotNull(param.get("RefundAmt"))) {
				oh.setOrderAmt(new BigDecimal(Double.parseDouble(param.get(
						"RefundAmt").toString())));
				op.setRefundAmt(new BigDecimal(Double.parseDouble(param.get(
						"RefundAmt").toString())));
			}
			op.setId(orderHeadId);
			if (isNew)
				session.save(op);
			else
				session.merge(op);
			// 退款原因
			if (WebUtil.isNotNull(param.get("RefundReason"))) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("REFUND_REASON");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(param.get("RefundReason").toString());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}

			}
			// 退款说明
			if (WebUtil.isNotNull(param.get("RefundDesc"))) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("REFUND_DESC");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(param.get("RefundDesc").toString());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}
			}
			// 客服备注
			if (WebUtil.isNotNull(param.get("CustMemo"))) {
				OrderNote note = new OrderNote();
				OrderNotePK notePK = new OrderNotePK();
				notePK.setNoteType("CUST_MEMO");
				notePK.setOrderHeadId(orderHeadId);
				note.setId(notePK);
				note.setNote(param.get("CustMemo").toString());
				if (isNew) {
					note.setCtime(date);
					session.save(note);
				} else {
					note.setMtime(date);
					session.merge(note);
				}
			}
			// 淘宝信息
			OrderTaobaoinfo ot = new OrderTaobaoinfo();
			ot.setId(orderHeadId);
			// 支付宝交易号
			if (WebUtil.isNotNull(param.get("AlipayNo")))
				ot.setAlipayNo(param.get("AlipayNo").toString());
			// 交易总金额
			if (WebUtil.isNotNull(param.get("TotalFee")))
				ot.setTotalFee(new BigDecimal(Double.parseDouble(param.get(
						"TotalFee").toString())));
			if (WebUtil.isNotNull(param.get("BuyerNick")))
				ot.setBuyerNick(param.get("BuyerNick").toString());
			if (WebUtil.isNotNull(param.get("SellerNick")))
				ot.setSellerNick(param.get("SellerNick").toString());
			// 退款申请时间
			if (WebUtil.isNotNull(param.get("Created")))
				ot.setCreated((Date) param.get("Created"));
			// 更新时间
			if (WebUtil.isNotNull(param.get("Modified")))
				ot.setModified((Date) param.get("Modified"));
			// 退款对应的订单交易状态
			if (WebUtil.isNotNull(param.get("OrderStatus")))
				ot.setStatus(param.get("OrderStatus").toString());
			// 支付给卖家的金额(交易总金额-退还给买家的金额)
			if (WebUtil.isNotNull(param.get("PaymentAmt")))
				ot.setPayment(new BigDecimal(Double.parseDouble(param.get(
						"PaymentAmt").toString())));
			// 物流方式
			if (WebUtil.isNotNull(param.get("ShippingType")))
				ot.setShippingType(param.get("ShippingType").toString());
			if (isNew) {
				ot.setCtime(date);
				session.save(ot);
			} else {
				ot.setMtime(date);
				session.merge(ot);
			}
			// 删除原明细
			session.createQuery(
					"delete OrderItem where OrderHeadId = " + orderHeadId)
					.executeUpdate();
			// 退货明细
			List<Map> oil = (List) param.get("OrderItem");
			if (oil != null && oil.size() > 0) {
				for (Map oi : oil) {
					OrderItem i = new OrderItem();
					// if(WebUtil.isNotNull(oi.get("OrderItemId")))
					// i.setId(Integer.parseInt(oi.get("OrderItemId").toString()));
					i.setOrderHeadId(orderHeadId);
					if (WebUtil.isNotNull(oi.get("ItemName")))
						i.setName(oi.get("ItemName").toString());
					if (WebUtil.isNotNull(oi.get("Qty"))) {
						i.setQty(new BigDecimal(Integer.parseInt(oi.get("Qty")
								.toString())));
						i.setReqQty(new BigDecimal(Integer.parseInt(oi.get(
								"Qty").toString())));
					}
					if (WebUtil.isNotNull(oi.get("OrigOrderItemNo")))
						i.setOrigOrderItemNo(oi.get("OrigOrderItemNo")
								.toString());
					if (WebUtil.isNotNull(oi.get("OrigSkuId")))
						i.setOrigSkuId(oi.get("OrigSkuId").toString());
					if (WebUtil.isNotNull(oi.get("Price")))
						i.setPrice(new BigDecimal(Double.parseDouble(oi.get(
								"Price").toString())));
					if (WebUtil.isNotNull(oi.get("ItemCd")))
						i.setItemCd(oi.get("ItemCd").toString());
					if (WebUtil.isNotNull(oi.get("SkuCd")))
						i.setSkuCd(oi.get("SkuCd").toString());
					if (WebUtil.isNotNull(oi.get("SkuProp1")))
						i.setSkuProp1(oi.get("SkuProp1").toString());
					if (WebUtil.isNotNull(oi.get("SkuProp2")))
						i.setSkuProp2(oi.get("SkuProp2").toString());
					if (WebUtil.isNotNull(oi.get("SkuProp3")))
						i.setSkuProp3(oi.get("SkuProp3").toString());
					if (WebUtil.isNotNull(oi.get("OrderItemStatus")))
						i.setOrderItemStatus(oi.get("OrderItemStatus")
								.toString());
					if (i.getQty() != null && i.getPrice() != null)
						i.setSubTotal(new BigDecimal(i.getQty().intValue()
								* i.getPrice().doubleValue()));
					// if(i.getId()==null)
					session.save(i);
					// else
					// session.update(i);
				}
			}
			tx.commit();
			session.close();
			result.put("Flag", "success");
		} catch (DataAccessResourceFailureException e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
		} catch (HibernateException e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
		} catch (NumberFormatException e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
		} catch (IllegalStateException e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
		}
		return result;
	}

	public String deleteRefundItem(int orderItemId, int orderHeadId) {
		// 必须是退货来源是ERP
		List<OrderHead> ol = this.getHibernateTemplate().find(
				"from OrderHead where OrderType = 'REFUND' and Origin = 'ERP' and id = "
						+ orderHeadId);
		if (ol == null || ol.size() == 0 || ol.get(0) == null)
			return "error";
		this.getHibernateTemplate().bulkUpdate(
				"delete OrderItem where id = " + orderItemId
						+ " and OrderHeadId = " + orderHeadId);
		return "success";
	}

	public String cancelRefund(int orderHeadId) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (l == null || l.size() == 0)
			return "订单未找到";
		OrderHead oh = l.get(0);
		if (!oh.getOrderStatus().equals("REFUND_START"))
			return "订单状态已改变";
		oh.setOrderStatus("REFUND_CLOSED");
		this.getHibernateTemplate().update(oh);
		return "success";
	}

	public String recoveryRefund(int orderHeadId) {
		List<OrderHead> l = this.getHibernateTemplate().find(
				"from OrderHead where id = " + orderHeadId);
		if (l == null || l.size() == 0)
			return "订单未找到";
		OrderHead oh = l.get(0);
		if (!oh.getOrderStatus().equals("REFUND_CLOSED"))
			return "订单状态已改变";
		oh.setOrderStatus("REFUND_START");
		this.getHibernateTemplate().update(oh);
		return "success";
	}

	@Override
	public Map updateSingleRefundFromPp(Map refund, Integer storeId) {
		Map result = new HashMap();
		Date date = new Date();
		List<Map> itemList = (List) refund.get("itemList");
		if (!WebUtil.isNullForList(itemList)) {
			try {
				Session session = this.getSession();
				for (Map item : itemList) {
					Map info = (Map) refund.get("refundInfo");
					Transaction tx = session.beginTransaction();
					List<OrderHead> ol = session.createQuery(
							"from OrderHead where OrigOrderNo = '"
									+ info.get("tradeinfoId") + "'").list();
					OrderHead oh = null;
					if (ol == null || ol.size() == 0) {
						oh = new OrderHead();
						oh.setOrigin("PAIPAI");
					} else
						oh = ol.get(0);
					boolean isNew = true;
					if (oh.getId() != null)
						isNew = false;
					if (WebUtil.isNull(oh.getOrderNo()))
						oh.setOrderNo(genericOrderNo("PPRO", 6, "yyyyMMdd",
								session));
					String status = "info_START";
					// 买家已经申请退款，等待卖家同意
					if (info.get("infoState").equals(
							"DS_info_WAIT_SELLER_AGREE")
							|| info.get("infoState").equals(
									"DS_info_ALL_WAIT_SELLER_AGREE")
							|| info.get("infoState").equals(
									"DS_TIMEOUT_SELLER_PASS_RETURN")) {
						status = "info_START";
					}
					// 卖家已经同意退款，等待买家退货
					else if (info.get("infoState").equals(
							"DS_info_WAIT_BUYER_DELIVERY")
							|| info.get("infoState").equals(
									"DS_TIMEOUT_BUYER_RECEIVE")) {
						status = "info_AUDITED";
					}
					// 买家已经退货，等待卖家确认收货
					else if (info.get("infoState").equals(
							"DS_info_WAIT_SELLER_RECEIVE")
							|| info.get("infoState").equals(
									"DS_TIMEOUT_SELLER_RECEIVE")
							|| info.get("infoState").equals(
									"DS_TIMEOUT_SELLER_PASS_info_ALL")) {
						status = "info_PROCESS";
					}
					// 退款关闭
					else if (info.get("infoState").equals("DS_info_ALL_CANCEL")
							|| info.get("infoState").equals("DS_info_CANCEL")) {
						status = "info_CLOSED";
					}
					// 退款成功
					else if (info.get("infoState").equals("DS_info_OK")
							|| info.get("infoState").equals("DS_info_ALL_OK")) {
						status = "info_COMPLETE";
						// 完成时间
						if (WebUtil.isNotNull(info.get("infoEndTime"))&&WebUtil.toDateForString(info.get("infoEndTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
							oh.setCompleteDate(WebUtil.toDateForString(info
									.get("infoEndTime").toString(),
									"yyyy-MM-dd HH:mm:ss"));
					}
					oh.setOrderStatus(status);
					oh.setOrigOrderNo((String) info.get("tradeinfoId"));
					oh.setOrigOrderType("PAIPAI");
					// 退款状态。可选值 WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意)
					// WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货)
					// WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货)
					// SELLER_REFUSE_BUYER(卖家拒绝退款) CLOSED(退款关闭) SUCCESS(退款成功)
					oh.setOrigOrderStatus((String) info.get("infoState"));
					// 货物状态。可选值 BUYER_NOT_RECEIVED (买家未收到货) BUYER_RECEIVED
					// (买家已收到货)
					// BUYER_RETURNED_GOODS (买家已退货)
					oh.setReceiveField5((String) info.get("infoItemState"));
					// 买家是否需要退货。可选值:true(是),false(否)
					if (WebUtil.isNotNull(info.get("infoReqitemFlag"))
							&& info.get("infoReqitemFlag").equals("1"))
						oh.setReceiveField6("true");
					else
						oh.setReceiveField6("false");
					// 物流公司
					oh.setPostCompany((String)info.get("buyerConsignmentWuliu"));
					// 运单号
					// oh.setPostNo(info.getSid());
					// 卖家地址
					// oh.setReceiveField2(info.getAddress());
					// 引用订单号
					if (WebUtil.isNull(oh.getRefOrderNo())) {
						oh.setRefOrderNo((String) refund.get("dealCode"));
						oh.setRefOrderType("ORDER");
					}
					// 订单类型
					if (WebUtil.isNull(oh.getOrderType()))
						oh.setOrderType("REFUND");
					// 时间记录
					if (WebUtil.isNull(oh.getId()))
						oh.setCtime(date);
					else
						oh.setMtime(date);
					// 店铺
					if (WebUtil.isNull(oh.getStoreId())) {
						oh.setStoreId(storeId);
						// 公司
						List<Integer> sl = session.createQuery(
								"select CompanyId from Store where id = "
										+ storeId).list();
						if (!WebUtil.isNullForList(sl))
							oh.setCompanyId(sl.get(0));
					}
					// 订单金额
					if (WebUtil.isNotNull(info.get("refundToBuyer")))
						oh.setOrderAmt(new BigDecimal(info.get("refundToBuyer").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
					if (WebUtil.isNotNull(info.get("refundToSeller")))
						oh.setPayment(new BigDecimal(info.get("refundToSeller").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
//					if (WebUtil.isNotNull(info.get("")))
//						oh.setTotalFee(new BigDecimal(info.get("").toString()));

					if (WebUtil.isNull(oh.getId())) {
						// 生成订单时间
						oh.setOrderDate(date);
						// 退款申请时间
						if(WebUtil.isNotNull(info.get("refundReqTime"))&&WebUtil.toDateForString(info.get("refundReqTime").toString(), "yyyy-MM-dd HH:mm:ss").after(WebUtil.toDateForString("1970-01-01 08:00:00", "yyyy-MM-dd HH:mm:ss")))
						oh.setRequestDate(WebUtil.toDateForString(info.get("refundReqTime").toString(), "yyyy-MM-dd HH:mm:ss"));
					}
					if (isNew)
						session.save(oh);
					else
						session.update(oh);
					Integer orderHeadId = oh.getId();

					// 退款原因
					if (!WebUtil.isNull(info.get("refundReasonDesc"))) {
						OrderNote note = new OrderNote();
						OrderNotePK notePK = new OrderNotePK();
						notePK.setNoteType("REFUND_REASON");
						notePK.setOrderHeadId(orderHeadId);
						note.setId(notePK);
						note.setNote((String)info.get("refundReasonDesc"));
						if (isNew) {
							note.setCtime(date);
							session.save(note);
						} else {
							note.setMtime(date);
							session.merge(note);
						}

					}
					// 退款说明
					if (!WebUtil.isNull(info.get("buyerConsignmentDesc"))) {
						OrderNote note = new OrderNote();
						OrderNotePK notePK = new OrderNotePK();
						notePK.setNoteType("REFUND_DESC");
						notePK.setOrderHeadId(orderHeadId);
						note.setId(notePK);
						note.setNote((String)info.get("buyerConsignmentDesc"));
						if (isNew) {
							note.setCtime(date);
							session.save(note);
						} else {
							note.setMtime(date);
							session.merge(note);
						}
					}

					// 退货明细,从原淘宝订单中得到
					List<OrderItem> oil = session.createQuery(
							"from OrderItem where OrderHeadId = " + orderHeadId
									+ " and SkuCd = '"
									+ item.get("stockLocalCode") + "'").list();
					List<OrderItem> ooil = session
							.createQuery(
									"from OrderItem where SkuCd = '"
											+ item.get("stockLocalCode")
											+ "'").list();
					OrderItem oi = null;
					// 新增退款明细
					if (oil == null || oil.size() == 0) {
						oi = new OrderItem();
						oi.setOrderHeadId(orderHeadId);
						// 无法得到原订单明细
						if (ooil == null || ooil.size() == 0) {
							//oi.setName(info.getTitle());
							if (WebUtil.isNotNull(info.get("refundToBuyerNum")))
								oi.setQty(new BigDecimal(info.get("refundToBuyerNum").toString()));
							else
								oi.setQty(new BigDecimal(0));
							if (WebUtil.isNotNull(info.get("refundToBuyerNum")))
								oi.setReqQty(new BigDecimal(info.get("refundToBuyerNum").toString()));
							else
								oi.setReqQty(new BigDecimal(0));
							oi.setOrigOrderItemNo((String)item.get("itemCode"));
							oi.setOrigSkuId((String)item.get("stockAttr"));
							BigDecimal total = new BigDecimal(0);
							if(WebUtil.isNotNull(info.get("refundToBuyer")))
								total = total.add(new BigDecimal(info.get("refundToBuyer").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
								if(WebUtil.isNotNull(info.get("refundToSeller")))
									total = total.add(new BigDecimal(info.get("refundToSeller").toString())).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
							if (WebUtil.isNotNull(oi.getReqQty()))
								oi.setPrice(total.divide(oi.getReqQty(),2,BigDecimal.ROUND_HALF_UP));
							else
								oi.setPrice(new BigDecimal(0));
							oi.setSubTotal(total);
						} else {
							OrderItem ooi = ooil.get(0);
							//oi.setName(info.getTitle());
							if (WebUtil.isNotNull(info.get("refundToBuyerNum")))
								oi.setQty(new BigDecimal(info.get("refundToBuyerNum").toString()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
							else
								oi.setQty(new BigDecimal(0));
							if (WebUtil.isNotNull(info.get("refundToBuyerNum")))
								oi.setReqQty(new BigDecimal(info.get("refundToBuyerNum").toString()));
							else
								oi.setReqQty(new BigDecimal(0));
							oi.setOrigOrderItemNo((String)item.get("itemCode"));
							oi.setOrigSkuId((String)item.get("stockAttr"));
							if (WebUtil.isNotNull(ooi.getPrice()))
								oi.setPrice(ooi.getPrice());
							else
								oi.setPrice(new BigDecimal(0));
							oi.setItemCd(ooi.getItemCd());
							oi.setSkuCd(ooi.getSkuCd());
							oi.setSkuProp1(ooi.getSkuProp1());
							oi.setSkuProp2(ooi.getSkuProp2());
							oi.setSkuProp3(ooi.getSkuProp3());
							oi.setOrderItemStatus(ooi.getOrderItemStatus());
							oi.setSubTotal(oi.getPrice().multiply(
									oi.getReqQty()));
						}
						oi.setCtime(date);
						session.save(oi);
					}
					// 用原订单明细更新退货明细
					else {
						oi = oil.get(0);
						if (ooil != null && ooil.size() > 0) {
							OrderItem ooi = ooil.get(0);
							if (oi.getOrderHeadId() == null)
								oi.setOrderHeadId(orderHeadId);
							oi.setItemCd(ooi.getItemCd());
							oi.setSkuCd(ooi.getSkuCd());
							oi.setSkuProp1(ooi.getSkuProp1());
							oi.setSkuProp2(ooi.getSkuProp2());
							oi.setSkuProp3(ooi.getSkuProp3());
							oi.setOrderItemStatus(ooi.getOrderItemStatus());
							oi.setMtime(date);
						}
						session.update(oi);
					}
					tx.commit();

				}
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
		}

		result.put("Flag", "ERROR");
		return result;
	}
}
