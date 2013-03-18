package com.sunmw.taobao.bean.notify;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sunmw.web.bean.dataInterface.TaobaoInterfaceServices;
import com.sunmw.web.common.GetBeanServlet;

public class NotifyDiscardThread extends Thread {
	
	static Logger logger = Logger.getLogger(NotifyDiscardThread.class);
	private int storeId;
	private String message;
	
	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void run()
	{
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this.message==null)
		{
			logger.info("无消息内容");
			return;
		}
		JSONObject mjson = JSONObject.fromObject(message);
		Long begin = (Long) mjson.get("begin");
		Long end = (Long) mjson.get("end");
		Map m = new HashMap();
		m.put("StoreId", storeId);
		m.put("Start", new Date(begin));
		m.put("End", new Date(end));
		TaobaoInterfaceServices taobaoInterfaceServices = (TaobaoInterfaceServices) GetBeanServlet.getBean("taobaoInterfaceServices");
		Map r = taobaoInterfaceServices.taobaoCometDiscardInfo(m);
		if(r.get("Flag").equals("success"))
		{
			logger.info("用户丢失消息成功");
		}
		else
		{
			logger.error("用户丢失消息失败，消息："+r.get("Message"));
		}
	}

}
