package com.sunmw.web.action.warehouse.inv;

import com.sunmw.web.bean.warehouse.inv.WarehouseInvServices;

public class InvSearchAction {

	private WarehouseInvServices wmsInvServices;
	public WarehouseInvServices getWmsInvServices() {
		return wmsInvServices;
	}
	public void setWmsInvServices(WarehouseInvServices wmsInvServices) {
		this.wmsInvServices = wmsInvServices;
	}
	public String searchInv()
	{
		return "success";
	}

}
