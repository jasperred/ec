package com.sunmw.taobao.bean.inventory.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.inventory.TBInventoryServices;
import com.sunmw.taobao.entity.TbSku;
import com.sunmw.taobao.entity.TbStore;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemQuantityUpdateRequest;
import com.taobao.api.response.ItemQuantityUpdateResponse;

public class TBInventoryServicesImpl extends HibernateDaoSupport implements
		TBInventoryServices {

	static Logger logger = Logger.getLogger(TBInventoryServicesImpl.class);
	public void taobaoItemQuantityUpdate(int storeId, Map params) {
		TaobaoClient client = (TaobaoClient) params.get("taobaoClient");
		if (client == null) {
			// 店铺查询
			List<TbStore> storeList = this.getHibernateTemplate().find(
					"from TbStore where id = " + storeId);
			// 未登记此店铺
			if (storeList == null || storeList.size() == 0) {
				logger.error("未登记此店铺[" + storeId + "]");
				return;
			}
			TbStore tbStore = storeList.get(0);
			// 此店铺状态为不可使用
			if (tbStore.getStatus() == null
					|| !tbStore.getStatus().equals("ACTIVE")) {
				logger.error("此店铺状态为不可使用[" + storeId + "]");
				return;
			}
			client = new DefaultTaobaoClient(tbStore.getSandboxUrl(), tbStore
					.getAppKey(), tbStore.getAppSercet());
		}
		ItemQuantityUpdateRequest ir = new ItemQuantityUpdateRequest();
		ir.setNumIid((Long) params.get("numIid"));
		ir.setOuterId((String) params.get("outerId"));
		ir.setQuantity((Long) params.get("quantity"));
		ir.setSkuId((Long) params.get("skuId"));
		ir.setTimestamp((Long) params.get("timestamp"));
		ir.setType((Long) params.get("type"));
		try {
			ItemQuantityUpdateResponse iqu = client.execute(ir);
			if (iqu.isSuccess()) {
				logger.info("商品[" + params.get("outerId") + "]更新成功,库存数量["
						+ params.get("quantity") + "]");
			} else {
				logger.error(iqu.getSubCode() + ":" + iqu.getSubMsg());
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateTaobaoInventory(int storeId, List<Map> invList,String invUpdateFlag) {
		// 店铺查询
		List<TbStore> storeList = this.getHibernateTemplate().find(
				"from TbStore where id = " + storeId);
		// 未登记此店铺
		if (storeList == null || storeList.size() == 0) {
			logger.error("未登记此店铺[" + storeId + "]");
			return;
		}
		TbStore tbStore = storeList.get(0);
		// 此店铺状态为不可使用
		if (tbStore.getStatus() == null || !tbStore.getStatus().equals("ACTIVE")) {
			logger.error("此店铺状态为不可使用[" + storeId + "]");
			return;
		}
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());

		StringBuffer skuStr = new StringBuffer();
		// 找出有库存的商品
		Map invMap = new HashMap();
		for (Map inv : invList) {
			if (skuStr.length() > 0)
				skuStr.append(",");
			skuStr.append("'" + inv.get("SkuCd") + "'");
			invMap.put(inv.get("SkuCd"), inv.get("Qty").toString());
		}
		// 匹配淘宝商品信息是否存在
		List<TbSku> tbSkuList = this.getHibernateTemplate().find(
				"from TbSku " 
				//+"where OuterId in (" + skuStr.toString() + ")"
				);
		Map<String, TbSku> tbSkuMap = new HashMap();
		if (!WebUtil.isNullForList(tbSkuList)) {
			for (TbSku tbsku : tbSkuList) {
				tbSkuMap.put(tbsku.getOuterId(), tbsku);
			}
		}

		//根据ERP库存来更新淘宝库存,ERP库存中不存在的SKU不更新淘宝库存
		if(WebUtil.isNull(invUpdateFlag)||invUpdateFlag.equals("ADD"))
		{
			// 更新淘宝库存
			for (Map inv : invList) {
				TbSku sku = tbSkuMap.get(inv.get("SkuCd"));
				// 无淘宝商品不更新库存
				if (sku == null)
				{
					continue;
				}
				taobaoItemQuantityUpdate(storeId, WebUtil.toMap("taobaoClient",
						client, "numIid", sku.getNumIid().longValue(), "skuId", sku
								.getSkuId().longValue(), "outerId", sku
								.getOuterId(), "quantity", new Long(inv
								.get("Qty").toString())));
			}
		}
		//根据淘宝SKU来更新库存,ERP库存中没有的SKU数量更新为0
		else
		{
			for (TbSku sku : tbSkuList) {
				long inv = invMap.get(sku.getOuterId())==null?0:new Long(invMap.get(sku.getOuterId()).toString());
				
				taobaoItemQuantityUpdate(storeId, WebUtil.toMap("taobaoClient",
						client, "numIid", sku.getNumIid().longValue(), "skuId", sku
								.getSkuId().longValue(), "outerId", sku
								.getOuterId(), "quantity", inv));
			}
		}
		
	}
	
	public void updateTaobaoInventoryForTBItem(int storeId, List<Map> invList) {
		// 店铺查询
		List<TbStore> storeList = this.getHibernateTemplate().find(
				"from TbStore where id = " + storeId);
		// 未登记此店铺
		if (storeList == null || storeList.size() == 0) {
			logger.error("未登记此店铺[" + storeId + "]");
			return;
		}
		TbStore tbStore = storeList.get(0);
		// 此店铺状态为不可使用
		if (tbStore.getStatus() == null || !tbStore.getStatus().equals("ACTIVE")) {
			logger.error("此店铺状态为不可使用[" + storeId + "]");
			return;
		}
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());

		//StringBuffer skuStr = new StringBuffer();
		// 找出有库存的商品
		Map skuInvMap = new HashMap();
		for (Map inv : invList) {
			skuInvMap.put(inv.get("SkuCd"), inv.get("Qty"));
		}
		// 匹配淘宝商品信息是否存在
		List<TbSku> tbSkuList = this.getHibernateTemplate().find(
				"from TbSku");

		// 更新淘宝库存
		for (TbSku sku : tbSkuList) {
			long inv = skuInvMap.get(sku.getOuterId())==null?0:new Long(skuInvMap
					.get(sku.getOuterId()).toString());
			taobaoItemQuantityUpdate(storeId, WebUtil.toMap("taobaoClient",
					client, "numIid", sku.getNumIid().longValue(), "skuId", sku
							.getSkuId().longValue(), "outerId", sku
							.getOuterId(), "quantity", inv));
		}
	}

}
