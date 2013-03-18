package com.sunmw.web.action.refund;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.bean.refund.RefundServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class RefundProcessAction {
	private RefundServices refundServices;
	private OrderServices orderServices;
	private int orderHeadId;
	private int orderItemId;
	private String refundNo;
	private String goodStatus;
	private String refundStatus;
	private String hasGoodReturn;
	private String refOrderNo;
	private int storeId;
	private double totalFee;
	private String refundDate;
	private double paymentAmt;
	private double refundAmt;
	private String refundReason;
	private String refundDesc;
	private String custMemo;
	private String alipayNo;
	private String buyerNick;
	private String orderItemsStr;
	private List orderItemList;
	private Map orderInfo;
	
	private String message;
	private boolean success;
	private String crumb;
	
	
	public RefundServices getRefundServices() {
		return refundServices;
	}

	public void setRefundServices(RefundServices refundServices) {
		this.refundServices = refundServices;
	}

	public OrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(OrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public int getOrderHeadId() {
		return orderHeadId;
	}

	public void setOrderHeadId(int orderHeadId) {
		this.orderHeadId = orderHeadId;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

	public String getHasGoodReturn() {
		return hasGoodReturn;
	}

	public void setHasGoodReturn(String hasGoodReturn) {
		this.hasGoodReturn = hasGoodReturn;
	}

	public String getRefOrderNo() {
		return refOrderNo;
	}

	public void setRefOrderNo(String refOrderNo) {
		this.refOrderNo = refOrderNo;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public double getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public double getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(double refundAmt) {
		this.refundAmt = refundAmt;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

	public String getCustMemo() {
		return custMemo;
	}

	public void setCustMemo(String custMemo) {
		this.custMemo = custMemo;
	}

	public String getAlipayNo() {
		return alipayNo;
	}

	public void setAlipayNo(String alipayNo) {
		this.alipayNo = alipayNo;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getOrderItemsStr() {
		return orderItemsStr;
	}

	public void setOrderItemsStr(String orderItemsStr) {
		this.orderItemsStr = orderItemsStr;
	}

	public List getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List orderItemList) {
		this.orderItemList = orderItemList;
	}

	public Map getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(Map orderInfo) {
		this.orderInfo = orderInfo;
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

	public String saveRefund()
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
		param.put("OrderHeadId", orderHeadId);
		param.put("OrigOrderNo", refundNo);
		param.put("RefundStatus", refundStatus);
		param.put("GoodStatus", goodStatus);
		param.put("HasGoodReturn", hasGoodReturn);
		param.put("RefOrderNo", refOrderNo);
		param.put("WhId", storeId);
		param.put("TotalFee", totalFee);
		param.put("Created", WebUtil.toDateForString(refundDate.replaceAll("T", " "), "yyyy-MM-dd HH:mm:ss".substring(0,refundDate.length())));
		param.put("PaymentAmt", paymentAmt);
		param.put("RefundAmt", refundAmt);
		param.put("RefundReason", refundReason);
		param.put("RefundDesc", refundDesc);
		param.put("CustMemo", custMemo);
		param.put("AlipayNo", alipayNo);
		param.put("BuyerNick", buyerNick);
		List s = orderItemList;
		//明细内容,使用JSON格式
		List orderItem = new ArrayList();
		if(WebUtil.isNotNull(orderItemsStr))
		{
			JSONArray jsonArray = JSONArray.fromObject(orderItemsStr);
			for(int i=0;i<jsonArray.size();i++)
			{
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i)); 
				Map m = new HashMap();
				m.put("OrderItemId", jsonObject.get("id"));
				m.put("ItemName", jsonObject.get("itemName"));
				m.put("Qty", jsonObject.get("qty"));
				m.put("OrigOrderItemNo", jsonObject.get("origOrderItemNo"));
				m.put("OrigSkuId", jsonObject.get("origSkuId"));
				m.put("Price", jsonObject.get("price"));
				m.put("ItemCd", jsonObject.get("itemCd"));
				m.put("SkuCd", jsonObject.get("skuCd"));
				m.put("SkuProp1", jsonObject.get("skuProp1"));
				m.put("SkuProp2", jsonObject.get("skuProp2"));
				m.put("SkuProp3", jsonObject.get("skuProp3"));
				m.put("OrderItemStatus", jsonObject.get("orderItemStatus"));
				orderItem.add(m);
			}				
		}
		param.put("OrderItem", orderItem);
		Map m = refundServices.saveRefund(param);
		if(m.get("Flag").equals("success"))
		{
			success = true;
		}
		else
		{
			success = false;
			message = (String) m.get("Message");
		}
		return "success";
	}
	
	public String closeRefund()
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
		message = refundServices.cancelRefund(orderHeadId);
		if(message.equals("success"))
			success = true;
		else
			success = false;
		return "success";
	}
	
	public String recoveryRefund()
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
		message = refundServices.recoveryRefund(orderHeadId);
		if(message.equals("success"))
			success = true;
		else
			success = false;
		return "success";
	}
	
	public String getTbOrderInfo()
	{
		orderInfo = this.orderServices.getOrderInfoByTbOrderNo(refOrderNo);
		return "success";
	}
	
}
