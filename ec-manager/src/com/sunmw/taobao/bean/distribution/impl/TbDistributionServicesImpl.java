package com.sunmw.taobao.bean.distribution.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.distribution.TbDistributionServices;
import com.sunmw.taobao.entity.TbStore;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.entity.TbPurchaseOrder;
import com.sunmw.web.entity.TbSubPurchaseOrder;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.PurchaseOrder;
import com.taobao.api.domain.SubPurchaseOrder;
import com.taobao.api.request.FenxiaoOrdersGetRequest;
import com.taobao.api.response.FenxiaoOrdersGetResponse;

public class TbDistributionServicesImpl extends HibernateDaoSupport implements TbDistributionServices {
	
	static Logger logger = Logger.getLogger(TbDistributionServicesImpl.class);

	@Override
	public Map taobaoFenxiaoOrdersGet(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【分销下载】准备执行,店铺：" + storeId);
		// 店铺查询
		List<TbStore> storeList = this.getHibernateTemplate().find(
				"from TbStore where id = " + storeId);
		// 未登记此店铺
		if (storeList == null || storeList.size() == 0) {
			logger.error("未登记此店铺[" + storeId + "]");
			result.put("Flag", "error");
			return result;
		}
		TbStore tbStore = storeList.get(0);
		// 此店铺状态为不可使用
		if (tbStore.getStatus() == null
				|| !tbStore.getStatus().equals("ACTIVE")) {
			logger.error("此店铺状态为不可使用[" + storeId + "]");
			result.put("Flag", "error");
			return result;
		}
		result.put("Flag", "success");
		//logger.info("准备调用淘宝client");
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		FenxiaoOrdersGetRequest fo = new FenxiaoOrdersGetRequest();
		fo.setStatus((String)param.get("status"));
		Date startCreated = null;
		Date endCreated = null;
		if(WebUtil.isNotNull(param.get("startCreated")))
		{
			startCreated = (Date)param.get("startCreated");
			
			if(WebUtil.isNotNull(param.get("endCreated")))
				endCreated = (Date)param.get("endCreated");
			else
				endCreated = new Date();
			fo.setStartCreated(startCreated);
			fo.setEndCreated(endCreated);
		}
		else if(WebUtil.isNotNull(param.get("purchaseOrderId")))
			fo.setPurchaseOrderId(new Long(param.get("purchaseOrderId").toString()));
		fo.setTimeType((String)param.get("timeType"));
		long pageNo = 1;
		fo.setPageNo(pageNo);
		if(WebUtil.isNull(param.get("pageSize")))
			fo.setPageSize(new Long(50));
		else
			fo.setPageSize(new Long(param.get("pageSize").toString()));
		long row = 0;
		while(true)
		{
			try {
				FenxiaoOrdersGetResponse fr = client.execute(fo, tbStore.getSessionKey());
				if(fr.isSuccess())
				{
					List<PurchaseOrder> poList = fr.getPurchaseOrders();
					if(!WebUtil.isNullForList(poList))
					{
						for(PurchaseOrder po:poList)
						{
							savePurchaseOrder(po,tbStore.getId());
						}
					}
					row = fo.getPageSize()*fo.getPageNo();
					if(fr.getTotalResults()>row)
					{
						pageNo++;
						fo.setPageNo(pageNo);
					}
					else
						break;
				}
				else
				{
					logger.error("【分销下载】执行失败,[" + fr.getErrorCode() + ","
							+ fr.getMsg() + "]");
					break;
				}
				
				
			} catch (ApiException e) {
				logger.error("【分销下载】异常,[" + e.getErrCode() + "," + e.getErrMsg()
						+ "]");
				break;
			}
		}
		return result;
	}
	
