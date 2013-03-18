package com.sunmw.taobao.bean.product;

import java.util.Map;

public interface TbProductServices {
	
	/**
	 * 下载店铺所有产品
	 * @param storeId
	 * @param params
	 */
	public Map taobaoProductsGet(Map params);
	
	/**
	 * 下载单个产品
	 * @param storeId
	 * @param params
	 */
	public Map taobaoProductGet(Map params);

}
