package com.sunmw.web.action.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.web.bean.inventory.InventoryServices;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class InventorySearchAction {
	
	private InventoryServices inventoryServices;
	
	private String whId;
	private String itemCd;
	private String skuCd;
	private String invType;
	
	private int currentPage = 1;// 当前页
	private int rowCount;// 总行数
	private int pageRow = 15;// 每页显示数
	private List<Map> resultList;// 查询结果
	private String isNext = "TRUE";// 是否有下一页
	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数	
	
	private String exportUrl;//导出文件的链接地址
	private String message;
	
	public InventoryServices getInventoryServices() {
		return inventoryServices;
	}

	public void setInventoryServices(InventoryServices inventoryServices) {
		this.inventoryServices = inventoryServices;
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

	//库存查询
	public String inventoryInSearch()
	{
		Map param = new HashMap();
		if(WebUtil.isNotNull(whId))
			param.put("WhId", whId);
		if(WebUtil.isNotNull(itemCd))
			param.put("ItemCd", itemCd);
		if(WebUtil.isNotNull(skuCd))
			param.put("SkuCd", skuCd);
		if(WebUtil.isNotNull(invType))
			param.put("InvType", invType);
		Map r = inventoryServices.searchInventory(param, currentPage, pageRow);
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
	
	//导出库存
	public String exportInventory()
	{
		Map param = new HashMap();
		if(WebUtil.isNotNull(whId))
			param.put("WhId", whId);
		if(WebUtil.isNotNull(itemCd))
			param.put("ItemCd", itemCd);
		if(WebUtil.isNotNull(skuCd))
			param.put("SkuCd", skuCd);
		if(WebUtil.isNotNull(invType))
			param.put("InvType", invType);
		Map r = inventoryServices.searchInventory(param, currentPage, pageRow);
		List l = (List) r.get("RESULT");
		String[] headers = new String[]{"商品编码","商品名称","SKU编码","颜色","尺码","库存类型","库存状态","库存数量","可用数量"};
		String[] fields = new String[]{"ItemCd","ItemName","SkuCd","SkuProp1","SkuProp2","InvType","InvStatus","Quantity","AvailableQuantity"};
		String fileName = "inventory_export.csv";
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

	//显示库存查询页面
	public String viewInventorySearch()
	{
		return "success";
	}

}
