package com.sunmw.web.action.base;

import java.util.List;

import com.sunmw.web.bean.base.BaseServices;
import com.sunmw.web.entity.Warehouse;

public class WarehouseAction {
	
	private BaseServices baseServices;
	
	private List<Warehouse> warehouseList;
	
	public BaseServices getBaseServices() {
		return baseServices;
	}

	public void setBaseServices(BaseServices baseServices) {
		this.baseServices = baseServices;
	}

	public List<Warehouse> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<Warehouse> warehouseList) {
		this.warehouseList = warehouseList;
	}

	public void warehouseList()
	{
		this.warehouseList = this.baseServices.warehouseList();
	}

}
