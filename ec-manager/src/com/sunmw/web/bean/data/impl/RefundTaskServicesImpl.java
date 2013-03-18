package com.sunmw.web.bean.data.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.data.RefundTaskServices;
import com.sunmw.web.entity.OrderHead;
import com.sunmw.web.entity.OrderItem;
import com.sunmw.web.entity.OrderNote;
import com.sunmw.web.entity.OrderPayment;
import com.sunmw.web.entity.OrderTaobaoinfo;
import com.sunmw.web.entity.StatusItem;
import com.sunmw.web.util.WebUtil;

public class RefundTaskServicesImpl extends HibernateDaoSupport implements
		RefundTaskServices {

	
	public List exportRefund(int storeId)
	{
		String hql = "from OrderHead oh,OrderTaobaoinfo ot,OrderPayment op,OrderItem oi where oh.id = ot.id and oh.id = op.id and oh.id = oi.OrderHeadId and oh.OrderType = 'REFUND' and oh.OrderStatus = 'REFUND_AUDITED' and oh.WhId = '"+storeId+"' order by ot.PayTime,oh.OrderNo";
		List<Object[]> l = this.getHibernateTemplate().find(hql);
		if(l==null||l.size()==0)
			return null;
		Map statusMap = getStatusMap("'TAOBAO_ORDER','TAOBAO_GOOD','TAOBAO_RETURN'");
		List r = new ArrayList();
		for(Object[] obj:l)
		{
			OrderHead oh = (OrderHead) obj[0];
			OrderTaobaoinfo ot = (OrderTaobaoinfo) obj[1];
			OrderPayment op = (OrderPayment) obj[2];
			OrderItem oi = (OrderItem) obj[3];
			//查退款原订单
			List<Object[]> olist = this.getHibernateTemplate().find("from OrderHead oh,OrderTaobaoinfo ot where oh.id = ot.id and oh.OrigOrderNo = '"+oh.getRefOrderNo()+"'");
			OrderHead ooh = null;
			OrderTaobaoinfo oot = null;
			if(olist!=null&&olist.size()>0)
			{
				Object[] o = olist.get(0);
				ooh = (OrderHead) o[0];
				oot = (OrderTaobaoinfo) o[1];
			}
			Map m = new HashMap();
			List<OrderNote> onl = this.getHibernateTemplate().find("from OrderNote where id.OrderHeadId = "+oh.getId());
			m.put("OrderNo", oh.getOrderNo());//系统单号
			m.put("Origin", oh.getOrigin());//订单来源淘宝下载或手工创建
			m.put("RefundOrderNo",oh.getOrigOrderNo() );//淘宝退款单号
			m.put("RefOrderNo",oh.getRefOrderNo() );//原淘宝单号
			m.put("RefundStatus", statusMap.get(oh.getOrigOrderStatus()));//退款状态
			if(ooh!=null)
				m.put("TbOrderStatus", statusMap.get(ooh.getOrigOrderStatus()));//订单状态
			m.put("GoodStatus", statusMap.get(oh.getReceiveField5()));//货物状态
			m.put("HasGoodReturn", oh.getReceiveField6());//买家是否需要退货
			m.put("RefundDate", WebUtil.formatDateString(ot.getCreated(), "yyyy-MM-dd HH:mm:ss"));//退款申请时间
			m.put("ActualTotalAmt", op.getActualTotalAmt());//订单金额
			m.put("PaymentAmt", op.getPaymentAmt());//支付金额
			m.put("RefundAmt", op.getRefundAmt());//退款金额
			m.put("BuyerName", ot.getBuyerNick());//买家
			//收货信息
			if(oot!=null)
			{
				m.put("ReceiverPhone",oot.getReceiverPhone() );
				m.put("ReceiverMobile", oot.getReceiverMobile());
				m.put("ReceiverName", oot.getReceiverName());
				m.put("ReceiverState", oot.getReceiverState());
				m.put("ReceiverCity", oot.getReceiverCity());
				m.put("ReceiverDistrict", oot.getReceiverDistrict());
				m.put("ReceiverAddress", oot.getReceiverAddress());
				m.put("ReceiverAZip", oot.getReceiverZip());
			}
			//备注
			if(onl!=null)
			{
				for(OrderNote on:onl)
				{
					m.put(on.getId().getNoteType(), on.getNote());
				}
			}
			m.put("Name",oi.getName() );
			m.put("SkuCd", oi.getSkuCd());
			m.put("ItemCd", oi.getItemCd());
			m.put("Price",oi.getPrice() );
			m.put("Qty", WebUtil.isNotNull(oi.getQty())?oi.getQty():oi.getReqQty());
			r.add(m);
		}
		return r;
	}
	
	//得到状态
	private Map getStatusMap(String type)
	{
		Map m = new HashMap();
		List<StatusItem> l = this.getHibernateTemplate().find("from StatusItem where StatusTypeId in ("+type+")");
		for(StatusItem si:l)
		{
			m.put(si.getStatusCode(), si.getDescription());
		}
		return m;
	}
	
	//更新退货单状态为退款中
	public String updateRefundWmsStatus(String orderNos)
	{
		if(orderNos==null||orderNos.trim().length()==0)
			return "success";
		//更新淘宝退款订单的状态为‘退款中’,要求必须是类型TB_REFUND状态REFUND_AUDITED
		this.getHibernateTemplate().bulkUpdate("update OrderHead set OrderStatus = 'REFUND_PROCESS' where OrderNo in ("+orderNos+") and OrderStatus = 'REFUND_AUDITED' and OrderType = 'REFUND'");
		return "success";
	}
	
	//保证status为系统中的状态REFUND_CLOSED或REFUND_COMPLETE
	public String updateRefundErpStatus(List<Map> l)
	{
		if(l==null||l.size()==0)
			return "success";
		Session session = this.getSession();
		for(Map m:l)
		{
			Transaction tx = session.beginTransaction();
			//更新状态、金额、商品、数量
			String orderNo = (String) m.get("OrderNo");
			String status = (String) m.get("OrderStatus");
			Double refundAmt = (Double) m.get("RefundAmt");
			String skuCd = (String) m.get("SkuCd");
			Integer qty = (Integer) m.get("RefundQty");
			if(WebUtil.isNull(status))
				continue;
			List<Object[]> ol = session.createQuery("from OrderHead oh,OrderPayment op where oh.id = op.id and OrderNo = '"+orderNo+"'").list();
			if(ol==null||ol.size()==0)
				continue;
			Object[] obj = ol.get(0);
			OrderHead oh = (OrderHead)obj[0];
			OrderPayment op = (OrderPayment)obj[1];
			oh.setOrderStatus(status);
			session.update(oh);
			op.setRefundAmt(new BigDecimal(refundAmt));
			session.update(op);
			if(WebUtil.isNotNull(skuCd))
			{
				//更新订单明细,使用订单ID和SkuCd为条件
				session.createQuery("update OrderItem set Qty = "+qty+" where OrderHeadId = "+oh.getId()+" and SkuCd = '"+skuCd+"'");
			}
			tx.commit();
			
		}
		session.close();
		return "success";
	}
}
