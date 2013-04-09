package com.sunmw.web.action.warehouse.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.store.StoreServices;
import com.sunmw.web.bean.warehouse.base.WarehouseBaseServices;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebUtil;

public class WarehouseSearchAction {
	private WarehouseBaseServices wmsBaseServices;
	
	private String whCode;
	private String whName;
	private String storeId;
	
	private int page = 1;// 当前页
	private int rowCount;// 总行数
	private int limit = 25;// 每页显示数
	private List<Map> resultList;// 查询结果
	private String isNext = "TRUE";// 是否有下一页
	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数
	
	

	public WarehouseBaseServices getWmsBaseServices() {
		return wmsBaseServices;
	}

	public void setWmsBaseServices(WarehouseBaseServices wmsBaseServices) {
		this.wmsBaseServices = wmsBaseServices;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getWhName() {
		return whName;
	}

	public void setWhName(String whName) {
		this.whName = whName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
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

	//店铺查询
	public String warehouseSearch()
	{
		Map param = new HashMap();
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		param.put("LOGIN_INFO", ul);
		if(WebUtil.isNotNull(whCode))
			param.put("whCode", whCode);
		if(WebUtil.isNotNull(whName))
			param.put("whName", whName);
		if(WebUtil.isNotNull(storeId))
			param.put("storeId", new Integer(storeId));
		Map r = this.wmsBaseServices.searchWarehouse(param, page, limit);
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

}
