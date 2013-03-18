package com.sunmw.web.action.warehouse.out;

import com.sunmw.web.bean.warehouse.out.WarehouseOutServices;

public class ShipmentSearchAction {

	private WarehouseOutServices wmsOutServices;
	public WarehouseOutServices getWmsOutServices() {
		return wmsOutServices;
	}
	public void setWmsOutServices(WarehouseOutServices wmsOutServices) {
		this.wmsOutServices = wmsOutServices;
	}
	public String searchShipmentOrder()
	{
		return "success";
	}

}
