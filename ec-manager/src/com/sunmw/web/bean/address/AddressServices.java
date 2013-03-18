package com.sunmw.web.bean.address;

import java.util.Map;

public interface AddressServices {
	
	/**
	 * 保存地址信息
	 * @param param
	 * @return
	 */
	public Map saveAddress(Map param);
	
	/**
	 * 查询地址信息
	 * @param param
	 * @return
	 */
	public Map searchAddress(Map param);

}
