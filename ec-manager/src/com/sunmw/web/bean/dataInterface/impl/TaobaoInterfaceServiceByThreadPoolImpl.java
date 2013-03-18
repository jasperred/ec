package com.sunmw.web.bean.dataInterface.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sunmw.taobao.bean.order.TBOrderDataServices;
import com.sunmw.taobao.bean.order.TBOrderServices;
import com.sunmw.web.bean.dataInterface.TaobaoInterfaceServices;
import com.sunmw.web.bean.order.OrderDataServices;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.bean.refund.RefundServices;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.domain.Trade;

public class TaobaoInterfaceServiceByThreadPoolImpl implements
		TaobaoInterfaceServices {
	
	private RefundServices refundServices;
	private TBOrderServices tbOrderServices;
	private TBOrderDataServices tbOrderDataServices;
	private OrderServices orderServices;
	private OrderDataServices orderDataServices;
	
	private ExecutorService tidPool = Executors.newFixedThreadPool(10);
	private ExecutorService tradePool = Executors.newFixedThreadPool(10);
	private ExecutorService orderPool = Executors.newFixedThreadPool(10);
	

	public RefundServices getRefundServices() {
		return refundServices;
	}

	public void setRefundServices(RefundServices refundServices) {
		this.refundServices = refundServices;
	}

	public TBOrderServices getTbOrderServices() {
		return tbOrderServices;
	}

	public void setTbOrderServices(TBOrderServices tbOrderServices) {
		this.tbOrderServices = tbOrderServices;
	}

	public OrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(OrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public TBOrderDataServices getTbOrderDataServices() {
		return tbOrderDataServices;
	}

	public void setTbOrderDataServices(TBOrderDataServices tbOrderDataServices) {
		this.tbOrderDataServices = tbOrderDataServices;
	}

	public OrderDataServices getOrderDataServices() {
		return orderDataServices;
	}

	public void setOrderDataServices(OrderDataServices orderDataServices) {
		this.orderDataServices = orderDataServices;
	}

	@Override
	public Map checkTaobaoOrderMiss(Map param) {
		Map result = new HashMap();
		Map r = tbOrderServices.taobaoCheckOrderMiss(param);
		if(r.get("Flag").equals("success"))
		{
			result.put("Flag", "success");
			String tids = (String) r.get("tids");
			if(WebUtil.isNull(tids))
				return result;
			String[] ts = tids.split(",");
	    	Collection<Callable<Object>> tl = new ArrayList();
	    	for(final String tid:ts)
	    	{
	    		tl.add(new Callable() {

					@Override
					public Object call() throws Exception {
						tbOrderDataServices.insertTid(new Long(tid));
						orderDataServices.insertNullOrder(tid, "TBOD");
						return null;
					}
				});
	    	}
	    	try {
				tidPool.invokeAll(tl);
				final Integer storeId = (Integer) param.get("StoreId");
				for(final String tid:ts)
				{
					Map pp = new HashMap();
					pp.putAll(param);
					pp.put("tid", tid);
					final Map m = tbOrderServices.taobaoTradeFullinfoGet(pp);
					if(m.get("Flag").equals("error"))
						continue;
					tradePool.execute(new Runnable() {
						
						@Override
						public void run() {
							tbOrderDataServices.updateTrade((Trade)m.get("trade"), storeId);
						}
					});
					orderPool.execute(new Runnable() {
						
						@Override
						public void run() {
							orderDataServices.updateOrder((Map)m.get("order"), storeId);
						}
					});
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			result.put("Message", "操作失败["+r.get("Message")+"]");
			result.put("Flag", "error");
		}
		return result;
	}

	@Override
	public Map dailyTaobaoRefundReport(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map dailyTaobaoSaleReport(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map downloadTbCat(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map downloadTbLogisticsCompany(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map monthTaobaoRefundReport(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map monthTaobaoSaleReport(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map syncTaobaoOrderToErp(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map syncTaobaoRefundToErp(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoCometDiscardInfo(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoDeliverySend(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoDeliveryTemplate(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoFenxiaoOrdersGet(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoItemCats(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoItemDownload(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoItemQuantityUpdate(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoProductsGet(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoRefundGet(Map params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoRefundsReceiveGet(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoSellercatsListGet(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoSkusPriceUpdate(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoTradeFullinfoGet(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoTradesSoldGet(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoTradesSoldIncrement(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoTradesSoldIncrementV(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

}
