package com.sunmw.web.action.order;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;

public class OrderAuditedAndReceive {
	
	private OrderServices orderServices;
	
	private int orderHeadId;
	private String orderHeadIds;
	private String receiveMessage;
	private String message;
	private boolean success;
	private String crumb;
	
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
	public String getOrderHeadIds() {
		return orderHeadIds;
	}
	public void setOrderHeadIds(String orderHeadIds) {
		this.orderHeadIds = orderHeadIds;
	}
	public String getReceiveMessage() {
		return receiveMessage;
	}
	public void setReceiveMessage(String receiveMessage) {
		this.receiveMessage = receiveMessage;
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
	//审核订单
	public String auditedOrder()
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
		message = orderServices.auditedOrder(orderHeadId);
		if(message.equals("success"))
			success = true;
		else
			success = false;
		return "success";
	}
	//批量审核订单
	public String auditedOrders()
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
		message = orderServices.auditedOrders(orderHeadIds);
		if(message.equals("success"))
			success = true;
		else
			success = false;
		return "success";
	}
	//保留订单
	public String receiveOrder()
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
		message = orderServices.receiveOrder(orderHeadId, receiveMessage);
		if(message.equals("success"))
			success = true;
		else
			success = false;
		return "success";
	}
	//批量保留订单
	public String receiveOrders()
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
		message = orderServices.receiveOrders(orderHeadIds, receiveMessage);
		if(message.equals("success"))
			success = true;
		else
			success = false;
		return "success";
	}

}
