package com.sunmw.web.bean.in.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.in.InventoryInServices;
import com.sunmw.web.entity.OrderHead;
import com.sunmw.web.entity.OrderItem;
import com.sunmw.web.entity.OrderNote;
import com.sunmw.web.entity.StatusItem;
import com.sunmw.web.entity.Warehouse;
import com.sunmw.web.util.WebUtil;

public class InventoryInServicesImpl extends HibernateDaoSupport implements
		InventoryInServices {
	public Map searchInventoryInOrder(Map param, int currentPage, int pageRow)
	{
		StringBuffer hql = new StringBuffer(" from OrderHead oh,OrderItem oi,StatusItem si where oh.id = oi.OrderHeadId and oh.OrderType = 'INV_IN'");
		String fields = "select distinct oh.id,oh.WhId,oh.OrderNo,oh.RequestDate,oh.DeliveryDate,oh.CompleteDate,oh.OrderStatus,si.Description,oh.ReceiveField1,sum(oi.ReqQty),sum(oi.Qty) ";
		StringBuffer con = new StringBuffer();
		if(WebUtil.isNotNull(param.get("OrderNo")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" oh.OrderNo = '"+param.get("OrderNo")+"'");
		}
		if(WebUtil.isNotNull(param.get("OrderStatus")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" oh.OrderStatus = '"+param.get("OrderStatus")+"'");
		}
		if(WebUtil.isNotNull(param.get("WhId")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" oh.WhId = '"+param.get("WhId")+"'");
		}
		if(WebUtil.isNotNull(param.get("FromDate")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" oh.RequestDate >= '"+param.get("FromDate")+"'");
		}
		if(WebUtil.isNotNull(param.get("EndDate")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" oh.RequestDate < '"+param.get("EndDate")+"'");
		}
		if(con.length()>0)
			hql.append(" and "+con.toString());
		if(WebUtil.isNotNull(param.get("SORT")))
			hql.append(" order by "+param.get("SORT"));
		else
			hql.append(" order by oh.RequestDate");
		if(WebUtil.isNotNull(param.get("ORDER")))
			hql.append(" "+param.get("ORDER"));
		
		List<Long> countList = this.getHibernateTemplate().find("select count(*) "+hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery(fields+hql.toString());
		if(pageRow>0)
		{
			q.setFirstResult((currentPage-1)*pageRow);
			q.setMaxResults(pageRow);
		}
		Map result = new HashMap();
		List<Object[]> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if(!WebUtil.isNullForList(l))
		{
			for(Object[] o:l)
			{
				Map r = new HashMap();
				r.put("OrderHeadId", o[0]);
				r.put("WhId", o[1]);
				r.put("OrderNo", o[2]);
				r.put("RequestDate", o[3]);
				r.put("DeliveryDate", o[4]);
				r.put("CompleteDate", o[5]);
				r.put("OrderStatus", o[6]);
				r.put("OrderStatusName", o[7]);
				r.put("Memo", o[8]);
				r.put("ReqQty", o[9]);
				r.put("Qty", o[10]);
				rlist.add(r);				
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;		
	}
	
	public Map getInventoryInInfo(int orderHeadId)
	{
		List<Object[]> ol = this.getHibernateTemplate().find("from OrderHead oh,Warehouse w,StatusItem si where oh.OrderType = 'INV_IN' and oh.WhId = w.id and oh.OrderStatus = si.StatusCode and si.StatusTypeId = 'INV_IN_STATUS' and oh.id = "+orderHeadId);
		if(WebUtil.isNullForList(ol))
			return null;
		OrderHead oh = (OrderHead)ol.get(0)[0];
		Warehouse w = (Warehouse)ol.get(0)[1];
		StatusItem si = (StatusItem)ol.get(0)[2];
		Map result = new HashMap();
		result.put("OrderHeadId", oh.getId());
		result.put("OrderNo", oh.getOrderNo());
		result.put("WhId", oh.getStoreId());
		result.put("WhName", w.getWhName());
		result.put("RequestDate", oh.getRequestDate());
		result.put("DeliveryDate", oh.getDeliveryDate());
		result.put("CompleteDate", oh.getCompleteDate());
		result.put("OrderStatus", oh.getOrderStatus());
		result.put("OrderStatusName", si.getDescription());
		List<OrderNote> onList = this.getHibernateTemplate().find("from OrderNote where id.OrderHeadId = "+orderHeadId);
		if(onList!=null)
		{
			for(OrderNote on:onList)
			{
				result.put(on.getId().getNoteType(), on.getNote());
			}
		}
		return result;
	}
	
	public Map getInventoryInOrderItem(Map param, int currentPage, int pageRow)
	{
		StringBuffer hql = new StringBuffer(" from OrderItem ");
		StringBuffer con = new StringBuffer();
		if(WebUtil.isNotNull(param.get("OrderHeadId")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" OrderHeadId = "+param.get("OrderHeadId"));
		}
		if(WebUtil.isNotNull(param.get("SkuCd")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" SkuCd like '%"+param.get("SkuCd")+"%'");
		}
		if(WebUtil.isNotNull(param.get("ItemCd")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" ItemCd like '%"+param.get("ItemCd")+"%'");
		}
		if(WebUtil.isNotNull(param.get("Name")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" Name like '%"+param.get("Name")+"%'");
		}
		if(con.length()>0)
			hql.append(" and "+con.toString());
		if(WebUtil.isNotNull(param.get("SORT")))
			hql.append(" order by "+param.get("SORT"));
		else
			hql.append(" order by Ctime");
		if(WebUtil.isNotNull(param.get("ORDER")))
			hql.append(" "+param.get("ORDER"));
		
		List<Long> countList = this.getHibernateTemplate().find("select count(*) "+hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery(hql.toString());
		if(pageRow>0)
		{
			q.setFirstResult((currentPage-1)*pageRow);
			q.setMaxResults(pageRow);
		}
		Map result = new HashMap();
		List<OrderItem> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if(!WebUtil.isNullForList(l))
		{
			for(OrderItem o:l)
			{
				Map r = new HashMap();
				r.put("OrderHeadId", o.getOrderHeadId());
				r.put("OrderItemId", o.getId());
				r.put("SkuCd", o.getSkuCd());
				r.put("ItemCd", o.getItemCd());
				r.put("Name", o.getName());
				r.put("SkuProp1", o.getSkuProp1());
				r.put("SkuProp2", o.getSkuProp2());
				r.put("SkuProp3", o.getSkuProp3());
				r.put("ReqQty", o.getReqQty());
				r.put("Qty", o.getQty());
				r.put("OrderItemType", o.getOrderItemType());
				r.put("OrderItemMemo", o.getOrderItemMemo());
				rlist.add(r);				
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;		
	}

}
