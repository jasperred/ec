package com.sunmw.taobao.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.taobao.bean.product.TbItemServices;
import com.sunmw.web.bean.product.ProductServices;
import com.sunmw.web.util.WebUtil;

public class UpdateTaobaoPriceAction {
	
	private ProductServices productServices;
	private TbItemServices tbItemServices;
	
	private int storeId;
	
	public ProductServices getProductServices() {
		return productServices;
	}

	public void setProductServices(ProductServices productServices) {
		this.productServices = productServices;
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

	public void taobaoSkuPriceUpdate()
	{
		List<Map> skuList = productServices.skuPriceUpdateList(storeId);
		if(WebUtil.isNullForList(skuList))
			return;
		tbItemServices.taobaoItemSkuPriceUpdate(WebUtil.toMap("StoreId", storeId,"SkuPriceList",skuList));
	}

}
