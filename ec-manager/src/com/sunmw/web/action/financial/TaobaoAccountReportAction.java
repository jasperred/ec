package com.sunmw.web.action.financial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.financial.AlipayFinancialServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class TaobaoAccountReportAction {

	private AlipayFinancialServices alipayFinancialServices;

	private String storeId;
	private String month;
	private String alipayFromDate;
	private String alipayEndDate;
	private boolean success;
	private String message;
	private String crumb;


	public AlipayFinancialServices getAlipayFinancialServices() {
		return alipayFinancialServices;
	}

	public void setAlipayFinancialServices(
			AlipayFinancialServices alipayFinancialServices) {
		this.alipayFinancialServices = alipayFinancialServices;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getAlipayFromDate() {
		return alipayFromDate;
	}

	public void setAlipayFromDate(String alipayFromDate) {
		this.alipayFromDate = alipayFromDate;
	}

	public String getAlipayEndDate() {
		return alipayEndDate;
	}

	public void setAlipayEndDate(String alipayEndDate) {
		this.alipayEndDate = alipayEndDate;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	// 生成淘宝对账报表
	public String genericTaobaoAccountReport() {
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		String userNo = ul.getUserNo();		
		if(crumb==null||!VerifyRequest.verifyCrumb(userNo, crumb))
		{
			message = MessageInfo.Verify;
			success = false;
			return "success";
		}
		Map param = new HashMap();
		param.put("LOGIN_INFO", ul);
		param.put("CompanyId", ul.getCompanyId());
		if (WebUtil.isNotNull(storeId))
			param.put("StoreId", new Integer(storeId));
		if (WebUtil.isNotNull(month))
			param.put("Month", month);
		if (WebUtil.isNotNull(alipayFromDate))
			param.put("AlipayFromDate", WebUtil.toDateForString(alipayFromDate, "yyyy-MM-dd"));
		if (WebUtil.isNotNull(alipayEndDate))
			param.put("AlipayEndDate", WebUtil.toDateForString(alipayEndDate, "yyyy-MM-dd"));
		
		Map r = null;
		try {
			r = this.alipayFinancialServices.taobaoAccountReport(param);
		} catch (Exception e) {
			message = e.getMessage();
			success = false;
			return "success";
		}
		if(r.get("Flag").equals("SUCCESS"))
		{
			message = "";
			success = true;
		}
		else
		{
			message = (String) r.get("Message");
			success = false;
		}
		return "success";
	}
}
