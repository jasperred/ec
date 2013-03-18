package com.sunmw.web.action.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.web.bean.product.ProductServices;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class SkuSearchAction {
	private ProductServices productServices;
	
	private String itemCd;
	private String skuCd;
	private String itemName;
	private String fromPrice;
	private String endPrice;
	
	private int currentPage = 1;// 当前页
	private int rowCount;// 总行数
	private int pageRow = 15;// 每页显示数
	private List<Map> resultList;// 查询结果
	private String isNext = "TRUE";// 是否有下一页
	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数	
	
	private String exportUrl;//导出文件的链接地址
	private String message;
	
	public ProductServices getProductServices() {
		return productServices;
	}

	public void setProductServices(ProductServices productServices) {
		this.productServices = productServices;
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

	public String getFromPrice() {
		return fromPrice;
	}

	public void setFromPrice(String fromPrice) {
		this.fromPrice = fromPrice;
	}

	public String getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(String endPrice) {
		this.endPrice = endPrice;
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

	//sku查询
	public String skuSearch()
	{
		Map param = new HashMap();
		param.put("ItemCd", itemCd);
		param.put("SkuCd", skuCd);
		param.put("ItemName", itemName);
		param.put("FromPrice", fromPrice);
		param.put("EndPrice", endPrice);
		Map r = this.productServices.searchSku(param, currentPage, pageRow);
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
	public String exportSku()
	{
		Map param = new HashMap();
		param.put("ItemCd", itemCd);
		param.put("SkuCd", skuCd);
		param.put("ItemName", itemName);
		param.put("FromPrice", fromPrice);
		param.put("EndPrice", endPrice);
		Map r = this.productServices.searchSku(param, currentPage, pageRow);
		List l = (List) r.get("RESULT");
		String[] headers = new String[]{"商品编码","SKU编码","商品名称","属性1","属性2","属性3","价格"};
		String[] fields = new String[]{"ItemCd","SkuCd","ItemName","SkuProp1","SkuProp2","SkuProp3","SkuPrice1"};
		String fileName = "sku_export.csv";
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
	public String viewSkuSearch()
	{
		return "success";
	}

}
