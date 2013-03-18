package com.sunmw.web.bean.order.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.order.OrderDataServices;
import com.sunmw.web.entity.No;
import com.sunmw.web.entity.OrderHead;
import com.sunmw.web.entity.OrderItem;
import com.sunmw.web.entity.OrderNote;
import com.sunmw.web.entity.OrderNotePK;
import com.sunmw.web.entity.OrderPromotion;
import com.sunmw.web.util.WebUtil;

public class OrderDataServicesImpl extends HibernateDaoSupport implements
		OrderDataServices {

	@Override
	public void insertNullOrder(String origOrderNo, String prefix) {
		// 此处同步处理，防止数据重复
		synchronized (this) {
			List l = this.getHibernateTemplate().find(
					"select count(*) from OrderHead where OrigOrderNo = ?",
					origOrderNo);
			if (l != null && l.size() > 0 && l.get(0) != null)
				return;
			OrderHead oh = new OrderHead();
			oh.setOrderNo(genericOrderNo(prefix, 6, "yyyyMMdd"));
			oh.setOrigOrderNo(origOrderNo);
			oh.setOrderStatus("NONE");
			oh.setCtime(new Date());
			this.getHibernateTemplate().save(oh);
		}
	}

	@Override
	public void updateOrder(Map param, Integer storeId) {
		OrderHead oh = null;
		List<OrderHead> ohList = this.getHibernateTemplate().find(
				"from OrderHead where OrigOrderNo = ?", param.get("Tid"));
		// 没有插入过Tid则不处理
		if (ohList == null || ohList.size() == 0) {
			return;
		}
		oh = ohList.get(0);
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
		List<Map> toList = (List) param.get("Orders");
		List<Map> pdList = (List) param.get("PromotionDetails");

		String tradeStatus = (String) param.get("Status");
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
					|| tradeStatus.equals("WAIT_SELLER_SEND_GOODS_ACOUNTED") || // 分销使用，已付款（已分账），待发货。只对代销分账支持
					// 已付款状态但保留原因不是未支付，订单状态设置为‘未审核’
					(tradeStatus.equals("WAIT_SELLER_SEND_GOODS")
							&& WebUtil.isNotNull(oh.getReceiveField1()) && !oh
							.getReceiveField1().equals("I"))) {
				oh.setOrderStatus("START");
			}
		}
		// 订单保留原因处理
		if (!WebUtil.isNull(param.get("BuyerMessage")))
			oh.setReceiveField1("A");
		if (WebUtil.isNull(param.get("ReceiverState"))
				|| WebUtil.isNull(param.get("ReceiverCity"))
				|| WebUtil.isNull(param.get("ReceiverDistrict"))
				|| WebUtil.isNull(param.get("ReceiverAddress")))
			oh.setReceiveField1("B");
		if (WebUtil.isNull(param.get("ReceiverMobile"))
				&& WebUtil.isNull(param.get("ReceiverPhone")))
			oh.setReceiveField1("D");
		if (WebUtil.isNull(param.get("ReceiverName")))
			oh.setReceiveField1("G");
		if (tradeStatus.equals("WAIT_BUYER_PAY")
				|| tradeStatus.equals("TRADE_NO_CREATE_PAY"))
			oh.setReceiveField1("I");
		if (toList == null || toList.size() == 0)
			oh.setReceiveField1("J");

		BigDecimal orderAmt = new BigDecimal(0);// 合计明细金额
		// 检查订单明细中的skuCd是否存在
		if (toList != null) {
			for (Map to : toList) {
				if (to.get("Price") != null && to.get("Num") != null)
					orderAmt = orderAmt.add(new BigDecimal(to.get("Price")
							.toString()).multiply(new BigDecimal(to.get("Num")
							.toString())));
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
		oh.setOrigOrderStatus(tradeStatus);
		// 发货时间
		if (WebUtil.isNotNull(param.get("ConsignTime"))
				&& WebUtil.isNull(oh.getDeliveryDate())) {
			oh.setDeliveryDate((Date) param.get("ConsignTime"));
		}
		// 支付时间
		oh.setPayTime((Date) param.get("PayTime"));
		// 支付帐号
		oh.setPayNo((String) param.get("AlipayNo"));
		// 发票
		oh.setInvoiceContent((String) param.get("InvoiceName"));
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
		// 订单时间取支付时间
		oh.setOrderDate((Date) param.get("PayTime"));
		if (WebUtil.isNull(oh.getId())) {
			oh.setRequestDate((Date) param.get("Created"));
		}

		Integer orderHeadId = oh.getId();
		if (oh.getOrderStatus() == null || oh.getOrderStatus().equals("NONE")) {
			oh.setOrigOrderType((String) param.get("OrigOrderType"));
			oh.setBuyerNick((String) param.get("BuyerNick"));
			oh.setSellerNick((String) param.get("SellerNick"));
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
			this.getHibernateTemplate().update(oh);
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
								+ " and  OrigOrderItemNo in (" + oids.toString()
								+ ")");
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
						detailAmt = detailAmt
								.add((BigDecimal) (to.get("TotalFee")));
				}
				// 已计算的整单折扣金额
				BigDecimal detailCount = new BigDecimal(0);

				int r = 1;
				for (Map to : toList) {
					OrderItem oi = new OrderItem();
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
							String[] props = to.get("SkuPropertiesName").toString()
									.split(";");
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
							oi.setRealPrice(oi.getRealPriceAmt().divide(
									oi.getReqQty(), 2, BigDecimal.ROUND_HALF_UP));
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
							&& pd.get("Id") == param.get("Tid")) {
						opm.setId(orderHeadId);
						opm.setType("ORDER_HEAD");
					}
					// 明细促销
					else {
						// 不在明细中的促销不处理
						if (WebUtil.isNull(pd.get("Id")))
							continue;
						if (WebUtil.isNull(tbIdMap.get(pd.get("Id").toString())))
							continue;
						opm.setId((Integer) tbIdMap.get(pd.get("Id").toString()));
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
		} else {
			oh.setMtime(date);
			this.getHibernateTemplate().update(oh);
		}
		
	}

	private String genericOrderNo(String prefix, int len, String format) {
		Date d = new Date();
		String date = WebUtil.formatDateString(d, format);
		No no = new No();
		no.setTime(d);
		this.getHibernateTemplate().save(no);
		return prefix + date + WebUtil.addPrefix("" + no.getId(), len, "0");

	}

}
