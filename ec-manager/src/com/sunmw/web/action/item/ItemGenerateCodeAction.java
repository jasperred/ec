package com.sunmw.web.action.item;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunmw.web.bean.item.GenerateItemCodeServices;
import com.sunmw.web.util.WebUtil;

public class ItemGenerateCodeAction {
	private GenerateItemCodeServices generateItemCodeServices;
	
	private String filePath;
	private List messageList;
	private String message;
	private boolean success;
	
	public GenerateItemCodeServices getGenerateItemCodeServices() {
		return generateItemCodeServices;
	}

	public void setGenerateItemCodeServices(
			GenerateItemCodeServices generateItemCodeServices) {
		this.generateItemCodeServices = generateItemCodeServices;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List getMessageList() {
		return messageList;
	}

	public void setMessageList(List messageList) {
		this.messageList = messageList;
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

	public String importItemCode()
	{
		File file = new File(filePath);
		if (!file.exists())
		{
			message = "文件不存在";
			success = false;
			return "success";
		}
		List l = WebUtil.readerCsv(file, true, "GBK");
		if (WebUtil.isNullForList(l))
		{
			message = "文件无内容";
			success = false;
			return "success";
		}
		Map param = new HashMap();
		param.put("itemList", l);
		Map r = this.generateItemCodeServices.generateItemCode(param);
		messageList = (List) r.get("messageList");
		success = true;
		return "success";
	}

}
