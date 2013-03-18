package com.sunmw.taobao.bean.product.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.TaoBaoApiParams;
import com.sunmw.taobao.bean.product.TbProductServices;
import com.sunmw.taobao.entity.TbProduct;
import com.sunmw.taobao.entity.TbProductImg;
import com.sunmw.taobao.entity.TbProductPropImg;
import com.sunmw.taobao.entity.TbStore;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Product;
import com.taobao.api.domain.ProductImg;
import com.taobao.api.domain.ProductPropImg;
import com.taobao.api.request.ProductGetRequest;
import com.taobao.api.request.ProductsGetRequest;
import com.taobao.api.response.ProductGetResponse;
import com.taobao.api.response.ProductsGetResponse;

public class TbProductServicesImpl extends HibernateDaoSupport implements
		TbProductServices {

	static Logger logger = Logger.getLogger(TbProductServicesImpl.class);
	public Map taobaoProductsGet(Map params)
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
		logger.info("【产品下载】准备执行,店铺："+storeId);
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
		ProductsGetRequest pgr = new ProductsGetRequest();
		//pgr.setCid((Long)params.get("cid"));
		//pgr.setFields((String)params.get("fields"));
		pgr.setFields(TaoBaoApiParams.ProductsGetFields);
		//pgr.setNick((String)params.get("nick"));
		//使用当前店铺昵称
		pgr.setNick(tbStore.getStoreNick());
		//pgr.setProps((String)params.get("props"));
		pgr.setTimestamp((Long)params.get("timestamp"));
		pgr.setPageSize((Long)params.get("pageSize"));
		//页号
		long pageNo = 1;
		//记录总结果数
		int row = 0;
		//下载全部产品
		while(true)
		{
			try {
				ProductsGetResponse tr = client.execute(pgr,tbStore.getSessionKey());
				//接口执行成功
				if(tr.isSuccess())
				{
					List<Product> products = tr.getProducts();
					//无数据退出
					if(products==null||products.size()==0)
						break;
					logger.info("淘宝API["+pgr.getApiMethodName()+"]执行成功,page["+pageNo+"]");
					
					row += products.size();
					for(Product p:products)
					{
						taobaoProductGet(WebUtil.toMap("taobaoClient", client, "productId", p.getProductId(), "fields", TaoBaoApiParams.ProductGetFields));
					}
					//下一页
					pageNo++;
					pgr.setPageNo(pageNo);
				}
				//接口执行失败
				else
				{
					logger.error("淘宝API["+pgr.getApiMethodName()+"]执行失败,["+tr.getErrorCode()+","+tr.getMsg()+"]");
					break;
				}
				
			} catch (ApiException e) {
				logger.error("异常,["+e.getErrCode()+","+e.getErrMsg()+"]");
				break;
			}
		}
		logger.info("执行成功");
		result.put("Flag", "success");
		return result;
	}
	
	public Map taobaoProductGet(Map params)
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
		logger.info("【产品信息下载】准备执行,店铺："+storeId);
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
		ProductGetRequest pgr = new ProductGetRequest();
		pgr.setCid((Long)params.get("cid"));
		pgr.setFields((String)params.get("fields"));
		pgr.setProductId((Long)params.get("productId"));
		pgr.setProps((String)params.get("props"));
		pgr.setTimestamp((Long)params.get("timestamp"));
		try {
			ProductGetResponse pr = client.execute(pgr);
			if(pr.isSuccess())
			{
				Product p = pr.getProduct();
				saveProduct(p);
			}
			else
				logger.error("淘宝API["+pgr.getApiMethodName()+"]执行失败,["+pr.getSubCode()+","+pr.getSubMsg()+"]");
		} catch (ApiException e) {
			logger.error("异常,["+e.getErrCode()+","+e.getErrMsg()+"]");
		}
		logger.info("执行成功");
		result.put("Flag", "success");
		return result;
	}
	
	//保存商品信息
	private void saveProduct(Product p)
	{
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		TbProduct tbp = null;
		List<TbProduct> l = session.createQuery("from TbProduct where TbProductId = "+p.getProductId()).list();
		if(WebUtil.isNullForList(l))
			tbp = new TbProduct();
		else
			tbp = l.get(0);
		tbp.setTbProductId(p.getProductId());
		tbp.setBinds(p.getBinds());
		tbp.setBindsStr(p.getBindsStr());
		tbp.setCatName(p.getCatName());
		tbp.setCid(p.getCid());
		tbp.setCollectNum(p.getCollectNum());
		tbp.setCreated(p.getCreated());
		tbp.setCustomerProps(p.getCustomerProps());
		tbp.setDescription(p.getDesc());
		tbp.setLevel(p.getLevel());
		tbp.setModified(p.getModified());
		tbp.setName(p.getName());
		tbp.setOuterId(p.getOuterId());
		tbp.setPicPath(p.getPicPath());
		tbp.setPicUrl(p.getPicUrl());
		tbp.setPrice(new BigDecimal(p.getPrice()));
		tbp.setPropertyAlias(p.getPropertyAlias());
		tbp.setProps(p.getProps());
		tbp.setPropsStr(p.getPropsStr());
		tbp.setSaleProps(p.getSaleProps());
		tbp.setSalePropsStr(p.getSalePropsStr());
		tbp.setStatus(p.getStatus());
		tbp.setTsc(p.getTsc());
		tbp.setVerticalMarket(p.getVerticalMarket());
		if(tbp.getId()==null)
			session.save(tbp);
		else
			session.update(tbp);
		//产品图片
		List<ProductImg> imgList = p.getProductImgs();
		if(!WebUtil.isNullForList(imgList))
		{
			List<TbProductImg> tbimgl = session.createQuery("from TbProductImg where TbProductId = "+p.getProductId()).list();
			Map<Long,TbProductImg> m = new HashMap();
			if(!WebUtil.isNullForList(tbimgl))
			{
				for(TbProductImg timg:tbimgl)
				{
					m.put(timg.getTbProductImgId(), timg);
				}
			}
			for(ProductImg img:imgList)
			{
				TbProductImg tbimg = m.get(img.getId());
				if(tbimg==null)
					tbimg = new TbProductImg();
				tbimg.setCreated(img.getCreated());
				tbimg.setModified(img.getModified());
				tbimg.setPosition(img.getPosition());
				tbimg.setTbProductId(img.getProductId());
				tbimg.setTbProductImgId(img.getId());
				tbimg.setUrl(img.getUrl());
				if(tbimg.getId()==null)
					session.save(tbimg);
				else
					session.update(tbimg);
			}
		}
		//产品属性图片
		List<ProductPropImg> propImgList = p.getProductPropImgs();
		if(!WebUtil.isNullForList(propImgList))
		{
			List<TbProductPropImg> tbimgl = session.createQuery("from TbProductPropImg where TbProductId = "+p.getProductId()).list();
			Map<Long,TbProductPropImg> m = new HashMap();
			if(!WebUtil.isNullForList(tbimgl))
			{
				for(TbProductPropImg timg:tbimgl)
				{
					m.put(timg.getTbProductPropImgId(), timg);
				}
			}
			for(ProductPropImg img:propImgList)
			{
				TbProductPropImg tbimg = m.get(img.getId());
				if(tbimg==null)
					tbimg = new TbProductPropImg();
				tbimg.setCreated(img.getCreated());
				tbimg.setModified(img.getModified());
				tbimg.setPosition(img.getPosition());
				tbimg.setProps(img.getProps());
				tbimg.setTbProductId(img.getProductId());
				tbimg.setTbProductPropImgId(img.getId());
				tbimg.setUrl(img.getUrl());
				if(tbimg.getId()==null)
					session.save(tbimg);
				else
					session.update(tbimg);
			}
		}
		tx.commit();
		session.close();
	}
}
