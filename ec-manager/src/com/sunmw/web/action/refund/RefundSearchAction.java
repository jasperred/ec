package com.sunmw.web.action.refund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.refund.RefundServices;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class RefundSearchAction {
	
	private RefundServices refundServices;
	private int storeId;
	private String refundPropsValue;
	private String refundPropsName;
	private String tbStatus;
	private String erpStatus;
	private String refundStatus;
	private String goodStatus;
	private String hasGoodReturn;
	private String refundFromDate;
	private String refundEndDate;
	private String completeFromDate;
	private String completeEndDate;
	
	private int page = 1;// 当前页
	private int rowCount;// 总行数
	private int limit = 15;// 每页显示数

	private List<Map> resultList;// 查询结果

	private String isNext = "TRUE";// 是否有下一页

	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数
	
	private String exportUrl;// 导出文件的链接地址
	private String message;
	
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

	public String getRefundPropsValue() {
		return refundPropsValue;
	}

	public void setRefundPropsValue(String refundPropsValue) {
		this.refundPropsValue = refundPropsValue;
	}

	public String getRefundPropsName() {
		return refundPropsName;
	}

	public void setRefundPropsName(String refundPropsName) {
		this.refundPropsName = refundPropsName;
	}

	public String getTbStatus() {
		return tbStatus;
	}

	public void setTbStatus(String tbStatus) {
		this.tbStatus = tbStatus;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
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

	public String getRefundFromDate() {
		return refundFromDate;
	}

	public void setRefundFromDate(String refundFromDate) {
		this.refundFromDate = refundFromDate;
	}

	public String getRefundEndDate() {
		return refundEndDate;
	}

	public void setRefundEndDate(String refundEndDate) {
		this.refundEndDate = refundEndDate;
	}

	public String getCompleteFromDate() {
		return completeFromDate;
	}

	public void setCompleteFromDate(String completeFromDate) {
		this.completeFromDate = completeFromDate;
	}

	public String getCompleteEndDate() {
		return completeEndDate;
	}

	public void setCompleteEndDate(String completeEndDate) {
		this.completeEndDate = completeEndDate;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<Map> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map> resultList) {
		this.resultList = resultList;
	}

	public String getIsNext() {
		return isNext;
	}

	public void setIsNext(String isNext) {
		this.isNext = isNext;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getExportUrl() {
		return exportUrl;
	}

	public void setExportUrl(String exportUrl) {
		this.exportUrl = exportUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	//订单查询
	public String refundSearch()
	{
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin)session.get("LOGIN_INFO");
		Map param = new HashMap();
		param.put("LOGIN_INFO", ul);
		if(storeId>0)
		param.put("storeId", storeId);
		if(WebUtil.isNotNull(refundPropsName)&&WebUtil.isNotNull(refundPropsValue))
		{
			param.put(refundPropsName, refundPropsValue);
		}
		if(WebUtil.isNotNull(tbStatus))
			param.put("tbStatus", tbStatus);
		if(WebUtil.isNotNull(erpStatus))
			param.put("erpStatus", erpStatus);
		if(WebUtil.isNotNull(refundStatus))
			param.put("refundStatus", refundStatus);
		if(WebUtil.isNotNull(goodStatus))
			param.put("goodStatus", goodStatus);
		if(WebUtil.isNotNull(hasGoodReturn))
			param.put("hasGoodReturn", hasGoodReturn);
		if(WebUtil.isNotNull(refundFromDate))
			param.put("refundFromDate", refundFromDate);
		if(WebUtil.isNotNull(refundEndDate))
			param.put("refundEndDate", refundEndDate);
		if(WebUtil.isNotNull(completeFromDate))
			param.put("completeFromDate", completeFromDate);
		if(WebUtil.isNotNull(completeEndDate))
			param.put("completeEndDate", completeEndDate);
		Map r = this.refundServices.searchRefund(param, page, limit);
		this.rowCount = (Integer) r.get("COUNT_ROW");
		this.resultList = (List) r.get("RESULT");
		countPage();
		return "success";
	}
	
	private void countPage() {
		if (rowCount % this.limit == 0)
			pageCount = rowCount / this.limit;
		else
			pageCount = rowCount / this.limit + 1;// 总页数
		if (page < pageCount)
			isNext = "true";
		else
			isNext = "false";
		if (page > 1)
			isBack = "true";
		else
			isBack = "false";
	}
	
	public String exportRefund() {
		this.exportUrl = null;
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin)session.get("LOGIN_INFO");
		Map param = new HashMap();
		param.put("LOGIN_INFO", ul);
		if(storeId>0)
		param.put("storeId", storeId);
		if(WebUtil.isNotNull(refundPropsName)&&WebUtil.isNotNull(refundPropsValue))
		{
			param.put(refundPropsName, refundPropsValue);
		}
		if(WebUtil.isNotNull(tbStatus))
			param.put("tbStatus", tbStatus);
		if(WebUtil.isNotNull(erpStatus))
			param.put("erpStatus", erpStatus);
		if(WebUtil.isNotNull(refundStatus))
			param.put("refundStatus", refundStatus);
		if(WebUtil.isNotNull(goodStatus))
			param.put("goodStatus", goodStatus);
		if(WebUtil.isNotNull(hasGoodReturn))
			param.put("hasGoodReturn", hasGoodReturn);
		if(WebUtil.isNotNull(refundFromDate))
			param.put("refundFromDate", refundFromDate);
		if(WebUtil.isNotNull(refundEndDate))
			param.put("refundEndDate", refundEndDate);
		if(WebUtil.isNotNull(completeFromDate))
			param.put("completeFromDate", completeFromDate);
		if(WebUtil.isNotNull(completeEndDate))
			param.put("completeEndDate", completeEndDate);
		Map r = this.refundServices.searchRefund(param, page, limit);
		List l = (List) r.get("RESULT");
		String[] headers = new String[] { "系统单号", "淘宝退款单号", "淘宝订单号", "买家", "交易金额",
				"退款金额", "申请时间", "退款状态", "系统状态", "店铺" };
		String[] fields = new String[] { "orderNo", "refundNo", "tbOrderNo",
				"buyerNick", "orderAmt", "refundAmt", "refundDate",
				"refundStatusName", "orderStatusName", "storeName" };
		String fileName = "refund_export.csv";
		String path = WebConfigProperties.getProperties("file.export.path")+ul.getCompanyId()+"/";
		boolean b = WebUtil.exportCSV(headers, fields, l, path, fileName);
		if (b) {
			String url = WebConfigProperties.getProperties("file.export.url")+ul.getCompanyId()+"/";
			this.exportUrl = url + fileName;
			message = "success";
		}
		message = "error";
		return "success";
	}
	
	public String viewRefundSearch()
	{
		return "success";
	}

}
