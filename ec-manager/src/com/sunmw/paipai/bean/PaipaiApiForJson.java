package com.sunmw.paipai.bean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.paipai.api.util.ApiClient;
import com.paipai.api.util.ApiParameter;
import com.sunmw.paipai.entity.PpStore;
import com.sunmw.web.util.WebUtil;

public class PaipaiApiForJson extends HibernateDaoSupport {

	public Map getDealDetail(Map param)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "无店铺ID");
			return result;
		}
		List<PpStore> ppStoreList = this.getHibernateTemplate().find("from PpStore where id = ?",storeId);
		if(WebUtil.isNullForList(ppStoreList))
		{
			result.put("Flag", "error");
			result.put("Message", "找不到店铺");
			return result;
		}
		PpStore ps = ppStoreList.get(0);
		ApiParameter parameter = new ApiParameter();		
		parameter.addStringParam("sellerUin", ps.getUin());
		if(WebUtil.isNotNull(param.get("dealCode")))
		parameter.addStringParam("dealCode", param.get("dealCode").toString());
		if(WebUtil.isNotNull(param.get("listItem")))
		parameter.addStringParam("listItem", param.get("listItem").toString());
		String apiUrl = "http://api.paipai.com/deal/getDealDetail.xhtml";
		callApi(result, ps, parameter, apiUrl);
		return result;
	}
	
	public Map sellerConsignDealItem(Map param)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "无店铺ID");
			return result;
		}
		List<PpStore> ppStoreList = this.getHibernateTemplate().find("from PpStore where id = ?",storeId);
		if(WebUtil.isNullForList(ppStoreList))
		{
			result.put("Flag", "error");
			result.put("Message", "找不到店铺");
			return result;
		}
		PpStore ps = ppStoreList.get(0);
		ApiParameter parameter = new ApiParameter();		
		parameter.addStringParam("sellerUin", ps.getUin());
		if(WebUtil.isNotNull(param.get("dealCode")))
		parameter.addStringParam("dealCode", param.get("dealCode").toString());
		if(WebUtil.isNotNull(param.get("logisticsName")))
		parameter.addStringParam("logisticsName", param.get("logisticsName").toString());
		if(WebUtil.isNotNull(param.get("logisticsDesc")))
		parameter.addStringParam("logisticsDesc", param.get("logisticsDesc").toString());
		if(WebUtil.isNotNull(param.get("logisticsCode")))
		parameter.addStringParam("logisticsCode", param.get("logisticsCode").toString());
		if(WebUtil.isNotNull(param.get("arriveDays")))
		parameter.addStringParam("arriveDays", param.get("arriveDays").toString());
		else
			parameter.addStringParam("arriveDays", "3");
		String apiUrl = "http://api.paipai.com/deal/sellerConsignDealItem.xhtml";
		callApi(result, ps, parameter, apiUrl);
		return result;
	}
	
	public Map sellerSearchDealList(Map param)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "无店铺ID");
			return result;
		}
		List<PpStore> ppStoreList = this.getHibernateTemplate().find("from PpStore where id = ?",storeId);
		if(WebUtil.isNullForList(ppStoreList))
		{
			result.put("Flag", "error");
			result.put("Message", "找不到店铺");
			return result;
		}
		try {
			PpStore ps = ppStoreList.get(0);
			ApiParameter parameter = new ApiParameter();		
			parameter.addStringParam("sellerUin", ps.getUin());
			if(WebUtil.isNotNull(param.get("historyDeal")))
			parameter.addStringParam("historyDeal", param.get("historyDeal").toString());
			if(WebUtil.isNotNull(param.get("timeType")))
			parameter.addStringParam("timeType", param.get("timeType").toString());
			if(WebUtil.isNotNull(param.get("timeBegin")))
			parameter.addStringParam("timeBegin", param.get("timeBegin").toString());
			if(WebUtil.isNotNull(param.get("timeEnd")))
			parameter.addStringParam("timeEnd", param.get("timeEnd").toString());
			if(WebUtil.isNotNull(param.get("dealState")))
			parameter.addStringParam("dealState", param.get("dealState").toString());
			if(WebUtil.isNotNull(param.get("itemCode")))
			parameter.addStringParam("itemCode", param.get("itemCode").toString());
			if(WebUtil.isNotNull(param.get("itemNameKey")))
			parameter.addStringParam("itemNameKey", param.get("itemNameKey").toString());
			if(WebUtil.isNotNull(param.get("listItem")))
			parameter.addStringParam("listItem", param.get("listItem").toString());
			if(WebUtil.isNotNull(param.get("dealCode")))
			parameter.addStringParam("dealCode", param.get("dealCode").toString());
			if(WebUtil.isNotNull(param.get("orderDesc")))
			parameter.addStringParam("orderDesc", param.get("orderDesc").toString());
			if(WebUtil.isNotNull(param.get("pageIndex")))
			parameter.addStringParam("pageIndex", param.get("pageIndex").toString());
			if(WebUtil.isNotNull(param.get("pageSize")))
			parameter.addStringParam("pageSize", param.get("pageSize").toString());
			if(WebUtil.isNotNull(param.get("dealNoteType")))
			parameter.addStringParam("dealNoteType", param.get("dealNoteType").toString());
			if(WebUtil.isNotNull(param.get("dealRateState")))
			parameter.addStringParam("dealRateState", param.get("dealRateState").toString());
			String apiUrl = "http://api.paipai.com/deal/sellerSearchDealList.xhtml";
			callApi(result, ps, parameter, apiUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public Map sellerSignDealPreparing(Map param)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "无店铺ID");
			return result;
		}
		List<PpStore> ppStoreList = this.getHibernateTemplate().find("from PpStore where id = ?",storeId);
		if(WebUtil.isNullForList(ppStoreList))
		{
			result.put("Flag", "error");
			result.put("Message", "找不到店铺");
			return result;
		}
		PpStore ps = ppStoreList.get(0);
		ApiParameter parameter = new ApiParameter();		
		parameter.addStringParam("sellerUin", ps.getUin());
		if(WebUtil.isNotNull(param.get("dealCode")))
		parameter.addStringParam("dealCode", param.get("dealCode").toString());
		String apiUrl = "http://api.paipai.com/deal/sellerSignDealPreparing.xhtml";
		callApi(result, ps, parameter, apiUrl);
		return result;
	}
	
	public Map getDealRefundDetailInfo(Map param)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "无店铺ID");
			return result;
		}
		List<PpStore> ppStoreList = this.getHibernateTemplate().find("from PpStore where id = ?",storeId);
		if(WebUtil.isNullForList(ppStoreList))
		{
			result.put("Flag", "error");
			result.put("Message", "找不到店铺");
			return result;
		}
		PpStore ps = ppStoreList.get(0);
		ApiParameter parameter = new ApiParameter();		
		parameter.addStringParam("userUin", ps.getUin());
		if(WebUtil.isNotNull(param.get("dealCode")))
		parameter.addStringParam("dealCode", param.get("dealCode").toString());
		if(WebUtil.isNotNull(param.get("userRole")))
		parameter.addStringParam("userRole", param.get("userRole").toString());
		String apiUrl = "http://api.paipai.com/deal/getDealRefundDetailInfo.xhtml";
		callApi(result, ps, parameter, apiUrl);
		return result;
	}
	
	public Map getDealRefundInfoList(Map param)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "无店铺ID");
			return result;
		}
		List<PpStore> ppStoreList = this.getHibernateTemplate().find("from PpStore where id = ?",storeId);
		if(WebUtil.isNullForList(ppStoreList))
		{
			result.put("Flag", "error");
			result.put("Message", "找不到店铺");
			return result;
		}
		PpStore ps = ppStoreList.get(0);
		ApiParameter parameter = new ApiParameter();		
		parameter.addStringParam("userUin", ps.getUin());
		if(WebUtil.isNotNull(param.get("pageIndex")))
		parameter.addStringParam("pageIndex", param.get("pageIndex").toString());
		if(WebUtil.isNotNull(param.get("pageSize")))
		parameter.addStringParam("pageSize", param.get("pageSize").toString());
		if(WebUtil.isNotNull(param.get("userRole")))
		parameter.addStringParam("userRole", param.get("userRole").toString());
		if(WebUtil.isNotNull(param.get("timeType")))
		parameter.addStringParam("timeType", param.get("timeType").toString());
		if(WebUtil.isNotNull(param.get("timeBegin")))
		parameter.addStringParam("timeBegin", param.get("timeBegin").toString());
		if(WebUtil.isNotNull(param.get("timeEnd")))
		parameter.addStringParam("timeEnd", param.get("timeEnd").toString());
		String apiUrl = "http://api.paipai.com/deal/getDealRefundInfoList.xhtml";
		callApi(result, ps, parameter, apiUrl);
		return result;
	}

	private void callApi(Map result, PpStore ps, ApiParameter parameter,
			String apiUrl) {
		ApiClient apiClient=new ApiClient(ps.getSpid(),ps.getSecretKey(),new Long(ps.getUin()),ps.getToken());
		apiClient.setCharset("utf-8");
		apiClient.setFormat("json");
		apiClient.setPureData(Byte.parseByte("1"));
		try {
			boolean api=apiClient.invokeApi(apiUrl,parameter );
			if(api)
			{
				String str = new String(apiClient.getLastResponseContent(),"UTF-8");
				if(WebUtil.isNull(str))
				{
					result.put("Flag", "error");
					result.put("Message", "无返回内容");
				}
				JSONObject jo = JSONObject.fromObject(str);
				if(jo.getInt("errorCode")!=0)
				{
					result.put("Flag", "error");
					result.put("Message", "errorCode:"+jo.get("errorCode")+",errorMessage:"+jo.get("errorMessage"));
				}
				else
				{
					result.put("Flag", "success");
					result.putAll(jo);
				}
			}
			else
			{
				result.put("Flag", "error");
				result.put("Message", apiClient.getLastErrMsg());
			}
		} catch (UnsupportedEncodingException e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
		} catch (IOException e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
		}
	}

}
