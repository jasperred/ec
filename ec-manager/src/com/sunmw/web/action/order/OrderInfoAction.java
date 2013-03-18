package com.sunmw.web.action.order;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;
import com.sunmw.web.util.WebUtil;

public class OrderInfoAction {
	
	private OrderServices orderServices;
	
	private String orderHeadId;
	private Map orderInfo;
	
	private String note;
	private String receiverName;
	private String receiverState;
	private String receiverCity;
	private String receiverDistrict;
	private String receiverAddress;
	private String receiverZip;
	private String receiverMobile;
	private String receiverPhone;
	
	private String invoice;
	private String invoiceMessage;
	private boolean success;
	private String message;
	private String crumb;
	
	public OrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(OrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public String getOrderHeadId() {
		return orderHeadId;
	}

	public void setOrderHeadId(String orderHeadId) {
		this.orderHeadId = orderHeadId;
	}

	public Map getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(Map orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverZip() {
		return receiverZip;
	}

	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
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

	public String getInvoiceMessage() {
		return invoiceMessage;
	}

	public void setInvoiceMessage(String invoiceMessage) {
		this.invoiceMessage = invoiceMessage;
	}

	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	//订单信息
	public String orderInfo()
	{
		if(WebUtil.isNotNull(orderHeadId))
		orderInfo = orderServices.getOrderInfo(new Integer(orderHeadId));
		return "success";
	}

	//客服留言保存
	public String custNoteSave()
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
		this.message = this.orderServices.saveOrderNote(new Integer(orderHeadId), "CUST_MEMO", note);
		if(message==null||!message.equals("success"))
			success = false;
		else
			success = true;
		return "success";
	}
	
	//收货人信息修改
	public String updateReceiverInfo()
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
		Map<String,String> param = new HashMap();
		param.put("ReceiverName", this.receiverName);
		param.put("ReceiverState", this.receiverState);
		param.put("ReceiverCity", this.receiverCity);
		param.put("ReceiverDistrict", this.receiverDistrict);
		param.put("ReceiverAddress", this.receiverAddress);
		param.put("ReceiverZip", this.receiverZip);
		param.put("ReceiverMobile", this.receiverMobile);
		param.put("ReceiverPhone", this.receiverPhone);
		this.message = this.orderServices.updateReveiverAddress(new Integer(orderHeadId), param);
		if(message==null||!message.equals("success"))
			success = false;
		else
			success = true;
		return "success";
	}
	
	//订单取消
	public String cancelOrder()
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
		this.message = this.orderServices.cancelOrder(new Integer(orderHeadId));
		if(message==null||!message.equals("success"))
			success = false;
		else
			success = true;
		return "success";
	}
	
	//订单恢复
	public String recoverOrder()
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
		this.message = this.orderServices.recoverOrder(new Integer(orderHeadId));
		if(message==null||!message.equals("success"))
			success = false;
		else
			success = true;
		return "success";
	}
	
	//保存发票信息
	public String saveInvoice()
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
		this.message = this.orderServices.saveInvoice(new Integer(orderHeadId), invoice, invoiceMessage);
		if(message==null||!message.equals("success"))
			success = false;
		else
			success = true;
		return "success";
	}
}
