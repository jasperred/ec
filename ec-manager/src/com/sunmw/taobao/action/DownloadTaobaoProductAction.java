package com.sunmw.taobao.action;

import java.util.HashMap;

import com.sunmw.taobao.bean.product.TbItemServices;
import com.sunmw.taobao.bean.product.TbProductServices;
import com.sunmw.web.util.WebUtil;

public class DownloadTaobaoProductAction {
	
	private TbProductServices tbProductServices;
	private TbItemServices tbItemServices;
	
	private int storeId;
	
	public TbProductServices getTbProductServices() {
		return tbProductServices;
	}

	public void setTbProductServices(TbProductServices tbProductServices) {
		this.tbProductServices = tbProductServices;
	}

	public TbItemServices getTbItemServices() {
		return tbItemServices;
	}

	public void setTbItemServices(TbItemServices tbItemServices) {
		this.tbItemServices = tbItemServices;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public void downloadProduct()
	{
		tbProductServices.taobaoProductsGet( new HashMap());
	}
	
	public void downloadItem()
	{
		//先下载仓库中的宝贝
		String banner = "regular_shelved,never_on_shelf,sold_out,off_shelf,for_shelved,violation_off_shelf,for_shelved";
		for(String b:banner.split(","))
			tbItemServices.taobaoItemsInventoryGet( WebUtil.toMap("banner", b));
		//下载销售中的宝贝
		tbItemServices.taobaoItemsOnsaleGet( new HashMap());
		//tbItemServices.taobaoItemsSearch(storeId, new HashMap());
	}

}
