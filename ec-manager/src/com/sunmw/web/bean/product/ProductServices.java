package com.sunmw.web.bean.product;

import java.util.List;
import java.util.Map;

public interface ProductServices {
	
	/**
	 * 通过淘宝商品同步ERP商品
	 * @param itemList
	 */
	public void synchronizationTbItemToErp(Map item);
	
	/**
	 * 价格更新的SKU列表
	 * @param storeId
	 * @return
	 */
	public List<Map> skuPriceUpdateList(int storeId);
	
	/**
	 * SKU查询
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchSku(Map param, int currentPage, int pageRow);
	
	/**
	 * 更新商品的价格,包括Item和Sku
	 * @param param
	 * @return
	 */
	public Map importItemPrice(Map param);
	
	/**
	 * 更新价格更新标记
	 * @param param
	 * @return
	 */
	public Map updateSkuPriceFlagOfShop(Map param);
	
	/**
	 * 保存商品信息到UnitItemMaster和UnitSkuMaster表
	 * @param param
	 * @return
	 */
	public Map saveUnitItemAndSku(Map param);

}
