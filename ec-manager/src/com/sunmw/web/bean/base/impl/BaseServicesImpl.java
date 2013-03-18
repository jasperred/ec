package com.sunmw.web.bean.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.web.bean.base.BaseServices;
import com.sunmw.web.entity.Province;
import com.sunmw.web.entity.StatusItem;
import com.sunmw.web.entity.Store;
import com.sunmw.web.entity.Unit;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.entity.Warehouse;
import com.sunmw.web.util.WebUtil;

public class BaseServicesImpl extends HibernateDaoSupport implements BaseServices {

	public List statusList(String type) {
		List<StatusItem> siList = this.getHibernateTemplate().find("from StatusItem where StatusTypeId = '"+type+"' order by Sequence");
		return siList;
	}

	public List storeList() {
		List<Store> storeList = this.getHibernateTemplate().find("from Store where Status = 'ACTIVE'");
		return storeList;
	}
	
	public List storeListByCompany(Map param)
	{
		UserLogin ul = (UserLogin) param.get("LOGIN_INFO");
		if(WebUtil.isNull(ul))
			return null;
		List<Store> storeList = null;

		//分公司权限判断
		if(WebUtil.isNull(ul.getUserType())||!ul.getUserType().equals("SYSTEM"))
			storeList = this.getHibernateTemplate().find("from Store where Status = 'ACTIVE' and CompanyId = ?",ul.getCompanyId());
		else
			storeList = this.getHibernateTemplate().find("from Store where Status = 'ACTIVE'");			
		return storeList;
	}
	
	public List warehouseList()
	{
		List<Warehouse> whList = this.getHibernateTemplate().find("from Warehouse where Status = 'ACTIVE'");
		return whList;
	}

	@Override
	public List provinceList() {
		List<Province> provinceList = this.getHibernateTemplate().find("from Province order by id");
		return provinceList;
	}

	@Override
	public List unitList(String unitType) {
		List<Unit> unitList = this.getHibernateTemplate().find("from Unit where UnitType = ? order by DispIndex",unitType);
		return unitList;
	}

}
