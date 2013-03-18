package com.sunmw.web.bean.dataInterface;

import java.util.Map;

public interface ErpInterfaceServices {
	
	/**
	 * 日结报表
	 * @param param
	 * @return
	 */
	public Map dailySaleAndRefundReport(Map param);

	/**
	 * 重新生成日结报表
	 * @param param
	 * @return
	 */
	public Map refreshDailySaleAndRefundReport(Map param);
	
	/**
	 * 导入商品价格，价格导入到ItemMaster、SkuMaster、UnitItemMaster、UnitSkuMaster中
	 * @param param
	 * @return
	 */
	public Map importItemPrice(Map param);
	

	/**
	 * 从淘宝同步商品
	 * @param param
	 * @return
	 */
	public Map syncItemFromTb(Map param);

}
