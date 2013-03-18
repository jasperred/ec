package com.sunmw.web.bean.product.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.entity.TbCatPropValue;
import com.sunmw.taobao.entity.TbItem;
import com.sunmw.taobao.entity.TbSku;
import com.sunmw.web.bean.product.ProductServices;
import com.sunmw.web.entity.ItemMaster;
import com.sunmw.web.entity.SkuMaster;
import com.sunmw.web.entity.Store;
import com.sunmw.web.entity.UnitItemMaster;
import com.sunmw.web.entity.UnitSkuMaster;
import com.sunmw.web.util.WebUtil;

public class ProductServicesImpl extends HibernateDaoSupport implements
		ProductServices {

	public void synchronizationTbItemToErp(Map itemSkuMap)
	{
		List<Object[]> itemList = (List)itemSkuMap.get("ItemList");
		if(WebUtil.isNullForList(itemList))
			return;
		Map<String,Map> itemMap = new HashMap();
		//整理淘宝ITEM
		for(Object[] obj:itemList)
		{
			TbItem ti = (TbItem)obj[0];
			TbSku ts = (TbSku) obj[1];
			Map m = itemMap.get(ti.getOuterId());
			if(m==null)
				m = new HashMap();
			m.put("Item", ti);
			List skuList = (List) m.get("SkuList");
			if(skuList==null)
				skuList = new ArrayList();
			skuList.add(ts);
			m.put("SkuList", skuList);
			itemMap.put(ti.getOuterId(), m);
		}
		Session session = this.getSession();
		Iterator<String> item = itemMap.keySet().iterator();
		Date date = new Date();
		//属性值
		List<TbCatPropValue> tcpvList = (List) itemSkuMap.get("TbCatPropValueList");
		Map<String,TbCatPropValue> propMap = new HashMap();
		if(!WebUtil.isNullForList(tcpvList))
		{
			for(TbCatPropValue tcpv:tcpvList)
			{
				propMap.put(tcpv.getCid().toString()+tcpv.getPid().toString()+tcpv.getVid().toString(), tcpv);
			}
		}
		//从淘宝同步商品,只同步ERP中没有的商品
		while(item.hasNext())
		{
			Map m = itemMap.get(item.next());
			TbItem ti = (TbItem) m.get("Item");
			if(WebUtil.isNull(ti.getOuterId()))
				continue;
			List<ItemMaster> imList = session.createQuery("from ItemMaster where ItemCd = '"+ti.getOuterId()+"'").list();
			ItemMaster im = null;
			Transaction tx = session.beginTransaction();
			if(WebUtil.isNullForList(imList))
			{
				im = new ItemMaster();
				im.setCtime(date);
				im.setItemCd(ti.getOuterId());
				//这个字段太大，不写入
				//im.setItemDesc(ti.getDescription());
				im.setItemName(ti.getTitle());
				im.setItemPrice(ti.getPrice());
				im.setItemStatus("NORMAL");
				im.setItemStdPrice(ti.getPrice());
				im.setItemType("");
				session.save(im);
			}
			else
			{
				im = imList.get(0);
				
			}
			List<TbSku> tbSkuList = (List)m.get("SkuList");
			//无SKU后面不执行
			if(WebUtil.isNullForList(tbSkuList))
			{
				tx.commit();
				continue;
			}
			int itemid = im.getId();
			StringBuffer skus = new StringBuffer();
			for(TbSku ts:tbSkuList)
			{
				if(skus.length()>0)
					skus.append(",");
				skus.append("'"+ts.getOuterId()+"'");
			}
			//颜色处理
			String itemPropAlias = ti.getPropertyAlias();
			Map<String,String> aliasMap = new HashMap();
			String[] ipas = itemPropAlias.split(";");
			if(ipas.length>0&&ipas[0].length()>0)
			{
				for(int i=0;i<ipas.length;i++)
				{
					String[] colors = ipas[i].split(":");
					if(colors.length==0||colors[0].length()==0)
						continue;
					if(colors[0].equals("1627207"))
					{
						aliasMap.put(colors[0]+colors[1], colors[2]);
					}
					else
						continue;
				}
				
			}
			List<SkuMaster> skuList = session.createQuery("from SkuMaster where SkuCd in ("+skus.toString()+")").list();
			if(WebUtil.isNullForList(skuList))
			{
				for(TbSku ts:tbSkuList)
				{
					SkuMaster sm = new SkuMaster();
					//属性处理
					String props = ts.getProperties();
					if(props!=null)
					{
						String[] ps = props.split(";");
						if(ps.length>0&&ps[0].length()>0)
						{
							for(String p:ps)
							{
								String[] pvs = p.split(":");
								if(pvs.length<2)
									continue;
								//颜色
								if(pvs[0].equals("1627207"))
								{
									String c = aliasMap.get(pvs[0]+pvs[1]);
									if(c==null)
									{
										TbCatPropValue tcpv = propMap.get(ti.getCid().toString()+pvs[0]+pvs[1]);
										if(tcpv!=null)
											sm.setSkuProp1(tcpv.getName());
									}
									else
										sm.setSkuProp1(c);
								}
								//尺码
								else
								{
									TbCatPropValue tcpv = propMap.get(ti.getCid().toString()+pvs[0]+pvs[1]);
									if(tcpv!=null)
										sm.setSkuProp2(tcpv.getName());
								}
							}
						}
					}
					sm.setBarcode1(ts.getOuterId());
					sm.setCtime(date);
					sm.setItemMaster(im);
					sm.setSkuCd(ts.getOuterId());
					sm.setSkuName(im.getItemName());
					sm.setSkuPrice1(ts.getPrice());
					sm.setSkuStatus("NORMAL");
					session.save(sm);
				}
			}
			else
			{
				Map skuMap = new HashMap();
				for(SkuMaster sm:skuList)
				{
					skuMap.put(sm.getSkuCd(), sm.getSkuCd());
				}
				for(TbSku ts:tbSkuList)
				{
					//已存在的不保存
					if(skuMap.get(ts.getOuterId())!=null)
						continue;
					SkuMaster sm = new SkuMaster();
					//属性处理
					String props = ts.getProperties();
					if(props!=null)
					{
						String[] ps = props.split(";");
						if(ps.length>0&&ps[0].length()>0)
						{
							for(String p:ps)
							{
								String[] pvs = p.split(":");
								if(pvs.length<2)
									continue;
								//颜色
								if(pvs[0].equals("1627207"))
								{
									String c = aliasMap.get(pvs[0]+pvs[1]);
									if(c==null)
									{
										TbCatPropValue tcpv = propMap.get(ti.getCid().toString()+pvs[0]+pvs[1]);
										if(tcpv!=null)
											sm.setSkuProp1(tcpv.getName());
									}
									else
										sm.setSkuProp1(c);
								}
								//尺码
								else
								{
									TbCatPropValue tcpv = propMap.get(ti.getCid().toString()+pvs[0]+pvs[1]);
									if(tcpv!=null)
										sm.setSkuProp2(tcpv.getName());
								}
							}
						}
					}
					sm.setBarcode1(ts.getOuterId());
					sm.setCtime(date);
					sm.setItemMaster(im);
					sm.setSkuCd(ts.getOuterId());
					sm.setSkuName(im.getItemName());
					sm.setSkuPrice1(ts.getPrice());
					sm.setSkuStatus("NORMAL");
					session.save(sm);
				}
			}
			tx.commit();
		}
		session.close();
	}

	//2012-07-27 改为查找UnitSkuMaster表
	public List<Map> skuPriceUpdateList(int storeId) {
		List<Object[]> sl = this.getHibernateTemplate().find(
				"select i.ItemPrice,s.SkuCd,s.SkuPrice1 from UnitItemMaster i,UnitSkuMaster s where i.id = s.ItemId and s.PriceUpdateFlag = ? and s.StoreId = ?",new Object[]{"N",storeId});
		if (WebUtil.isNullForList(sl))
			return null;
		List<Map> resultList = new ArrayList();
		for (Object[] sm : sl) {
			Map m = new HashMap();
			m.put("ItemPrice", sm[0]);
			m.put("SkuCd", sm[1]);
			m.put("SkuPrice", sm[2]);
			resultList.add(m);
		}
		return resultList;
	}
	
	public Map updateSkuPriceFlagOfShop(Map param)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "StoreId is null");
			return result;
		}
		this.getHibernateTemplate().bulkUpdate("update UnitSkuMaster set PriceUpdateFlag = 'Y' where StoreId = "+storeId);
		result.put("Flag", "success");
		return result;
	}
	
	public Map searchSku(Map param, int currentPage, int pageRow) {
		StringBuffer hql = new StringBuffer(" from ItemMaster im left outer join im.skuMaster sm ");
		StringBuffer con = new StringBuffer();
		List conList = new ArrayList();
		if (!WebUtil.isNull(param.get("ItemName"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" im.ItemName like :Condition" + conList.size());
			conList.add("%"+param.get("ItemName").toString()+"%");
		}
		if (!WebUtil.isNull(param.get("ItemCd"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" im.ItemCd = :Condition" + conList.size());
			conList.add(param.get("ItemCd"));
		}
		if (!WebUtil.isNull(param.get("SkuCd"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" sm.SkuCd = :Condition" + conList.size());
			conList.add(param.get("SkuCd"));
		}
		if (!WebUtil.isNull(param.get("FromPrice"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" sm.SkuPrice1 >= :Condition" + conList.size());
			conList.add(new BigDecimal(param.get("FromPrice").toString()));
		}
		if (!WebUtil.isNull(param.get("EndPrice"))) {
			if (con.length() > 0)
				con.append(" and ");
			con.append(" sm.SkuPrice1 <= :Condition" + conList.size());
			conList.add(new BigDecimal(param.get("EndPrice").toString()));
		}
		
		if (con.length() > 0)
			hql.append(" where " + con.toString());

		Session session = this.getSession();
		Map result = new HashMap();
		try {
			Query q1 = session.createQuery("select count(*) " + hql.toString());
			// 查询条件
			for (int i = 0; i < conList.size(); i++) {
				q1.setParameter("Condition" + i, conList.get(i));
			}
			List<Long> countList = q1.list();
			int count = countList.get(0).intValue();
			Query q = session.createQuery(hql.toString());
			// 查询条件
			for (int i = 0; i < conList.size(); i++) {
				q.setParameter("Condition" + i, conList.get(i));
			}
			if (pageRow > 0) {
				q.setFirstResult((currentPage - 1) * pageRow);
				q.setMaxResults(pageRow);
			}
			
			List<Object[]> l = q.list();
			List<Map> r = new ArrayList();
			for (Object[] obj : l) {
				ItemMaster im = (ItemMaster) obj[0];
				SkuMaster sm = (SkuMaster) obj[1];
				Map m = new HashMap();
				m.put("ItemId", im.getId());
				m.put("SkuId", sm.getId());
				m.put("ItemCd", im.getItemCd());
				m.put("SkuCd", sm.getSkuCd());
				m.put("ItemName", im.getItemName());
				m.put("SkuPrice1", WebUtil.round(sm.getSkuPrice1(), 2));
				m.put("SkuPrice2", WebUtil.round(sm.getSkuPrice2(), 2));
				m.put("SkuProp1", sm.getSkuProp1());
				m.put("SkuProp2", sm.getSkuProp2());
				m.put("SkuProp3", sm.getSkuProp3());
				m.put("SkuProp4", sm.getSkuProp4());
				r.add(m);
			}
			result.put("RESULT", r);
			result.put("COUNT_ROW", count);
		} catch (HibernateException e) {
			result.put("Flag", "ERROR");
			result.put("Message", e.getMessage());
			logger.error(e.getMessage());
		} catch (RuntimeException e) {
			result.put("Flag", "ERROR");
			result.put("Message", e.getMessage());
			logger.error(e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return result;
	}

	public Map importItemPrice(Map param)
	{
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		//是否用统一价格，使用统一价格不区分店铺，同一公司下的店铺都使用这个价格
//		String unifiedPrice = (String) param.get("UnifiedPrice");
//		if(WebUtil.isNull(unifiedPrice))
//			unifiedPrice = "Y";
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "StoreId is null");
			return result;
		}
		List<Store> stList = this.getHibernateTemplate().find("from Store where id = ?",storeId);
		if(WebUtil.isNullForList(stList))
		{
			result.put("Flag", "error");
			result.put("Message", "没找到店铺");
			return result;
		}
		Store st = stList.get(0);
		if(st.getCompanyId()==null)
		{
			result.put("Flag", "error");
			result.put("Message", "店铺没有对应的公司");
			return result;
		}
		List<Map> priceList = (List) param.get("PriceList");
		if(WebUtil.isNullForList(priceList))
		{
			result.put("Flag", "error");
			result.put("Message", "没有价格列表");
			return result;
		}
		Session session = this.getSession();
		Date date = new Date();
		for(Map pm:priceList)
		{
			Transaction tx = session.beginTransaction();
			//如果导入文件中有StoreId但与执行任务的StoreId不同，则忽略此记录
			if(WebUtil.isNotNull(pm.get("StoreId")))
			{
				if(storeId!=new Integer(pm.get("StoreId").toString()))
					continue;
			}
			//更新Item价格
			if(WebUtil.isNotNull(pm.get("ItemCd"))||WebUtil.isNotNull(pm.get("ItemPrice")))
			{
				session.createQuery("update ItemMaster set ItemPrice = "+pm.get("ItemPrice")+" where ItemCd = '"+pm.get("ItemCd")+"' and CompanyId = "+st.getCompanyId()).executeUpdate();
				if(WebUtil.isNull(pm.get("StoreId")))
				{
					session.createQuery("update UnitItemMaster set ItemPrice = "+pm.get("ItemPrice")+",PriceUpdateFlag = 'N',PriceUpdateTime = '"+WebUtil.formatDateString(date, "yyyy-MM-dd HH:mm:ss")+"' where ItemCd = '"+pm.get("ItemCd")+"' and CompanyId = "+st.getCompanyId()).executeUpdate();
				}
				else
				{
					session.createQuery("update UnitItemMaster set ItemPrice = "+pm.get("ItemPrice")+",PriceUpdateFlag = 'N',PriceUpdateTime = '"+WebUtil.formatDateString(date, "yyyy-MM-dd HH:mm:ss")+"' where ItemCd = '"+pm.get("ItemCd")+"' and StoreId = "+storeId).executeUpdate();
				}
			}
			//更新Sku价格
			if(WebUtil.isNotNull(pm.get("SkuCd"))||WebUtil.isNotNull(pm.get("SkuPrice")))
			{
				session.createQuery("update SkuMaster set SkuPrice1 = "+pm.get("SkuPrice")).executeUpdate();
				if(WebUtil.isNull(pm.get("StoreId")))
				{
					session.createQuery("update UnitSkuMaster set SkuPrice1 = "+pm.get("SkuPrice")+",PriceUpdateFlag = 'N',PriceUpdateTime = '"+WebUtil.formatDateString(date, "yyyy-MM-dd HH:mm:ss")+"' where SkuCd = '"+pm.get("SkuCd")+"' and CompanyId = "+st.getCompanyId()).executeUpdate();
				}
				else
				{
					session.createQuery("update UnitSkuMaster set SkuPrice1 = "+pm.get("SkuPrice")+",PriceUpdateFlag = 'N',PriceUpdateTime = '"+WebUtil.formatDateString(date, "yyyy-MM-dd HH:mm:ss")+"' where SkuCd = '"+pm.get("SkuCd")+"' and StoreId = "+storeId).executeUpdate();
				}
			}			
			tx.commit();
		}
		session.close();
		result.put("Flag", "success");
		return result;
	}

	@Override
	public Map saveUnitItemAndSku(Map param) {
		Map result = new HashMap(0);
		Integer storeId = (Integer) param.get("StoreId");
		Map itemInfoMap = (Map) param.get("ItemInfo");
		List<Map> skuList = (List) param.get("SkuList");
		if(storeId==null)
		{
			result.put("Flag", "error");
			result.put("Message", "没有店铺ID信息");
			return result;
		}
		if(WebUtil.isNull(itemInfoMap))
		{
			result.put("Flag", "error");
			result.put("Message", "没有Item信息");
			return result;
		}
		Session session = this.getSession();
		try {
			Transaction tx = session.beginTransaction();
			List<UnitItemMaster> itemList = this.getHibernateTemplate().find("from UnitItemMaster where ItemCd = ? and StoreId = ?",new Object[]{itemInfoMap.get("ItemCd"),storeId});
			UnitItemMaster item = null;
			if(WebUtil.isNullForList(itemList))
			{
				item = new UnitItemMaster();
				item.setItemCd((String)itemInfoMap.get("ItemCd"));
				item.setCtime(new Date());
				item.setStoreId(storeId);
				List<Store> stList = this.getHibernateTemplate().find("from Store where id = ?",storeId);
				if(!WebUtil.isNullForList(stList))
					item.setCompanyId(stList.get(0).getCompanyId());
			}
			else
			{
				item = itemList.get(0);
				item.setMtime(new Date());
			}
			item.setItemName((String)itemInfoMap.get("ItemName"));
			item.setItemPrice((BigDecimal)itemInfoMap.get("ItemPrice"));
			//item.setItemDesc((String)itemInfoMap.get("ItemDesc"));
			boolean isNew = false;
			if(item.getId()==null)
			{
				isNew = true;
				session.save(item);
			}
			else
				session.update(item);
			//Sku为空时以Item为Sku
			if(WebUtil.isNullForList(skuList))
			{
				UnitSkuMaster sku = new UnitSkuMaster();
				sku.setItemId(item.getId());
				sku.setSkuCd(item.getItemCd());
				sku.setSkuName(item.getItemName());
				sku.setSkuPrice1(item.getItemPrice());
				//sku.setSkuDesc(item.getItemDesc());
				sku.setStoreId(storeId);
				sku.setCompanyId(item.getCompanyId());
				sku.setCtime(new Date());				
			}
			else
			{
				Map<String,UnitSkuMaster> skuMap = new HashMap();
				if(isNew)
				{
					List<UnitSkuMaster> usList = this.getHibernateTemplate().find("from UnitSkuMaster where ItemId = ?",item.getId());
					if(!WebUtil.isNullForList(usList))
					{
						for(UnitSkuMaster us:usList)
						{
							skuMap.put(us.getSkuCd(), us);
						}
					}
				}
				for(Map m:skuList)
				{
					UnitSkuMaster sku = skuMap.get(m.get("SkuCd"));
					if(sku==null)
					{
						 sku = new UnitSkuMaster();
							sku.setItemId(item.getId());
							sku.setSkuCd(item.getItemCd());
							sku.setStoreId(storeId);
							sku.setCompanyId(item.getCompanyId());
							sku.setCtime(new Date());	
					}
					sku.setSkuName((String)m.get("SkuName"));
					//sku.setSkuDesc((String)m.get("SkuDesc"));
					sku.setSkuPrice1((BigDecimal)m.get("SkuPrice"));
					if(sku.getId()==null)
						session.save(sku);
					else
						session.update(sku);
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
			return result;
		} catch (DataAccessException e) {
			result.put("Flag", "error");
			result.put("Message", e.getMessage());
			return result;
		}
		session.close();
		result.put("Flag", "success");
		return result;
	}
}