	private synchronized void savePurchaseOrder(PurchaseOrder po,int storeId)
	{
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		OrderServices orderServices = (OrderServices) GetBeanServlet.getBean("orderServices");
		Map updateOrderResult = orderServices.updateSingleOrderFromTb(formatPurchaseOrderToMap(po), storeId);
		List<TbPurchaseOrder> tpoList = this.getHibernateTemplate().find("from TbPurchaseOrder where FenxiaoId = ? and StoreId = ?",new Object[]{po.getFenxiaoId(),storeId});
		TbPurchaseOrder tpo = null;
		if(WebUtil.isNullForList(tpoList))
		{
			tpo = new TbPurchaseOrder();
			tpo.setStoreId(storeId);
		}
		else
		{
			tpo = tpoList.get(0);
		}
		tpo.setAlipayNo(po.getAlipayNo());
		tpo.setBuyerNick(po.getBuyerNick());
		tpo.setConsignTime(po.getConsignTime());
		tpo.setCreated(po.getCreated());
		tpo.setDistributorFrom(po.getDistributorFrom());
		if(WebUtil.isNotNull(po.getDistributorPayment()))
			tpo.setDistributorPayment(new BigDecimal(po.getDistributorPayment()));
		else
			tpo.setDistributorPayment(new BigDecimal(0));
		tpo.setDistributorUsername(po.getDistributorUsername());
		tpo.setFenxiaoId(po.getFenxiaoId());
		tpo.setLogisticsCompanyName(po.getLogisticsCompanyName());
		tpo.setLogisticsId(po.getLogisticsId());
		tpo.setMemo(po.getMemo());
		tpo.setModified(po.getModified());
		tpo.setPayTime(po.getPayTime());
		tpo.setPayType(po.getPayType());
		tpo.setPoId(po.getId());
		if(WebUtil.isNotNull(po.getPostFee()))
			tpo.setPostFee(new BigDecimal(po.getPostFee()));
		else
			tpo.setPostFee(new BigDecimal(0));
		if(po.getReceiver()!=null)
		{
			tpo.setReceiverAddress(po.getReceiver().getAddress());
			tpo.setReceiverCity(po.getReceiver().getCity());
			tpo.setReceiverDistrict(po.getReceiver().getDistrict());
			tpo.setReceiverMobilePhone(po.getReceiver().getMobilePhone());
			tpo.setReceiverName(po.getReceiver().getName());
			tpo.setReceiverPhone(po.getReceiver().getPhone());
			tpo.setReceiverState(po.getReceiver().getState());
			tpo.setReceiverZip(po.getReceiver().getZip());
		}
		tpo.setShipping(po.getShipping());
		tpo.setSnapshotUrl(po.getSnapshotUrl());
		tpo.setStatus(po.getStatus());
		tpo.setSupplierFrom(po.getSupplierFrom());
		tpo.setSupplierMemo(po.getSupplierMemo());
		tpo.setSupplierUsername(po.getSupplierUsername());
		tpo.setTcOrderId(po.getTcOrderId());
		if(WebUtil.isNotNull(po.getTotalFee()))
			tpo.setTotalFee(new BigDecimal(po.getTotalFee()));
		else
			tpo.setTotalFee(new BigDecimal(0));
		tpo.setTradeType(po.getTradeType());
		tpo.setUpdateTime(new Date());
		//如果订单成功更新，UpdateFlag标记为E
		if(updateOrderResult.get("Flag").equals("ERROR"))
			tpo.setUpdateFlag("D");
		else
			tpo.setUpdateFlag("E");
		if (tpo.getId() == null)
			session.save(tpo);
		else
			session.update(tpo);
		List<SubPurchaseOrder> spoList = po.getSubPurchaseOrders();
		if(!WebUtil.isNullForList(spoList))
		{
			for(SubPurchaseOrder spo:spoList)
			{
				List<TbSubPurchaseOrder> tspoList = this.getHibernateTemplate().find("from TbSubPurchaseOrder where SpoId = ? and FenxiaoId = ?",new Object[]{spo.getId(),po.getFenxiaoId()});
				TbSubPurchaseOrder tspo = null;
				if(WebUtil.isNullForList(tspoList))
				{
					tspo = new TbSubPurchaseOrder();
				}
				else
				{
					tspo = tspoList.get(0);
				}
				if(WebUtil.isNotNull(spo.getBuyerPayment()))
					tspo.setBuyerPayment(new BigDecimal(spo.getBuyerPayment()));
				else
					tspo.setBuyerPayment(new BigDecimal(0));
				tspo.setCreated(spo.getCreated());
				if(WebUtil.isNotNull(spo.getDistributorPayment()))
					tspo.setDistributorPayment(new BigDecimal(spo.getDistributorPayment()));
				else
					tspo.setDistributorPayment(new BigDecimal(0));
				tspo.setFenxiaoId(spo.getFenxiaoId());
				tspo.setItemId(spo.getItemId());
				tspo.setItemOuterId(spo.getItemOuterId());
				tspo.setNum(spo.getNum().intValue());
				tspo.setOldSkuProperties(spo.getOldSkuProperties());
				//tspo.setOrder200Status(spo);
				if(WebUtil.isNotNull(spo.getPrice()))
					tspo.setPrice(new BigDecimal(spo.getPrice()));
				else
					tspo.setPrice(new BigDecimal(0));
				if(WebUtil.isNotNull(spo.getRefundFee()))
					tspo.setRefundFee(new BigDecimal(spo.getRefundFee()));
				else
					tspo.setRefundFee(new BigDecimal(0));
				tspo.setSkuId(spo.getSkuId());
				tspo.setSkuOuterId(spo.getSkuOuterId());
				tspo.setSkuProperties(spo.getSkuProperties());
				tspo.setSnapshotUrl(spo.getSnapshotUrl());
				tspo.setSpoId(spo.getId());
				tspo.setStatus(spo.getStatus());
				tspo.setStoreId(storeId);
				tspo.setTcOrderId(spo.getTcOrderId());
				tspo.setTitle(spo.getTitle());
				tspo.setUpdateTime(new Date());
				//如果订单成功更新，UpdateFlag标记为E
				if(updateOrderResult.get("Flag").equals("ERROR"))
					tspo.setUpdateFlag("D");
				else
					tspo.setUpdateFlag("E");
				if (tspo.getId() == null)
					session.save(tspo);
				else
					session.update(tspo);
			}
		}
		tx.commit();
		session.close();
	}
	
