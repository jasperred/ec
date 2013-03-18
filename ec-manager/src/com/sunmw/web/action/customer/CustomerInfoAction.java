package com.sunmw.web.action.customer;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.customer.CustomerServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.Customer;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class CustomerInfoAction {
	
	private CustomerServices customerServices;
	
	private String custId;
	private java.lang.String custNo;
	private java.lang.String custName;
	private java.lang.String mobile;
	private java.lang.String tel;
	private java.lang.String birthDay;
	private java.lang.String email;
	private java.lang.String sex;
	private java.lang.String province;
	private java.lang.String city;
	private java.lang.String district;
	private java.lang.String address;
	private java.lang.String zipcode;
	
	private Customer customerInfo;
	

	private boolean success;
	private String message;
	private String crumb;
	
	

	public CustomerServices getCustomerServices() {
		return customerServices;
	}

	public void setCustomerServices(CustomerServices customerServices) {
		this.customerServices = customerServices;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	public java.lang.String getTel() {
		return tel;
	}

	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}

	public java.lang.String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(java.lang.String birthDay) {
		this.birthDay = birthDay;
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

	public java.lang.String getProvince() {
		return province;
	}

	public void setProvince(java.lang.String province) {
		this.province = province;
	}

	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}

	public java.lang.String getDistrict() {
		return district;
	}

	public void setDistrict(java.lang.String district) {
		this.district = district;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getZipcode() {
		return zipcode;
	}

	public void setZipcode(java.lang.String zipcode) {
		this.zipcode = zipcode;
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

	public Customer getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(Customer customerInfo) {
		this.customerInfo = customerInfo;
	}

	public String companyInfo()
	{
		try {
			if(WebUtil.isNull(custId))
				return "success";
			Map session = ActionContext.getContext().getSession();
			UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
			Map param = new HashMap();
			param.put("UserLogin", ul);
			param.put("custId", new Integer(custId));
			Map r = this.customerServices.customerInfo(param);
			this.customerInfo = (Customer) r.get("customerInfo");
		} catch (Exception e) {
			message = MessageInfo.OperationType;
			success = false;
			return "error";
		}
		success = true;
		return "success";
	}
	
	public String updateCustomer()
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
			if(WebUtil.isNull(custId))
				return "success";
			Map param = new HashMap();
			param.put("UserLogin", ul);
			param.put("custId", new Integer(custId));
			param.put("address", address);
			param.put("birthDay", birthDay);
			param.put("city", city);
			param.put("custName", custName);
			param.put("district", district);
			param.put("email", email);
			param.put("mobile", mobile);
			param.put("province", province);
			param.put("sex", sex);
			param.put("tel", tel);
			param.put("zipcode", zipcode);
			Map r = customerServices.updateCustomer(param);
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

	public String newCustomer()
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
			param.put("custNo", custNo);
			param.put("address", address);
			param.put("birthDay", birthDay);
			param.put("city", city);
			param.put("custName", custName);
			param.put("district", district);
			param.put("email", email);
			param.put("mobile", mobile);
			param.put("province", province);
			param.put("sex", sex);
			param.put("tel", tel);
			param.put("zipcode", zipcode);
			Map r = customerServices.newCustomer(param);
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
	public String deleteCustomer()
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
			if(WebUtil.isNull(custId))
				return "success";
			Map param = new HashMap();
			param.put("UserLogin", ul);
			param.put("custId", new Integer(custId));
			Map r = customerServices.deleteCustomer(param);
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
