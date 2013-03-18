package com.sunmw.web.action.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sunmw.web.bean.item.GenerateItemCodeServices;
import com.sunmw.web.entity.UserLogin;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ItemGenerateCodeSearchAction {
	
	private GenerateItemCodeServices generateItemCodeServices;
	private String brand;
	private String cat;
	private String subCat;
	private String year;
	private String season;
	private String sex;
	private String dept;
	private String detail;
	private String select;
	private String fromDate;
	private String endDate;
	
	private int page = 1;// 当前页
	private int rowCount;// 总行数
	private int limit = 25;// 每页显示数
	private List<Map> resultList;// 查询结果
	private String isNext = "TRUE";// 是否有下一页
	private String isBack = "TRUE";// 是否有上一页
	private int pageCount;// 页数

	private String exportUrl;// 导出文件的链接地址
	private String message;

	public GenerateItemCodeServices getGenerateItemCodeServices() {
		return generateItemCodeServices;
	}

	public void setGenerateItemCodeServices(
			GenerateItemCodeServices generateItemCodeServices) {
		this.generateItemCodeServices = generateItemCodeServices;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getSubCat() {
		return subCat;
	}

	public void setSubCat(String subCat) {
		this.subCat = subCat;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
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

	public String searchGenerateItemCode()
	{
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		Map param = new HashMap();
		param.put("LOGIN_INFO", ul);
		if (WebUtil.isNotNull(brand))
			param.put("brand", brand);
		if (WebUtil.isNotNull(cat))
			param.put("cat", cat);
		if (WebUtil.isNotNull(subCat))
			param.put("subCat", subCat);
		if (WebUtil.isNotNull(year))
			param.put("year", year);
		if (WebUtil.isNotNull(season))
			param.put("season", season);
		if (WebUtil.isNotNull(sex))
			param.put("sex", sex);
		if (WebUtil.isNotNull(dept))
			param.put("dept", dept);
		if (WebUtil.isNotNull(detail))
			param.put("detail", detail);
		if (WebUtil.isNotNull(select))
			param.put("select", select);
		if (WebUtil.isNotNull(fromDate))
			param.put("fromDate", fromDate);
		if (WebUtil.isNotNull(endDate))
			param.put("endDate", endDate);
		Map r = this.generateItemCodeServices.searchGenerateItem(param, page, limit);
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
	
	public String exportGenerateItemCode()
	{
		Map session = ActionContext.getContext().getSession();
		UserLogin ul = (UserLogin) session.get("LOGIN_INFO");
		Map param = new HashMap();
		param.put("LOGIN_INFO", ul);
		if (WebUtil.isNotNull(brand))
			param.put("brand", brand);
		if (WebUtil.isNotNull(cat))
			param.put("cat", cat);
		if (WebUtil.isNotNull(subCat))
			param.put("subCat", subCat);
		if (WebUtil.isNotNull(year))
			param.put("year", year);
		if (WebUtil.isNotNull(season))
			param.put("season", season);
		if (WebUtil.isNotNull(sex))
			param.put("sex", sex);
		if (WebUtil.isNotNull(dept))
			param.put("dept", dept);
		if (WebUtil.isNotNull(detail))
			param.put("detail", detail);
		if (WebUtil.isNotNull(select))
			param.put("select", select);
		if (WebUtil.isNotNull(fromDate))
			param.put("fromDate", fromDate);
		if (WebUtil.isNotNull(endDate))
			param.put("endDate", endDate);
		Map r = this.generateItemCodeServices.exportGenerateItem(param);
		List l = (List) r.get("RESULT");
		String[] headers = new String[] {"品牌","系列","子系列","年","季节","性别","部门","细分","品番（商品代码）","样品号","商品名称"};
		String[] fields = new String[] { "brand", "cat","subCat", "year",
				 "season", "sex", "dept",
				"detail", "serial", "itemCode", "itemName" };
		String fileName = "item_code_export.csv";
		String path = WebConfigProperties.getProperties("file.export.path");
		if(WebUtil.isNotNull(ul.getCompanyId()))
		{
			path = path + ul.getCompanyId()+"/";
		}
		boolean b = WebUtil.exportCSV(headers, fields, l, path, fileName,"GBK");
		if (b) {
			String url = WebConfigProperties.getProperties("file.export.url");

			if(WebUtil.isNotNull(ul.getCompanyId()))
			{
				url = url + ul.getCompanyId()+"/";
			}
			this.exportUrl = url + fileName;
			message = "success";
		}
		message = "error";
		return "success";
	}
}
