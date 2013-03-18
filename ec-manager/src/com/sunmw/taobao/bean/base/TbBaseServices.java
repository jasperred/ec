package com.sunmw.taobao.bean.base;

import java.util.Map;

public interface TbBaseServices {
	
	/**
	 * 下载淘宝物流公司
	 * @param storeId
	 */
	public Map downloadTbLogisticsCompany(Map params);
	
	/**
	 * 下载淘宝分类
	 * @param storeId
	 */
	public Map downloadTbCat(Map params);

}
