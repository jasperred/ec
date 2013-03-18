package com.sunmw.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.base.BaseServices;
import com.sunmw.web.entity.Store;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class StoreAction {
	
	private BaseServices baseServices;
	
	private List storeList;
	
	
	public BaseServices getBaseServices() {
		return baseServices;
	}


	public void setBaseServices(BaseServices baseServices) {
		this.baseServices = baseServices;
	}


	public List getStoreList() {
		return storeList;
	}


	public void setStoreList(List storeList) {
		this.storeList = storeList;
	}

	public String storeList()
	{
		this.storeList = this.baseServices.storeList();
		return "success";
	}
	
	public String storeListByCompany()
	{
		if(this.storeList==null)
			this.storeList = new ArrayList();
		this.storeList.clear();
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin)session.get("LOGIN_INFO");
		List<Store> l = this.baseServices.storeListByCompany(WebUtil.toMap("LOGIN_INFO", ul));
		if(!WebUtil.isNullForList(l))
		{
			for(Store s:l)
			{
				Map m = new HashMap();
				m.put("name", s.getStoreName());
				m.put("value", s.getId());
				this.storeList.add(m);
			}
		}
		return "success";
	}

}
