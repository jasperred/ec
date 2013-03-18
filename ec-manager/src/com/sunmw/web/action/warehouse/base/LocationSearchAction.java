package com.sunmw.web.action.warehouse.base;

import com.sunmw.web.bean.warehouse.base.WarehouseBaseServices;

public class LocationSearchAction {
	
	private WarehouseBaseServices wmsBaseServices;
	public WarehouseBaseServices getWmsBaseServices() {
		return wmsBaseServices;
	}
	public void setWmsBaseServices(WarehouseBaseServices wmsBaseServices) {
		this.wmsBaseServices = wmsBaseServices;
	}
	public String searchLocation()
	{
		return "success";
	}

}
