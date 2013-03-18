package com.sunmw.web.bean.item.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.item.ItemServices;
import com.sunmw.web.entity.ItemMaster;
import com.sunmw.web.entity.SkuMaster;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class ItemServicesImpl extends HibernateDaoSupport implements ItemServices {

	@Override
	public Map searchSku(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		StringBuffer hql = new StringBuffer(" from ItemMaster im left outer join fetch im.skuMaster sm");
		StringBuffer con = new StringBuffer();
		List conList = new ArrayList();
		// 分公司权限判断
		if (WebUtil.isNull(ul.getUserType())
				|| !ul.getUserType().equals("SYSTEM")) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" im.CompanyId = " + ul.getCompanyId());
		}
		if (!WebUtil.isNull(param.get("ItemName"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" im.ItemName like :Condition" + conList.size());
			conList.add("%"+param.get("ItemName").toString()+"%");
		}
		if (!WebUtil.isNull(param.get("ItemCd"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" im.ItemCd like :Condition" + conList.size());
			conList.add("%"+param.get("ItemCd")+"%");
		}
		if (!WebUtil.isNull(param.get("SkuCd"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" im.SkuMaster.SkuCd = :Condition" + conList.size());
			conList.add(param.get("SkuCd"));
		}
		
		if (con.length() > 0)
			hql.append(" and " + con.toString());

		Session session = this.getSession();
		try {
			Query q1 = session.createQuery("select count(*) " + hql.toString());
			// 查询条件
			for (int i = 0; i < conList.size(); i++) {
				q1.setParameter("Condition" + i, conList.get(i));
			}
			List<Long> countList = q1.list();
			int count = countList.get(0).intValue();
			Query q = session.createQuery(hql.toString());
			// 查询条件
			for (int i = 0; i < conList.size(); i++) {
				q.setParameter("Condition" + i, conList.get(i));
			}
			if (pageRow > 0) {
				q.setFirstResult((currentPage - 1) * pageRow);
				q.setMaxResults(pageRow);
			}
			
			List<Object[]> l = q.list();
			List<Map> r = new ArrayList();
			for (Object[] obj : l) {
				ItemMaster im = (ItemMaster) obj[0];
				SkuMaster sm = (SkuMaster) obj[1];
				Map m = new HashMap();
				m.put("ItemId", im.getId());
				m.put("SkuId", sm.getId());
				m.put("ItemCd", im.getItemCd());
				m.put("SkuCd", sm.getSkuCd());
				m.put("ItemName", im.getItemName());
				m.put("ItemPrice", WebUtil.round(im.getItemPrice(), 2));
				m.put("SkuPrice1", WebUtil.round(sm.getSkuPrice1(), 2));
				m.put("SkuPrice2", WebUtil.round(sm.getSkuPrice2(), 2));
				m.put("SkuProp1", sm.getSkuProp1());
				m.put("SkuProp2", sm.getSkuProp2());
				m.put("SkuProp3", sm.getSkuProp3());
				m.put("SkuProp4", sm.getSkuProp4());
				r.add(m);
			}
			result.put("RESULT", r);
			result.put("COUNT_ROW", count);
		} catch (HibernateException e) {
			result.put("Flag", "ERROR");
			result.put("Message", e.getMessage());
			logger.error(e.getMessage());
		} catch (RuntimeException e) {
			result.put("Flag", "ERROR");
			result.put("Message", e.getMessage());
			logger.error(e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return result;
	}

	@Override
	public Map searchSkuByBarcode(Map param) {
		
		Map result = new HashMap();

		result.put("Flag", "SUCCESS");
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if (WebUtil.isNull(ul)) {
			result.put("Flag", "ERROR");
			return result;
		}
		String hql = " from ItemMaster im left outer join fetch im.skuMaster sm where (sm.Barcode1 = ? or sm.Barcode2 = ?)";
		Object[] hqlParam = null;
		// 分公司权限判断
		if (WebUtil.isNull(ul.getUserType())
				|| !ul.getUserType().equals("SYSTEM")) {
			hql = hql+" and im.CompanyId = ?";
			hqlParam = new Object[]{param.get("barcode"),param.get("barcode"),ul.getCompanyId()};
		}
		else
		{
			hqlParam = new Object[]{param.get("barcode"),param.get("barcode")};
		}
		List<ItemMaster> l = this.getHibernateTemplate().find(hql,hqlParam);
		if(WebUtil.isNullForList(l))
			return result;
		ItemMaster im = l.get(0);
		result.put("itemId", im.getId());
		result.put("itemCd", im.getItemCd());
		result.put("itemName", im.getItemName());
		result.put("itemPrice", WebUtil.round(im.getItemPrice(), 2));
		Set<SkuMaster> ss = im.getSkuMaster();
		if(ss!=null&&ss.size()>0)
		{
			SkuMaster sm = ss.iterator().next();
			result.put("skuId", sm.getId());
			result.put("skuCd", sm.getSkuCd());
			result.put("skuProp1", sm.getSkuProp1());
			result.put("skuProp2", sm.getSkuProp2());
			result.put("skuProp3", sm.getSkuProp3());
			result.put("skuProp4", sm.getSkuProp4());
			result.put("skuPrice1", WebUtil.round(sm.getSkuPrice1(), 2));	
			result.put("skuPrice2", WebUtil.round(sm.getSkuPrice2(), 2));		
		}		
		return result;
	}

}
