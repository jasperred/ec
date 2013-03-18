package com.sunmw.web.action.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.company.CompanyServices;
import com.sunmw.web.entity.Company;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class CompanySearchAction {

	private CompanyServices companyServices;

	private String companyId;
	private String companyNo;
	private String companyName;
	private String companyCategoryNo;
	private String provinceNo;
	private String rcnNo;

	private List companyList;

	private int page = 1;// 当前页
	private int rowCount;// 总行数
	private int limit = 25;// 每页显示数
	private List<Map> resultList;// 查询结果
	private String isNext = "TRUE";// 是否有下一页
	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数	

	public CompanyServices getCompanyServices() {
		return companyServices;
	}

	public void setCompanyServices(CompanyServices companyServices) {
		this.companyServices = companyServices;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCategoryNo() {
		return companyCategoryNo;
	}

	public void setCompanyCategoryNo(String companyCategoryNo) {
		this.companyCategoryNo = companyCategoryNo;
	}

	public String getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	public String getRcnNo() {
		return rcnNo;
	}

	public void setRcnNo(String rcnNo) {
		this.rcnNo = rcnNo;
	}

	public List getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List companyList) {
		this.companyList = companyList;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<Map> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map> resultList) {
		this.resultList = resultList;
	}

	public String getIsNext() {
		return isNext;
	}

	public void setIsNext(String isNext) {
		this.isNext = isNext;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String searchCompany() {
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map param = new HashMap();
			param.put("UserLogin", ul);
			// param.put("CompanyId", companyId);
			param.put("CompanyNo", WebUtil.filterSpecialCharacters(companyNo));
			param.put("CompanyName", WebUtil
					.filterSpecialCharacters(companyName));
			param.put("CompanyCategoryNo", WebUtil
					.filterSpecialCharacters(companyCategoryNo));
			param
					.put("ProvinceNo", WebUtil
							.filterSpecialCharacters(provinceNo));
			param.put("RcnNo", WebUtil.filterSpecialCharacters(rcnNo));
			Map r = this.companyServices.searchCompany(param, page,
					limit);
			this.rowCount = (Integer) r.get("COUNT_ROW");
			this.resultList = (List) r.get("RESULT");
			countPage();
		} catch (Exception e) {
			if (this.resultList != null)
				this.resultList.clear();
			this.rowCount = 0;
		}
		return "success";
	}

	private void countPage() {
		if (rowCount % this.limit == 0)
			pageCount = rowCount / this.limit;
		else
			pageCount = rowCount / this.limit + 1;// 总页数
		if (page < pageCount)
			isNext = "true";
		else
			isNext = "false";
		if (page > 1)
			isBack = "true";
		else
			isBack = "false";
	}

	public String companyList() {
		try {
			this.companyList = companyServices.companyList();
		} catch (Exception e) {
			if (this.companyList != null)
				this.companyList.clear();
		}
		return "success";
	}

	public void companyListByShop() {
		try {
			this.companyList = companyServices.companyListByShop();
		} catch (Exception e) {
			this.companyList = null;
		}
	}
}
