package com.sunmw.web.action.refund;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.refund.RefundServices;
import com.sunmw.web.common.message.MessageInfo;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.VerifyRequest;

public class RefundAuditedAndReceive {
	
	private RefundServices refundServices;
	
	private int orderHeadId;
	private String orderHeadIds;
	private String receiveMessage;
	private boolean success;
	private String message;
	private String crumb;
	
	
	public RefundServices getRefundServices() {
		return refundServices;
	}
	public void setRefundServices(RefundServices refundServices) {
		this.refundServices = refundServices;
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
	//审核退货
	public String auditedRefund()
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
		message = refundServices.auditedRefund(orderHeadId);
		if(message.equals("success"))
			success = true;
		else
			success = false;
		return "success";
	}
	//批量审核退货
	public String auditedRefunds()
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
		message = refundServices.auditedRefunds(orderHeadIds);
		if(message.equals("success"))
			success = true;
		else
			success = false;
		return "success";
	}
	//保留退货
	public String receiveRefund()
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
		message = refundServices.receiveRefund(orderHeadId, receiveMessage);
		if(message.equals("success"))
			success = true;
		else
			success = false;
		return "success";
	}
	//批量保留退货
	public String receiveRefunds()
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
		message = refundServices.receiveRefunds(orderHeadIds, receiveMessage);
		if(message.equals("success"))
			success = true;
		else
			success = false;
		return "success";
	}

}
