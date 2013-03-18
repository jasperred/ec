package com.sunmw.web.action.item;

import java.util.HashMap;
import java.util.Map;

import com.sunmw.web.bean.item.GenerateItemCodeServices;
import com.sunmw.web.util.WebUtil;

public class ItemGenerateTypeInfoAction {
	private GenerateItemCodeServices generateItemCodeServices;
	private String typeId;
	private String name;
	private String value;
	private String type;
	private String parent;
	private String message;
	private boolean success;
	
	public GenerateItemCodeServices getGenerateItemCodeServices() {
		return generateItemCodeServices;
	}

	public void setGenerateItemCodeServices(
			GenerateItemCodeServices generateItemCodeServices) {
		this.generateItemCodeServices = generateItemCodeServices;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
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

	//保存编码类型
	public String saveItemGenerateType()
	{
		Map param = new HashMap();
		if(WebUtil.isNotNull(typeId))
		param.put("typeId", new Integer(typeId));
		param.put("name", name);
		param.put("value", value);
		param.put("type", type);
		param.put("parent", parent);
		Map r = generateItemCodeServices.saveGenerateType(param);
		if(r.get("Flag").equals("error"))
		{
			message = (String)r.get("Message");
			success = false;
		}
		else
		{
			this.typeId = r.get("typeId").toString();
			success = true;
		}
		return "success";
	}
	
	//删除编码类型
	public String deleteItemGenerateType()
	{
		Map param = new HashMap();
		if(WebUtil.isNull(typeId))
		{
			message = "itemId is null";
			success = false;
		}
		else
		{
			generateItemCodeServices.deleteGenerateType(WebUtil.toMap("typeId", new Integer(typeId)));
			success = true;
		}
		return "success";
	}

}
