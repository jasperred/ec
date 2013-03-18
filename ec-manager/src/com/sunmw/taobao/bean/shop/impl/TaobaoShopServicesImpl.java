package com.sunmw.taobao.bean.shop.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.shop.TaobaoShopServices;
import com.sunmw.taobao.entity.TbStore;
import com.sunmw.web.entity.TbArea;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.request.SellercatsListGetRequest;
import com.taobao.api.response.SellercatsListGetResponse;

public class TaobaoShopServicesImpl extends HibernateDaoSupport implements
		TaobaoShopServices {
	
	static Logger logger = Logger.getLogger(TaobaoShopServicesImpl.class);
	
	public Map taobaoSellercatsListGet(Map params)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if(storeId==null)
		{
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		//接口执行消息
		logger.info("【增量下载订单】准备执行,店铺："+storeId);
		//店铺查询
		List<TbStore> storeList = this.getHibernateTemplate().find("from TbStore where id = "+storeId);
		//未登记此店铺
		if(storeList==null||storeList.size()==0)
		{
			logger.error("未登记此店铺["+storeId+"]");
			result.put("Flag", "error");
			return result;
		}
		TbStore tbStore = storeList.get(0);
		//此店铺状态为不可使用
		if(tbStore.getStatus()==null||!tbStore.getStatus().equals("ACTIVE"))
		{
			logger.error("此店铺状态为不可使用["+storeId+"]");
			result.put("Flag", "error");
			return result;
		}
		logger.info("准备调用淘宝client");
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),tbStore.getAppKey(),tbStore.getAppSercet());
		SellercatsListGetRequest sl = new SellercatsListGetRequest();
		if(WebUtil.isNotNull(params.get("Nick")))
		sl.setNick((String)params.get("Nick"));
		else
			sl.setNick(tbStore.getStoreNick());
		try {
			SellercatsListGetResponse tr = client.execute(sl,tbStore.getSessionKey());
			if(tr.isSuccess())
			{
				List<SellerCat> l = tr.getSellerCats();

				logger.info("执行成功，总记录：" + l.size());
			}
			else
			{
				logger.error("淘宝API["+sl.getApiMethodName()+"]执行失败,["+tr.getErrorCode()+","+tr.getMsg()+"]");
			}
		} catch (ApiException e) {
			logger.error(e.getMessage());
		}
		result.put("Flag", "success");
		return result;
	}
	
	public Map tbStoreInfo(Map param)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "StoreId is null");
			return result;
		}
		result.put("Flag", "success");
		List<TbStore> tsl = this.getHibernateTemplate().find("from TbStore where id = ?",storeId);
		if(!WebUtil.isNullForList(tsl))
		{
			result.put("StoreInfo", tsl.get(0));
		}
		return result;
	}
	
	public Map saveTbStore(Map param)
	{
		Map result = new HashMap();
		try {
			TbStore ts = new TbStore();
			ts.setId((Integer)param.get("StoreId"));
			ts.setAddress((String)param.get("Address"));
			ts.setAppKey((String)param.get("AppKey"));
			ts.setAppSercet((String)param.get("AppSercet"));
			ts.setAreaId((Long)param.get("AreaId"));
			ts.setMemo((String)param.get("Memo"));
			ts.setMobile((String)param.get("Mobile"));
			ts.setPhone((String)param.get("Phone"));
			ts.setSandboxUrl((String)param.get("SandboxUrl"));
			ts.setSessionKey((String)param.get("SessionKey"));
			ts.setSessionUrl((String)param.get("SessionUrl"));
			ts.setStatus((String)param.get("Status"));
			ts.setStoreNick((String)param.get("StoreNick"));
			ts.setStoreType((String)param.get("StoreType"));
			ts.setZip((String)param.get("Zip"));
			ts.setUseNotify((String)param.get("UseNotify"));
			this.getHibernateTemplate().merge(ts);
			result.put("Flag", "success");
		} catch (DataAccessException e) {
			result.put("Flag", "error");
			result.put("Flag", e.getMessage());
		}
		return result;
	}
	
	public Map deleteTbStore(Map param)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "StoreId is null");
			return result;
		}
		try {
			this.getHibernateTemplate().bulkUpdate("delete TbStore where id = ?",storeId);
			result.put("Flag", "success");
		} catch (DataAccessException e) {
			result.put("Flag", "error");
			result.put("Flag", e.getMessage());
		}
		return result;
	}

	public List tbAreaList()
	{
		List<TbArea> taList = this.getHibernateTemplate().find("from TbArea order by id");
		return taList;
	}
}
