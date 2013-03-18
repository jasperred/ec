package com.sunmw.web.bean.dataInterface.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.TaoBaoApiParams;
import com.sunmw.taobao.bean.base.TbBaseServices;
import com.sunmw.taobao.bean.distribution.TbDistributionServices;
import com.sunmw.taobao.bean.inventory.TBInventoryServices;
import com.sunmw.taobao.bean.notify.TbNotifyServices;
import com.sunmw.taobao.bean.order.TBOrderServices;
import com.sunmw.taobao.bean.product.TbItemServices;
import com.sunmw.taobao.bean.product.TbProductServices;
import com.sunmw.taobao.bean.refunds.TbRefundsServices;
import com.sunmw.taobao.bean.shop.TaobaoShopServices;
import com.sunmw.taobao.entity.TbOrder;
import com.sunmw.taobao.entity.TbPromotionDetail;
import com.sunmw.taobao.entity.TbRefund;
import com.sunmw.taobao.entity.TbTrade;
import com.sunmw.web.bean.dataInterface.TaobaoInterfaceServices;
import com.sunmw.web.bean.inventory.InventoryServices;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.bean.product.ProductServices;
import com.sunmw.web.bean.refund.RefundServices;
import com.sunmw.web.bean.report.ReportServices;
import com.sunmw.web.bean.store.StoreServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class TaobaoInterfaceServicesImpl extends HibernateDaoSupport implements
		TaobaoInterfaceServices {

	static Logger logger = Logger.getLogger(TaobaoInterfaceServicesImpl.class);

	public Map syncTaobaoRefundToErp(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【同步淘宝退货单到本地】开始执行");
		Map result = new HashMap();
		RefundServices refundServices = (RefundServices) GetBeanServlet
				.getBean("refundServices");
		Session session = this.getSession();
		while (true) {
			Query q = session
					.createQuery("from TbRefund where UpdateFlag = 'D' and StoreId = "
							+ param.get("StoreId") + " order by Created");
			q.setMaxResults(200);
			List<TbRefund> refundList = q.list();
			if (WebUtil.isNullForList(refundList))
				break;
			String tids = refundServices.updateRefundFromTb(refundList);
			if (WebUtil.isNotNull(tids)) {
				this.getHibernateTemplate().bulkUpdate(
						"update TbRefund set UpdateFlag = 'E' where RefundId in ("
								+ tids + ") and StoreId = "
								+ param.get("StoreId"));
			}
		}
		session.close();
		result.put("Flag", "success");
		logger.info("接口【同步淘宝退货单到本地】执行结束,耗时："
				+ (System.currentTimeMillis() - ctm));
		return result;
	}

	public Map syncTaobaoOrderToErp(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【同步淘宝订单到本地】开始执行");
		Map result = new HashMap();
		Session session = this.getSession();
		while (true) {
			Query q = session
					.createQuery("from TbTrade tt,TbOrder to where tt.Tid = to.TradeId and tt.UpdateFlag = 'D' and tt.StoreId = "
							+ param.get("StoreId")
							+ " order by tt.PayTime,tt.Tid,to.Oid");
			q.setMaxResults(200);
			List<Object[]> tradeList = q.list();
			if (WebUtil.isNullForList(tradeList))
				break;
			StringBuffer findtids = new StringBuffer();
			for (Object[] obj : tradeList) {
				TbTrade tt = (TbTrade) obj[0];
				TbOrder to = (TbOrder) obj[1];
				if (findtids.length() > 0)
					findtids.append(",");
				findtids.append(tt.getTid().longValue());
			}
			List<TbPromotionDetail> tpdList = this.getHibernateTemplate().find(
					"from TbPromotionDetail where UpdateFlag = 'D' and StoreId = "
							+ param.get("StoreId") + " and TradeId in ("
							+ findtids.toString() + ")");
			Map<BigDecimal, List> pdMap = new HashMap();
			if (!WebUtil.isNullForList(tpdList)) {
				for (TbPromotionDetail pd : tpdList) {
					List l = pdMap.get(pd.getTradeId());
					if (l == null)
						l = new ArrayList();
					l.add(pd);
					pdMap.put(pd.getTradeId(), l);
				}
			}
			Map<Long, Map> tradeMap = new HashMap();
			for (Object[] obj : tradeList) {
				TbTrade tt = (TbTrade) obj[0];
				TbOrder to = (TbOrder) obj[1];
				Map m = tradeMap.get(tt.getTid().longValue());
				if (m == null) {
					m = new HashMap();
					m.put("Trade", tt);
					m.put("OrderPromotion", pdMap.get(tt.getTid()));
				}
				List toList = (List) m.get("Order");
				if (toList == null)
					toList = new ArrayList();
				toList.add(to);
				m.put("Order", toList);
				tradeMap.put(tt.getTid().longValue(), m);
			}
			List trList = new ArrayList();
			Iterator<Long> iter = tradeMap.keySet().iterator();
			while (iter.hasNext()) {
				trList.add(tradeMap.get(iter.next()));
			}
			OrderServices orderServices = (OrderServices) GetBeanServlet
					.getBean("orderServices");
			String tids = orderServices.updateOrderFromTb(trList);
			if (WebUtil.isNotNull(tids)) {
				this.getHibernateTemplate().bulkUpdate(
						"update TbTrade set UpdateFlag = 'E' where Tid in ("
								+ tids + ") and StoreId = "
								+ param.get("StoreId"));
				this.getHibernateTemplate().bulkUpdate(
						"update TbOrder set UpdateFlag = 'E' where TradeId in ("
								+ tids + ") and StoreId = "
								+ param.get("StoreId"));
				this.getHibernateTemplate().bulkUpdate(
						"update TbPromotionDetail set UpdateFlag = 'E' where TradeId in ("
								+ tids + ") and StoreId = "
								+ param.get("StoreId"));
			}
		}
		session.close();
		result.put("Flag", "success");
		logger.info("接口【同步淘宝订单到本地】执行结束,耗时："
				+ (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map taobaoTradesSoldIncrement(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【增量订单下载】开始执行");
		TBOrderServices tbOrderServices = (TBOrderServices) GetBeanServlet
				.getBean("tbOrderServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", param.get("StoreId"));
		tbParam.put("startModified", param.get("LastExecTime"));
		tbParam.put("fields", "tid");
		tbParam.put("status", param.get("status"));
		Map result = tbOrderServices.taobaoTradesSoldIncrementGet(tbParam);
		// Session session = this.getSession();
		// while (true) {
		// Query q = session
		// .createQuery("from TbTrade tt,TbOrder to where tt.Tid = to.TradeId and tt.UpdateFlag = 'D' and tt.StoreId = "
		// + param.get("StoreId")
		// + " order by tt.PayTime,tt.Tid,to.Oid");
		// q.setMaxResults(200);
		// List<Object[]> tradeList = q.list();
		// if (WebUtil.isNullForList(tradeList))
		// break;
		// StringBuffer findtids = new StringBuffer();
		// for (Object[] obj : tradeList) {
		// TbTrade tt = (TbTrade) obj[0];
		// TbOrder to = (TbOrder) obj[1];
		// if (findtids.length() > 0)
		// findtids.append(",");
		// findtids.append(tt.getTid().longValue());
		// }
		// List<TbPromotionDetail> tpdList = this.getHibernateTemplate().find(
		// "from TbPromotionDetail where UpdateFlag = 'D' and StoreId = "
		// + param.get("StoreId") + " and TradeId in ("
		// + findtids.toString() + ")");
		// Map<BigDecimal, List> pdMap = new HashMap();
		// if (!WebUtil.isNullForList(tpdList)) {
		// for (TbPromotionDetail pd : tpdList) {
		// List l = pdMap.get(pd.getTradeId());
		// if (l == null)
		// l = new ArrayList();
		// l.add(pd);
		// pdMap.put(pd.getTradeId(), l);
		// }
		// }
		// Map<Long, Map> tradeMap = new HashMap();
		// for (Object[] obj : tradeList) {
		// TbTrade tt = (TbTrade) obj[0];
		// TbOrder to = (TbOrder) obj[1];
		// Map m = tradeMap.get(tt.getTid().longValue());
		// if (m == null) {
		// m = new HashMap();
		// m.put("Trade", tt);
		// m.put("OrderPromotion", pdMap.get(tt.getTid()));
		// }
		// List toList = (List) m.get("Order");
		// if (toList == null)
		// toList = new ArrayList();
		// toList.add(to);
		// m.put("Order", toList);
		// tradeMap.put(tt.getTid().longValue(), m);
		// }
		// List trList = new ArrayList();
		// Iterator<Long> iter = tradeMap.keySet().iterator();
		// while (iter.hasNext()) {
		// trList.add(tradeMap.get(iter.next()));
		// }
		// OrderServices orderServices = (OrderServices) GetBeanServlet
		// .getBean("orderServices");
		// String tids = orderServices.updateOrderFromTb(trList);
		// if (WebUtil.isNotNull(tids)) {
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbTrade set UpdateFlag = 'E' where Tid in ("
		// + tids + ") and StoreId = "
		// + param.get("StoreId"));
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbOrder set UpdateFlag = 'E' where TradeId in ("
		// + tids + ") and StoreId = "
		// + param.get("StoreId"));
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbPromotionDetail set UpdateFlag = 'E' where TradeId in ("
		// + tids + ") and StoreId = "
		// + param.get("StoreId"));
		// }
		// }
		// session.close();
		logger.info("接口【增量订单下载】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}
	
	@Override
	public Map taobaoTradesSoldIncrementV(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【增量订单下载】开始执行");
		TBOrderServices tbOrderServices = (TBOrderServices) GetBeanServlet
				.getBean("tbOrderServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", param.get("StoreId"));
		tbParam.put("startModified", param.get("LastExecTime"));
		tbParam.put("fields", "tid");
		tbParam.put("status", param.get("status"));
		Map result = tbOrderServices.taobaoTradesSoldIncrementVGet(tbParam);
		logger.info("接口【增量订单下载】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map downloadTbCat(Map params) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【下载淘宝类目】开始执行");
		Map result = new HashMap();
		TbBaseServices tbBaseServices = (TbBaseServices) GetBeanServlet
				.getBean("tbBaseServices");
		if (WebUtil.isNull(params.get("StoreId"))) {
			result.put("Flag", "error");
			result.put("Message", "店铺ID没有传入");
			return result;
		}
		result = tbBaseServices.downloadTbCat(params);
		result.put("Flag", "success");
		logger.info("接口【下载淘宝类目】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map downloadTbLogisticsCompany(Map params) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【下载淘宝物流公司】开始执行");

		Map result = new HashMap();
		TbBaseServices tbBaseServices = (TbBaseServices) GetBeanServlet
				.getBean("tbBaseServices");
		if (WebUtil.isNull(params.get("StoreId"))) {
			result.put("Flag", "error");
			return result;
		}
		result = tbBaseServices.downloadTbLogisticsCompany(params);
		result.put("Flag", "success");
		logger
				.info("接口【下载淘宝物流公司】执行结束,耗时："
						+ (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map taobaoItemDownload(Map params) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【下载淘宝商品】开始执行");
		TbItemServices tbItemServices = (TbItemServices) GetBeanServlet
				.getBean("tbItemServices");
		Map result = tbItemServices.taobaoItemsOnsaleGet(params);
		//tbItemServices.taobaoItemsInventoryGet(params);
		logger.info("接口【下载淘宝商品】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map taobaoItemQuantityUpdate(Map params) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【库存更新】开始执行");
		InventoryServices inventoryServices = (InventoryServices) GetBeanServlet.getBean("inventoryServices");
		Map result = inventoryServices.getAvailableInvOfShop(params);
		if(result.get("Flag").toString().equalsIgnoreCase("error"))
			return result;
		TBInventoryServices tbInventoryServices = (TBInventoryServices) GetBeanServlet.getBean("tbInventoryServices");
		tbInventoryServices.updateTaobaoInventory((Integer)params.get("StoreId"), (List)result.get("SkuInvList"), null);
		result = inventoryServices.updateInvFlagOfShop(WebUtil.toMap("StoreId", params.get("StoreId")));
		logger.info("接口【库存更新】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return null;
	}

	@Override
	public Map taobaoProductsGet(Map params) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【淘宝产品下载】开始执行");
		TbProductServices tbProductServices = (TbProductServices) GetBeanServlet
				.getBean("tbProductServices");
		Map result = tbProductServices.taobaoProductsGet(params);
		logger.info("接口【淘宝产品下载】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map taobaoRefundsReceiveGet(Map params) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【淘宝退货下载】开始执行");
		Map result = null;
		try {
			TbRefundsServices tbRefundServices = (TbRefundsServices) GetBeanServlet
					.getBean("tbRefundServices");
			Map tbParam = new HashMap();
			tbParam.put("StoreId", params.get("StoreId"));
			tbParam.put("startModified", params.get("LastExecTime"));
			tbParam.put("fields", "refund_id");

			String format = "yyyy-MM-dd HH:mm:ss";
			if (WebUtil.isNotNull(params.get("endModified"))) {
				String ec = (String) params.get("endModified");
				if(WebUtil.toDateForString(ec, format
						.substring(0, ec.length())).after((Date)params.get("LastExecTime")))
				tbParam.put("endModified", WebUtil.toDateForString(ec, format
						.substring(0, ec.length())));
			}
			result = tbRefundServices.taobaoRefundsReceiveGet(tbParam);
		} catch (Exception e) {

			logger.info("接口【淘宝退货下载】执行失败,错误：" + e.getMessage()+","+e.getCause());
			return result;
		}
//		RefundServices refundServices = (RefundServices) GetBeanServlet
//				.getBean("refundServices");
		// Session session = this.getSession();
		// while (true) {
		// Query q = session
		// .createQuery("from TbRefund where UpdateFlag = 'D' and StoreId = "
		// + params.get("StoreId") + " order by Created");
		// q.setMaxResults(200);
		// List<TbRefund> refundList = q.list();
		// if (WebUtil.isNullForList(refundList))
		// break;
		// String tids = refundServices.updateRefundFromTb(refundList);
		// if (WebUtil.isNotNull(tids)) {
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbRefund set UpdateFlag = 'E' where RefundId in ("
		// + tids + ") and StoreId = "
		// + params.get("StoreId"));
		// }
		// }
		// session.close();
		logger.info("接口【淘宝退货下载】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map taobaoSellercatsListGet(Map params) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【淘宝店铺类目下载】开始执行");
		TaobaoShopServices tbShopServices = (TaobaoShopServices) GetBeanServlet
				.getBean("tbShopServices");
		Map result = tbShopServices.taobaoSellercatsListGet(params);
		logger
				.info("接口【淘宝店铺类目下载】执行结束,耗时："
						+ (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map taobaoTradesSoldGet(Map params) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【淘宝订单下载】开始执行");
		TBOrderServices tbOrderServices = (TBOrderServices) GetBeanServlet
				.getBean("tbOrderServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", params.get("StoreId"));
		tbParam.put("startModified", params.get("LastExecTime"));
		tbParam.put("fields", "tid");
		tbParam.put("status", params.get("status"));
		// 格式化日期
		String format = "yyyy-MM-dd HH:mm:ss";
		if (WebUtil.isNotNull(params.get("startCreated"))) {
			String sc = (String) params.get("startCreated");
			tbParam.put("startCreated", WebUtil.toDateForString(sc, format
					.substring(0, sc.length())));
		}
		if (WebUtil.isNotNull(params.get("endCreated"))) {
			String ec = (String) params.get("endCreated");
			tbParam.put("endCreated", WebUtil.toDateForString(ec, format
					.substring(0, ec.length())));
		}
		Map result = tbOrderServices.taobaoTradesSoldGet(tbParam);
		// Session session = this.getSession();
		// while (true) {
		// Query q = session
		// .createQuery("from TbTrade tt,TbOrder to where tt.Tid = to.TradeId and tt.UpdateFlag = 'D' and tt.StoreId = "
		// + params.get("StoreId")
		// + " order by tt.PayTime,tt.Tid,to.Oid");
		// q.setMaxResults(200);
		// List<Object[]> tradeList = q.list();
		// if (WebUtil.isNullForList(tradeList))
		// break;
		// StringBuffer findtids = new StringBuffer();
		// for (Object[] obj : tradeList) {
		// TbTrade tt = (TbTrade) obj[0];
		// TbOrder to = (TbOrder) obj[1];
		// if (findtids.length() > 0)
		// findtids.append(",");
		// findtids.append(tt.getTid().longValue());
		// }
		// List<TbPromotionDetail> tpdList = this.getHibernateTemplate().find(
		// "from TbPromotionDetail where UpdateFlag = 'D' and StoreId = "
		// + params.get("StoreId") + " and TradeId in ("
		// + findtids.toString() + ")");
		// Map<BigDecimal, List> pdMap = new HashMap();
		// if (!WebUtil.isNullForList(tpdList)) {
		// for (TbPromotionDetail pd : tpdList) {
		// List l = pdMap.get(pd.getTradeId());
		// if (l == null)
		// l = new ArrayList();
		// l.add(pd);
		// pdMap.put(pd.getTradeId(), l);
		// }
		// }
		// Map<Long, Map> tradeMap = new HashMap();
		// for (Object[] obj : tradeList) {
		// TbTrade tt = (TbTrade) obj[0];
		// TbOrder to = (TbOrder) obj[1];
		// Map m = tradeMap.get(tt.getTid().longValue());
		// if (m == null) {
		// m = new HashMap();
		// m.put("Trade", tt);
		// m.put("OrderPromotion", pdMap.get(tt.getTid()));
		// }
		// List toList = (List) m.get("Order");
		// if (toList == null)
		// toList = new ArrayList();
		// toList.add(to);
		// m.put("Order", toList);
		// tradeMap.put(tt.getTid().longValue(), m);
		// }
		// List trList = new ArrayList();
		// Iterator<Long> iter = tradeMap.keySet().iterator();
		// while (iter.hasNext()) {
		// trList.add(tradeMap.get(iter.next()));
		// }
		// OrderServices orderServices = (OrderServices) GetBeanServlet
		// .getBean("orderServices");
		// String tids = orderServices.updateOrderFromTb(trList);
		// if (WebUtil.isNotNull(tids)) {
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbTrade set UpdateFlag = 'E' where Tid in ("
		// + tids + ") and StoreId = "
		// + params.get("StoreId"));
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbOrder set UpdateFlag = 'E' where TradeId in ("
		// + tids + ") and StoreId = "
		// + params.get("StoreId"));
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbPromotionDetail set UpdateFlag = 'E' where TradeId in ("
		// + tids + ") and StoreId = "
		// + params.get("StoreId"));
		// }
		// }
		// session.close();
		// List<Object[]> tradeList =
		// this.getHibernateTemplate().find("from TbTrade tt,TbOrder to where tt.Tid = to.TradeId and tt.UpdateFlag = 'D' and tt.StoreId = "+params.get("StoreId")+" order by tt.PayTime,tt.Tid,to.Oid");
		// List<TbPromotionDetail> tpdList =
		// this.getHibernateTemplate().find("from TbPromotionDetail where UpdateFlag = 'D' and StoreId = "+params.get("StoreId"));
		// Map<BigDecimal,List> pdMap = new HashMap();
		// if(!WebUtil.isNullForList(tpdList))
		// {
		// for(TbPromotionDetail pd:tpdList)
		// {
		// List l = pdMap.get(pd.getTradeId());
		// if(l==null)
		// l = new ArrayList();
		// l.add(pd);
		// pdMap.put(pd.getTradeId(), l);
		// }
		// }
		// Map<Long,Map> tradeMap = new HashMap();
		// for(Object[] obj:tradeList)
		// {
		// TbTrade tt = (TbTrade) obj[0];
		// TbOrder to = (TbOrder) obj[1];
		// Map m = tradeMap.get(tt.getTid().longValue());
		// if(m==null)
		// {
		// m = new HashMap();
		// m.put("Trade", tt);
		// m.put("OrderPromotion", pdMap.get(tt.getTid()));
		// }
		// List toList = (List) m.get("Order");
		// if(toList==null)
		// toList = new ArrayList();
		// toList.add(to);
		// m.put("Order", toList);
		// }
		// List trList = new ArrayList();
		// Iterator<Long> iter = tradeMap.keySet().iterator();
		// while(iter.hasNext())
		// {
		// trList.add(tradeMap.get(iter.next()));
		// }
		// OrderServices orderServices =
		// (OrderServices)GetBeanServlet.getBean("orderServices");
		// String tids = orderServices.updateOrderFromTb(trList);
		// if(WebUtil.isNotNull(tids))
		// {
		// this.getHibernateTemplate().bulkUpdate("update TbTrade set UpdateFlag = 'E' where Tid in ("+tids+") and StoreId = "+params.get("StoreId"));
		// this.getHibernateTemplate().bulkUpdate("update TbOrder set UpdateFlag = 'E' where TradeId in ("+tids+") and StoreId = "+params.get("StoreId"));
		// this.getHibernateTemplate().bulkUpdate("update TbPromotionDetail set UpdateFlag = 'E' where TradeId in ("+tids+") and StoreId = "+params.get("StoreId"));
		// }
		logger.info("接口【淘宝订单下载】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map taobaoSkusPriceUpdate(Map param) {
		long ctm = System.currentTimeMillis();
		Map result = new HashMap();
		logger.info("接口【价格更新】开始执行");
		ProductServices productServices = (ProductServices) GetBeanServlet.getBean("productServices");
		List priceList = productServices.skuPriceUpdateList((Integer)param.get("StoreId"));
		if(WebUtil.isNullForList(priceList))
		{
			result.put("Flag", "success");
			result.put("Message", "无需要更新的价格信息");
			return result;
		}
		TbItemServices tbItemServices = (TbItemServices) GetBeanServlet.getBean("tbItemServices");
		result = tbItemServices.taobaoItemSkuPriceUpdate(WebUtil.toMap("StoreId", param.get("StoreId"),"SkuPriceList",priceList));
		if(result.get("Flag").toString().equalsIgnoreCase("error"))
			return result;
		result = productServices.updateSkuPriceFlagOfShop(WebUtil.toMap("StoreId", param.get("StoreId")));
		if(result.get("Flag").toString().equalsIgnoreCase("error"))
			return result;
		logger.info("接口【价格更新】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	public Map taobaoDeliverySend(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【上传发货信息】开始执行");
		Map result = new HashMap();
		OrderServices orderServices = (OrderServices) GetBeanServlet
				.getBean("orderServices");
		// 查询已发货状态订单
		Map m = orderServices.tbDeliveryOrder(param);
		if (m.get("Flag").equals("error")) {
			result.putAll(m);
			return result;
		}
		// 上传发货信息到淘宝
		List deliveryList = (List) m.get("DeliveryList");
		if (WebUtil.isNullForList(deliveryList)) {
			result.put("Flag", "success");
			result.put("Message", "无需要上传的发货信息");
			return result;
		}
		TBOrderServices tbOrderServices = (TBOrderServices) GetBeanServlet
				.getBean("tbOrderServices");
		m = tbOrderServices.taobaoDeliverySend(WebUtil.toMap("StoreId", param
				.get("StoreId"), "LogisticsList", deliveryList));
		if (m.get("Flag").equals("error")) {
			result.putAll(m);
			return result;
		}
		// 更新本地订单状态（发货成功或发货失败）
		m = orderServices.updateTbDeliveryStatus(WebUtil.toMap("StoreId", param
				.get("StoreId"), "SuccessOrderNo", m.get("SuccessOrderNo"),
				"ErrorOrderNo", param.get("ErrorOrderNo")));
		if (m.get("Flag").equals("error")) {
			result.putAll(m);
			return result;
		}
		result.put("Flag", "success");
		logger.info("接口【上传发货信息】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map dailyTaobaoSaleReport(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【日销售报表】开始执行");
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		ReportServices reportServices = (ReportServices) GetBeanServlet
				.getBean("reportServices");
		Date date = new Date();
		result.put("Flag", "success");
		result.put("Message", "日销售报表执行成功");
		List<Map> reportList = reportServices.searchSaleReport(storeId, WebUtil
				.formatDateString(date, "yyyy-MM-dd")
				+ " 00:00:00", WebUtil.formatDateString(date, "yyyy-MM-dd")
				+ " 23:59:59",null,null, "'DELIVERY','COMPLETE'", "ORDER");
		if (WebUtil.isNullForList(reportList)) {
			result.put("Message", "日销售报表无内容");
			return result;
		}
		exportSaleReport("daily_sale_report_"
				+ WebUtil.formatDateString(date, "yyyy-MM-dd") + ".xls",
				WebUtil.formatDateString(date, "yyyy-MM-dd"), reportList,
				storeId);
		logger.info("接口【日销售报表】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map dailyTaobaoRefundReport(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【日退货报表】开始执行");
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		ReportServices reportServices = (ReportServices) GetBeanServlet
				.getBean("reportServices");
		Date date = new Date();
		result.put("Flag", "success");
		result.put("Message", "日退货执行成功");
		List<Map> reportList = reportServices.searchRefundReport(storeId,
				WebUtil.formatDateString(date, "yyyy-MM-dd") + " 00:00:00",
				WebUtil.formatDateString(date, "yyyy-MM-dd") + " 23:59:59",
				null, null, "'DELIVERY','COMPLETE'", "ORDER");
		if (WebUtil.isNullForList(reportList)) {
			result.put("Message", "日退货报表无内容");
			return result;
		}
		exportRefundReport("daily_refund_report_"
				+ WebUtil.formatDateString(date, "yyyy-MM-dd") + ".xls",
				WebUtil.formatDateString(date, "yyyy-MM-dd"), reportList,
				storeId);
		logger.info("接口【日退货报表】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map monthTaobaoSaleReport(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【月销售报表】开始执行");
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		ReportServices reportServices = (ReportServices) GetBeanServlet
				.getBean("reportServices");
		Date date = new Date();
		String fromDate = null;
		String endDate = null;
		String m = WebUtil.formatDateString(date, "M");
		String y = WebUtil.formatDateString(date, "yyyy");
		if (m.equals("1")) {
			m = "12";
			y = "" + (new Integer(y) - 1);
		} else {
			m = "" + (new Integer(m) - 1);
		}
		m = WebUtil.addPrefix(m, 2, "0");
		fromDate = y + "-" + m + "-01 00:00:00";
		endDate = y + "-" + m + "-31 23:59:59";
		result.put("Flag", "success");
		result.put("Message", "月销售报表执行成功");
		List<Map> reportList = reportServices.searchSaleReport(storeId,
				fromDate, endDate,null,null, "'DELIVERY','COMPLETE'", "ORDER");
		if (WebUtil.isNullForList(reportList)) {
			result.put("Message", "月销售报表无内容");
			return result;
		}
		exportSaleReport("month_sale_report_"
				+ WebUtil.formatDateString(date, "yyyy-MM-dd") + ".xls",
				fromDate + "~" + endDate, reportList, storeId);
		logger.info("接口【月销售报表】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map monthTaobaoRefundReport(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【月退货报表】开始执行");
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		ReportServices reportServices = (ReportServices) GetBeanServlet
				.getBean("reportServices");
		Date date = new Date();
		String fromDate = null;
		String endDate = null;
		String m = WebUtil.formatDateString(date, "M");
		String y = WebUtil.formatDateString(date, "yyyy");
		if (m.equals("1")) {
			m = "12";
			y = "" + (new Integer(y) - 1);
		} else {
			m = "" + (new Integer(m) - 1);
		}
		m = WebUtil.addPrefix(m, 2, "0");
		fromDate = y + "-" + m + "-01 00:00:00";
		endDate = y + "-" + m + "-31 23:59:59";
		result.put("Flag", "success");
		result.put("Message", "月退货执行成功");
		List<Map> reportList = reportServices
				.searchRefundReport(storeId, fromDate, endDate, null, null,
						"'DELIVERY','COMPLETE'", "ORDER");
		if (WebUtil.isNullForList(reportList)) {
			result.put("Message", "月退货报表无内容");
			return result;
		}
		exportRefundReport("month_refund_report_"
				+ WebUtil.formatDateString(date, "yyyy-MM-dd") + ".xls",
				fromDate + "~" + endDate, reportList, storeId);
		logger.info("接口【月退货报表】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	// 销售报表导出
	private void exportSaleReport(String fileName, String d, List<Map> l,
			Integer storeId) {
		if (l == null || l.size() == 0)
			return;
		try {
			// 文件名称
			String path = WebConfigProperties.getProperties("report.sale.path");
			// 分不同公司路径
			if (WebUtil.isNotNull(storeId)) {
				StoreServices storeServices = (StoreServices) GetBeanServlet
						.getBean("storeServices");
				Map storeInfo = (Map) storeServices.storeInfo(
						WebUtil.toMap("StoreId", storeId)).get("StoreInfo");
				if (storeInfo != null
						&& WebUtil.isNotNull(storeInfo.get("CompanyId"))) {
					path = path + storeInfo.get("CompanyId") + "/";
				}
			}
			// 模板文件
			String template = WebConfigProperties
					.getProperties("report.sale.template");
			File tem = new File(template);
			// 模板文件不存在
			if (!tem.exists()) {

			}
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();
			File file = new File(path + fileName);

			if (!file.exists())
				file.createNewFile();
			// 读模板文件
			Workbook wbtem = jxl.Workbook.getWorkbook(tem);
			// 写入到新文件
			WritableWorkbook wwb = Workbook.createWorkbook(file, wbtem);
			WritableSheet ws = wwb.getSheet(0);
			// 写日期
			ws.addCell(new jxl.write.Label(1, 0, d));
			int r = 2;// 从第3行开始写
			String orderNo = null;
			String[] keys = new String[] { "RequestDate", "DeliveryDate",
					"OrderNo", "OrigOrderNo", "PostNo","#淘宝", "#销售", "OrderAmt",
					"FreightAmt", "PaymentAmt", "OrderItem.ItemCd","OrderItem.SkuCd",
					"OrderItem.Name", "OrderItem.Price", "OrderItem.Qty",
					"OrderItem.SubTotal", "BUYER_MESSAGE", "BuyerNick",
					"ReceiverName", "ReceiverMobile", "ReceiverPhone",
					"ReceiverState", "ReceiverCity", "ReceiverDistrict",
					"ReceiverAddress", "ReceiverZip" };
			for (Map m : l) {
				for (int i = 0; i < keys.length; i++) {
					if (keys[i].indexOf(".") >= 0)
						continue;
					if (keys[i].indexOf("#") >= 0) {
						ws.addCell(new jxl.write.Label(i, r, keys[i]
								.replaceAll("#", "")));
					} else if (WebUtil.isNull(m.get(keys[i]))) {
						ws.addCell(new jxl.write.Label(i, r, ""));
					} else if (m.get(keys[i]) instanceof java.math.BigDecimal) {
						ws
								.addCell(new jxl.write.Number(i, r, m
										.get(keys[i]) == null ? 0 : Double
										.parseDouble(m.get(keys[i]).toString())));
					} else {
						ws.addCell(new jxl.write.Label(i, r,
								m.get(keys[i]) == null ? "" : m.get(keys[i])
										.toString()));
					}
				}
				List<Map> orderItemList = (List) m.get("OrderItem");
				if (!WebUtil.isNullForList(orderItemList)) {
					for (Map item : orderItemList) {
						for (int i = 0; i < keys.length; i++) {
							if (keys[i].indexOf(".") < 0)
								continue;
							String k = keys[i]
									.substring(keys[i].indexOf(".") + 1);
							if (keys[i].indexOf("#") >= 0) {
								ws.addCell(new jxl.write.Label(i, r, keys[i]
										.replaceAll("#", "")));
							} else if (WebUtil.isNull(item.get(k))) {
								ws.addCell(new jxl.write.Label(i, r, ""));
							} else if (item.get(k) instanceof java.math.BigDecimal) {
								ws.addCell(new jxl.write.Number(i, r, item
										.get(k) == null ? 0 : Double
										.parseDouble(item.get(k).toString())));
							} else {
								ws.addCell(new jxl.write.Label(i, r, item
										.get(k) == null ? "" : item.get(k)
										.toString()));
							}
						}
						r++;
					}
				} else
					r++;
			}
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 退款报表导出
	private void exportRefundReport(String fileName, String d, List<Map> l,
			Integer storeId) {
		if (l == null || l.size() == 0)
			return;
		try {
			// 文件名称
			// String fileName = "refund_report_"+WebUtil.formatDateString(date,
			// "yyyyMMdd")+".xls";
			String path = WebConfigProperties
					.getProperties("report.refund.path");

			// 分不同公司路径
			if (WebUtil.isNotNull(storeId)) {
				StoreServices storeServices = (StoreServices) GetBeanServlet
						.getBean("storeServices");
				Map storeInfo = (Map) storeServices.storeInfo(
						WebUtil.toMap("StoreId", storeId)).get("StoreInfo");
				if (storeInfo != null
						&& WebUtil.isNotNull(storeInfo.get("CompanyId"))) {
					path = path + storeInfo.get("CompanyId") + "/";
				}
			}
			// 模板文件
			String template = WebConfigProperties
					.getProperties("report.refund.template");
			File tem = new File(template);
			// 模板文件不存在
			if (!tem.exists()) {

			}
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();
			File file = new File(path + fileName);

			if (!file.exists())
				file.createNewFile();
			// 读模板文件
			Workbook wbtem = jxl.Workbook.getWorkbook(tem);
			// 写入到新文件
			WritableWorkbook wwb = Workbook.createWorkbook(file, wbtem);
			WritableSheet ws = wwb.getSheet(0);
			// 写日期
			ws.addCell(new jxl.write.Label(1, 0, d));
			int r = 3;// 从第4行开始写
			String orderNo = null;
			String[] keys = new String[] { "RequestDate", "CompleteDate",
					"OrderNo", "OrigOrderNo", "RefOrderNo", "OrigPostNo",
					"#淘宝", "#退货", "OrderAmt", "RefundAmt","OrderItem.ItemCd", "OrderItem.SkuCd",
					"OrderItem.Name", "OrderItem.Price", "OrderItem.Qty",
					"OrderItem.SubTotal", "REFUND_DESC", "BuyerNick",
					"ReceiverName", "ReceiverMobile", "ReceiverPhone",
					"ReceiverState", "ReceiverCity", "ReceiverDistrict",
					"ReceiverAddress", "ReceiverZip" };
			for (Map m : l) {
				for (int i = 0; i < keys.length; i++) {
					if (keys[i].indexOf(".") >= 0)
						continue;
					if (keys[i].indexOf("#") >= 0) {
						ws.addCell(new jxl.write.Label(i, r, keys[i]
								.replaceAll("#", "")));
					} else if (WebUtil.isNull(m.get(keys[i]))) {
						ws.addCell(new jxl.write.Label(i, r, ""));
					} else if (m.get(keys[i]) instanceof java.math.BigDecimal) {
						ws
								.addCell(new jxl.write.Number(i, r, m
										.get(keys[i]) == null ? 0 : Double
										.parseDouble(m.get(keys[i]).toString())));
					} else {
						ws.addCell(new jxl.write.Label(i, r,
								m.get(keys[i]) == null ? "" : m.get(keys[i])
										.toString()));
					}
				}
				List<Map> orderItemList = (List) m.get("OrderItem");
				if (!WebUtil.isNullForList(orderItemList)) {
					for (Map item : orderItemList) {
						for (int i = 0; i < keys.length; i++) {
							if (keys[i].indexOf(".") < 0)
								continue;
							String k = keys[i]
									.substring(keys[i].indexOf(".") + 1);
							if (keys[i].indexOf("#") >= 0) {
								ws.addCell(new jxl.write.Label(i, r, keys[i]
										.replaceAll("#", "")));
							} else if (WebUtil.isNull(item.get(k))) {
								ws.addCell(new jxl.write.Label(i, r, ""));
							} else if (item.get(k) instanceof java.math.BigDecimal) {
								ws.addCell(new jxl.write.Number(i, r, item
										.get(k) == null ? 0 : Double
										.parseDouble(item.get(k).toString())));
							} else {
								ws.addCell(new jxl.write.Label(i, r, item
										.get(k) == null ? "" : item.get(k)
										.toString()));
							}
						}
						r++;
					}
				} else
					r++;
			}
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map taobaoDeliveryTemplate(Map param) {
		long ctm = System.currentTimeMillis();
		Map result = new HashMap();
		return result;
	}

	@Override
	public Map taobaoItemCats(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【淘宝类目下载】开始执行");
		TbBaseServices tbBaseServices = (TbBaseServices) GetBeanServlet
				.getBean("tbBaseServices");
		Map result = tbBaseServices.downloadTbCat(param);
		logger.info("接口【淘宝类目下载】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map checkTaobaoOrderMiss(Map params) {
		long ctm = System.currentTimeMillis();

		logger.info("接口【淘宝漏单检查】开始执行");
		TBOrderServices tbOrderServices = (TBOrderServices) GetBeanServlet
				.getBean("tbOrderServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", params.get("StoreId"));
		String checkDate = (String) params.get("CheckDate");
		long c = 0;
		if(WebUtil.isNotNull(checkDate))
			c = WebUtil.toDateForString(
					checkDate,
			"yyyy-MM-dd HH:mm:ss").getTime();
		else
			c=WebUtil.toDateForString(
				WebUtil.formatDateString(new Date(), "yyyy-MM-dd"),
				"yyyy-MM-dd").getTime();
		tbParam.put("startCreated", new Date(c - 72 * 60 * 60 * 1000 * 3));
		tbParam.put("endCreated", new Date(c));

		Map result = tbOrderServices.taobaoCheckOrderMiss(tbParam);
		String tids = (String) result.get("Tids");
		if (WebUtil.isNull(tids))
			return result;
		// Session session = this.getSession();
		//
		// Query q = session
		// .createQuery("from TbTrade tt,TbOrder to where tt.Tid = to.TradeId and tt.UpdateFlag = 'D' and tt.StoreId = "
		// + params.get("StoreId")
		// + " and tt.Tid in ("
		// + tids
		// + ") order by tt.PayTime,tt.Tid,to.Oid");
		// q.setMaxResults(200);
		// List<Object[]> tradeList = q.list();
		// if (WebUtil.isNullForList(tradeList))
		// return result;
		// List<TbPromotionDetail> tpdList = this.getHibernateTemplate().find(
		// "from TbPromotionDetail where UpdateFlag = 'D' and StoreId = "
		// + params.get("StoreId") + " and TradeId in ("
		// + tids+")");
		// Map<BigDecimal, List> pdMap = new HashMap();
		// if (!WebUtil.isNullForList(tpdList)) {
		// for (TbPromotionDetail pd : tpdList) {
		// List l = pdMap.get(pd.getTradeId());
		// if (l == null)
		// l = new ArrayList();
		// l.add(pd);
		// pdMap.put(pd.getTradeId(), l);
		// }
		// }
		// Map<Long, Map> tradeMap = new HashMap();
		// for (Object[] obj : tradeList) {
		// TbTrade tt = (TbTrade) obj[0];
		// TbOrder to = (TbOrder) obj[1];
		// Map m = tradeMap.get(tt.getTid().longValue());
		// if (m == null) {
		// m = new HashMap();
		// m.put("Trade", tt);
		// m.put("OrderPromotion", pdMap.get(tt.getTid()));
		// }
		// List toList = (List) m.get("Order");
		// if (toList == null)
		// toList = new ArrayList();
		// toList.add(to);
		// m.put("Order", toList);
		// tradeMap.put(tt.getTid().longValue(), m);
		// }
		// List trList = new ArrayList();
		// Iterator<Long> iter = tradeMap.keySet().iterator();
		// while (iter.hasNext()) {
		// trList.add(tradeMap.get(iter.next()));
		// }
		// OrderServices orderServices = (OrderServices) GetBeanServlet
		// .getBean("orderServices");
		// String ids = orderServices.updateOrderFromTb(trList);
		// if (WebUtil.isNotNull(ids)) {
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbTrade set UpdateFlag = 'E' where Tid in (" + ids
		// + ") and StoreId = " + params.get("StoreId"));
		// this.getHibernateTemplate()
		// .bulkUpdate(
		// "update TbOrder set UpdateFlag = 'E' where TradeId in ("
		// + ids + ") and StoreId = "
		// + params.get("StoreId"));
		// this.getHibernateTemplate()
		// .bulkUpdate(
		// "update TbPromotionDetail set UpdateFlag = 'E' where TradeId in ("
		// + ids + ") and StoreId = "
		// + params.get("StoreId"));
		// }
		//
		// session.close();
		logger.info("接口【淘宝漏单检查】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map taobaoTradeFullinfoGet(Map params) {
		long ctm = System.currentTimeMillis();
		// logger.info("接口【获取单笔交易的详细信息】开始执行");
		TBOrderServices tbOrderServices = (TBOrderServices) GetBeanServlet
				.getBean("tbOrderServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", params.get("StoreId"));
		tbParam.put("fields", TaoBaoApiParams.TradeFullinfoGetFields);
		tbParam.put("tid", params.get("Tid"));

		Map result = tbOrderServices.taobaoTradeFullinfoGet(tbParam);
		// Session session = this.getSession();
		//
		// Query q = session
		// .createQuery("from TbTrade tt,TbOrder to where tt.Tid = to.TradeId and tt.UpdateFlag = 'D' and tt.StoreId = "
		// + params.get("StoreId")
		// + " and tt.Tid = "
		// + params.get("Tid")
		// + " order by tt.PayTime,tt.Tid,to.Oid");
		// q.setMaxResults(200);
		// List<Object[]> tradeList = q.list();
		// if (WebUtil.isNullForList(tradeList))
		// return result;
		// List<TbPromotionDetail> tpdList = this.getHibernateTemplate().find(
		// "from TbPromotionDetail where UpdateFlag = 'D' and StoreId = "
		// + params.get("StoreId") + " and TradeId = "
		// + params.get("Tid"));
		// Map<BigDecimal, List> pdMap = new HashMap();
		// if (!WebUtil.isNullForList(tpdList)) {
		// for (TbPromotionDetail pd : tpdList) {
		// List l = pdMap.get(pd.getTradeId());
		// if (l == null)
		// l = new ArrayList();
		// l.add(pd);
		// pdMap.put(pd.getTradeId(), l);
		// }
		// }
		// Map<Long, Map> tradeMap = new HashMap();
		// for (Object[] obj : tradeList) {
		// TbTrade tt = (TbTrade) obj[0];
		// TbOrder to = (TbOrder) obj[1];
		// Map m = tradeMap.get(tt.getTid().longValue());
		// if (m == null) {
		// m = new HashMap();
		// m.put("Trade", tt);
		// m.put("OrderPromotion", pdMap.get(tt.getTid()));
		// }
		// List toList = (List) m.get("Order");
		// if (toList == null)
		// toList = new ArrayList();
		// toList.add(to);
		// m.put("Order", toList);
		// tradeMap.put(tt.getTid().longValue(), m);
		// }
		// List trList = new ArrayList();
		// Iterator<Long> iter = tradeMap.keySet().iterator();
		// while (iter.hasNext()) {
		// trList.add(tradeMap.get(iter.next()));
		// }
		// OrderServices orderServices = (OrderServices) GetBeanServlet
		// .getBean("orderServices");
		// String tids = orderServices.updateOrderFromTb(trList);
		// if (WebUtil.isNotNull(tids)) {
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbTrade set UpdateFlag = 'E' where Tid in (" + tids
		// + ") and StoreId = " + params.get("StoreId"));
		// this.getHibernateTemplate()
		// .bulkUpdate(
		// "update TbOrder set UpdateFlag = 'E' where TradeId in ("
		// + tids + ") and StoreId = "
		// + params.get("StoreId"));
		// this.getHibernateTemplate()
		// .bulkUpdate(
		// "update TbPromotionDetail set UpdateFlag = 'E' where TradeId in ("
		// + tids + ") and StoreId = "
		// + params.get("StoreId"));
		// }
		//
		// session.close();
		// logger.info("接口【获取单笔交易的详细信息】执行结束,耗时："
		// + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map taobaoRefundGet(Map params) {
		long ctm = System.currentTimeMillis();
		// logger.info("接口【单笔退款详情】开始执行");
		TbRefundsServices tbRefundServices = (TbRefundsServices) GetBeanServlet
				.getBean("tbRefundServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", params.get("StoreId"));
		tbParam.put("fields", TaoBaoApiParams.RefundGetFields);
		params.put("refundId", params.get("RefundId"));
		Map result = tbRefundServices.taobaoRefundsReceiveGet(tbParam);
		// RefundServices refundServices = (RefundServices) GetBeanServlet
		// .getBean("refundServices");
		// Session session = this.getSession();
		// Query q = session
		// .createQuery("from TbRefund where UpdateFlag = 'D' and StoreId = "
		// + params.get("StoreId")
		// + " and RefundId = "
		// + params.get("RefundId") + " order by Created");
		// q.setMaxResults(200);
		// List<TbRefund> refundList = q.list();
		// if (WebUtil.isNullForList(refundList))
		// return result;
		// String tids = refundServices.updateRefundFromTb(refundList);
		// if (WebUtil.isNotNull(tids)) {
		// this.getHibernateTemplate()
		// .bulkUpdate(
		// "update TbRefund set UpdateFlag = 'E' where RefundId in ("
		// + tids + ") and StoreId = "
		// + params.get("StoreId"));
		// }
		// session.close();
		// logger.info("接口【单笔退款详情】执行结束,耗时：" + (System.currentTimeMillis() -
		// ctm));
		return result;
	}

	@Override
	public Map taobaoCometDiscardInfo(Map params) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【获取丢失消息】开始执行");
		TbNotifyServices tbNotifyServices = (TbNotifyServices) GetBeanServlet
				.getBean("tbNotifyServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", params.get("StoreId"));
		tbParam.put("Start", params.get("Start"));
		tbParam.put("End", params.get("End"));
		Map result = tbNotifyServices.taobaoCometDiscardInfo(tbParam);
		if (!result.get("Flag").equals("success")) {
			return result;
		}
		String tids = (String) result.get("Tids");
		String refundIds = (String) result.get("RefundIds");
		// RefundServices refundServices = (RefundServices) GetBeanServlet
		// .getBean("refundServices");
		// Session session = this.getSession();
		// if (WebUtil.isNotNull(tids)) {
		// Query q = session
		// .createQuery("from TbTrade tt,TbOrder to where tt.Tid = to.TradeId and tt.UpdateFlag = 'D' and tt.StoreId = "
		// + params.get("StoreId")
		// + " and tt.Tid in ("
		// + tids
		// + ") order by tt.PayTime,tt.Tid,to.Oid");
		// List<Object[]> tradeList = q.list();
		// if (WebUtil.isNullForList(tradeList))
		// return result;
		// List<TbPromotionDetail> tpdList = this.getHibernateTemplate().find(
		// "from TbPromotionDetail where UpdateFlag = 'D' and StoreId = "
		// + params.get("StoreId") + " and TradeId in ("
		// + tids+")");
		// Map<BigDecimal, List> pdMap = new HashMap();
		// if (!WebUtil.isNullForList(tpdList)) {
		// for (TbPromotionDetail pd : tpdList) {
		// List l = pdMap.get(pd.getTradeId());
		// if (l == null)
		// l = new ArrayList();
		// l.add(pd);
		// pdMap.put(pd.getTradeId(), l);
		// }
		// }
		// Map<Long, Map> tradeMap = new HashMap();
		// for (Object[] obj : tradeList) {
		// TbTrade tt = (TbTrade) obj[0];
		// TbOrder to = (TbOrder) obj[1];
		// Map m = tradeMap.get(tt.getTid().longValue());
		// if (m == null) {
		// m = new HashMap();
		// m.put("Trade", tt);
		// m.put("OrderPromotion", pdMap.get(tt.getTid()));
		// }
		// List toList = (List) m.get("Order");
		// if (toList == null)
		// toList = new ArrayList();
		// toList.add(to);
		// m.put("Order", toList);
		// tradeMap.put(tt.getTid().longValue(), m);
		// }
		// List trList = new ArrayList();
		// Iterator<Long> iter = tradeMap.keySet().iterator();
		// while (iter.hasNext()) {
		// trList.add(tradeMap.get(iter.next()));
		// }
		// OrderServices orderServices = (OrderServices) GetBeanServlet
		// .getBean("orderServices");
		// String ids = orderServices.updateOrderFromTb(trList);
		// if (WebUtil.isNotNull(ids)) {
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbTrade set UpdateFlag = 'E' where Tid in ("
		// + ids + ") and StoreId = "
		// + params.get("StoreId"));
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbOrder set UpdateFlag = 'E' where TradeId in ("
		// + ids + ") and StoreId = "
		// + params.get("StoreId"));
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbPromotionDetail set UpdateFlag = 'E' where TradeId in ("
		// + ids + ") and StoreId = "
		// + params.get("StoreId"));
		// }
		// }
		// if (WebUtil.isNotNull(refundIds)) {
		// Query q = session
		// .createQuery("from TbRefund where UpdateFlag = 'D' and StoreId = "
		// + params.get("StoreId")
		// + " and RefundId in ("
		// + refundIds + ") order by Created");
		// List<TbRefund> refundList = q.list();
		// if (WebUtil.isNullForList(refundList))
		// return result;
		// String ids = refundServices.updateRefundFromTb(refundList);
		// if (WebUtil.isNotNull(ids)) {
		// this.getHibernateTemplate().bulkUpdate(
		// "update TbRefund set UpdateFlag = 'E' where RefundId in ("
		// + ids + ") and StoreId = "
		// + params.get("StoreId"));
		// }
		// }
		//
		// session.close();
		logger.info("接口【获取丢失消息】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}

	@Override
	public Map taobaoFenxiaoOrdersGet(Map param) {
		long ctm = System.currentTimeMillis();
		logger.info("接口【分销订单下载】开始执行");
		TbDistributionServices tbDistributionServices = (TbDistributionServices) GetBeanServlet
				.getBean("tbDistributionServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", param.get("StoreId"));
		tbParam.put("status", param.get("status"));
		tbParam.put("purchaseOrderId", param.get("purchaseOrderId"));
		tbParam.put("timeType", param.get("timeType"));
		tbParam.put("pageSize", param.get("pageSize"));
		tbParam.put("pageNo", param.get("pageNo"));
		Map result = null;
		//时间循环，淘宝要求时间范围不能超过7天
		if(WebUtil.isNotNull(param.get("startCreated")))
		{
			Date start = WebUtil.toDateForString(param.get("startCreated").toString(), "yyyy-MM-dd HH:mm:ss");
			Date end = null;
			if(WebUtil.isNull(param.get("endCreated")))
				end = new Date();
			else
				end = WebUtil.toDateForString(param.get("endCreated").toString(), "yyyy-MM-dd HH:mm:ss");
			while(true)
			{
				if(end.getTime()>start.getTime()+7*24*60*60*100)
				{
					tbParam.put("startCreated", start);
					tbParam.put("endCreated", new Date(start.getTime()+7*24*60*60*1000));
					
				}
				else
				{
					tbParam.put("startCreated", start);
					tbParam.put("endCreated", end);
				}
				result = tbDistributionServices.taobaoFenxiaoOrdersGet(tbParam);
				if(end.getTime()<=start.getTime()+7*24*60*60*100)
				{
					break;
				}
				start = new Date(start.getTime()+7*24*60*60*1000);
			}
		}
		else
		{
			Date d = (Date)param.get("LastExecTime");
			tbParam.put("startCreated", d);
			tbParam.put("endCreated", new Date(d.getTime()+7*24*60*60*1000));
			result = tbDistributionServices.taobaoFenxiaoOrdersGet(tbParam);
		}
		logger.info("接口【分销订单下载】执行结束,耗时：" + (System.currentTimeMillis() - ctm));
		return result;
	}
}