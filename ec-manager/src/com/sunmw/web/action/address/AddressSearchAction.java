package com.sunmw.web.action.address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.web.bean.address.AddressServices;

public class AddressSearchAction {
	
	private AddressServices addressServices;
	
	private String consignee;
	private String email;
	private String tel;
	private String mobile;
	
	private List addressList;
	
	public AddressServices getAddressServices() {
		return addressServices;
	}

	public void setAddressServices(AddressServices addressServices) {
		this.addressServices = addressServices;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List getAddressList() {
		return addressList;
	}

	public void setAddressList(List addressList) {
		this.addressList = addressList;
	}

	public String searchAddress()
	{
		Map param = new HashMap();
		param.put("Consignee", consignee);
		param.put("Email", email);
		param.put("Tel", tel);
		param.put("Mobile", mobile);
		Map r = addressServices.searchAddress(param);
		addressList = (List) r.get("UserAddressList");
		return "success";
	}

}
