package com.sunmw.web.bean.customer;

import java.util.Map;

public interface CustomerServices {
	
	/**
	 * 新增客户
	 * @param param
	 * @return
	 */
	public Map newCustomer(Map param);
	/**
	 * 修改客户
	 * @param param
	 * @return
	 */
	public Map updateCustomer(Map param);
	/**
	 * 删除客户
	 * @param param
	 * @return
	 */
	public Map deleteCustomer(Map param);
	/**
	 * 得到客户信息
	 * @param param
	 * @return
	 */
	public Map customerInfo(Map param);

	/**
	 * 查询客户
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchCustomer(Map param, int currentPage, int pageRow);
	/**
	 * 查询客户
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchCustomerByQ(Map param, int currentPage, int pageRow);

}
