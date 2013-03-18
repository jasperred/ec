package com.sunmw.paipai.bean.Item;

import java.util.Map;

public interface PaipaiItemServices {
	
	/**
	 * 根据卖家指定的条件查询商品信息列表
	 * @param param
	 * @return
	 */
	public Map sellerSearchItemList(Map param);
	
	

}
