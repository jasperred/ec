package com.sunmw.web.action.warehouse.check;

import com.sunmw.web.bean.warehouse.check.WarehouseCheckServices;

public class CheckSearchAction {
	
	private WarehouseCheckServices wmsCheckServices;
	
	public WarehouseCheckServices getWmsCheckServices() {
		return wmsCheckServices;
	}

	public void setWmsCheckServices(WarehouseCheckServices wmsCheckServices) {
		this.wmsCheckServices = wmsCheckServices;
	}

	public String searchCheckOrder()
	{
		return "success";
	}

}
