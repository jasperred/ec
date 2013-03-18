package com.sunmw.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.web.bean.base.BaseServices;
import com.sunmw.web.entity.StatusItem;
import com.sunmw.web.entity.Store;
import com.sunmw.web.util.WebUtil;

public class StatusAction {

	private BaseServices baseServices;

	private List<StatusItem> tbStatusList;
	private List<StatusItem> erpStatusList;
	private List<StatusItem> refundStatusList;
	private List<StatusItem> goodStatusList;
	private String statusType;
	private List statusList;
	private List<StatusItem> receiveStatusList;

	public BaseServices getBaseServices() {
		return baseServices;
	}

	public void setBaseServices(BaseServices baseServices) {
		this.baseServices = baseServices;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public List<StatusItem> getTbStatusList() {
		return tbStatusList;
	}

	public void setTbStatusList(List<StatusItem> tbStatusList) {
		this.tbStatusList = tbStatusList;
	}

	public List<StatusItem> getErpStatusList() {
		return erpStatusList;
	}

	public void setErpStatusList(List<StatusItem> erpStatusList) {
		this.erpStatusList = erpStatusList;
	}

	public List<StatusItem> getReceiveStatusList() {
		return receiveStatusList;
	}

	public void setReceiveStatusList(List<StatusItem> receiveStatusList) {
		this.receiveStatusList = receiveStatusList;
	}

	public List<StatusItem> getRefundStatusList() {
		return refundStatusList;
	}

	public void setRefundStatusList(List<StatusItem> refundStatusList) {
		this.refundStatusList = refundStatusList;
	}

	public List<StatusItem> getGoodStatusList() {
		return goodStatusList;
	}

	public void setGoodStatusList(List<StatusItem> goodStatusList) {
		this.goodStatusList = goodStatusList;
	}

	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public void orderStatusList() {
		this.erpStatusList = this.baseServices.statusList("ORDER");
		this.tbStatusList = this.baseServices.statusList("TAOBAO_ORDER");
		this.receiveStatusList = this.baseServices.statusList("TAOBAO_RECEIVE");
	}

	public void refundStatusList() {
		this.erpStatusList = this.baseServices.statusList("REFUND");
		this.tbStatusList = this.baseServices.statusList("TAOBAO_ORDER");
		this.refundStatusList = this.baseServices.statusList("TAOBAO_RETURN");
		this.goodStatusList = this.baseServices.statusList("TAOBAO_GOOD");
	}

	public String statusListByType() {
		if (this.statusList == null)
			this.statusList = new ArrayList();
		this.statusList.clear();
		List<StatusItem> l = this.baseServices.statusList(statusType);
		if (!WebUtil.isNullForList(l)) {
			for (StatusItem s : l) {
				Map m = new HashMap();
				m.put("name", s.getDescription());
				m.put("value", s.getStatusCode());
				this.statusList.add(m);
			}
		}
		return "success";
	}

}
