package com.sunmw.taobao.bean.shop;

import java.util.List;
import java.util.Map;

public interface TaobaoShopServices {
	
	/**
	 * 淘宝店铺自定义类目
	 * @param storeId
	 * @param params
	 * @return
	 */
	public Map taobaoSellercatsListGet(Map params);
	
	/**
	 * 淘宝区域列表
	 * @return
	 */
	public List tbAreaList();
	
	/**
	 * 删除淘宝店铺
	 * @param param
	 * @return
	 */
	public Map deleteTbStore(Map param);
	
	/**
	 * 保存淘宝店铺
	 * @param param
	 * @return
	 */
	public Map saveTbStore(Map param);
	
	/**
	 * 淘宝店铺信息
	 * @param param
	 * @return
	 */
	public Map tbStoreInfo(Map param);

}
