package com.sunmw.web.action.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.taobao.bean.shop.TaobaoShopServices;
import com.sunmw.taobao.entity.TbStore;
import com.sunmw.web.bean.store.StoreServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class StoreInfoAction {
	
	private StoreServices storeServices;
	
	private String storeId;
	private String storeType;
	private String storeName;
	private String companyId;
	private String status;
	
	private String address;
	private String appKey;
	private String appSercet;
	private String areaId;
	private String memo;
	private String mobile;
	private String phone;
	private String sandboxUrl;
	private String sessionKey;
	private String sessionUrl;
	private String storeNick;
	private String zip;
	private String useNotify;
	
	private List tbAreaList;
	
	private boolean success;
	private String message;
	private String crumb;
	
	
	public StoreServices getStoreServices() {
		return storeServices;
	}

	public void setStoreServices(StoreServices storeServices) {
		this.storeServices = storeServices;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSercet() {
		return appSercet;
	}

	public void setAppSercet(String appSercet) {
		this.appSercet = appSercet;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSandboxUrl() {
		return sandboxUrl;
	}

	public void setSandboxUrl(String sandboxUrl) {
		this.sandboxUrl = sandboxUrl;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getSessionUrl() {
		return sessionUrl;
	}

	public void setSessionUrl(String sessionUrl) {
		this.sessionUrl = sessionUrl;
	}

	public String getStoreNick() {
		return storeNick;
	}

	public void setStoreNick(String storeNick) {
		this.storeNick = storeNick;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getUseNotify() {
		return useNotify;
	}

	public void setUseNotify(String useNotify) {
		this.useNotify = useNotify;
	}

	public List getTbAreaList() {
		return tbAreaList;
	}

	public void setTbAreaList(List tbAreaList) {
		this.tbAreaList = tbAreaList;
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

	public String saveStore()
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
		if(WebUtil.isNotNull(storeId))
			param.put("StoreId", new Integer(storeId));
		if(WebUtil.isNotNull(storeType))
			param.put("StoreType", storeType);
		if(WebUtil.isNotNull(storeName))
			param.put("StoreName", storeName);
		if(WebUtil.isNotNull(companyId))
			param.put("CompanyId", companyId);
		if(WebUtil.isNotNull(status))
			param.put("Status", status);
		Map r = this.storeServices.saveStore(param);
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
	
	public String deleteStore()
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
		if(WebUtil.isNotNull(storeId))
			param.put("StoreId", new Integer(storeId));
		Map r = this.storeServices.deleteStore(param);
		String flag = (String) r.get("Flag");
		if(flag.equals("success"))
		{
			TaobaoShopServices tss = (TaobaoShopServices) GetBeanServlet.getBean("tbShopServices");
			r = tss.deleteTbStore(param);
//			flag = (String) r.get("Flag");
//			if(flag.equals("success"))
//				success = true;
//			else
//			{
//				success = false;
//				message = (String) r.get("Message");
//			}
			success = true;
		}
		else
		{
			success = false;
			message = (String) r.get("Message");
		}		
		return "success";
	}
	
	public String saveTbStore()
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
		if(WebUtil.isNotNull(storeId))
			param.put("StoreId", new Integer(storeId));
		if(WebUtil.isNotNull(address))
			param.put("Address", address);
		if(WebUtil.isNotNull(appKey))
			param.put("AppKey", appKey);
		if(WebUtil.isNotNull(appSercet))
			param.put("AppSercet", appSercet);
		if(WebUtil.isNotNull(areaId))
			param.put("AreaId", new Long(areaId));
		if(WebUtil.isNotNull(memo))
			param.put("Memo", memo);
		if(WebUtil.isNotNull(mobile))
			param.put("Mobile", mobile);
		if(WebUtil.isNotNull(phone))
			param.put("Phone", phone);
		if(WebUtil.isNotNull(sandboxUrl))
			param.put("SandboxUrl", sandboxUrl);
		if(WebUtil.isNotNull(sessionKey))
			param.put("SessionKey", sessionKey);
		if(WebUtil.isNotNull(sessionUrl))
			param.put("SessionUrl", sessionUrl);
		if(WebUtil.isNotNull(storeNick))
			param.put("StoreNick", storeNick);
		if(WebUtil.isNotNull(zip))
			param.put("Zip", zip);
		if(WebUtil.isNotNull(useNotify))
			param.put("UseNotify", useNotify);
		if(WebUtil.isNotNull(storeType))
			param.put("StoreType", storeType);
		if(WebUtil.isNotNull(status))
			param.put("Status", status);
		TaobaoShopServices tss = (TaobaoShopServices) GetBeanServlet.getBean("tbShopServices");
		Map r = tss.saveTbStore(param);
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
	public String tbStoreInfo()
	{
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");	
		String userNo = ul.getUserNo();		
//		if(crumb==null||!VerifyRequest.verifyCrumb(userNo, crumb))
//		{
//			message = MessageInfo.Verify;
//			success = false;
//			return "success";
//		}
		Map param = new HashMap();
		param.put("LOGIN_INFO", ul);
		if(WebUtil.isNotNull(storeId))
			param.put("StoreId", new Integer(storeId));
		TaobaoShopServices tss = (TaobaoShopServices) GetBeanServlet.getBean("tbShopServices");
		Map r = tss.tbStoreInfo(param);
		String flag = (String) r.get("Flag");
		if(flag.equals("success"))
		{
			TbStore ts = (TbStore) r.get("StoreInfo");
			if(ts!=null)
			{
				storeType = ts.getStoreType();
				status = ts.getStatus();
				address = ts.getAddress();
				appKey = ts.getAppKey();
				appSercet = ts.getAppSercet();
				if(ts.getAreaId()!=null)
				areaId = ts.getAreaId().toString();
				memo = ts.getMemo();
				mobile = ts.getMobile();
				phone = ts.getPhone();
				sandboxUrl = ts.getSandboxUrl();
				sessionKey = ts.getSessionKey();
				sessionUrl = ts.getSessionUrl();
				storeNick = ts.getStoreNick();
				zip = ts.getZip();
				useNotify = ts.getUseNotify();
			}
			else
			{
				status = null;
				address = null;
				appKey = null;
				appSercet = null;
				areaId = null;
				memo = null;
				mobile = null;
				phone = null;
				sandboxUrl = null;
				sessionKey = null;
				sessionUrl = null;
				storeNick = null;
				zip = null;
				useNotify = null;
			}
			success = true;
		}
		else
		{
			success = false;
			message = (String) r.get("Message");
		}
		return "success";
	}

	public String tbAreaList()
	{
		TaobaoShopServices tss = (TaobaoShopServices) GetBeanServlet.getBean("tbShopServices");
		this.tbAreaList = tss.tbAreaList();
		return "success";
	}
}
