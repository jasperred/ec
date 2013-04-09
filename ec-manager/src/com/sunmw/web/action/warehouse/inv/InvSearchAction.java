package com.sunmw.web.action.warehouse.inv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.warehouse.inv.WarehouseInvServices;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class InvSearchAction {

	private WarehouseInvServices wmsInvServices;
	private String whId;
	private String itemCd;
	private String skuCd;
	private String invType;

	private int page = 1;// 当前页
	private int rowCount;// 总行数
	private int limit = 25;// 每页显示数
	private List<Map> resultList;// 查询结果
	private String isNext = "TRUE";// 是否有下一页
	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数

	private String exportUrl;// 导出文件的链接地址
	private String message;

	public WarehouseInvServices getWmsInvServices() {
		return wmsInvServices;
	}

	public void setWmsInvServices(WarehouseInvServices wmsInvServices) {
		this.wmsInvServices = wmsInvServices;
	}

	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
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

	public String getInvType() {
		return invType;
	}

	public void setInvType(String invType) {
		this.invType = invType;
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

	public String searchInv() {
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		Map param = new HashMap();
		param.put("LOGIN_INFO", ul);
		param.put("WhId", whId);
		param.put("ItemCd", itemCd);
		param.put("SkuCd", skuCd);
		param.put("InvType", invType);

		Map r = this.wmsInvServices.searchInventory(param, page, limit);
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

	// 导出订单
	public String exportInv() {
		this.exportUrl = null;
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		Map param = new HashMap();
		param.put("LOGIN_INFO", ul);
		param.put("WhId", whId);
		param.put("ItemCd", itemCd);
		param.put("SkuCd", skuCd);
		param.put("InvType", invType);

		Map r = this.wmsInvServices.searchInventory(param, page, limit);
		List l = (List) r.get("RESULT");
		String[] headers = new String[] { "仓库ID", "仓库名称", "商品编码", "商品名称",
				"SKU", "属性1", "属性2", "库存类型", "库存状态", "库存数量", "可用数量" };
		String[] fields = new String[] { "WhId", "WhName", "ItemCd",
				"ItemName", "SkuCd", "SkuProp1", "SkuProp2", "InvType",
				"InvStatus", "Quantity", "AvailableQuantity" };
		String fileName = "inventory_export.csv";
		String path = WebConfigProperties.getProperties("file.export.path");
		if (WebUtil.isNotNull(ul.getCompanyId())) {
			path = path + ul.getCompanyId() + "/";
		}
		boolean b = WebUtil.exportCSV(headers, fields, l, path, fileName);
		if (b) {
			String url = WebConfigProperties.getProperties("file.export.url");

			if (WebUtil.isNotNull(ul.getCompanyId())) {
				url = url + ul.getCompanyId() + "/";
			}
			this.exportUrl = url + fileName;
			message = "success";
		}
		message = "error";
		return "success";
	}

}
