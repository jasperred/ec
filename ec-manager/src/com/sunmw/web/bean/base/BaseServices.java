package com.sunmw.web.bean.base;

import java.util.List;
import java.util.Map;

public interface BaseServices {
	/**
	 * 店铺列表
	 * @return
	 */
	public List storeList();
	/**
	 * 店铺列表
	 * 按公司
	 * @return
	 */
	public List storeListByCompany(Map param);
	
	/**
	 * 状态列表
	 * @param type
	 * @return
	 */
	public List statusList(String type);
	
	/**
	 * 仓库列表
	 * @return
	 */
	public List warehouseList(Map param);
	
	/**
	 * 省份列表
	 * @return
	 */
	public List provinceList();
	
	/**
	 * Unit列表
	 * @param unitType
	 * @return
	 */
	public List unitList(String unitType);

}
