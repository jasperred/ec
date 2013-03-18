package com.sunmw.web.action.refund;

import java.util.Map;

import com.sunmw.web.bean.refund.RefundServices;

public class RefundInfoAction {
	
	private RefundServices refundServices;
	
	private int orderHeadId;
	private Map refundInfo;
	
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

	public Map getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(Map refundInfo) {
		this.refundInfo = refundInfo;
	}

	//退货信息
	public String refundInfo()
	{
		this.refundInfo = this.refundServices.getRefundInfo(orderHeadId);
		return "success";
	}

}
