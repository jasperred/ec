package com.sunmw.taobao.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.taobao.bean.refunds.TbRefundsServices;
import com.sunmw.web.bean.refund.RefundServices;

public class DownloadTaobaoRefundAction {

	private TbRefundsServices tbRefundServices;
	private RefundServices refundServices;
	private int storeId;
	private int taskId;
	
	public TbRefundsServices getTbRefundServices() {
		return tbRefundServices;
	}

	public void setTbRefundServices(TbRefundsServices tbRefundServices) {
		this.tbRefundServices = tbRefundServices;
	}

	public RefundServices getRefundServices() {
		return refundServices;
	}

	public void setRefundServices(RefundServices refundServices) {
		this.refundServices = refundServices;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void downloadTbRefunds()
	{
		Map params = new HashMap();
		params.put("StoreId", storeId);
		params.put("fields", "refund_id");
		params.put("startModified", new Date(System.currentTimeMillis()-20*24*60*60*1000));
		params.put("endModified", new Date(System.currentTimeMillis()));
		//下载淘宝退货单
		tbRefundServices.taobaoRefundsReceiveGet(params);
		//得到需要更新到ERP的退货单
		List l = tbRefundServices.getNotUpdateTBRefund(storeId);
		//更新ERP退货单
		String refundIds = refundServices.updateRefundFromTb(l);
		//更新淘宝退货单更新标记
		tbRefundServices.updateTbRefundStatusForImportErp(refundIds);
	}
}
