package com.sunmw.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.base.BaseServices;
import com.sunmw.web.entity.Store;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.entity.Warehouse;
import com.sunmw.web.util.WebUtil;

public class WarehouseAction {

	private BaseServices baseServices;

	private List<Map> warehouseList;

	public BaseServices getBaseServices() {
		return baseServices;
	}

	public void setBaseServices(BaseServices baseServices) {
		this.baseServices = baseServices;
	}

	public List<Map> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<Map> warehouseList) {
		this.warehouseList = warehouseList;
	}

	public String warehouseListByCompany() {
		if(this.warehouseList==null)
			this.warehouseList = new ArrayList();
		this.warehouseList.clear();
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		List<Warehouse> l = this.baseServices.warehouseList(WebUtil.toMap(
				"LOGIN_INFO", ul));
		if(!WebUtil.isNullForList(l))
		{
			for(Warehouse w:l)
			{
				Map m = new HashMap();
				m.put("name", w.getWhName());
				m.put("value", ""+w.getId());
				this.warehouseList.add(m);
			}
		}
		return "success";
	}
}
