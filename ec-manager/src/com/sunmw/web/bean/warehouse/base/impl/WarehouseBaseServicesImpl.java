package com.sunmw.web.bean.warehouse.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.warehouse.base.WarehouseBaseServices;
import com.sunmw.web.entity.Company;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.entity.Warehouse;
import com.sunmw.web.util.WebUtil;

public class WarehouseBaseServicesImpl extends HibernateDaoSupport implements
		WarehouseBaseServices {

	@Override
	public Map deleteWarehouse(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if(WebUtil.isNull(ul))
		{
			result.put("Flag", "error");
			result.put("Message", "无操作用户信息");
			return result;
		}
		Integer whId = (Integer)param.get("whId");
		List<Warehouse> sl = null;
		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
			sl = this.getHibernateTemplate().find("from Warehouse where id = ? and CompanyId = ?",new Object[]{whId,ul.getCompanyId()});
		else
			sl = this.getHibernateTemplate().find("from Warehouse where id = ?",whId);
		if(!WebUtil.isNullForList(sl))
		{
			Warehouse w = sl.get(0);
			w.setStatus("INACTIVE");
			this.getHibernateTemplate().update(w);
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
	public Map saveWarehouse(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if(WebUtil.isNull(ul))
		{
			result.put("Flag", "error");
			result.put("Message", "无操作用户信息");
			return result;
		}
		Integer whId = (Integer)param.get("whId");
		Warehouse wh = null;
		if(whId!=null)
		{
			List<Warehouse> sl = this.getHibernateTemplate().find("from Warehouse where id = ?",whId);
			if(!WebUtil.isNullForList(sl))
				wh = sl.get(0);
		}
		Date date = new Date();
		if(wh==null)
		{
			wh = new Warehouse();
			wh.setCtime(date);
			wh.setCuser(ul.getUserName());
		}
		else
		{
			wh.setMtime(date);
			wh.setMuser(ul.getUserName());
		}
		if(WebUtil.isNotNull(param.get("companyId")))
			wh.setCompanyId((Integer)param.get("companyId"));
		if(WebUtil.isNotNull(param.get("status")))
			wh.setStatus((String)param.get("status"));
		if(WebUtil.isNotNull(param.get("whName")))
			wh.setWhName((String)param.get("whName"));
		if(WebUtil.isNotNull(param.get("whType")))
			wh.setWhType((String)param.get("whType"));
		if(WebUtil.isNotNull(param.get("whCode")))
			wh.setWhCode((String)param.get("whCode"));
		if(WebUtil.isNotNull(param.get("reserveInv")))
			wh.setReserveInv((Integer)(param.get("reserveInv")));
		if(WebUtil.isNotNull(param.get("allowNegativeInv")))
			wh.setAllowNegativeInv((String)param.get("allowNegativeInv"));
		if(WebUtil.isNotNull(param.get("validTime")))
			wh.setValidTime((Integer)param.get("validTime"));
		try {
			if(wh.getId()==null)
			{
				this.getHibernateTemplate().save(wh);
			}
			else
				this.getHibernateTemplate().update(wh);
			result.put("Flag", "success");
		} catch (DataAccessException e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
		}
		return result;
	}

	@Override
	public Map searchWarehouse(Map param, int currentPage, int pageRow) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if(WebUtil.isNull(ul))
		{
			result.put("RESULT", null);
			result.put("COUNT_ROW", 0);
			return result;
		}
		StringBuffer hql = new StringBuffer(" from Warehouse w where w.Status = 'ACTIVE' ");
		StringBuffer con = new StringBuffer();
		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" w.CompanyId = "+ul.getCompanyId());
		}
		if(WebUtil.isNotNull(param.get("whCode")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" w.WhCode = '"+param.get("whCode")+"'");
		}
		if(WebUtil.isNotNull(param.get("whName")))
		{
			if(con.length()>0)
				con.append(" and ");
			con.append(" w.WhName like '%"+param.get("whName")+"%'");
		}
		
		if(con.length()>0)
			hql.append(" and "+con.toString());
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
		List<Warehouse> l = q.list();
		List<Map> rlist = new ArrayList<Map>();
		if(l!=null&&l.size()>0)
		{
			for(Warehouse w:l)
			{
				Map r = new HashMap();
				r.put("whId", w.getId());
				r.put("whName", w.getWhName());
				r.put("whType", w.getWhType());
				r.put("whCode", w.getWhCode());
				r.put("allowNegativeInv", w.getAllowNegativeInv());
				r.put("reserveInv", w.getReserveInv());
				r.put("validTime", w.getValidTime());
				r.put("status", w.getStatus());
				r.put("companyId", w.getCompanyId());
				r.put("companyName", companyMap.get(w.getCompanyId()));
				rlist.add(r);
			}
		}
		result.put("RESULT", rlist);
		result.put("COUNT_ROW", count);
		session.close();
		return result;
	}

	@Override
	public Map warehouseInfo(Map param) {
		Map result = new HashMap();
		result.put("Flag", "SUCCESS");
		Integer whId = (Integer) param.get("whId");
		if(whId==null)
		{
			result.put("Flag", "ERROR");
			result.put("Message", "店铺ID没有");
			return result;
		}
		List<Warehouse> whList = this.getHibernateTemplate().find("from Warehouse where id = ?",whId);
		if(WebUtil.isNullForList(whList))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "没找到店铺信息");
			return result;
		}
		Warehouse o = whList.get(0);
		Map r = new HashMap();
		r.put("whId", o.getId());
		r.put("whName", o.getWhName());
		r.put("whType", o.getWhType());
		r.put("whCode", o.getWhCode());
		r.put("allowNegativeInv", o.getAllowNegativeInv());
		r.put("reserveInv", o.getReserveInv());
		r.put("validTime", o.getValidTime());
		r.put("status", o.getStatus());
		r.put("companyId", o.getCompanyId());
		result.put("WarehouseInfo", r);
		return result;
	}

}
