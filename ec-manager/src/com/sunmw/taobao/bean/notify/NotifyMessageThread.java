package com.sunmw.taobao.bean.notify;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sunmw.web.bean.dataInterface.TaobaoInterfaceServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebUtil;

/**
 * 处理淘宝主动业务通知消息，收到消息后根据消息类型调用相应的接口下载信息
 * 多线程异步处理
 * @author jasper 2012-04-10 21：25
 *
 */
public class NotifyMessageThread extends Thread {
	static Logger logger = Logger.getLogger(NotifyMessageThread.class);
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
		if(this.message==null)
		{
			logger.info("无消息内容");
			return;
		}
		JSONObject mjson = JSONObject.fromObject(message);
		TaobaoInterfaceServices taobaoInterfaceServices = (TaobaoInterfaceServices) GetBeanServlet.getBean("taobaoInterfaceServices");
		//订单
		if(WebUtil.isNotNull(mjson.get("notify_trade")))
		{
			String tradeStr = mjson.get("notify_trade").toString();
			JSONObject tradejson = JSONObject.fromObject(tradeStr);
			if(WebUtil.isNull(tradejson.get("tid")))
				return;
			Long tid = (Long)tradejson.get("tid");
			Map param = new HashMap();
			param.put("StoreId", storeId);
			param.put("Tid", tid);
			Map r = taobaoInterfaceServices.taobaoTradeFullinfoGet(param);
			if(r.get("Flag").equals("success"))
			{
				//logger.info("订单下载成功");
			}
			else
			{
				logger.error("订单下载失败，消息："+r.get("Message"));
			}
		}
		//退货
		if(WebUtil.isNotNull(mjson.get("notify_refund")))
		{
			String refundStr = mjson.get("notify_refund").toString();
			JSONObject refundjson = JSONObject.fromObject(refundStr);
			if(WebUtil.isNull(refundjson.get("rid")))
				return;
			Long rid = (Long)refundjson.get("rid");
			Map param = new HashMap();
			param.put("StoreId", storeId);
			param.put("RefundId", rid);
			Map r = taobaoInterfaceServices.taobaoRefundGet(param);
			if(r.get("Flag").equals("success"))
			{
				//logger.info("退货单下载成功");
			}
			else
			{
				logger.error("退货单下载失败，消息："+r.get("Message"));
			}
		}
		//商品
		if(WebUtil.isNotNull(mjson.get("notify_item")))
		{
			
		}
	}

}
