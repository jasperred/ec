package com.sunmw.web.bean.customer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.customer.CustomerServices;
import com.sunmw.web.entity.Customer;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class CustomerServicesImpl extends HibernateDaoSupport implements CustomerServices {

	@Override
	public Map deleteCustomer(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("UserLogin");
		Integer custId = (Integer)param.get("custId");
		Customer c = new Customer();
		c.setId(custId);
		// 分公司权限判断
		if (WebUtil.isNull(ul.getUserType())
				|| !ul.getUserType().equals("SYSTEM")) {

			c.setCompanyId(ul.getCompanyId());
		}
		this.getHibernateTemplate().delete("Customer",c);
		result.put("Flag", "SUCCESS");
		return result;
	}

	@Override
	public Map newCustomer(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("UserLogin");
		String customerNo = (String) param.get("custNo");
		if(WebUtil.isNotNull(customerNo))
		{
			List l = this.getHibernateTemplate().find("from Customer where CustNo = ? and CompanyId = ?", new Object[]{customerNo,ul.getCompanyId()});
			if(l!=null&&l.size()>0)
			{
				result.put("Flag", "ERROR");
				result.put("Message", "客户号重复");
				return result;
			}
		}
		else
		{
			String pre = "A0"+WebUtil.formatDateString(new Date(), "yyMMdd");
			List<Long> l = this.getHibernateTemplate().find("select count(*) from Customer where CustNo like '"+pre+"%'");
			customerNo = pre+WebUtil.addPrefix(""+(l.get(0)+1), 4, "0");
		}
		Customer c = new Customer();
		c.setCustNo(customerNo);
		// 分公司权限判断
		if (WebUtil.isNull(ul.getUserType())
				|| !ul.getUserType().equals("SYSTEM")) {

			c.setCompanyId(ul.getCompanyId());
		}
		else
		{
			if(WebUtil.isNotNull(param.get("companyId")))
				c.setCompanyId((Integer)param.get("companyId"));
			else
				c.setCompanyId(ul.getCompanyId());
		}
		
		c.setAddress((String)param.get("address"));
		c.setBirthDay((String)param.get("birthDay"));
		c.setCity((String)param.get("city"));
		c.setCtime(new Date());
		if(ul!=null)
		c.setCuser(ul.getUserName());
		c.setCustName((String)param.get("custName"));
		c.setDistrict((String)param.get("district"));
		c.setEmail((String)param.get("email"));
		c.setMobile((String)param.get("mobile"));
		c.setProvince((String)param.get("province"));
		c.setSex((String)param.get("sex"));
		c.setTel((String)param.get("tel"));
		c.setZipcode((String)param.get("zipcode"));
		this.getHibernateTemplate().save(c);
		result.put("Flag", "SUCCESS");
		return result;
	}

	@Override
	public Map searchCustomer(Map param, int currentPage, int pageRow) {
		StringBuffer hql = new StringBuffer();
		UserLogin ul = (UserLogin) param.get("UserLogin");
		hql
				.append("from Customer c");
		StringBuffer con = new StringBuffer();
		// 分公司权限判断
		if (WebUtil.isNull(ul.getUserType())
				|| !ul.getUserType().equals("SYSTEM")) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.CompanyId = " + ul.getCompanyId());
		}
		List conList = new ArrayList();
		if (!WebUtil.isNull(param.get("custNo"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.CustNo = :Condition" + conList.size());
			conList.add(param.get("custNo").toString());
		}
		if (!WebUtil.isNull(param.get("mobile"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.Mobile = :Condition" + conList.size());
			conList.add(param.get("mobile"));
		}
		if (!WebUtil.isNull(param.get("custName"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.CustName like :Condition" + conList.size());
			conList.add("%" + param.get("custName") + "%");
		}
		if (!WebUtil.isNull(param.get("email"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.Email = :Condition" + conList.size());
			conList.add(param.get("email"));
		}
		if (!WebUtil.isNull(param.get("sex"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.Sex = :Condition" + conList.size());
			conList.add(param.get("sex"));
		}
		if (WebUtil.isNotNull(param.get("birthDayFrom"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.BirthDay >= '" + param.get("birthDayFrom")
					+ "'");
		}
		if (WebUtil.isNotNull(param.get("birthDayEnd"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.BirthDay < '" + param.get("birthDayEnd") + "'");
		}
		if (con.length() > 0)
			hql.append(" where " + con.toString());
		hql.append(" order by c.Ctime desc");

		Session session = this.getSession();
		Map result = new HashMap();
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
			
			List<Customer> l = q.list();
			result.put("RESULT", l);
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
	public Map updateCustomer(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("UserLogin");
		Integer custId = (Integer)param.get("custId");
		List<Customer> l = null;
		// 分公司权限判断
		if (WebUtil.isNull(ul.getUserType())
				|| !ul.getUserType().equals("SYSTEM")) {

			l = this.getHibernateTemplate().find("from Customer where id = ? and CompanyId = ?", new Object[]{custId,ul.getCompanyId()});
		}
		else
		{
			l = this.getHibernateTemplate().find("from Customer where id = ? ", custId);
		}
		
		if(WebUtil.isNullForList(l))
		{
			result.put("Flag", "ERROR");
			result.put("Message", "客户不存在");
			return result;
		}
		Customer c = l.get(0);
		c.setAddress((String)param.get("address"));
		c.setBirthDay((String)param.get("birthDay"));
		c.setCity((String)param.get("city"));
		c.setMtime(new Date());
		if(ul!=null)
		c.setMuser(ul.getUserName());
		c.setCustName((String)param.get("custName"));
		c.setDistrict((String)param.get("district"));
		c.setEmail((String)param.get("email"));
		c.setMobile((String)param.get("mobile"));
		c.setProvince((String)param.get("province"));
		c.setSex((String)param.get("sex"));
		c.setTel((String)param.get("tel"));
		c.setZipcode((String)param.get("zipcode"));
		this.getHibernateTemplate().update(c);
		result.put("Flag", "SUCCESS");
		return result;
	}

	@Override
	public Map customerInfo(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("UserLogin");
		Integer custId = (Integer)param.get("custId");
		List<Customer> l = null;
		// 分公司权限判断
		if (WebUtil.isNull(ul.getUserType())
				|| !ul.getUserType().equals("SYSTEM")) {

			l = this.getHibernateTemplate().find("from Customer where id = ? and CompanyId = ?", new Object[]{custId,ul.getCompanyId()});
		}
		else
		{
			l = this.getHibernateTemplate().find("from Customer where id = ? ", custId);
		}
		result.put("customerInfo", l.get(0));
		return result;
	}

}
