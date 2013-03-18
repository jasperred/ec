package com.sunmw.web.action.product;

import java.util.Map;

import com.sunmw.taobao.bean.product.TbItemServices;
import com.sunmw.web.bean.product.ProductServices;

public class ProductSynchronizationAction {
	
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

	public void syncProductForTb()
	{
		Map m = tbItemServices.getTbItemAll(storeId);
		productServices.synchronizationTbItemToErp(m);
	}

}
