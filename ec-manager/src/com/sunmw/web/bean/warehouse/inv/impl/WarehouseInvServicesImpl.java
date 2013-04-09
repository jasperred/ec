package com.sunmw.web.bean.warehouse.inv.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.warehouse.inv.WarehouseInvServices;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class WarehouseInvServicesImpl extends HibernateDaoSupport implements WarehouseInvServices {

	
	public Map searchInventory(Map param, int currentPage, int pageRow) 
	{
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		StringBuffer hql = new StringBuffer(" from Inventory inv,Warehouse wh,ItemMaster im left outer join im.skuMaster sm where inv.WhId = wh.id and (inv.SkuCd = sm.SkuCd or inv.SkuCd = im.ItemCd) ");
		String fields = "select im.ItemCd,im.ItemName,sm.SkuCd,sm.SkuProp1,sm.SkuProp2,inv.InvType,inv.InvStatus,inv.Quantity,inv.AvailableQuantity,wh.id,wh.WhName ";
		StringBuffer con = new StringBuffer();
		// 分公司权限判断
		if (WebUtil.isNull(ul.getUserType())
				|| !ul.getUserType().equals("SYSTEM")) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" wh.CompanyId = " + ul.getCompanyId());
		}
		if(WebUtil.isNotNull(param.get("WhId")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" inv.WhId = '"+param.get("WhId")+"'");
		}
		if(WebUtil.isNotNull(param.get("ItemCd")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" im.ItemCd = '"+param.get("ItemCd")+"'");
		}
		if(WebUtil.isNotNull(param.get("SkuCd")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" sm.SkuCd = '"+param.get("SkuCd")+"'");
		}
		if(WebUtil.isNotNull(param.get("InvType")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" inv.InvType = '"+param.get("InvType")+"'");
		}
		if(con.length()>0)
			hql.append(" and "+con.toString());
		hql.append(" order by inv.Mtime desc");
		List<Long> countList = this.getHibernateTemplate().find("select count(*) "+hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery(fields+hql.toString());
		if(pageRow>0)
		{
			q.setFirstResult((currentPage-1)*pageRow);
			q.setMaxResults(pageRow);
		}
		List<Object[]> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if(!WebUtil.isNullForList(l))
		{
			for(Object[] o:l)
			{
				Map r = new HashMap();
				r.put("ItemCd", o[0]);
				r.put("ItemName", o[1]);
				r.put("SkuCd", o[2]);
				r.put("SkuProp1", o[3]);
				r.put("SkuProp2", o[4]);
				r.put("InvType", o[5]);
				r.put("InvStatus", o[6]);
				r.put("Quantity", o[7]);
				r.put("AvailableQuantity", o[8]);
				r.put("WhId", o[9]);
				r.put("WhName", o[10]);
				rlist.add(r);				
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}
}
