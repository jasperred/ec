package com.sunmw.web.action.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.web.bean.item.GenerateItemCodeServices;

public class ItemGenerateTypeListAction {
	private GenerateItemCodeServices generateItemCodeServices;

	private String type;
	private String parent;
	private String name;
	private List<Map> typeList;// 查询结果
	
	public GenerateItemCodeServices getGenerateItemCodeServices() {
		return generateItemCodeServices;
	}

	public void setGenerateItemCodeServices(
			GenerateItemCodeServices generateItemCodeServices) {
		this.generateItemCodeServices = generateItemCodeServices;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<Map> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Map> typeList) {
		this.typeList = typeList;
	}

	//得到编码类型
	public String searchItemGenerateType()
	{
		Map param = new HashMap();
		param.put("type", type);
		param.put("name", name);
		param.put("parent", parent);
		Map r = generateItemCodeServices.getGenerateType(param);
		typeList = (List) r.get("typeList");
		return "success";
	}

}
