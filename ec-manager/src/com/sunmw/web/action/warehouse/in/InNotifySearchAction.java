package com.sunmw.web.action.warehouse.in;

import com.sunmw.web.bean.warehouse.in.WarehouseInServices;

public class InNotifySearchAction {
	
	private WarehouseInServices wmsInServices;
	public WarehouseInServices getWmsInServices() {
		return wmsInServices;
	}
	public void setWmsInServices(WarehouseInServices wmsInServices) {
		this.wmsInServices = wmsInServices;
	}
	public String searchInNotify()
	{
		return "success";
	}

}
