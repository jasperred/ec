package com.sunmw.taobao.bean.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.TaoBaoApiParams;
import com.sunmw.taobao.bean.base.TbBaseServices;
import com.sunmw.taobao.entity.TbCat;
import com.sunmw.taobao.entity.TbCatProp;
import com.sunmw.taobao.entity.TbCatPropValue;
import com.sunmw.taobao.entity.TbLogisticsCompany;
import com.sunmw.taobao.entity.TbStore;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.ItemCat;
import com.taobao.api.domain.ItemProp;
import com.taobao.api.domain.LogisticsCompany;
import com.taobao.api.domain.PropValue;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.request.ItempropsGetRequest;
import com.taobao.api.request.ItempropvaluesGetRequest;
import com.taobao.api.request.LogisticsCompaniesGetRequest;
import com.taobao.api.response.ItemcatsGetResponse;
import com.taobao.api.response.ItempropsGetResponse;
import com.taobao.api.response.LogisticsCompaniesGetResponse;

public class TbBaseServicesImpl extends HibernateDaoSupport implements
		TbBaseServices {
	
	public Map downloadTbLogisticsCompany(Map params)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【淘宝物流公司下载】准备执行,店铺：" + storeId);
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
		logger.info("准备调用淘宝client");
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		LogisticsCompaniesGetRequest lcgr = new LogisticsCompaniesGetRequest();
		logger.info("准备调用淘宝API[" + lcgr.getApiMethodName() + "],设置参数");
		lcgr.setFields(TaoBaoApiParams.LogisticsCompaniesGetFields);
		//lcgr.setIsRecommended(false);
		//lcgr.setOrderMode("");
		logger.info("参数设置完成,准备执行");
		try {
			LogisticsCompaniesGetResponse r = client.execute(lcgr,tbStore.getSessionKey());
			if(r.isSuccess())
			{
				List<LogisticsCompany> l = r.getLogisticsCompanies();
				if(WebUtil.isNullForList(l))
				{
					return result;
				}
				for(LogisticsCompany lc:l)
				{
					TbLogisticsCompany tlc = new TbLogisticsCompany();
					tlc.setId(lc.getId().toString());
					tlc.setCode(lc.getCode());
					tlc.setName(lc.getName());
					this.getHibernateTemplate().merge(tlc);
				}
				logger.info("执行成功，总记录：" + l.size());
				
			}
			else
			{
				logger.error("执行失败,[" + r.getErrorCode() + ","
						+ r.getMsg() + "]");
			}
		} catch (ApiException e) {
			logger.error(e.getMessage());
		}
		logger.info("执行成功" );
		result.put("Flag", "success");
		return result;
		
	}
	
	public Map downloadTbCat(Map params)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【淘宝类目下载】准备执行,店铺：" + storeId);
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
		logger.info("准备调用淘宝client");
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		Session session = this.getSession();
		Integer catId = (Integer) params.get("CatId");
		if(catId==null)
			catId = 0;
		try {
			tbCat(catId,tbStore,client,session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		logger.info("执行成功");
		result.put("Flag", "success");
		return result;
	}
	
	private void tbCat(long parentCid,TbStore tbStore,TaobaoClient client,Session session)
	{
		ItemcatsGetRequest igr = new ItemcatsGetRequest();
		igr.setParentCid(parentCid);
		igr.setFields("cid,parent_cid,name,is_parent,status,sort_order");
		try {
			ItemcatsGetResponse r = client.execute(igr,tbStore.getSessionKey());
			if(r.isSuccess())
			{
				List<ItemCat> l = r.getItemCats();
				for(ItemCat lc:l)
				{
					Transaction tx = session.beginTransaction();
					//tbcat
					TbCat tbcat = null;
					List<TbCat> tcl = this.getHibernateTemplate().find("from TbCat where Cid = ?",lc.getCid());
					if(WebUtil.isNullForList(tcl))
						tbcat = new TbCat();
					else
						tbcat = tcl.get(0);
					tbcat.setCid(lc.getCid());
					tbcat.setIsParent(lc.getIsParent().toString());
					tbcat.setName(lc.getName());
					tbcat.setParentCid(lc.getParentCid());
					tbcat.setSortOrder(lc.getSortOrder());
					tbcat.setStatus(lc.getStatus());
					if(tbcat.getId()==null)
						session.save(tbcat);
					else
						session.update(tbcat);
					//tbcatprop
					ItempropsGetRequest ipgr = new ItempropsGetRequest();
					ipgr.setCid(lc.getCid());
					ipgr.setFields("is_input_prop,pid,parent_pid,parent_vid,name,is_key_prop,is_sale_prop,is_color_prop,is_enum_prop,is_item_prop,must,multi,prop_values,status,sort_order,child_template,is_allow_alia, prop_values");
					ItempropsGetResponse ipg = client.execute(ipgr);
					if(ipg.isSuccess())
					{
						List<ItemProp> ipList = ipg.getItemProps();
						if(!WebUtil.isNullForList(ipList))
						{
							for(ItemProp ip:ipList)
							{
								List<TbCatProp> tcpl = session.createQuery("from TbCatProp where Pid = "+ip.getPid()).list();
								TbCatProp tcp = null;
								if(WebUtil.isNullForList(tcpl))
									tcp = new TbCatProp();
								else
									tcp = tcpl.get(0);
								tcp.setChildTemplate(ip.getChildTemplate());
								if(ip.getIsAllowAlias()!=null)
								tcp.setIsAllowAlias(ip.getIsAllowAlias().toString());
								if(ip.getIsColorProp()!=null)
								tcp.setIsColorProp(ip.getIsColorProp().toString());
								if(ip.getIsEnumProp()!=null)
								tcp.setIsEnumProp(ip.getIsEnumProp().toString());
								if(ip.getIsInputProp()!=null)
								tcp.setIsInputProp(ip.getIsInputProp().toString());
								if(ip.getIsItemProp()!=null)
								tcp.setIsItemProp(ip.getIsItemProp().toString());
								if(ip.getIsKeyProp()!=null)
								tcp.setIsKeyProp(ip.getIsKeyProp().toString());
								if(ip.getIsSaleProp()!=null)
								tcp.setIsSaleProp(ip.getIsSaleProp().toString());
								tcp.setMulti(ip.getMulti().toString());
								tcp.setMust(ip.getMust().toString());
								tcp.setName(ip.getName());
								tcp.setParentPid(ip.getParentPid());
								tcp.setParentVid(ip.getParentVid());
								tcp.setPid(ip.getPid());
								if(tcp.getId()==null)
									session.save(tcp);
								else
									session.update(tcp);
								List<PropValue> pvList = ip.getPropValues();
								if(!WebUtil.isNullForList(pvList))
								{
									for(PropValue pv:pvList)
									{
										List<TbCatPropValue> tcpvl = session.createQuery("from TbCatPropValue where Vid = "+pv.getVid()+" and Cid = "+pv.getCid()+" and Pid = "+pv.getPid()).list();
										TbCatPropValue tcpv = null;
										if(WebUtil.isNullForList(tcpvl))
											tcpv = new TbCatPropValue();
										else
											tcpv = tcpvl.get(0);
										tcpv.setCid(lc.getCid());
										if(pv.getIsParent()!=null)
										tcpv.setIsParent(pv.getIsParent().toString());
										tcpv.setName(pv.getName());
										tcpv.setNameAlias(pv.getNameAlias());
										tcpv.setPid(ip.getPid());
										tcpv.setPropName(pv.getPropName());
										tcpv.setSortOrder(pv.getSortOrder());
										tcpv.setStatus(pv.getStatus());
										tcpv.setVid(pv.getVid());
										if(tcpv.getId()==null)
											session.save(tcpv);
										else
											session.update(tcpv);
									}
								}
							}
						}
					}
					//tbcatpropvalue
					//ItempropvaluesGetRequest ipvgr = new ItempropvaluesGetRequest();
					tx.commit();
					if(lc.getIsParent())
						tbCat(lc.getCid(),tbStore,client,session);
				}
				
			}
			else
			{
				
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
