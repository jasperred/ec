package com.sunmw.web.bean.company.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.company.CompanyServices;
import com.sunmw.web.entity.Company;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class CompanyServicesImpl extends HibernateDaoSupport implements
		CompanyServices {

	public List companyList() {
		List<Company> l = this.getHibernateTemplate().find(
				"from Company where Status = 'NORMAL'");
		return l;
	}

	// 可结算已删除公司
	public List companyListByShop() {
		List<Company> l = this.getHibernateTemplate().find(
				"from Company where Category = 'SHOP'");
		return l;
	}

	public Map searchCompany(Map param, int currentPage, int pageRow) {
		StringBuffer hql = new StringBuffer();
		UserLogin ul = (UserLogin) param.get("UserLogin");
		//String companyIds = getCompanyByUser(ul);
		hql
				.append("from Company c where c.Status = 'NORMAL'");
//		if (companyIds != null)
//			hql.append(" and c.id in (" + companyIds + ")");
		StringBuffer con = new StringBuffer();
		List conList = new ArrayList();
		if (!WebUtil.isNull(param.get("CompanyId"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.CompanyNo = :Condition" + conList.size());
			conList.add(param.get("CompanyId").toString());
		}
		if (!WebUtil.isNull(param.get("CompanyNo"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.CompanyNo = :Condition" + conList.size());
			conList.add(param.get("CompanyNo"));
		}
		if (!WebUtil.isNull(param.get("CompanyName"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.CompanyName like :Condition" + conList.size());
			conList.add("%" + param.get("CompanyName") + "%");
		}
		if (!WebUtil.isNull(param.get("CompanyCategoryNo"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.Category = :Condition" + conList.size());
			conList.add(param.get("CompanyCategoryNo"));
		}
		if (!WebUtil.isNull(param.get("ProvinceNo"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.ProvinceCd like :Condition" + conList.size());
			conList.add(param.get("ProvinceNo"));
		}
		if (!WebUtil.isNull(param.get("RcnNo"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" c.RcnNo = :Condition" + conList.size());
			conList.add(param.get("RcnNo"));
		}
		if (con.length() > 0)
			hql.append(" and " + con.toString());

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
			
			List<Company> l = q.list();
			List<Map> r = new ArrayList();
			for (Company c : l) {
				Map m = new HashMap();
				m.put("companyId", c.getId());
				m.put("companyNo", c.getCompanyNo());
				m.put("companyName", c.getCompanyName());
				m.put("companyCategoryNo", c.getCategory());
				m.put("rcnNo", c.getRcnNo());
				m.put("provinceNo", c.getProvinceCd());
				m.put("companyAddress", c.getAddress());
				m.put("companyCity", c.getCity());
				m.put("companyContact", c.getContact());
				m.put("companyTel", c.getPhone());
				m.put("status", c.getStatus());
				m.put("companyZip", c.getZip());
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

	private String getCompanyByUser(UserLogin ul) {
		List<Company> cl = this.getHibernateTemplate().find(
				"from Company where Status = 'NORMAL' and id = ?",
				ul.getCompanyId());
		if (WebUtil.isNullForList(cl))
			return "0";
		Company c = cl.get(0);
		if (WebUtil.isNull(c.getCategory()))
			return "0";
		if (c.getCategory().equals("SHOP"))
			return c.getId().toString();
		else {
			List<String> areaList = this
					.getHibernateTemplate()
					.find(
							"select sga.id.AreaCd from UserLoginSecurityGroup ulsg,SecurityGroupArea sga where ulsg.id.GroupId = sga.id.GroupId and ulsg.id.UserId = ?",
							ul.getId());
			if (WebUtil.isNullForList(areaList))
				return null;
			StringBuffer areaCds = new StringBuffer();
			for (String area : areaList) {
				if (areaCds.length() > 0)
					areaCds.append(",");
				areaCds.append("'" + area + "'");
			}
			// from Company where ProvinceCd in (select apr.ProvinceCd from
			// UserLoginSecurityGroup ulsg,SecurityGroupArea sga,AreaProvinceRef
			// apr where ulsg.id.GroupId = sga.id.GroupId and sga.id.AreaCd =
			// apr.AreaCd and ulsg.UserId = ?)
			List<Integer> companyList = this
					.getHibernateTemplate()
					.find(
							"select c.id from Company c,AreaProvinceRef apr where c.ProvinceCd = apr.ProvinceCd and apr.AreaCd in ("
									+ areaCds.toString() + ")");
			if (WebUtil.isNullForList(companyList))
				return "0";
			StringBuffer companyIds = new StringBuffer();
			for (Integer companyId : companyList) {
				if (companyIds.length() > 0)
					companyIds.append(",");
				companyIds.append(companyId);
			}
			return companyIds.toString();
		}
	}

	private boolean checkProvince(UserLogin ul, String provinceCd) {
		List<String> areaList = this
				.getHibernateTemplate()
				.find(
						"select sga.id.AreaCd from UserLoginSecurityGroup ulsg,SecurityGroupArea sga where ulsg.id.GroupId = sga.id.GroupId and ulsg.id.UserId = ?",
						ul.getId());
		// 无区域分配认为是有权限
		if (WebUtil.isNullForList(areaList))
			return true;
		StringBuffer areaCds = new StringBuffer();
		for (String area : areaList) {
			if (areaCds.length() > 0)
				areaCds.append(",");
			areaCds.append("'" + area + "'");
		}
		// from Company where ProvinceCd in (select apr.ProvinceCd from
		// UserLoginSecurityGroup ulsg,SecurityGroupArea sga,AreaProvinceRef apr
		// where ulsg.id.GroupId = sga.id.GroupId and sga.id.AreaCd = apr.AreaCd
		// and ulsg.UserId = ?)
		List<Integer> companyList = this.getHibernateTemplate().find(
				" from AreaProvinceRef apr where  apr.AreaCd in ("
						+ areaCds.toString() + ") and apr.ProvinceCd = ?",
				provinceCd);
		// 区域中不包含当前省份,则无权限
		if (WebUtil.isNullForList(companyList))
			return false;
		else
			return true;
	}

	public Map saveCompany(Map param) {
		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("UserLogin");
//		List<Company> cl = this.getHibernateTemplate().find(
//				"from Company where Status = 'NORMAL' and id = ?",
//				ul.getCompanyId());
//		if (WebUtil.isNullForList(cl)) {
//			result.put("Flag", "ERROR");
//			result.put("Message", "用户公司信息不存在");
//			return result;
//		}
//		Company cc = cl.get(0);
//		if (WebUtil.isNull(cc.getCategory())
//				|| !(cc.getCategory().equals("RCN"))) {
//			result.put("Flag", "ERROR");
//			result.put("Message", "无权限查看");
//			return result;
//		}
//		// 省份权限判断
//		if (!checkProvince(ul, (String) param.get("ProvinceCd"))) {
//			result.put("Flag", "ERROR");
//			result.put("Message", "无该省份管理权限");
//			return result;
//		}
		String companyId = (String) param.get("CompanyId");
		Company c = null;
		if (WebUtil.isNotNull(companyId)) {
			List<Company> l = this.getHibernateTemplate().find(
					"from Company where id = ?", new Integer(companyId));
			if (!WebUtil.isNullForList(l)) {
				c = l.get(0);
			}
		}
		Date date = new Date();
		if (c == null) {
			c = new Company();
			c.setCtime(date);
			c.setCuser((String) param.get("UserName"));
		} else {
			c.setMtime(date);
			c.setMuser((String) param.get("UserName"));
		}
		if (WebUtil.isNotNull(param.get("CompanyName")))
			c.setCompanyName((String) param.get("CompanyName"));
		if (WebUtil.isNotNull(param.get("CompanyNo")))
			c.setCompanyNo((String) param.get("CompanyNo"));
		if (WebUtil.isNotNull(param.get("Category")))
			c.setCategory((String) param.get("Category"));
		if (WebUtil.isNotNull(param.get("RcnNo")))
			c.setRcnNo((String) param.get("RcnNo"));
		if (WebUtil.isNotNull(param.get("ProvinceCd")))
			c.setProvinceCd((String) param.get("ProvinceCd"));
		if (WebUtil.isNotNull(param.get("City")))
			c.setCity((String) param.get("City"));
		if (WebUtil.isNotNull(param.get("Address")))
			c.setAddress((String) param.get("Address"));
		if (WebUtil.isNotNull(param.get("Zip")))
			c.setZip((String) param.get("Zip"));
		if (WebUtil.isNotNull(param.get("Phone")))
			c.setPhone((String) param.get("Phone"));
		if (WebUtil.isNotNull(param.get("Contact")))
			c.setContact((String) param.get("Contact"));
		c.setStatus("NORMAL");
		if (c.getId() == null)
			this.getHibernateTemplate().save(c);
		else
			this.getHibernateTemplate().update(c);
		result.put("Flag", "SUCCESS");
		result.put("CompanyId", c.getId());
		return result;
	}

	public Map getCompanyInfo(String companyId) {
		if(!WebUtil.isNumeric(companyId))
		{
			return null;
		}
		List<Company> l = this.getHibernateTemplate().find(
				"from Company where id = ?", new Integer(companyId));
		if (WebUtil.isNullForList(l))
			return null;
		Company c = l.get(0);
		Map result = new HashMap();
		result.put("CompanyId", c.getId().toString());
		result.put("CompanyNo", c.getCompanyNo());
		result.put("CompanyName", c.getCompanyName());
		result.put("Category", c.getCategory());
		result.put("RcnNo", c.getRcnNo());
		result.put("ProvinceCd", c.getProvinceCd());
		result.put("City", c.getCity());
		result.put("Address", c.getAddress());
		result.put("Zip", c.getZip());
		result.put("Phone", c.getPhone());
		result.put("Contact", c.getContact());
		result.put("Status", c.getStatus());
		return result;
	}

	public Map deleteCompany(Map param) {

		Map result = new HashMap();
		UserLogin ul = (UserLogin) param.get("UserLogin");
		String companyId = (String) param.get("CompanyId");
		if (ul.getCompanyId() != null
				&& ul.getCompanyId().equals(new Integer(companyId))) {
			result.put("Flag", "ERROR");
			result.put("Message", "要删除公司是当前用户所在公司,不能删除!");
			return result;
		}
		List<Company> l = this.getHibernateTemplate().find(
				"from Company where id = ?", new Integer(companyId));
		if (WebUtil.isNullForList(l)) {
			result.put("Flag", "ERROR");
			result.put("Message", "公司不存在");
			return result;
		}
		Company c = l.get(0);
		List<UserLogin> uList = this.getHibernateTemplate().find(
				"from UserLogin where Enabled = 'Y' and CompanyId = ?",
				c.getId());
		if (!WebUtil.isNullForList(uList)) {
			result.put("Flag", "ERROR");
			result.put("Message", "该公司下还有有效用户,不能删除!");
			return result;
		}
		c.setStatus("DELETE");
		this.getHibernateTemplate().update(c);
		result.put("Flag", "SUCCESS");
		return result;
	}


}
