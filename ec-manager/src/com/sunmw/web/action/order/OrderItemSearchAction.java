package com.sunmw.web.action.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class OrderItemSearchAction {
	
	private OrderServices orderServices;
	
	private String storeId;
	private String orderPropsValue;
	private String orderPropsName;
	private String tbStatus;
	private String erpStatus;
	private String receiveMessage;
	private String orderFromDate;
	private String orderEndDate;
	private String itemCd;
	private String skuCd;
	private String itemName;
	
	private int page = 1;// 当前页
	private int rowCount;// 总行数
	private int limit = 25;// 每页显示数
	private List<Map> resultList;// 查询结果
	private String isNext = "TRUE";// 是否有下一页
	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数	
	
	private String exportUrl;//导出文件的链接地址
	private String message;
	
	public OrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(OrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getOrderPropsValue() {
		return orderPropsValue;
	}

	public void setOrderPropsValue(String orderPropsValue) {
		this.orderPropsValue = orderPropsValue;
	}

	public String getOrderPropsName() {
		return orderPropsName;
	}

	public void setOrderPropsName(String orderPropsName) {
		this.orderPropsName = orderPropsName;
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

	public String getReceiveMessage() {
		return receiveMessage;
	}

	public void setReceiveMessage(String receiveMessage) {
		this.receiveMessage = receiveMessage;
	}

	public String getOrderFromDate() {
		return orderFromDate;
	}

	public void setOrderFromDate(String orderFromDate) {
		this.orderFromDate = orderFromDate;
	}

	public String getOrderEndDate() {
		return orderEndDate;
	}

	public void setOrderEndDate(String orderEndDate) {
		this.orderEndDate = orderEndDate;
	}
	
	public String getItemCd() {
		return itemCd;
	}

	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	public String getSkuCd() {
		return skuCd;
	}

	public void setSkuCd(String skuCd) {
		this.skuCd = skuCd;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	//订单明细查询
	public String orderItemSearch()
	{
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		Map param = new HashMap();
		param.put("LOGIN_INFO", ul);
		if(WebUtil.isNotNull(storeId))
			param.put("storeId", new Integer(storeId));
		if(orderPropsValue!=null&&orderPropsValue.trim().length()>0&&orderPropsName!=null&&orderPropsName.trim().length()>0)
		{
			param.put(orderPropsName, orderPropsValue);
		}
		if(tbStatus!=null&&tbStatus.trim().length()>0)
		param.put("tbStatus", tbStatus);
		if(erpStatus!=null&&erpStatus.trim().length()>0)
		param.put("erpStatus", erpStatus);
		if(receiveMessage!=null&&receiveMessage.trim().length()>0)
		param.put("receiveMessage", receiveMessage);
		if(orderFromDate!=null&&orderFromDate.trim().length()>0)
		param.put("orderFromDate", orderFromDate);
		if(orderEndDate!=null&&orderEndDate.trim().length()>0)
		param.put("orderEndDate", orderEndDate);
		if(itemCd!=null&&itemCd.trim().length()>0)
			param.put("itemCd", itemCd);
		if(skuCd!=null&&skuCd.trim().length()>0)
			param.put("skuCd", skuCd);
		if(itemName!=null&&itemName.trim().length()>0)
			param.put("itemName", itemName);
		Map r = this.orderServices.searchOrderItem(param, page, limit);
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
	
	//导出订单明细
	public String exportOrderItem()
	{
		Map param = new HashMap();
		if(WebUtil.isNotNull(storeId))
			param.put("storeId", new Integer(storeId));
		if(orderPropsValue!=null&&orderPropsValue.trim().length()>0&&orderPropsName!=null&&orderPropsName.trim().length()>0)
		{
			param.put(orderPropsName, orderPropsValue);
		}
		if(tbStatus!=null&&tbStatus.trim().length()>0)
		param.put("tbStatus", tbStatus);
		if(erpStatus!=null&&erpStatus.trim().length()>0)
		param.put("erpStatus", erpStatus);
		if(receiveMessage!=null&&receiveMessage.trim().length()>0)
		param.put("receiveMessage", receiveMessage);
		if(orderFromDate!=null&&orderFromDate.trim().length()>0)
		param.put("orderFromDate", orderFromDate);
		if(orderEndDate!=null&&orderEndDate.trim().length()>0)
		param.put("orderEndDate", orderEndDate);
		if(itemCd!=null&&itemCd.trim().length()>0)
			param.put("itemCd", itemCd);
		if(skuCd!=null&&skuCd.trim().length()>0)
			param.put("skuCd", skuCd);
		if(itemName!=null&&itemName.trim().length()>0)
			param.put("itemName", itemName);
		Map r = this.orderServices.searchOrderItem(param, page, limit);
		List l = (List) r.get("RESULT");
		String[] headers = new String[]{"订单号 ","淘宝订单号 ","买家 ","订单时间 ","付款时间 ","淘宝状态 ","系统状态 ","店铺 ","商品编码 ","SKU编码 ","商品名称 ","价格 ","数量 ","金额"};
		String[] fields = new String[]{"orderNo","origOrderNo","buyerNick","requestDate","payTime","origOrderStatusName","orderStatusName","storeName","itemCd","skuCd","itemName","itemPrice","itemQty","orderAmt"};
		String fileName = "order_item_export.csv";
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
	public String viewOrderItemSearch()
	{
		return "success";
	}

}
