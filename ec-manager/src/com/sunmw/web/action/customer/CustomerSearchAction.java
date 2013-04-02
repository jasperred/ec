package com.sunmw.web.action.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.customer.CustomerServices;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class CustomerSearchAction {

	@Resource
	private CustomerServices customerServices;

	private java.lang.String custNo;
	private java.lang.String custName;
	private java.lang.String mobile;
	private java.lang.String birthDayFrom;
	private java.lang.String birthDayEnd;
	private java.lang.String email;
	private java.lang.String sex;
	private java.lang.String q;
	
	private int page = 1;// 当前页
	private int rowCount;// 总行数
	private int limit = 25;// 每页显示数
	private List<Map> resultList;// 查询结果
	private String isNext = "TRUE";// 是否有下一页
	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数	

	private String exportUrl;// 导出文件的链接地址
	private String message;
	

	public CustomerServices getCustomerServices() {
		return customerServices;
	}

	public void setCustomerServices(CustomerServices customerServices) {
		this.customerServices = customerServices;
	}

	public java.lang.String getQ() {
		return q;
	}

	public void setQ(java.lang.String q) {
		this.q = q;
	}

	public java.lang.String getCustNo() {
		return custNo;
	}

	public void setCustNo(java.lang.String custNo) {
		this.custNo = custNo;
	}

	public java.lang.String getCustName() {
		return custName;
	}

	public void setCustName(java.lang.String custName) {
		this.custName = custName;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getBirthDayFrom() {
		return birthDayFrom;
	}

	public void setBirthDayFrom(java.lang.String birthDayFrom) {
		this.birthDayFrom = birthDayFrom;
	}

	public java.lang.String getBirthDayEnd() {
		return birthDayEnd;
	}

	public void setBirthDayEnd(java.lang.String birthDayEnd) {
		this.birthDayEnd = birthDayEnd;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getSex() {
		return sex;
	}

	public void setSex(java.lang.String sex) {
		this.sex = sex;
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

	public String getExportUrl() {
		return exportUrl;
	}

	public void setExportUrl(String exportUrl) {
		this.exportUrl = exportUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String searchCustomer() {
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map param = new HashMap();
			param.put("UserLogin", ul);
			param.put("custNo", custNo);
			param.put("birthDayFrom", birthDayFrom);
			param.put("birthDayEnd", birthDayEnd);
			param.put("custName", custName);
			param.put("email", email);
			param.put("mobile", mobile);
			param.put("sex", sex);
			Map r = this.customerServices.searchCustomer(param, page,
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
	
	public String searchCustomerByQ() {
		try {
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map param = new HashMap();
			param.put("UserLogin", ul);
			param.put("q", q);
			Map r = this.customerServices.searchCustomerByQ(param, page,
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
	// 导出客户
	public String exportCustomer() {
		this.exportUrl = null;
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		Map param = new HashMap();
		param.put("UserLogin", ul);
		param.put("custNo", custNo);
		param.put("birthDayFrom", birthDayFrom);
		param.put("birthDayEnd", birthDayEnd);
		param.put("custName", custName);
		param.put("email", email);
		param.put("mobile", mobile);
		param.put("sex", sex);
		Map r = this.customerServices.searchCustomer(param, page,
				limit);
		List l = (List) r.get("RESULT");
		String[] headers = new String[] { "客户编号", "客户名称", "邮件", "手机", "性别",
				"生日", "省", "市", "区", "地址","邮编" };
		String[] fields = new String[] {"custNo", "custName", "email",
				"mobile", "sex", "birthDay","province","city","district","address","zipcode"};
		String fileName = "customer_export_"+WebUtil.formatDateString(new Date(), "yyyyMMddHHmmss")+".csv";
		String path = WebConfigProperties.getProperties("report.customer.path");
		if(WebUtil.isNotNull(ul.getCompanyId()))
		{
			path = path + ul.getCompanyId()+"/";
		}
		boolean b = WebUtil.exportCSV(headers, fields, l, path, fileName);
		if (b) {
			String url = WebConfigProperties.getProperties("report.customer.url");

			if(WebUtil.isNotNull(ul.getCompanyId()))
			{
				url = url + ul.getCompanyId()+"/";
			}
			this.exportUrl = url + fileName;
			message = "success";
		}
		message = "error";
		return "success";
	}

}
