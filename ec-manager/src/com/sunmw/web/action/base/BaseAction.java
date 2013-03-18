package com.sunmw.web.action.base;

import java.util.List;

import com.sunmw.web.bean.base.BaseServices;

public class BaseAction {
	
	private BaseServices baseServices;
	
	private String unitType;
	private List provinceList;
	private List unitList;
	
	
	public BaseServices getBaseServices() {
		return baseServices;
	}

	public void setBaseServices(BaseServices baseServices) {
		this.baseServices = baseServices;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public List getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List provinceList) {
		this.provinceList = provinceList;
	}

	public List getUnitList() {
		return unitList;
	}

	public void setUnitList(List unitList) {
		this.unitList = unitList;
	}

	public String provinceList()
	{
		this.provinceList = this.baseServices.provinceList();
		return "success";
	}
	
	public String unitList()
	{
		this.unitList = this.baseServices.unitList(unitType);
		return "success";
	}

}
