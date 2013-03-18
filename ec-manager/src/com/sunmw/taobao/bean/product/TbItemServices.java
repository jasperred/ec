package com.sunmw.taobao.bean.product;

import java.util.List;
import java.util.Map;

public interface TbItemServices {
	
	/**
	 * 下载店铺所有在售商品
	 * @param storeId
	 * @param params
	 */
//	public Map taobaoItemsGet(Map params);
	
	/**
	 * 查询店铺所有商品
	 * @param storeId
	 * @param params
	 */
//	public Map taobaoItemsSearch(Map params);
	/**
	 * 下载单个商品
	 * @param storeId
	 * @param params
	 */
	public Map taobaoItemGet(Map params);
	
	/**
	 * 查询淘宝仓库中的宝贝
	 * @param storeId
	 * @param params
	 */
	public Map taobaoItemsInventoryGet(Map params);
	
	/**
	 * 得到店铺的全部淘宝商品
	 * @param storeId
	 * @return
	 */
	public Map getTbItemAll(int storeId);
	
	/**
	 * 淘宝SKU更新
	 * @param storeId
	 * @param params
	 */
	public Map taobaoItemSkuUpdate(Map params);
	
	/**
	 * 根据外部ID得到淘宝SKU
	 * @param storeId
	 * @param params
	 * @return
	 */
	public Map taobaoSkusCustomGet(Map params);
	
	/**
	 * 淘宝SKU价格更新
	 * @param storeId
	 * @param skuList
	 */
	public Map taobaoItemSkuPriceUpdate(Map param);
	
	/**
	 * 查询淘宝在售商品
	 * @param params
	 * @return
	 */
	public Map taobaoItemsOnsaleGet(Map params);

}
