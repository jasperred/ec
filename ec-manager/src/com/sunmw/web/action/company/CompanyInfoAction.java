package com.sunmw.web.action.company;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.company.CompanyServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class CompanyInfoAction {
	
	private CompanyServices companyServices;
	
	private String companyId;
	private String companyNo;
	private String companyName;
	private String companyCategoryNo;
	private String provinceNo;
	private String rcnNo;
	private String companyCity;
	private String companyAddress;
	private String companyZip;
	private String companyTel;
	private String companyContact;
	

	private boolean success;
	private String message;
	private String crumb;
	
	
	public CompanyServices getCompanyServices() {
		return companyServices;
	}

	public void setCompanyServices(CompanyServices companyServices) {
		this.companyServices = companyServices;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
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

	public String getCompanyCity() {
		return companyCity;
	}

	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyZip() {
		return companyZip;
	}

	public void setCompanyZip(String companyZip) {
		this.companyZip = companyZip;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
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

	public String companyInfo()
	{
		try {
			if(WebUtil.isNull(companyId))
			{
				this.companyId = " ";
				this.companyNo = null;
				this.companyName = null;
				this.companyCategoryNo = null;
				this.provinceNo = null;
				this.rcnNo = null;
				this.companyCity = null;
				this.companyAddress = null;
				this.companyZip = null;
				this.companyTel = null;
				this.companyContact = null;
				return "success";
			}
			Map<String,String> m = companyServices.getCompanyInfo(companyId);
			
			if(m==null)
				return "error";
			this.companyId = WebUtil.replaceSpecialCharacters(m.get("CompanyId"));
			this.companyNo = WebUtil.replaceSpecialCharacters(m.get("CompanyNo"));
			this.companyName = WebUtil.replaceSpecialCharacters(m.get("CompanyName"));
			this.companyCategoryNo = WebUtil.replaceSpecialCharacters(m.get("Category"));
			this.provinceNo = WebUtil.replaceSpecialCharacters(m.get("ProvinceCd"));
			this.rcnNo = WebUtil.replaceSpecialCharacters(m.get("RcnNo"));
			this.companyCity = WebUtil.replaceSpecialCharacters(m.get("City"));
			this.companyAddress = WebUtil.replaceSpecialCharacters(m.get("Address"));
			this.companyZip = WebUtil.replaceSpecialCharacters(m.get("Zip"));
			this.companyTel = WebUtil.replaceSpecialCharacters(m.get("Phone"));
			this.companyContact = WebUtil.replaceSpecialCharacters(m.get("Contact"));
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
			return "error";
		}
		success = true;
		return "success";
	}
	
	public String saveCompany()
	{
		try {
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
			param.put("UserLogin", ul);
			param.put("CompanyId", WebUtil.filterSpecialCharacters(companyId));
			param.put("CompanyNo", WebUtil.filterSpecialCharacters(companyNo));
			param.put("CompanyName", WebUtil.filterSpecialCharacters(companyName));
			param.put("Category", WebUtil.filterSpecialCharacters(companyCategoryNo));
			param.put("RcnNo", WebUtil.filterSpecialCharacters(rcnNo));
			param.put("ProvinceCd", WebUtil.filterSpecialCharacters(provinceNo));
			param.put("City", WebUtil.filterSpecialCharacters(companyCity));
			param.put("Address", WebUtil.filterSpecialCharacters(companyAddress));
			param.put("Zip", WebUtil.filterSpecialCharacters(companyZip));
			param.put("Phone", WebUtil.filterSpecialCharacters(companyTel));
			param.put("Contact", WebUtil.filterSpecialCharacters(companyContact));
			param.put("UserName", ul.getUserName());
			Map r = companyServices.saveCompany(param);
			String flag = (String) r.get("Flag");
			if(flag.equals("SUCCESS"))
			{
				this.message =  "";
				success = true;
			}
			else
			{
				this.message = (String)r.get("Message");
				success = false;
			}
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
		}
		return "success";
	}
	
	public String deleteCompany()
	{
		try {
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
			param.put("UserLogin", ul);
			param.put("CompanyId", WebUtil.filterSpecialCharacters(companyId));
			Map r = companyServices.deleteCompany(param);
			if(r.get("Flag").equals("SUCCESS"))
			{
				this.message = null;
				success = true;
			}
			else
			{
				this.message = (String)r.get("Message");
				success = false;
			}
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
		}
		return "success";
	}

}