	private Map formatPurchaseOrderToMap(PurchaseOrder po)
	{
		Map r = new HashMap();
		r.put("Tid", po.getFenxiaoId().toString());
		r.put("BuyerMessage", po.getMemo());
		r.put("Status", po.getStatus());
		r.put("OrigOrderType", "TAOBAO_FX");
		r.put("BuyerNick", po.getBuyerNick());
		r.put("SellerNick", po.getSupplierUsername());
		r.put("ConsignTime", po.getConsignTime());
		r.put("PayTime", po.getPayTime());
		r.put("Created", po.getCreated());
		r.put("AlipayNo", po.getAlipayNo());
		if(po.getReceiver()!=null)
		{
			r.put("ReceiverAddress", po.getReceiver().getAddress());
			r.put("ReceiverCity", po.getReceiver().getCity());
			r.put("ReceiverDistrict", po.getReceiver().getDistrict());
			r.put("ReceiverMobile", po.getReceiver().getMobilePhone());
			r.put("ReceiverName", po.getReceiver().getName());
			r.put("ReceiverState", po.getReceiver().getState());
			r.put("ReceiverPhone", po.getReceiver().getPhone());
			r.put("ReceiverZip", po.getReceiver().getZip());
		}		
		//r.put("BuyerEmail", po.getBuyerEmail());
		//r.put("InvoiceName", po.getInvoiceName());
		r.put("TotalFee", new BigDecimal(po.getTotalFee()!=null?po.getTotalFee():"0"));
		r.put("Payment", new BigDecimal(po.getDistributorPayment()!=null?po.getDistributorPayment():"0"));
		r.put("PostFee", new BigDecimal(po.getPostFee()!=null?po.getPostFee():"0"));
		//r.put("DiscountFee", new BigDecimal(po.getDiscountFee()!=null?po.getDiscountFee():"0"));
		//r.put("PointFee", po.getPointFee()!=null?po.getPointFee().intValue():0);
		r.put("Created", po.getCreated());
		//r.put("BuyerMemo", po.getBuyerMemo());
		r.put("SellerMemo", po.getSupplierMemo());
		//r.put("TradeMemo", po.getTradeMemo());
		if(!WebUtil.isNullForList(po.getSubPurchaseOrders()))
		{
			List<Map> toList = new ArrayList();
			for(SubPurchaseOrder spo:po.getSubPurchaseOrders())
			{
				Map o = new HashMap();
				o.put("Oid", spo.getId().toString());
				o.put("OuterIid", spo.getItemId().toString());
				o.put("Title", spo.getTitle());
				o.put("SkuId", spo.getSkuId());
				o.put("Num", new BigDecimal(spo.getNum()!=null?spo.getNum():0));
				o.put("Price", new BigDecimal(spo.getPrice()!=null?spo.getPrice():"0"));
				//o.put("AdjustFee", new BigDecimal(spo.getAdjustFee()!=null?spo.getAdjustFee():"0"));
				//o.put("DiscountFee", new BigDecimal(spo.get!=null?spo.getDiscountFee():"0"));
				o.put("OuterSkuId", spo.getSkuOuterId());
				o.put("SkuPropertiesName", spo.getSkuProperties());
				o.put("TotalFee", new BigDecimal(spo.getTotalFee()!=null?spo.getTotalFee():"0"));
				toList.add(o);
			}
			r.put("Orders", toList);
		}
		return r;
	}

}
