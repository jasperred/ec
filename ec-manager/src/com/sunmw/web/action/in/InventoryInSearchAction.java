package com.sunmw.web.action.in;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.web.bean.in.InventoryInServices;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class InventoryInSearchAction {
	
	private InventoryInServices inventoryInServices;
	
	private String orderNo;
	private String orderStatus;
	private String whId;
	private String fromDate;
	private String endDate;
	private int orderHeadId;
	
	private int currentPage = 1;// 当前页
	private int rowCount;// 总行数
	private int pageRow = 15;// 每页显示数
	private List<Map> resultList;// 查询结果
	private String isNext = "TRUE";// 是否有下一页
	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数	
	private String sort;//排序
	private String order;//排序方式
	
	private String exportUrl;//导出文件的链接地址
	private String message;
	
	public InventoryInServices getInventoryInServices() {
		return inventoryInServices;
	}

	public void setInventoryInServices(InventoryInServices inventoryInServices) {
		this.inventoryInServices = inventoryInServices;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageRow() {
		return pageRow;
	}

	public void setPageRow(int pageRow) {
		this.pageRow = pageRow;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
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

	public int getOrderHeadId() {
		return orderHeadId;
	}

	public void setOrderHeadId(int orderHeadId) {
		this.orderHeadId = orderHeadId;
	}

	//订单查询
	public String inventoryInSearch()
	{
		Map param = new HashMap();
		if(WebUtil.isNotNull(whId))
			param.put("WhId", whId);
		if(WebUtil.isNotNull(orderNo))
			param.put("OrderNo", orderNo);
		if(WebUtil.isNotNull(orderStatus))
			param.put("OrderStatus", orderStatus);
		if(WebUtil.isNotNull(fromDate))
			param.put("FromDate", fromDate);
		if(WebUtil.isNotNull(endDate))
			param.put("EndDate", endDate);
		if(WebUtil.isNotNull(sort))
			param.put("SORT", sort);
		if(WebUtil.isNotNull(order))
			param.put("ORDER", order);
		Map r = inventoryInServices.searchInventoryInOrder(param, currentPage, pageRow);
		this.rowCount = (Integer) r.get("COUNT_ROW");
		this.resultList = (List) r.get("RESULT");
		countPage();
		return "success";
	}
	
	//订单明细查询
	public String inventoryInItemSearch()
	{
		if(orderHeadId<=0)
		{
			currentPage = 1;
			rowCount = 0;
			resultList = null;
			countPage();
			return "success";
		}
		Map param = new HashMap();
		param.put("OrderHeadId", orderHeadId);
		param.put("SORT", "Ctime");
		Map r = inventoryInServices.getInventoryInOrderItem(param, currentPage, pageRow);
		this.rowCount = (Integer) r.get("COUNT_ROW");
		this.resultList = (List) r.get("RESULT");
		countPage();
		return "success";
	}
	
	private void countPage() {
		if (rowCount % this.pageRow == 0)
			pageCount = rowCount / this.pageRow;
		else
			pageCount = rowCount / this.pageRow + 1;// 总页数
		if (currentPage < pageCount)
			isNext = "true";
		else
			isNext = "false";
		if (currentPage > 1)
			isBack = "true";
		else
			isBack = "false";
	}
	
	//导出订单
	public String exportInventoryIn()
	{
		Map param = new HashMap();
		if(WebUtil.isNotNull(whId))
			param.put("WhId", whId);
		if(WebUtil.isNotNull(orderNo))
			param.put("OrderNo", orderNo);
		if(WebUtil.isNotNull(orderStatus))
			param.put("OrderStatus", orderStatus);
		if(WebUtil.isNotNull(fromDate))
			param.put("FromDate", fromDate);
		if(WebUtil.isNotNull(endDate))
			param.put("EndDate", endDate);
		if(WebUtil.isNotNull(sort))
			param.put("SORT", sort);
		if(WebUtil.isNotNull(order))
			param.put("ORDER", order);
		Map r = inventoryInServices.searchInventoryInOrder(param, currentPage, pageRow);
		List l = (List) r.get("RESULT");
		String[] headers = new String[]{"入库单号","入库日期","计划数量","实际数量","备注","入库状态"};
		String[] fields = new String[]{"OrderNo","RequestDate","ReqQty","Qty","Memo","OrderStatusName"};
		String fileName = "inventory_in_export.csv";
		String path = WebConfigProperties.getProperties("file.export.path");
		boolean b = WebUtil.exportCSV(headers,fields, l, path, fileName);
		if(b)
		{
			String url = WebConfigProperties.getProperties("file.export.url");
			this.exportUrl = url+fileName;
			message = "success";
		}
		message = "error";
		return "success";
	}
	
	//显示订单查询页面
	public String viewInventoryInSearch()
	{
		return "success";
	}

}
