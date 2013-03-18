package com.sunmw.web.action.item;

import java.util.HashMap;
import java.util.Map;

import com.sunmw.web.bean.item.GenerateItemCodeServices;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class ItemCodeInfoAction {
	private GenerateItemCodeServices generateItemCodeServices;
	private String itemId;
	private String brand;
	private String brandCode;
	private String cat;
	private String subCat;
	private String subCatCode;
	private String year;
	private String yearCode;
	private String season;
	private String seasonCode;
	private String sex;
	private String sexCode;
	private String dept;
	private String detail;
	private String detailCode;
	private String serial;
	private String itemCode;
	private String sel;
	private String itemName;
	private String imageUrl;
	
	private String message;
	private boolean success;
	
	public GenerateItemCodeServices getGenerateItemCodeServices() {
		return generateItemCodeServices;
	}

	public void setGenerateItemCodeServices(
			GenerateItemCodeServices generateItemCodeServices) {
		this.generateItemCodeServices = generateItemCodeServices;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getSubCatCode() {
		return subCatCode;
	}

	public void setSubCatCode(String subCatCode) {
		this.subCatCode = subCatCode;
	}

	public String getYearCode() {
		return yearCode;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	public String getSeasonCode() {
		return seasonCode;
	}

	public void setSeasonCode(String seasonCode) {
		this.seasonCode = seasonCode;
	}

	public String getSexCode() {
		return sexCode;
	}

	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}

	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	//得到商品信息
	public String getItemCodeInfo()
	{
		if(WebUtil.isNull(itemId))
		{
			message = "itemId is null";
			success = false;
		}
		else
		{
			Map r = generateItemCodeServices.getItemInfo(WebUtil.toMap("itemId", new Integer(itemId)));
			if(r.get("Flag").equals("error"))
			{
				message = (String)r.get("Message");
				success = false;
			}
			else
			{
				brand = (String)r.get("brand");
				cat = (String)r.get("cat");
				subCat = (String)r.get("subCat");
				year = (String)r.get("year");
				season = (String)r.get("season");
				sex = (String)r.get("sex");
				dept = (String)r.get("dept");
				detail = (String)r.get("detail");
				serial = (String)r.get("serial");
				itemCode = (String)r.get("itemCode");
				itemName = (String)r.get("itemName");
				sel = (String)r.get("sel");
				imageUrl = (String)r.get("imageUrl");
				if(WebUtil.isNotNull(imageUrl))
				{
					imageUrl = WebConfigProperties.getProperties("system.file.url")+imageUrl;
				}
				else
				{
					imageUrl = WebConfigProperties.getProperties("system.file.url")+"noimage.gif";
				}
				success = true;
			}
		}
		return "success";
	}
	
	//生成商品编码和保存商品信息
	public String saveItemCode()
	{
		Map param = new HashMap();
		if(WebUtil.isNotNull(itemId))
		{
			param.put("itemId", itemId);
			param.put("sel", sel);
			param.put("itemName", itemName);
		}
		else
		{
			param.put("brand", brand);
			param.put("cat", cat);
			param.put("subCat", subCat);
			param.put("year", year);
			param.put("season", season);
			param.put("sex", sex);
			param.put("dept", dept);
			param.put("detail", detail);
			param.put("sel", sel);
			param.put("itemName", itemName);
			param.put("brandCode", brandCode);
			param.put("subCatCode", subCatCode);
			param.put("yearCode", yearCode);
			param.put("seasonCode", seasonCode);
			param.put("sexCode", sexCode);
			param.put("detailCode", detailCode);
		}
		Map r = generateItemCodeServices.saveItemCode(param);
		if(r.get("Flag").equals("error"))
		{
			message = (String)r.get("Message");
			success = false;
		}
		else
		{
			itemId = r.get("itemId").toString();
			success = true;
		}
		return "success";
	}
	
	//得到商品的品番，可能不是最終的品番
	public String getItemSerial()
	{
		this.serial = WebUtil.addPrefix("" + generateItemCodeServices.getItemSerial(year), 3, "0");
		return "success";
	}
	
	//保存商品图片
	public String saveItemImage()
	{
		if(WebUtil.isNull(itemId))
		{
			message = "itemId is null";
			success = false;
		}
		else
		{
			Map r = generateItemCodeServices.saveItemImage(WebUtil.toMap("itemId", new Integer(itemId),"imageUrl",imageUrl));
			success = true;
		}
		return "success";
	}

}
