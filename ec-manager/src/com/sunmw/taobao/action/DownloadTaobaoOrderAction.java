package com.sunmw.taobao.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sunmw.taobao.bean.order.TBOrderServices;
import com.sunmw.web.bean.inventory.InventoryServices;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.util.WebUtil;

public class DownloadTaobaoOrderAction {
	
	static Logger logger = Logger.getLogger(DownloadTaobaoOrderAction.class);
	
	private TBOrderServices tbOrderServices;
	private OrderServices orderServices;
	private InventoryServices inventoryServices;
	private int taskId;
	
	private int storeId;
	
	public OrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(OrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public TBOrderServices getTbOrderServices() {
		return tbOrderServices;
	}

	public void setTbOrderServices(TBOrderServices tbOrderServices) {
		this.tbOrderServices = tbOrderServices;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public InventoryServices getInventoryServices() {
		return inventoryServices;
	}

	public void setInventoryServices(InventoryServices inventoryServices) {
		this.inventoryServices = inventoryServices;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void downloadTBOrder()
	{
		long start = System.currentTimeMillis();
		Map params = new HashMap();
		params.put("StoreId", storeId);
		params.put("fields", "tid");
		params.put("startModified", new Date(System.currentTimeMillis()-1*24*60*60*1000));
		params.put("endModified", new Date(System.currentTimeMillis()));
		//下载淘宝订单
		tbOrderServices.taobaoTradesSoldIncrementGet( params);
		//tbOrderServices.updateTbOrderToErp(storeId);
		//得到需要更新到ERP的订单
//		List l = tbOrderServices.getNotUpdateTBOrder(storeId);
//		int r = 0;
//		if(!WebUtil.isNullForList(l))
//			r = l.size();
//		logger.info("需要导入到ERP的淘宝订单条数为["+r+"]");
////		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext .getServletContext());
////		OrderServices orderServices = (OrderServices) ctx.getBean("orderServices");
//		//更新ERP订单
//		String tids = orderServices.updateOrderFromTb(l);
//		logger.info("更新到ERP的淘宝订单为["+tids+"]");
//		r = 0;
//		if(WebUtil.isNotNull(tids))
//			r = tids.split(",").length;
//		logger.info("更新到ERP的淘宝订单数量为["+r+"]");
//		//更新淘宝订单更新标记
//		tbOrderServices.updateTbOrderStatusForImportErp(tids);
//		//更新库存
//		inventoryServices.tbOrderInventoryProcess(storeId);
	}
	
	public void downloadTBOrder2()
	{
		long start = System.currentTimeMillis();
		Map params = new HashMap();
		params.put("StoreId", storeId);
		params.put("fields", "tid");
		params.put("startCreated", WebUtil.toDateForString("2011-08-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		params.put("endCreated", WebUtil.toDateForString("2011-10-31 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		//下载淘宝订单
		tbOrderServices.taobaoTradesSoldGet( params);
		//tbOrderServices.updateTbOrderToErp(storeId);
		//得到需要更新到ERP的订单
//		List l = tbOrderServices.getNotUpdateTBOrder(storeId);
//		int r = 0;
//		if(!WebUtil.isNullForList(l))
//			r = l.size();
//		logger.info("需要导入到ERP的淘宝订单条数为["+r+"]");
////		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext .getServletContext());
////		OrderServices orderServices = (OrderServices) ctx.getBean("orderServices");
//		//更新ERP订单
//		String tids = orderServices.updateOrderFromTb(l);
//		logger.info("更新到ERP的淘宝订单为["+tids+"]");
//		r = 0;
//		if(WebUtil.isNotNull(tids))
//			r = tids.split(",").length;
//		logger.info("更新到ERP的淘宝订单数量为["+r+"]");
//		//更新淘宝订单更新标记
//		tbOrderServices.updateTbOrderStatusForImportErp(tids);
//		//更新库存
//		inventoryServices.tbOrderInventoryProcess(storeId);
	}
}
