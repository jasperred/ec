package com.sunmw.taobao.action;

import java.util.HashMap;
import java.util.Map;

import com.sunmw.taobao.bean.base.TbBaseServices;

public class TbBaseDownloadAction {
	
	private TbBaseServices tbBaseServices;
	private int catId;
	private int storeId;
	
	public TbBaseServices getTbBaseServices() {
		return tbBaseServices;
	}

	public void setTbBaseServices(TbBaseServices tbBaseServices) {
		this.tbBaseServices = tbBaseServices;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public void tbLogisticsCompany()
	{
		Map param = new HashMap();
		param.put("StoreId", storeId);
		tbBaseServices.downloadTbLogisticsCompany(param);
	}
	
	public void tbCat()
	{
		Map param = new HashMap();
		param.put("StoreId", storeId);
		param.put("CatId", catId);
		tbBaseServices.downloadTbCat(param);
	}

}
