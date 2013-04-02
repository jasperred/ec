package com.sunmw.web.action.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.address.AddressServices;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class OrderNewAction {
	private OrderServices orderServices;
	private AddressServices addressServices;
	
	private String shopId;
	private String shopName;
	private String receiverAddress;
	private String receiverCity;
	private String receiverDistrict;
	private String receiverMobile;
	private String receiverName;
	private String receiverState;
	private String receiverPhone;
	private String receiverZip;
	private String buyerEmail;
	
	private String custId;
	private String custNo;
	private String totalFee;
	private String obtainPoint;
	private String payment;
	private String postFee;
	private String discountFee;
	private String buyerMemo;
	

	private String orderItems;
	
	private String message;
	private boolean success;
	private String crumb;
	
	public OrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(OrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public AddressServices getAddressServices() {
		return addressServices;
	}

	public void setAddressServices(AddressServices addressServices) {
		this.addressServices = addressServices;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public String getReceiverDistrict() {
		return receiverDistrict;
	}

	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverState() {
		return receiverState;
	}

	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverZip() {
		return receiverZip;
	}

	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getObtainPoint() {
		return obtainPoint;
	}

	public void setObtainPoint(String obtainPoint) {
		this.obtainPoint = obtainPoint;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getPostFee() {
		return postFee;
	}

	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public String getBuyerMemo() {
		return buyerMemo;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	public String getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(String orderItems) {
		this.orderItems = orderItems;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	public String newOrder()
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
		//地址保存
//		Map address = new HashMap();
//		address.put("AddressName", "寄送地址");
//		address.put("Consignee", receiverName);
//		address.put("Email", buyerEmail);
//		address.put("Country", "");
//		address.put("Province", receiverState);
//		address.put("City", receiverCity);
//		address.put("District", receiverDistrict);
//		address.put("Address", receiverAddress);
//		address.put("Zipcode", receiverZip);
//		address.put("Tel", receiverPhone);
//		address.put("Mobile", receiverMobile);
//		address.put("LOGIN_INFO", ul);
//		addressServices.saveAddress(address);
		//订单保存
		param.put("CompanyId", ul.getCompanyId());
		param.put("StoreId", new Integer(this.shopId));
		param.put("SellerNick", this.shopName);
		param.put("PayTime", new Date());
		param.put("ReceiverAddress", this.receiverAddress);
		param.put("ReceiverCity", this.receiverCity);
		param.put("ReceiverDistrict", this.receiverDistrict);
		param.put("ReceiverMobile", this.receiverMobile);
		param.put("ReceiverName", this.receiverName);
		param.put("ReceiverState", this.receiverState);
		param.put("ReceiverPhone", this.receiverPhone);
		param.put("ReceiverZip", this.receiverZip);
		param.put("BuyerEmail", this.buyerEmail);
		if(WebUtil.isNotNull(custId))
			param.put("CustId", new Integer(custId));
		param.put("BuyerNick", this.custNo);
		if(WebUtil.isNotNull(obtainPoint))
			param.put("ObtainPoint", new Integer(obtainPoint));
		if(WebUtil.isNotNull(this.totalFee))
		param.put("TotalFee", new BigDecimal(this.totalFee));
		if(WebUtil.isNotNull(this.payment))
		param.put("Payment", new BigDecimal(this.payment));
		if(WebUtil.isNotNull(this.postFee))
		param.put("PostFee", new BigDecimal(this.postFee));
		if(WebUtil.isNotNull(this.discountFee))
		param.put("DiscountFee", new BigDecimal(this.discountFee));
		param.put("PointFee", 0);
		param.put("BuyerMessage", this.buyerMemo);
		//明细内容,使用JSON格式
		List orderItem = new ArrayList();
		if(WebUtil.isNotNull(orderItems))
		{
			JSONArray jsonArray = JSONArray.fromObject(orderItems);
			for(int i=0;i<jsonArray.size();i++)
			{
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i)); 
				Map m = new HashMap();
				m.put("ItemCd", jsonObject.get("itemCd"));
				m.put("Title", jsonObject.get("name"));
				if(WebUtil.isNotNull(jsonObject.get("qty")))
				m.put("Num", new BigDecimal(jsonObject.get("qty").toString()));
				if(WebUtil.isNotNull(jsonObject.get("price")))
					m.put("Price", new BigDecimal(jsonObject.get("price").toString()));
				if(WebUtil.isNotNull(jsonObject.get("discount")))
					m.put("DiscountFee", new BigDecimal(jsonObject.get("discount").toString()));
				if(WebUtil.isNotNull(jsonObject.get("skuCd")))
				m.put("SkuCd", jsonObject.get("skuCd").toString());
				if(WebUtil.isNotNull(jsonObject.get("skuProp1")))
				m.put("SkuProp1", jsonObject.get("skuProp1").toString());
				if(WebUtil.isNotNull(jsonObject.get("skuProp2")))
				m.put("SkuProp2", jsonObject.get("skuProp2").toString());
				if(WebUtil.isNotNull(jsonObject.get("subAmt")))
					m.put("TotalFee", new BigDecimal(jsonObject.get("subAmt").toString()));
				orderItem.add(m);
			}				
		}
		param.put("OrderItem", orderItem);
		Map result = orderServices.newOrder(param);
		if("SUCCESS".equals(result.get("Flag")))
		{
			message = null;
			success = true;
		}
		else
		{
			message = "保存失败";
			success = false;
		}
		return "success";
	}

}
