package com.sunmw.web.bean.data.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.entity.OrderHead;
import com.sunmw.web.entity.OrderItem;
import com.sunmw.web.entity.OrderNote;
import com.sunmw.web.entity.OrderPayment;
import com.sunmw.web.entity.OrderTaobaoinfo;
import com.sunmw.web.util.WebUtil;

public class OrderTaskServicesImpl extends HibernateDaoSupport implements com.sunmw.web.bean.data.OrderTaskServices {

	public List exportOrderToWMS(int storeId)
	{
		List<Object[]> l = this.getHibernateTemplate().find("from OrderHead oh,OrderTaobaoinfo ot,OrderPayment op,OrderItem oi where oh.id = ot.id and oh.id = op.id and oh.id = oi.OrderHeadId and oh.OrderType = 'ORDER' and oh.OrderStatus = 'AUDITED' and oh.WhId = '"+storeId+"' order by ot.PayTime,oh.OrderNo");
		if(l==null||l.size()==0)
			return null;
		List r = new ArrayList();
		for(Object[] obj:l)
		{
			OrderHead oh = (OrderHead) obj[0];
			OrderTaobaoinfo ot = (OrderTaobaoinfo) obj[1];
			OrderPayment op = (OrderPayment) obj[2];
			OrderItem oi = (OrderItem) obj[3];
			//淘宝状态为空或淘宝状态是买未付款不导出
			if(WebUtil.isNull(oh.getOrigOrderStatus())||oh.getOrigOrderStatus().equals("WAIT_BUYER_PAY"))
				continue;
			Map m = new HashMap();
			List<OrderNote> onl = this.getHibernateTemplate().find("from OrderNote where id.OrderHeadId = "+oh.getId());
			m.put("OrderNo", oh.getOrderNo());
			m.put("TaobaoOrderNo",oh.getOrigOrderNo() );
			m.put("BuyerName", ot.getBuyerNick());
			m.put("BuyerMail", ot.getBuyerEmail());
			m.put("ReceiverPhone",ot.getReceiverPhone() );
			m.put("ReceiverMobile", ot.getReceiverMobile());
			m.put("ReceiverName", ot.getReceiverName());
			m.put("ReceiverState", ot.getReceiverState());
			m.put("ReceiverCity", ot.getReceiverCity());
			m.put("ReceiverDistrict", ot.getReceiverDistrict());
			m.put("ReceiverAddress", ot.getReceiverAddress());
			m.put("ReceiverAZip", ot.getReceiverZip());
			//发票和发票抬头
			if(oh.getInvoiceId()!=null&&oh.getInvoiceId()==1)
			{
				m.put("IsInvoice", "是");
				if(WebUtil.isNull(oh.getReceiveField2()))
				{
					m.put("InvoiceTitle", "个人");
				}
				else
				{
					m.put("InvoiceTitle", oh.getReceiveField2().trim());
				}
			}
			if(onl!=null)
			{
				for(OrderNote on:onl)
				{
					if(on.getId().getNoteType().equals("BUYER_MESSAGE"))
						m.put("BuyerMessage", on.getNote());
					if(on.getId().getNoteType().equals("BUYER_MEMO"))
						m.put("BuyerMemo", on.getNote());
					if(on.getId().getNoteType().equals("SELLER_MEMO"))
						m.put("SellerMemo", on.getNote());
					if(on.getId().getNoteType().equals("TRADE_MEMO"))
						m.put("TradeMemo", on.getNote());
					if(on.getId().getNoteType().equals("CUST_MEMO"))
						m.put("CustMemo", on.getNote());
				}
			}
			m.put("ShippingType", ot.getShippingType());
			m.put("Name",oi.getName() );
			m.put("SkuCd", oi.getSkuCd());
			m.put("ItemCd", oi.getItemCd());
			m.put("Price",oi.getPrice() );
			m.put("Qty", oi.getQty());
			m.put("ActualTotalAmt", op.getActualTotalAmt());
			m.put("PaymentAmt", op.getPaymentAmt());
			m.put("FreightAmt", op.getFreightAmt());
			//优惠金额=订单金额-（支付金额-运费）
			double gift = op.getActualTotalAmt().doubleValue()-(op.getPaymentAmt().doubleValue()-op.getFreightAmt().doubleValue());
			m.put("AllGift", gift);
			if(gift>0)
			{
				//分摊金额=优惠金额*（价格*数量/订单金额）/数量--四舍五入
				double pGift = gift*((oi.getPrice().doubleValue()*oi.getQty().doubleValue())/op.getActualTotalAmt().doubleValue())/oi.getQty().intValue();
				//四舍五入
				BigDecimal   b   =   new   BigDecimal(pGift); 
				double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
				m.put("OrderItemPriceGift", f1);//明细分摊的优惠金额
			}
			else
			{
				m.put("OrderItemPriceGift", new Double(0));				
			}
			r.add(m);
		}
		return r;
	}
	
