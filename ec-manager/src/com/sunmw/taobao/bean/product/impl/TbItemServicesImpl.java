package com.sunmw.taobao.bean.product.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.TaoBaoApiParams;
import com.sunmw.taobao.bean.product.TbItemServices;
import com.sunmw.taobao.entity.TbCatPropValue;
import com.sunmw.taobao.entity.TbItem;
import com.sunmw.taobao.entity.TbItemImg;
import com.sunmw.taobao.entity.TbItemPropImg;
import com.sunmw.taobao.entity.TbSku;
import com.sunmw.taobao.entity.TbStore;
import com.sunmw.web.bean.product.ProductServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemImg;
import com.taobao.api.domain.PropImg;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemSkuPriceUpdateRequest;
import com.taobao.api.request.ItemSkuUpdateRequest;
import com.taobao.api.request.ItemSkusGetRequest;
import com.taobao.api.request.ItemsInventoryGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.request.SkusCustomGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemSkuPriceUpdateResponse;
import com.taobao.api.response.ItemSkuUpdateResponse;
import com.taobao.api.response.ItemSkusGetResponse;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.api.response.SkusCustomGetResponse;

public class TbItemServicesImpl extends HibernateDaoSupport implements
		TbItemServices {
	static Logger logger = Logger.getLogger(TbItemServicesImpl.class);

//	public Map taobaoItemsSearch(Map params) {
//		Map result = new HashMap();
//		Integer storeId = (Integer) params.get("StoreId");
//		if (storeId == null) {
//			logger.error("店铺ID是空");
//			result.put("Flag", "error");
//			return result;
//		}
//		// 接口执行消息
//		logger.info("【ITEM下载】准备执行,店铺：" + storeId);
//		// 店铺查询
//		List<TbStore> storeList = this.getHibernateTemplate().find(
//				"from TbStore where id = " + storeId);
//		// 未登记此店铺
//		if (storeList == null || storeList.size() == 0) {
//			logger.error("未登记此店铺[" + storeId + "]");
//			result.put("Flag", "error");
//			return result;
//		}
//		TbStore tbStore = storeList.get(0);
//		// 此店铺状态为不可使用
//		if (tbStore.getStatus() == null
//				|| !tbStore.getStatus().equals("ACTIVE")) {
//			logger.error("此店铺状态为不可使用[" + storeId + "]");
//			result.put("Flag", "error");
//			return result;
//		}
//		logger.info("准备调用淘宝client");
//		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
//				tbStore.getAppKey(), tbStore.getAppSercet());
//		ItemsSearchRequest isr = new ItemsSearchRequest();
//		isr.setCid((Long) params.get("cid"));
//		isr.setEndPrice((Long) params.get("endPrice"));
//		isr.setEndScore((Long) params.get("endScore"));
//		isr.setEndVolume((Long) params.get("endVolume"));
//		//isr.setFields((String)params.get("fields"));
//		isr.setFields(TaoBaoApiParams.ItemsSearchFields);
//		isr.setGenuineSecurity((Boolean) params.get("genuineSecurity"));
//		isr.setIs3D((Boolean) params.get("is3D"));
//		isr.setIsCod((Boolean) params.get("isCod"));
//		isr.setIsMall((Boolean) params.get("isMall"));
//		isr.setIsPrepay((Boolean) params.get("isPrepay"));
//		isr.setLocationCity((String) params.get("locationCity"));
//		isr.setLocationState((String) params.get("LocationState"));
//		//isr.setNicks((String)params.get("nicks"));
//		// 使用当前店铺昵称
//		isr.setNicks(tbStore.getStoreNick());
//		isr.setOneStation((Boolean) params.get("oneStation"));
//		isr.setPostFree((Boolean) params.get("postFee"));
//		isr.setProductId((Long) params.get("productId"));
//		isr.setPromotedService((String) params.get("promotedService"));
//		isr.setProps((String) params.get("props"));
//		isr.setQ((String) params.get("q"));
//		isr.setStartPrice((Long) params.get("startPrice"));
//		isr.setStartScore((Long) params.get("startScore"));
//		isr.setStartVolume((Long) params.get("startVolume"));
//		isr.setStuffStatus((String) params.get("stuffStatus"));
//		isr.setTimestamp((Long) params.get("timestamp"));
//		isr.setWwStatus((Boolean) params.get("wwStatus"));
//		isr.setAuctionFlag((Boolean) params.get("auctionFlag"));
//		isr.setAutoPost((Boolean) params.get("autoPost"));
//		isr.setHasDiscount((Boolean) params.get("hasDiscount"));
//		isr.setPageSize((Long) params.get("pageSize"));
//		// 页号
//		long pageNo = 1;
//		// 记录总结果数
//		int row = 0;
//		// 下载全部产品
//		while (true) {
//			try {
//				ItemsSearchResponse tr = client.execute(isr, tbStore
//						.getSessionKey());
//				// 接口执行成功
//				if (tr.isSuccess()) {
//					List<Item> items = tr.getItemSearch().getItems();
//					// 无数据退出
//					if (items == null || items.size() == 0)
//						break;
//					logger.info("淘宝API[" + isr.getApiMethodName()
//							+ "]执行成功,page[" + pageNo + "],总记录["
//							+ tr.getTotalResults() + "]");
//
//					row += items.size();
//					for (Item i : items) {
//						taobaoItemGet(WebUtil.toMap("taobaoClient", client,
//								"StoreId", storeId, "numIid", i.getNumIid(),
//								"fields", TaoBaoApiParams.ItemGetFields));
//					}
//					// 下一页
//					pageNo++;
//					isr.setPageNo(pageNo);
//				}
//				// 接口执行失败
//				else {
//					logger.error("淘宝API[" + isr.getApiMethodName() + "]执行失败,["
//							+ tr.getSubCode() + "," + tr.getSubMsg() + "]");
//					break;
//				}
//
//			} catch (ApiException e) {
//				logger.error("异常,[" + e.getErrCode() + "," + e.getErrMsg()
//						+ "]");
//				break;
//			}
//		}
//		logger.info("执行成功");
//		result.put("Flag", "success");
//		return result;
//	}

//	public Map taobaoItemsGet(Map params) {
//		Map result = new HashMap();
//		Integer storeId = (Integer) params.get("StoreId");
//		if (storeId == null) {
//			logger.error("店铺ID是空");
//			result.put("Flag", "error");
//			return result;
//		}
//		// 接口执行消息
//		logger.info("【ITEM下载】准备执行,店铺：" + storeId);
//		// 店铺查询
//		List<TbStore> storeList = this.getHibernateTemplate().find(
//				"from TbStore where id = " + storeId);
//		// 未登记此店铺
//		if (storeList == null || storeList.size() == 0) {
//			logger.error("未登记此店铺[" + storeId + "]");
//			result.put("Flag", "error");
//			return result;
//		}
//		TbStore tbStore = storeList.get(0);
//		// 此店铺状态为不可使用
//		if (tbStore.getStatus() == null
//				|| !tbStore.getStatus().equals("ACTIVE")) {
//			logger.error("此店铺状态为不可使用[" + storeId + "]");
//			result.put("Flag", "error");
//			return result;
//		}
//		logger.info("准备调用淘宝client");
//		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
//				tbStore.getAppKey(), tbStore.getAppSercet());
//		ItemsOnsaleGetRequest igr = new ItemsGetRequest();
//		igr.setCid((Long) params.get("cid"));
//		igr.setEndPrice((Long) params.get("endPrice"));
//		igr.setEndScore((Long) params.get("endScore"));
//		igr.setEndVolume((Long) params.get("endVolume"));
//		// igr.setFields((String)params.get("fields"));
//		igr.setFields(TaoBaoApiParams.ItemsGetFields);
//		igr.setGenuineSecurity((Boolean) params.get("genuineSecurity"));
//		igr.setIs3D((Boolean) params.get("is3D"));
//		igr.setIsCod((Boolean) params.get("isCod"));
//		igr.setIsMall((Boolean) params.get("isMall"));
//		igr.setIsPrepay((Boolean) params.get("isPrepay"));
//		igr.setLocationCity((String) params.get("locationCity"));
//		igr.setLocationState((String) params.get("LocationState"));
//		// igr.setNicks((String)params.get("nicks"));
//		// 使用当前店铺昵称
//		igr.setNicks(tbStore.getStoreNick());
//		igr.setOneStation((Boolean) params.get("oneStation"));
//		igr.setPostFree((Boolean) params.get("postFee"));
//		igr.setProductId((Long) params.get("productId"));
//		igr.setPromotedService((String) params.get("promotedService"));
//		igr.setProps((String) params.get("props"));
//		igr.setQ((String) params.get("q"));
//		igr.setStartPrice((Long) params.get("startPrice"));
//		igr.setStartScore((Long) params.get("startScore"));
//		igr.setStartVolume((Long) params.get("startVolume"));
//		igr.setStuffStatus((String) params.get("stuffStatus"));
//		igr.setTimestamp((Long) params.get("timestamp"));
//		igr.setWwStatus((Boolean) params.get("wwStatus"));
//		igr.setPageSize((Long) params.get("pageSize"));
//		// 页号
//		long pageNo = 1;
//		// 记录总结果数
//		int row = 0;
//		// 下载全部产品
//		while (true) {
//			try {
//				ItemsGetResponse tr = client.execute(igr, tbStore
//						.getSessionKey());
//				// 接口执行成功
//				if (tr.isSuccess()) {
//					List<Item> items = tr.getItems();
//					// 无数据退出
//					if (items == null || items.size() == 0)
//						break;
//					logger.info("淘宝API[" + igr.getApiMethodName()
//							+ "]执行成功,page[" + pageNo + "]");
//
//					row += items.size();
//					for (Item i : items) {
//						taobaoItemGet(WebUtil.toMap("taobaoClient", client,
//								"StoreId", storeId, "numIid", i.getNumIid(),
//								"fields", TaoBaoApiParams.ItemGetFields));
//					}
//					// 下一页
//					pageNo++;
//					igr.setPageNo(pageNo);
//				}
//				// 接口执行失败
//				else {
//					logger.error("淘宝API[" + igr.getApiMethodName() + "]执行失败,["
//							+ tr.getSubCode() + "," + tr.getSubMsg() + "]");
//					break;
//				}
//
//			} catch (ApiException e) {
//				logger.error("异常,[" + e.getErrCode() + "," + e.getErrMsg()
//						+ "]");
//				break;
//			}
//		}
//		logger.info("执行成功");
//		result.put("Flag", "success");
//		return result;
//	}
	
	public Map taobaoItemsOnsaleGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【ITEM下载】准备执行,店铺：" + storeId);
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
		ItemsOnsaleGetRequest igr = new ItemsOnsaleGetRequest();
		igr.setCid((Long) params.get("cid"));
		igr.setFields(TaoBaoApiParams.ItemsGetFields);
		igr.setQ((String) params.get("q"));
		igr.setSellerCids((String) params.get("sellerCids"));
		if(WebUtil.isNotNull(params.get("hasDiscount"))&&params.get("hasDiscount").equals("Y"))
			igr.setHasDiscount(true);
		if(WebUtil.isNotNull(params.get("hasDiscount"))&&params.get("hasDiscount").equals("N"))
			igr.setHasDiscount(false);
		if(WebUtil.isNotNull(params.get("hasShowcase"))&&params.get("hasShowcase").equals("Y"))
			igr.setHasShowcase(true);
		if(WebUtil.isNotNull(params.get("hasShowcase"))&&params.get("hasShowcase").equals("N"))
			igr.setHasShowcase(false);
		if(WebUtil.isNotNull(params.get("isEx"))&&params.get("isEx").equals("Y"))
			igr.setIsEx(true);
		if(WebUtil.isNotNull(params.get("isEx"))&&params.get("isEx").equals("N"))
			igr.setIsEx(false);
		if(WebUtil.isNotNull(params.get("isTaobao"))&&params.get("isTaobao").equals("Y"))
			igr.setIsTaobao(true);
		if(WebUtil.isNotNull(params.get("isTaobao"))&&params.get("isTaobao").equals("N"))
			igr.setIsTaobao(false);
		igr.setOrderBy((String) params.get("orderBy"));
		if(WebUtil.isNotNull(params.get("startModified")))
		igr.setStartModified(WebUtil.toDateForString(params.get("startModified").toString(), "yyyy-MM-dd HH:mm:ss"));
		if(WebUtil.isNotNull(params.get("endModified")))
		igr.setEndModified(WebUtil.toDateForString(params.get("endModified").toString(), "yyyy-MM-dd HH:mm:ss"));
		igr.setTimestamp((Long) params.get("timestamp"));
		igr.setPageSize((Long) params.get("pageSize"));
		// 页号
		long pageNo = 1;
		// 记录总结果数
		int row = 0;
		// 下载全部产品
		while (true) {
			try {
				ItemsOnsaleGetResponse tr = client.execute(igr, tbStore
						.getSessionKey());
				// 接口执行成功
				if (tr.isSuccess()) {
					List<Item> items = tr.getItems();
					// 无数据退出
					if (items == null || items.size() == 0)
						break;
					logger.info("淘宝API[" + igr.getApiMethodName()
							+ "]执行成功,page[" + pageNo + "]");

					row += items.size();
					for (Item i : items) {
						taobaoItemGet(WebUtil.toMap("taobaoClient", client,
								"StoreId", storeId, "numIid", i.getNumIid(),
								"fields", TaoBaoApiParams.ItemGetFields));
					}
					// 下一页
					pageNo++;
					igr.setPageNo(pageNo);
				}
				// 接口执行失败
				else {
					logger.error("淘宝API[" + igr.getApiMethodName() + "]执行失败,["
							+ tr.getSubCode() + "," + tr.getSubMsg() + "]");
					break;
				}

			} catch (ApiException e) {
				logger.error("异常,[" + e.getErrCode() + "," + e.getErrMsg()
						+ "]");
				break;
			}
		}
		logger.info("执行成功");
		result.put("Flag", "success");
		return result;
	}

	public Map taobaoItemsInventoryGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【库存更新】准备执行,店铺：" + storeId);
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
		ItemsInventoryGetRequest igr = new ItemsInventoryGetRequest();
		igr.setBanner((String) params.get("banner"));
		igr.setCid((Long) params.get("cid"));
		igr.setEndModified((Date) params.get("endModified"));
		// igr.setFields((String)params.get("fields"));
		igr.setFields(TaoBaoApiParams.ItemsInventoryGetFields);
		igr.setHasDiscount((Boolean) params.get("hasDiscount"));
		// igr.setHasShowcase((Boolean)params.get("hasShowcase"));
		igr.setIsEx((Boolean) params.get("isEx"));
		igr.setIsTaobao((Boolean) params.get("isTaobao"));
		igr.setOrderBy((String) params.get("orderBy"));
		igr.setQ((String) params.get("q"));
		igr.setSellerCids((String) params.get("sellerCids"));
		igr.setStartModified((Date) params.get("startModified"));
		igr.setTimestamp((Long) params.get("timestamp"));
		igr.setPageSize((Long) params.get("pageSize"));
		// 页号
		long pageNo = 1;
		// 记录总结果数
		int row = 0;
		// 下载全部产品
		while (true) {
			try {
				ItemsInventoryGetResponse tr = client.execute(igr, tbStore
						.getSessionKey());
				// 接口执行成功
				if (tr.isSuccess()) {
					List<Item> items = tr.getItems();
					// 无数据退出
					if (items == null || items.size() == 0)
						break;
					logger.info("淘宝API[" + igr.getApiMethodName()
							+ "]执行成功,page[" + pageNo + "],总记录["
							+ tr.getTotalResults() + "]");

					row += items.size();
					for (Item i : items) {
						taobaoItemGet(WebUtil.toMap("taobaoClient", client,
								"StoreId", storeId, "numIid", i.getNumIid(),
								"fields", TaoBaoApiParams.ItemGetFields));
					}
					// 下一页
					pageNo++;
					igr.setPageNo(pageNo);
				}
				// 接口执行失败
				else {
					logger.error("淘宝API[" + igr.getApiMethodName() + "]执行失败,["
							+ tr.getSubCode() + "," + tr.getSubMsg() + "]");
					break;
				}

			} catch (ApiException e) {
				logger.error("异常,[" + e.getErrCode() + "," + e.getErrMsg()
						+ "]");
				break;
			}
		}
		logger.info("执行成功");
		result.put("Flag", "success");
		return result;
	}

	public Map taobaoItemGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【ITEM下载】准备执行,店铺：" + storeId);
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
		ItemGetRequest igr = new ItemGetRequest();
		igr.setFields((String) params.get("fields"));
		// igr.setNick((String)params.get("nick"));
		igr.setNumIid((Long) params.get("numIid"));
		igr.setTimestamp((Long) params.get("timestamp"));
		try {
			ItemGetResponse ir = client.execute(igr, tbStore.getSessionKey());
			if (ir.isSuccess()) {
				logger.info("淘宝API[" + igr.getApiMethodName() + "]执行成功");
				Item i = ir.getItem();
				saveItem(i, storeId);
			} else
				logger.error("淘宝API[" + igr.getApiMethodName() + "]执行失败,["
						+ ir.getSubCode() + "," + ir.getSubMsg() + "]");
		} catch (ApiException e) {
			logger.error("异常,[" + e.getErrCode() + "," + e.getErrMsg() + "]");
		}
		logger.info("执行成功");
		result.put("Flag", "success");
		return result;
	}

	private void saveItem(Item i, int storeId) {
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		Map info = new HashMap();
		info.put("StoreId", storeId);
		TbItem tbi = null;
		List<TbItem> l = session.createQuery(
				"from TbItem where NumIid = " + i.getNumIid()).list();
		if (WebUtil.isNullForList(l))
			tbi = new TbItem();
		else
			tbi = l.get(0);
		tbi.setAfterSaleId(i.getAfterSaleId());
		tbi.setApproveStatus(i.getApproveStatus());
		tbi.setAuctionPoint(i.getAuctionPoint());
		tbi.setAutoFill(i.getAutoFill());
		tbi.setCid(i.getCid());
		tbi.setCodPostageId(i.getCodPostageId());
		tbi.setCreated(i.getCreated());
		tbi.setDelistTime(i.getDelistTime());
		tbi.setDescription(i.getDesc());
		tbi.setDetailUrl(i.getDetailUrl());
		tbi.setEmsFee(new BigDecimal(i.getEmsFee()));
		tbi.setExpressFee(new BigDecimal(i.getExpressFee()));
		tbi.setFreightPayer(i.getFreightPayer());
		tbi.setHasDiscount(i.getHasDiscount());
		tbi.setHasInvoice(i.getHasInvoice());
		tbi.setHasShowcase(i.getHasShowcase());
		tbi.setHasWarranty(i.getHasWarranty());
		tbi.setIncrement(i.getIncrement());
		tbi.setInputPids(i.getInputPids());
		tbi.setInputStr(i.getInputStr());
		tbi.setIs3d(i.getIs3D());
		tbi.setIsEx(i.getIsEx());
		tbi.setIsPrepay(i.getIsPrepay());
		tbi.setIsTaobao(i.getIsTaobao());
		tbi.setIsTiming(i.getIsTiming());
		tbi.setIsVirtual(i.getIsVirtual());
		tbi.setListTime(i.getListTime());
		tbi.setModified(i.getModified());
		tbi.setNick(i.getNick());
		tbi.setNum(i.getNum());
		tbi.setNumIid(new BigDecimal(i.getNumIid()));
		tbi.setOneStation(i.getOneStation());
		tbi.setOuterId(i.getOuterId());
		tbi.setPicUrl(i.getPicUrl());
		tbi.setPostageId(i.getPostageId());
		tbi.setPostFee(new BigDecimal(i.getPostFee()));
		tbi.setPrice(new BigDecimal(i.getPrice()));
		tbi.setProductId(i.getProductId());
		tbi.setPromotedService(i.getPromotedService());
		tbi.setPropertyAlias(i.getPropertyAlias());
		tbi.setProps(i.getProps());
		tbi.setPropsName(i.getPropsName());
		tbi.setScore(i.getScore());
		tbi.setSecondKill(i.getSecondKill());
		tbi.setSellerCids(i.getSellerCids());
		tbi.setSellPromise(i.getSellPromise());
		tbi.setStoreId(storeId);
		tbi.setStuffStatus(i.getStuffStatus());
		tbi.setTitle(i.getTitle());
		tbi.setType(i.getType());
		tbi.setValidThru(i.getValidThru());
		tbi.setViolation(i.getViolation());
		tbi.setVolume(i.getVolume());
		tbi.setWapDesc(i.getWapDesc());
		tbi.setWapDetailUrl(i.getWapDetailUrl());
		tbi.setWwStatus(i.getWwStatus());
		if (tbi.getId() == null)
			session.save(tbi);
		else
			session.update(tbi);
		Map itemInfo = new HashMap();
		itemInfo.put("ItemCd", tbi.getOuterId());
		itemInfo.put("ItemName", tbi.getTitle());
		itemInfo.put("ItemPrice", tbi.getPrice());
		itemInfo.put("ItemDesc", tbi.getDescription());
		info.put("ItemInfo", itemInfo);
		// sku
		// List<Sku> skuList =
		// taobaoItemSkusGet(storeId,WebUtil.toMap("taobaoClient",client,"fields",TaoBaoApiParams.ItemSkusGetFields,"numIids",i.getNumIid().toString()));
		List<Sku> skuList = i.getSkus();
		List<Map> skuInfoList = new ArrayList();
		if (!WebUtil.isNullForList(skuList)) {
			List<TbSku> tbsku = session.createQuery(
					"from TbSku where NumIid = " + i.getNumIid()).list();
			Map<Long, TbSku> m = new HashMap();
			if (!WebUtil.isNullForList(tbsku)) {
				for (TbSku sku : tbsku) {
					m.put(sku.getSkuId().longValue(), sku);
				}
			}
			for (Sku s : skuList) {
				TbSku tbs = m.get(s.getSkuId());
				if (tbs == null)
					tbs = new TbSku();
				tbs.setCreated(WebUtil.toDateForString(s.getCreated(),
						"yyyy-MM-dd HH:mm:ss"));
				tbs.setModified(WebUtil.toDateForString(s.getModified(),
						"yyyy-MM-dd HH:mm:ss"));
				tbs.setNumIid(new BigDecimal(i.getNumIid().toString()));
				tbs.setOuterId(s.getOuterId());
				tbs.setPrice(new BigDecimal(s.getPrice()));
				tbs.setProperties(s.getProperties());
				tbs.setQuantity(s.getQuantity());
				tbs.setSkuId(new BigDecimal(s.getSkuId()));
				tbs.setStatus(s.getStatus());
				tbs.setStoreId(storeId);
				if (tbs.getId() == null)
					session.save(tbs);
				else
					session.update(tbs);
				Map skuMap = new HashMap();
				skuMap.put("SkuCd", tbs.getOuterId());
				skuMap.put("SkuPrice", tbs.getPrice());
				skuInfoList.add(skuMap);
			}
		}
		info.put("SkuList", skuInfoList);
		// item图片
		List<ItemImg> itemImgList = i.getItemImgs();
		if (!WebUtil.isNullForList(itemImgList)) {
			List<TbItemImg> tbitemimg = session.createQuery(
					"from TbItemImg where NumIid = " + i.getNumIid()).list();
			Map<Long, TbItemImg> m = new HashMap();
			if (!WebUtil.isNullForList(tbitemimg)) {
				for (TbItemImg img : tbitemimg) {
					m.put(img.getTbItemImgId(), img);
				}
			}
			for (ItemImg ii : itemImgList) {
				TbItemImg tbii = m.get(ii.getId());
				if (tbii == null)
					tbii = new TbItemImg();
				tbii.setCreated(ii.getCreated());
				tbii.setNumIid(new BigDecimal(i.getNumIid().toString()));
				tbii.setPosition(ii.getPosition());
				tbii.setTbItemImgId(ii.getId());
				tbii.setUrl(ii.getUrl());
				tbii.setStoreId(storeId);
				if (tbii.getId() == null)
					session.save(tbii);
				else
					session.update(tbii);
			}
		}
		// 属性图片
		List<PropImg> propImgList = i.getPropImgs();
		if (!WebUtil.isNullForList(propImgList)) {
			List<TbItemPropImg> tbitempropimg = session.createQuery(
					"from TbItemPropImg where NumIid = " + i.getNumIid())
					.list();
			Map<Long, TbItemPropImg> m = new HashMap();
			if (!WebUtil.isNullForList(tbitempropimg)) {
				for (TbItemPropImg img : tbitempropimg) {
					m.put(img.getTbItemPropImgId(), img);
				}
			}
			for (PropImg pi : propImgList) {
				TbItemPropImg tbpi = m.get(pi.getId());
				if (tbpi == null)
					tbpi = new TbItemPropImg();
				tbpi.setCreated(pi.getCreated());
				tbpi.setNumIid(new BigDecimal(i.getNumIid().toString()));
				tbpi.setPosition(pi.getPosition());
				tbpi.setProperties(pi.getProperties());
				tbpi.setTbItemPropImgId(pi.getId());
				tbpi.setUrl(pi.getUrl());
				tbpi.setStoreId(storeId);
				if (tbpi.getId() == null)
					session.save(tbpi);
				else
					session.update(tbpi);
			}
		}
		tx.commit();
		session.close();
		ProductServices productServices = (ProductServices) GetBeanServlet.getBean("productServices");
		productServices.saveUnitItemAndSku(info);
	}

	public Map taobaoItemSkusGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【SKU下载】准备执行,店铺：" + storeId);
		// 店铺查询
		List<TbStore> storeList = this.getHibernateTemplate().find(
				"from TbStore where id = " + storeId);
		// 未登记此店铺
		if (storeList == null || storeList.size() == 0) {
			logger.error("未登记此店铺[" + storeId + "]");
		}
		TbStore tbStore = storeList.get(0);
		// 此店铺状态为不可使用
		if (tbStore.getStatus() == null
				|| !tbStore.getStatus().equals("ACTIVE")) {
			logger.error("此店铺状态为不可使用[" + storeId + "]");
		}
		logger.info("准备调用淘宝client");
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		ItemSkusGetRequest ir = new ItemSkusGetRequest();
		ir.setFields((String) params.get("fields"));
		ir.setNumIids((String) params.get("numIids"));
		ir.setTimestamp((Long) params.get("timestamp"));
		try {
			ItemSkusGetResponse isg = client.execute(ir, tbStore
					.getSessionKey());
			if (isg.isSuccess()) {
				logger.info("淘宝API[" + ir.getApiMethodName() + "]执行成功");
				List<Sku> skuList = isg.getSkus();
				// return skuList;
			} else
				logger.error("淘宝API[" + ir.getApiMethodName() + "]执行失败,["
						+ isg.getSubCode() + "," + isg.getSubMsg() + "]");
		} catch (ApiException e) {
			logger.error("异常,[" + e.getErrCode() + "," + e.getErrMsg() + "]");
		}
		logger.info("执行成功");
		result.put("Flag", "success");
		return result;

	}

	public Map getTbItemAll(int storeId) {
		Map result = new HashMap();
		String sql = "from TbItem ti,TbSku ts where ti.NumIid = ts.NumIid and ti.StoreId = "
				+ storeId + " and ts.StoreId = " + storeId;
		List<Object> l = this.getHibernateTemplate().find(sql);
		result.put("ItemList", l);
		List<TbCatPropValue> tcpvl = this.getHibernateTemplate().find(
				"from TbCatPropValue where Cid in (select Cid from TbItem where StoreId = "
						+ storeId + ")");
		result.put("TbCatPropValueList", tcpvl);
		return result;
	}

	public Map taobaoItemSkuUpdate(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【库存和价格更新】准备执行,店铺：" + storeId);
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
		ItemSkuUpdateRequest isur = new ItemSkuUpdateRequest();
		isur.setNumIid((Long) params.get("NumIid"));
		isur.setProperties((String) params.get("Properties"));
		isur.setItemPrice((String) params.get("ItemPrice"));
		isur.setLang((String) params.get("Lang"));
		isur.setOuterId((String) params.get("OuterId"));
		isur.setPrice((String) params.get("Price"));
		isur.setQuantity((Long) params.get("Quantity"));
		isur.setTimestamp((Long) params.get("Timestamp"));
		try {
			ItemSkuUpdateResponse is = client.execute(isur, tbStore
					.getSessionKey());
			Sku s = is.getSku();
			logger.info("更新成功,时间[" + s.getModified() + "],NumIid["
					+ s.getNumIid() + "]");
		} catch (ApiException e) {
			logger.error("异常,[" + e.getErrCode() + "," + e.getErrMsg() + "]");
		}
		logger.info("执行成功");
		result.put("Flag", "success");
		return result;
	}

	public Map taobaoSkusCustomGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【SKU下载】准备执行,店铺：" + storeId);
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
		SkusCustomGetRequest scgr = new SkusCustomGetRequest();
		scgr.setFields((String) params.get("Fields"));
		scgr.setOuterId((String) params.get("OuterId"));
		scgr.setTimestamp((Long) params.get("Timestamp"));
		try {
			SkusCustomGetResponse sc = client.execute(scgr, tbStore
					.getSessionKey());
			List<Sku> s = sc.getSkus();
		} catch (ApiException e) {
			logger.error("异常,[" + e.getErrCode() + "," + e.getErrMsg() + "]");
		}
		logger.info("执行成功");
		result.put("Flag", "success");
		return result;
	}

	public Map taobaoItemSkuPriceUpdate(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【SKU下载】准备执行,店铺：" + storeId);
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
		List<Map> skuPriceList = (List) param.get("SkuPriceList");
		if(WebUtil.isNullForList(skuPriceList))
		{
			logger.error("无价格更新列表");
			result.put("Flag", "error");
			return result;
		}
		//每次100条
		int r = 100;
		int p = 1;
		int a = skuPriceList.size();
		Map<String,TbSku> tbSkuMap = new HashMap();
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		ItemSkuPriceUpdateRequest ispr = new ItemSkuPriceUpdateRequest();
		List<String> successList = new ArrayList();
		List<String> errorList = new ArrayList();
		while(true)
		{
			tbSkuMap.clear();
			int c = r*100-1;
			try {
				List<Map> l = null;
				if(c>=a)
					l = skuPriceList.subList(r*(p-1), a);
				else
					l = skuPriceList.subList(r*(p-1), c);
				StringBuffer skuCds = new StringBuffer();
				for(Map m:l)
				{
					if(skuCds.length()>0)
						skuCds.append(",");
					skuCds.append("'"+m.get("SkuCd")+"'");
				}
				List<TbSku> tbSkuList = this.getHibernateTemplate().find("from TbSku where OuterId in ("+skuCds.toString()+")");
				if(!WebUtil.isNullForList(tbSkuList))
				{
					for(TbSku s:tbSkuList)
					{
						tbSkuMap.put(s.getOuterId(), s);
					}
				}
				for(Map m:skuPriceList)
				{
					TbSku s = tbSkuMap.get((String)m.get("SkuCd"));
					//无TbSku信息，这里考虑以后把日志放到数据库中
					if(s==null)
					{
						logger.error("Sku["+m.get("SkuCd")+"]找不到对应的淘宝Sku");
						errorList.add((String)m.get("SkuCd"));
					}
					else
					{
						if(WebUtil.isNull(m.get("SkuPrice")))
						{
							logger.error("Sku["+m.get("SkuCd")+"]找不到对应的价格");
							errorList.add((String)m.get("SkuCd"));
							continue;
						}
						if(WebUtil.isNotNull(m.get("ItemPrice")))
						ispr.setItemPrice(m.get("ItemPrice").toString());
						ispr.setNumIid(s.getNumIid().longValue());
						ispr.setOuterId(s.getOuterId());
						ispr.setPrice(m.get("SkuPrice").toString());
						ispr.setProperties(s.getProperties());
						try {
							ItemSkuPriceUpdateResponse isr = client.execute(ispr, tbStore.getSessionKey());
							if(isr.isSuccess())
							{
								successList.add((String)m.get("SkuCd"));
							}
							else
							{
								logger.error("Sku["+m.get("SkuCd")+"]价格更新失败,"+isr.getErrorCode()+","+isr.getMsg()+";"+isr.getSubCode()+","+isr.getSubMsg());
								errorList.add((String)m.get("SkuCd"));
								continue;
							}
						} catch (ApiException e) {
							logger.error("Sku["+m.get("SkuCd")+"]价格更新失败,"+e.getMessage());
							errorList.add((String)m.get("SkuCd"));
						}
					}
				}
			} catch (DataAccessException e) {
				break;
			}
			if(c>=a)
				break;
			p++;
		}
		logger.info("执行成功");
		result.put("Flag", "success");
		result.put("SuccessList", successList);
		result.put("ErrorList", errorList);
		return result;
	}
}
