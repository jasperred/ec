package com.sunmw.web.bean.company;

import java.util.List;
import java.util.Map;

public interface CompanyServices {
	
	/**
	 * 公司查询
	 * @param param
	 * @param currentPage
	 * @param pageRow
	 * @return
	 */
	public Map searchCompany(Map param, int currentPage, int pageRow);
	
	/**
	 * 新增和修改公司信息
	 * @param param
	 * @return
	 */
	public Map saveCompany(Map param);
	
	/**
	 * 获得公司信息
	 * @param companyId
	 * @return
	 */
	public Map getCompanyInfo(String companyId);
	
	/**
	 * 删除公司
	 * @param companyId
	 * @return
	 */
	public Map deleteCompany(Map param);
	
	/**
	 * 公司列表
	 * @return
	 */
	public List companyList();
	
	/**
	 * 特约店列表
	 * @return
	 */
	public List companyListByShop();

}