	public String updateOrderWmsStatus(String orderNos)
	{
		if(orderNos==null||orderNos.trim().length()==0)
			return "success";
		//更新淘宝订单的状态为‘进入WMS’,要求必须是类型TB_ORDER状态AUDITED
		this.getHibernateTemplate().bulkUpdate("update OrderHead set OrderStatus = 'WMS' where OrderNo in ("+orderNos+") and OrderStatus = 'AUDITED' and OrderType = 'ORDER'");
		return "success";
	}
	
	public String updateOrderLogistics(List<Map> logisticsList)
	{
		Session session = this.getSession();
		if(logisticsList==null)
			return "success";
		Date date = new Date();
		//Transaction tx = session.beginTransaction();
		for(Map<String,String> m:logisticsList)
		{
			String taobaoNo = m.get("TaobaoNo");
			String company = m.get("company");
			String sid = m.get("sid");
			if(WebUtil.isNull(taobaoNo)||WebUtil.isNull(company)||WebUtil.isNull(sid))
			{
				continue;
			}
			//更新订单为已发货状态,必须是类型TB_ORDER状态WMS
//			List<OrderHead> ol = session.createQuery("from OrderHead where OrderStatus = 'WMS' and OrderType = 'ORDER' and OrigOrderNo = '"+taobaoNo+"'").list();
//			if(WebUtil.isNullForList(ol))
//				continue;
//			OrderHead o = ol.get(0);
//			o.setOrderStatus("DELIVERY");
//			o.setDeliveryDate(date);
//			o.setReceiveField3(company);
//			o.setReceiveField4(sid);
			
			int i = session.createQuery("update OrderHead set OrderStatus = 'DELIVERY',DeliveryDate = '"+WebUtil.formatDateString(date, "yyyy-MM-dd HH:mm:ss")+"',ReceiveField3 = '"+company+"',ReceiveField4 = '"+sid+"' where OrderStatus = 'WMS' and OrderType = 'ORDER' and OrigOrderNo = '"+taobaoNo+"'").executeUpdate();	
			//在这里减库存,认为物流信息传过来就可以减库存了
//			List<OrderItem> oil = session.createQuery("from OrderItem where OrderHeadId = "+o.getId()).list();
//			if(WebUtil.isNullForList(oil))
//			{
//				continue;
//			}
//			String whId = inventoryServices.getWhId(new Integer(o.getWhId()));
//			List<Map> invList = new ArrayList();
//			for(OrderItem oi:oil)
//			{
//				Map im = new HashMap();
//				im.put("WhId", whId);
//				im.put("SkuCd", oi.getSkuCd());
//				im.put("Qty", oi.getQty().intValue());
//				im.put("InvType", "A");
//				im.put("LogType", "ORDER");
//				im.put("IoType", "O");
//				invList.add(im);
//			}
//			Map<String,String> r = inventoryServices.inventoryProcess(invList, WebConfigProperties.getProperties("inventory.allowNegativeInv"));
//			//库存失败不做物流信息更新
//			if(r.get("FLAG").equals("ERROR"))
//			{
//				tx.rollback();
//				continue;
//			}
//			session.update(o);
//			tx.commit();
			
		}
		session.close();
		return "success";
	}
	
	public List getToTbLogisticsOrder(int storeId)
	{
		List<OrderHead> l = this.getHibernateTemplate().find("from OrderHead where OrderStatus = 'DELIVERY' and OrderType = 'ORDER' and WhId = '"+storeId+"'");
		if(l==null||l.size()==0)
			return null;
		List<Map> r = new ArrayList();
		for(OrderHead oh:l)
		{
			Map m = new HashMap();
			m.put("TaobaoNo", oh.getOrigOrderNo());
			//delivery_needed(物流订单发货),virtual_goods(虚拟物品发货)
			m.put("OrderType", "delivery_needed");
			m.put("CompanyCode", oh.getReceiveField3());
			m.put("OutSid", oh.getReceiveField4());
			r.add(m);
		}
		return r;
		
	}
	
	public String updateOrderLogisticsStatus(String taobaoNos)
	{
		if(taobaoNos==null||taobaoNos.trim().length()==0)
			return "success";
		String date = WebUtil.formatDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
		//更新淘宝物流信息上传后的状态为COMPLETE,必须是类型TB_ORDER状态DELIVERY--暂时不做更改,淘宝交易成功时才改为完成状态
		//this.getHibernateTemplate().bulkUpdate("update OrderHead set OrderStatus = 'COMPLETE',CompleteDate = '"+date+"' where OrigOrderNo in ("+taobaoNos+") and OrderStatus = 'DELIVERY' and OrderType = 'ORDER'");
		//是否要减库存???
		return "success";
	}
}
