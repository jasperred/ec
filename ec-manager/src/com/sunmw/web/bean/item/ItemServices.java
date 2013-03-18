package com.sunmw.web.bean.item;

import java.util.Map;

public interface ItemServices {
	
	/**
	 * 按Barcode查询Sku
	 * @param param
	 * @return
	 */
	public Map searchSkuByBarcode(Map param);
	
	/**
	 * 分页查询Sku
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchSku(Map param, int currentPage, int pageRow);

}
