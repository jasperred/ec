package com.sunmw.web.action.warehouse.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.taobao.bean.shop.TaobaoShopServices;
import com.sunmw.web.bean.store.StoreServices;
import com.sunmw.web.bean.warehouse.base.WarehouseBaseServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.Customer;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class WarehouseInfoAction {
	
	private WarehouseBaseServices wmsBaseServices;
	
	private String whType;
	private String whName;
	private String companyId;
	private String status;	
	private String whCode;
	private String reserveInv;
	private String allowNegativeInv;
	private String validTime;
	private String whId;
	
	private boolean success;
	private String message;
	private String crumb;
	
	

	public WarehouseBaseServices getWmsBaseServices() {
		return wmsBaseServices;
	}

	public void setWmsBaseServices(WarehouseBaseServices wmsBaseServices) {
		this.wmsBaseServices = wmsBaseServices;
	}

	public String getWhType() {
		return whType;
	}

	public void setWhType(String whType) {
		this.whType = whType;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getReserveInv() {
		return reserveInv;
	}

	public void setReserveInv(String reserveInv) {
		this.reserveInv = reserveInv;
	}

	public String getAllowNegativeInv() {
		return allowNegativeInv;
	}

	public void setAllowNegativeInv(String allowNegativeInv) {
		this.allowNegativeInv = allowNegativeInv;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
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

	public String saveWarehouse()
	{		
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
		param.put("companyId", ul.getCompanyId());
		if(WebUtil.isNotNull(status))
			param.put("status", status);
		if(WebUtil.isNotNull(whName))
			param.put("whName", whName);
		if(WebUtil.isNotNull(whType))
			param.put("whType", whType);
		if(WebUtil.isNotNull(whCode))
			param.put("whCode", whCode);
		if(WebUtil.isNotNull(reserveInv))
			param.put("reserveInv", new Integer(reserveInv));
		if(WebUtil.isNotNull(allowNegativeInv))
			param.put("allowNegativeInv", allowNegativeInv);
		if(WebUtil.isNotNull(validTime))
			param.put("validTime", new Integer(validTime));
		if(WebUtil.isNotNull(whId))
			param.put("whId", new Integer(whId));
		Map r = this.wmsBaseServices.saveWarehouse(param);
		String flag = (String) r.get("Flag");
		if(flag.equals("success"))
			success = true;
		else
		{
			success = false;
			message = (String) r.get("Message");
		}
		return "success";
	}
	
	public String deleteWarehouse()
	{
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
		if(WebUtil.isNotNull(whId))
			param.put("whId", new Integer(whId));
		Map r = this.wmsBaseServices.deleteWarehouse(param);
		String flag = (String) r.get("Flag");
		if(flag.equals("success"))
		{
			success = true;
		}
		else
		{
			success = false;
			message = (String) r.get("Message");
		}		
		return "success";
	}
	
	public String warehouseInfo()
	{
		try {
			if(WebUtil.isNull(whId))
				return "success";
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map param = new HashMap();
			param.put("UserLogin", ul);
			param.put("whId", new Integer(whId));
			Map r = this.wmsBaseServices.warehouseInfo(param);
			Map m = (Map) r.get("WarehouseInfo");
			if(m!=null)
			{
				whType = (String)m.get("whType");
				whName = (String)m.get("whName");
				companyId = m.get("companyId")==null?"":m.get("companyId").toString();
				status = (String)m.get("status");	
				whCode = (String)m.get("whCode");
				reserveInv = m.get("reserveInv")==null?"":m.get("reserveInv").toString();
				allowNegativeInv = (String)m.get("allowNegativeInv");
				validTime = m.get("validTime")==null?"":m.get("validTime").toString();
			}
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
			return "error";
		}
		success = true;
		return "success";
	}
}
