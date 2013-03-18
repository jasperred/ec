package com.sunmw.web.bean.store.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.shop.TaobaoShopServices;
import com.sunmw.web.bean.store.StoreServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.entity.Company;
import com.sunmw.web.entity.Store;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class StoreServicesImpl extends HibernateDaoSupport implements StoreServices {

	@Override
	public Map deleteStore(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if(WebUtil.isNull(ul))
		{
			result.put("Flag", "error");
			result.put("Message", "无操作用户信息");
			return result;
		}
		Integer storeId = (Integer)param.get("StoreId");
		List<Store> sl = null;
		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
			sl = this.getHibernateTemplate().find("from Store where id = ? and CompanyId = ?",new Object[]{storeId,ul.getCompanyId()});
		else
			sl = this.getHibernateTemplate().find("from Store where id = ?",storeId);
		if(!WebUtil.isNullForList(sl))
		{
			this.getHibernateTemplate().delete(sl.get(0));
			TaobaoShopServices tss = (TaobaoShopServices) GetBeanServlet.getBean("tbShopServices");
			tss.deleteTbStore(param);
			result.put("Flag", "success");
		}
		else
		{
			result.put("Flag", "error");
			result.put("Message", "无记录");
		}
		return result;
	}

	@Override
	public Map saveStore(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if(WebUtil.isNull(ul))
		{
			result.put("Flag", "error");
			result.put("Message", "无操作用户信息");
			return result;
		}
		Integer storeId = (Integer)param.get("StoreId");
		Store store = null;
		if(storeId!=null)
		{
			List<Store> sl = this.getHibernateTemplate().find("from Store where id = ?",storeId);
			if(!WebUtil.isNullForList(sl))
				store = sl.get(0);
		}
		Date date = new Date();
		if(store==null)
		{
			store = new Store();
			store.setCtime(date);
			store.setCuser(ul.getUserName());
		}
		else
		{
			store.setMtime(date);
			store.setMuser(ul.getUserName());
		}
		if(WebUtil.isNotNull(param.get("CompanyId")))
			store.setCompanyId(new Integer((String)param.get("CompanyId")));
		if(WebUtil.isNotNull(param.get("Status")))
			store.setStatus((String)param.get("Status"));
		if(WebUtil.isNotNull(param.get("StoreName")))
			store.setStoreName((String)param.get("StoreName"));
		if(WebUtil.isNotNull(param.get("StoreType")))
			store.setStoreType((String)param.get("StoreType"));
		try {
			if(store.getId()==null)
			{
				List<Integer> idl = this.getHibernateTemplate().find("select max(id)+1 from Store");
				if(WebUtil.isNullForList(idl))
					store.setId(1);
				else
					store.setId(idl.get(0));
				this.getHibernateTemplate().save(store);
			}
			else
				this.getHibernateTemplate().update(store);
			result.put("Flag", "success");
		} catch (DataAccessException e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
		}
		return result;
	}

	@Override
	public Map searchStore(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if(WebUtil.isNull(ul))
		{
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		StringBuffer hql = new StringBuffer(" from Store");
		StringBuffer con = new StringBuffer();
		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" CompanyId = "+ul.getCompanyId());
		}
		if(WebUtil.isNotNull(param.get("StoreType")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" StoreType = '"+param.get("StoreType")+"'");
		}
		if(WebUtil.isNotNull(param.get("StoreName")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" StoreName like '%"+param.get("StoreName")+"%'");
		}
		if(WebUtil.isNotNull(param.get("CompanyId")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" CompanyId = "+param.get("CompanyId"));
		}
		
		if(con.length()>0)
			hql.append(" where "+con.toString());
		if(WebUtil.isNotNull(param.get("OrderBy")))
		{
			hql.append(" order by "+param.get("OrderBy"));
			if(WebUtil.isNotNull(param.get("isDesc")))
				hql.append(" desc");				
		}
		List<Long> countList = this.getHibernateTemplate().find("select count(*) "+hql.toString());
		int count = countList.get(0).intValue();
		Session session = this.getSession();
		Query q = session.createQuery(hql.toString());
		if(pageRow>0)
		{
			q.setFirstResult((currentPage-1)*pageRow);
			q.setMaxResults(pageRow);
		}
		List<Company> companyList = this.getHibernateTemplate().find("from Company");
		Map<Integer,String> companyMap = new HashMap();
		if(!WebUtil.isNullForList(companyList))
		{
			for(Company c:companyList)
				companyMap.put(c.getId(), c.getCompanyName());
		}
		List<Store> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if(l!=null&&l.size()>0)
		{
			for(Store o:l)
			{
				Map r = new HashMap();
				r.put("StoreId", o.getId());
				r.put("StoreName", o.getStoreName());
				r.put("StoreType", o.getStoreType());
				r.put("Status", o.getStatus());
				r.put("CompanyId", o.getCompanyId());
				r.put("CompanyName", companyMap.get(o.getCompanyId()));
				rlist.add(r);
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}

	@Override
	public Map storeInfo(Map param) {
		Map result = new HashMap();
		result.put("Flag", "SUCCESS");
		Integer storeId = (Integer) param.get("StoreId");
		if(storeId==null)
		{
			result.put("Flag", "ERROR");
			result.put("Message", "店铺ID没有");
			return result;
		}
		List<Store> storeList = this.getHibernateTemplate().find("from Store where id = ?",storeId);
		if(WebUtil.isNullForList(storeList))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "没找到店铺信息");
			return result;
		}
		Store s = storeList.get(0);
		Map info = new HashMap();
		info.put("StoreId", s.getId());
		info.put("CompanyId", s.getCompanyId());
		info.put("Status", s.getStatus());
		info.put("StoreName", s.getStoreName());
		info.put("StoreType", s.getStoreType());
		result.put("StoreInfo", info);
		return result;
	}

}
