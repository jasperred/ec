package com.sunmw.taobao.bean.task.impl;

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

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.base.TbBaseServices;
import com.sunmw.taobao.bean.order.TBOrderServices;
import com.sunmw.taobao.bean.product.TbItemServices;
import com.sunmw.taobao.bean.product.TbProductServices;
import com.sunmw.taobao.bean.refunds.TbRefundsServices;
import com.sunmw.taobao.bean.shop.TaobaoShopServices;
import com.sunmw.taobao.bean.task.TaobaoTaskServices;
import com.sunmw.taobao.entity.TbOrder;
import com.sunmw.taobao.entity.TbPromotionDetail;
import com.sunmw.taobao.entity.TbRefund;
import com.sunmw.taobao.entity.TbTrade;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.bean.refund.RefundServices;
import com.sunmw.web.bean.report.ReportServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebConfigProperties;
import com.sunmw.web.util.WebUtil;

public class TaobaoTaskServicesImpl extends HibernateDaoSupport implements TaobaoTaskServices {

	@Override
	public synchronized Map taobaoTradesSoldIncrement(Map param) {
		TBOrderServices tbOrderServices = (TBOrderServices)GetBeanServlet.getBean("tbOrderServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", param.get("StoreId"));
		tbParam.put("startModified", param.get("LastExecTime"));
		tbParam.put("fields", "tid");
		tbParam.put("status", param.get("status"));
		Map result = tbOrderServices.taobaoTradesSoldIncrementGet(tbParam);
		Session session = this.getSession();
		while(true)
		{
			Query q = session.createQuery("from TbTrade tt,TbOrder to where tt.Tid = to.TradeId and tt.UpdateFlag = 'D' and tt.StoreId = "+param.get("StoreId")+" order by tt.PayTime,tt.Tid,to.Oid");
			q.setMaxResults(1000);
			List<Object[]> tradeList =q.list();
			if(WebUtil.isNullForList(tradeList))
				break;
			StringBuffer findtids = new StringBuffer();
			for(Object[] obj:tradeList)
			{
				TbTrade tt = (TbTrade) obj[0];
				TbOrder to = (TbOrder) obj[1];
				if(findtids.length()>0)
					findtids.append(",");
				findtids.append(tt.getTid().longValue());
			}
			List<TbPromotionDetail> tpdList = this.getHibernateTemplate().find("from TbPromotionDetail where UpdateFlag = 'D' and StoreId = "+param.get("StoreId") +" and TradeId in ("+findtids.toString()+")");
			Map<BigDecimal,List> pdMap = new HashMap();
			if(!WebUtil.isNullForList(tpdList))
			{
				for(TbPromotionDetail pd:tpdList)
				{
					List l = pdMap.get(pd.getTradeId());
					if(l==null)
						l = new ArrayList();
					l.add(pd);
					pdMap.put(pd.getTradeId(), l);
				}
			}
			Map<Long,Map> tradeMap = new HashMap();
			for(Object[] obj:tradeList)
			{
				TbTrade tt = (TbTrade) obj[0];
				TbOrder to = (TbOrder) obj[1];
				Map m = tradeMap.get(tt.getTid().longValue());
				if(m==null)
				{
					m = new HashMap();
					m.put("Trade", tt);
					m.put("OrderPromotion", pdMap.get(tt.getTid()));
				}
				List toList = (List) m.get("Order");
				if(toList==null)
					toList = new ArrayList();
				toList.add(to);
				m.put("Order", toList);
				tradeMap.put(tt.getTid().longValue(), m);
			}
			List trList = new ArrayList();
			Iterator<Long> iter = tradeMap.keySet().iterator();
			while(iter.hasNext())
			{
				trList.add(tradeMap.get(iter.next()));
			}
			OrderServices orderServices = (OrderServices)GetBeanServlet.getBean("orderServices");
			String tids = orderServices.updateOrderFromTb(trList);
			if(WebUtil.isNotNull(tids))
			{
				this.getHibernateTemplate().bulkUpdate("update TbTrade set UpdateFlag = 'E' where Tid in ("+tids+") and StoreId = "+param.get("StoreId"));
				this.getHibernateTemplate().bulkUpdate("update TbOrder set UpdateFlag = 'E' where TradeId in ("+tids+") and StoreId = "+param.get("StoreId"));
				this.getHibernateTemplate().bulkUpdate("update TbPromotionDetail set UpdateFlag = 'E' where TradeId in ("+tids+") and StoreId = "+param.get("StoreId"));
			}
		}
		session.close();
		return result;
	}

	@Override
	public synchronized Map downloadTbCat(Map params) {
		Map result = new HashMap();
		TbBaseServices tbBaseServices = (TbBaseServices) GetBeanServlet.getBean("tbBaseServices");
		if(WebUtil.isNull(params.get("StoreId")))
		{
			result.put("Flag", "error");
			return result;
		}
		result = tbBaseServices.downloadTbCat(params);
		result.put("Flag", "success");
		return result;
	}

	@Override
	public synchronized Map downloadTbLogisticsCompany(Map params) {

		Map result = new HashMap();
		TbBaseServices tbBaseServices = (TbBaseServices) GetBeanServlet.getBean("tbBaseServices");
		if(WebUtil.isNull(params.get("StoreId")))
		{
			result.put("Flag", "error");
			return result;
		}
		result = tbBaseServices.downloadTbLogisticsCompany(params);
		result.put("Flag", "success");
		return result;
	}

	@Override
	public synchronized Map taobaoItemDownload(Map params) {
		TbItemServices tbItemServices = (TbItemServices) GetBeanServlet.getBean("tbItemServices");
		Map result = tbItemServices.taobaoItemsOnsaleGet(params);
		tbItemServices.taobaoItemsInventoryGet(params);
		return result;
	}

	@Override
	public synchronized Map taobaoItemQuantityUpdate(Map params) {
		return null;
	}

	@Override
	public synchronized Map taobaoProductsGet(Map params) {
		TbProductServices tbProductServices = (TbProductServices)GetBeanServlet.getBean("tbProductServices");
		Map result = tbProductServices.taobaoProductsGet(params);
		return result;
	}

	@Override
	public synchronized Map taobaoRefundsReceiveGet(Map params) {
		TbRefundsServices tbRefundServices = (TbRefundsServices)GetBeanServlet.getBean("tbRefundServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", params.get("StoreId"));
		tbParam.put("startModified", params.get("LastExecTime"));
		tbParam.put("fields", "refund_id");
		params.put("endModified", params.get("endModified"));
		Map result = tbRefundServices.taobaoRefundsReceiveGet(tbParam);
		List<TbRefund> l = this.getHibernateTemplate().find("from TbRefund where UpdateFlag = 'D' and StoreId = "+params.get("StoreId"));
		RefundServices refundServices = (RefundServices)GetBeanServlet.getBean("refundServices");
		String tids = refundServices.updateRefundFromTb(l);
		if(WebUtil.isNotNull(tids))
		{
			this.getHibernateTemplate().bulkUpdate("update TbRefund set UpdateFlag = 'E' where Tid in ("+tids+") and StoreId = "+params.get("StoreId"));
		}
		return result;
	}

	@Override
	public synchronized Map taobaoSellercatsListGet(Map params) {
		TaobaoShopServices tbShopServices = (TaobaoShopServices) GetBeanServlet.getBean("tbShopServices");
		Map result = tbShopServices.taobaoSellercatsListGet(params);
		return result;
	}

	@Override
	public synchronized Map taobaoTradesSoldGet(Map params) {
		TBOrderServices tbOrderServices = (TBOrderServices)GetBeanServlet.getBean("tbOrderServices");
		Map tbParam = new HashMap();
		tbParam.put("StoreId", params.get("StoreId"));
		tbParam.put("startModified", params.get("LastExecTime"));
		tbParam.put("fields", "tid");
		tbParam.put("status", params.get("status"));
		tbParam.put("startCreated", params.get("startCreated"));
		tbParam.put("endCreated", params.get("endCreated"));
		Map result = tbOrderServices.taobaoTradesSoldGet(tbParam);
		List<Object[]> tradeList = this.getHibernateTemplate().find("from TbTrade tt,TbOrder to where tt.Tid = to.TradeId and tt.UpdateFlag = 'D' and tt.StoreId = "+params.get("StoreId")+" order by tt.PayTime,tt.Tid,to.Oid");
		List<TbPromotionDetail> tpdList = this.getHibernateTemplate().find("from TbPromotionDetail where UpdateFlag = 'D' and StoreId = "+params.get("StoreId"));
		Map<BigDecimal,List> pdMap = new HashMap();
		if(!WebUtil.isNullForList(tpdList))
		{
			for(TbPromotionDetail pd:tpdList)
			{
				List l = pdMap.get(pd.getTradeId());
				if(l==null)
					l = new ArrayList();
				l.add(pd);
				pdMap.put(pd.getTradeId(), l);
			}
		}
		Map<Long,Map> tradeMap = new HashMap();
		for(Object[] obj:tradeList)
		{
			TbTrade tt = (TbTrade) obj[0];
			TbOrder to = (TbOrder) obj[1];
			Map m = tradeMap.get(tt.getTid().longValue());
			if(m==null)
			{
				m = new HashMap();
				m.put("Trade", tt);
				m.put("OrderPromotion", pdMap.get(tt.getTid()));
			}
			List toList = (List) m.get("Order");
			if(toList==null)
				toList = new ArrayList();
			toList.add(to);
			m.put("Order", toList);
		}
		List trList = new ArrayList();
		Iterator<Long> iter = tradeMap.keySet().iterator();
		while(iter.hasNext())
		{
			trList.add(tradeMap.get(iter.next()));
		}
		OrderServices orderServices = (OrderServices)GetBeanServlet.getBean("orderServices");
		String tids = orderServices.updateOrderFromTb(trList);
		if(WebUtil.isNotNull(tids))
		{
			this.getHibernateTemplate().bulkUpdate("update TbTrade set UpdateFlag = 'E' where Tid in ("+tids+") and StoreId = "+params.get("StoreId"));
			this.getHibernateTemplate().bulkUpdate("update TbOrder set UpdateFlag = 'E' where TradeId in ("+tids+") and StoreId = "+params.get("StoreId"));
			this.getHibernateTemplate().bulkUpdate("update TbPromotionDetail set UpdateFlag = 'E' where TradeId in ("+tids+") and StoreId = "+params.get("StoreId"));
		}
		return result;
	}

	@Override
	public synchronized Map taobaoSkusPriceUpdate(Map param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map taobaoDeliverySend(Map param)
	{
		Map result = new HashMap();
		result.put("Flag", "success");
		//得到发货信息
		OrderServices orderServices = (OrderServices)GetBeanServlet.getBean("orderServices");
		Map m = orderServices.tbDeliveryOrder(param);
		List<Map> deliveryList = (List) m.get("DeliveryList");
		if(WebUtil.isNullForList(deliveryList))
		{
			logger.info("无发货信息");
			return result;
		}
		//更新发货信息到淘宝
		TBOrderServices tbOrderServices = (TBOrderServices)GetBeanServlet.getBean("tbOrderServices");
		param.put("LogisticsList", deliveryList);
		m = tbOrderServices.taobaoDeliverySend(param);
		String flag = (String) m.get("Flag");
		if(flag.equals("error"))
		{
			logger.error(m.get("Message"));
			result.put("Message", m.get("Message"));
			result.put("Flag", "success");
			return result;
		}
		//更新订单状态
		String successTaobaoNos = (String) m.get("SuccessOrderNo");
		String errorTaobaoNos = (String) m.get("ErrorOrderNo");
		param.put("SuccessOrderNo", successTaobaoNos);
		param.put("ErrorOrderNo", errorTaobaoNos);
		m = orderServices.updateTbDeliveryStatus(param);
		flag = (String) m.get("Flag");
		if(flag.equals("error"))
		{
			logger.error(m.get("Message"));
			result.put("Message", m.get("Message"));
			result.put("Flag", "success");
			return result;
		}
		return result;
	}

	@Override
	public Map dailyTaobaoSaleReport(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		ReportServices reportServices = (ReportServices) GetBeanServlet.getBean("reportServices");
		Date date = new Date();	
		result.put("Flag", "success");
		result.put("Message", "日销售报表执行成功");	
		List<Map> reportList = reportServices.searchSaleReport(storeId, WebUtil.formatDateString(date, "yyyy-MM-dd")+" 00:00:00", WebUtil.formatDateString(date, "yyyy-MM-dd")+" 23:59:59",null,null, "'DELIVERY','COMPLETE'","ORDER");
		if(WebUtil.isNullForList(reportList))
		{
			result.put("Message", "日销售报表无内容");	
			return result;
		}
		exportSaleReport("daily_sale_report_"+WebUtil.formatDateString(date, "yyyy-MM-dd")+".xls",WebUtil.formatDateString(date, "yyyy-MM-dd"),reportList);
		return result;
	}

	@Override
	public Map dailyTaobaoRefundReport(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		ReportServices reportServices = (ReportServices) GetBeanServlet.getBean("reportServices");
		Date date = new Date();	
		result.put("Flag", "success");
		result.put("Message", "日退货执行成功");	
		List<Map> reportList = reportServices.searchRefundReport(storeId, WebUtil.formatDateString(date, "yyyy-MM-dd")+" 00:00:00", WebUtil.formatDateString(date, "yyyy-MM-dd")+" 23:59:59",null,null, "'DELIVERY','COMPLETE'","ORDER");
		if(WebUtil.isNullForList(reportList))
		{
			result.put("Message", "日退货报表无内容");	
			return result;
		}
		exportRefundReport("daily_refund_report_"+WebUtil.formatDateString(date, "yyyy-MM-dd")+".xls",WebUtil.formatDateString(date, "yyyy-MM-dd"),reportList);
		return result;
	}
	
	//销售报表导出
	private void exportSaleReport(String fileName, String d, List<Map> l) {
		if(l==null||l.size()==0)
			return;
		try {
			//文件名称
			String path = WebConfigProperties
					.getProperties("report.sale.path");
			//模板文件
			String template= WebConfigProperties.getProperties("report.sale.template");
			File tem = new File(template);
			//模板文件不存在
			if(!tem.exists())
			{
				
			}
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();
			File file = new File(path + fileName);
			
			if (!file.exists())
				file.createNewFile();
			//读模板文件
			Workbook wbtem = jxl.Workbook.getWorkbook(tem);
			//写入到新文件
			WritableWorkbook wwb = Workbook.createWorkbook(file,wbtem);
			WritableSheet ws = wwb.getSheet(0);
			//写日期
			ws.addCell(new jxl.write.Label(1, 0,d));
			int r = 2;// 从第3行开始写
			String orderNo = null;
			String[] keys = new String[] {  
					"RequestDate", "DeliveryDate", "OrderNo",
					"OrigOrderNo", "#淘宝", "#销售",
					"OrderAmt", "FreightAmt",
					"PaymentAmt", "OrderItem.SkuCd", "OrderItem.Name",
					"OrderItem.Price", "OrderItem.Qty","OrderItem.SubTotal", "BUYER_MESSAGE","BuyerNick","ReceiverName","ReceiverMobile","ReceiverPhone","ReceiverState","ReceiverCity","ReceiverDistrict","ReceiverAddress","ReceiverZip"};
			for (Map m : l) {
				for(int i=0;i<keys.length;i++)
				{
					if(keys[i].indexOf(".")>=0)
						continue;
					if(keys[i].indexOf("#")>=0)
					{
						ws.addCell(new jxl.write.Label(i, r,keys[i].replaceAll("#", "")));
					}
					else if(WebUtil.isNull(m.get(keys[i])))
					{
						ws.addCell(new jxl.write.Label(i, r,""));
					}
					else if (m.get(keys[i]) instanceof java.math.BigDecimal)
					{
						ws.addCell(new jxl.write.Number(i, r,m.get(keys[i])==null?0:Double.parseDouble(m.get(keys[i]).toString())));
					}
					else
					{
						ws.addCell(new jxl.write.Label(i, r,m.get(keys[i])==null?"":m.get(keys[i]).toString()));
					}
				}
				List<Map> orderItemList = (List) m.get("OrderItem");
				if(!WebUtil.isNullForList(orderItemList))
				{
					for(Map item:orderItemList)
					{
						for(int i=0;i<keys.length;i++)
						{
							if(keys[i].indexOf(".")<0)
								continue;
							String k = keys[i].substring(keys[i].indexOf(".")+1);
							if(keys[i].indexOf("#")>=0)
							{
								ws.addCell(new jxl.write.Label(i, r,keys[i].replaceAll("#", "")));
							}
							else if(WebUtil.isNull(item.get(k)))
							{
								ws.addCell(new jxl.write.Label(i, r,""));
							}
							else if (item.get(k) instanceof java.math.BigDecimal)
							{
								ws.addCell(new jxl.write.Number(i, r,item.get(k)==null?0:Double.parseDouble(item.get(k).toString())));
							}
							else
							{
								ws.addCell(new jxl.write.Label(i, r,item.get(k)==null?"":item.get(k).toString()));
							}
						}
						r++;
					}
				}
				else
					r++;
			}
//			for (Map m : l) {
//				for(int i=0;i<keys.length;i++)
//				{
//					if(keys[i].equals("OrderAmt")||keys[i].equals("FreightAmt")||keys[i].equals("PaymentAmt"))
//					{
//						ws.addCell(new jxl.write.Number(i, r,m.get(keys[i])==null?0:Double.parseDouble(m.get(keys[i]).toString())));
//					}
//					else
//						ws.addCell(new jxl.write.Label(i, r,m.get(keys[i])==null?"":m.get(keys[i]).toString()));
//				}
//				List<Map> itemList = (List)m.get("OrderItem");
//				if(itemList!=null&&itemList.size()>0)
//				{
//					for(Map i:itemList)
//					{
//						ws.addCell(new jxl.write.Label(9, r,i.get("SkuCd")==null?"":i.get("SkuCd").toString()));
//						ws.addCell(new jxl.write.Label(10, r,i.get("Name")==null?"":i.get("Name").toString()));
//						ws.addCell(new jxl.write.Number(11, r,i.get("Price")==null?0:Double.parseDouble(i.get("Price").toString())));
//						ws.addCell(new jxl.write.Number(12, r,i.get("Qty")==null?0:Double.parseDouble(i.get("Qty").toString())));
//						ws.addCell(new jxl.write.Number(13, r,i.get("SubTotal")==null?0:Double.parseDouble(i.get("SubTotal").toString())));
//						r++;
//					}
//				}
//				else
//					r++;
//			}
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
	//退款报表导出
	private void exportRefundReport(String fileName, String d, List<Map> l) {
		if(l==null||l.size()==0)
			return;
		try {
			//文件名称
			//String fileName = "refund_report_"+WebUtil.formatDateString(date, "yyyyMMdd")+".xls";
			String path = WebConfigProperties
					.getProperties("report.refund.path");
			//模板文件
			String template= WebConfigProperties.getProperties("report.refund.template");
			File tem = new File(template);
			//模板文件不存在
			if(!tem.exists())
			{
				
			}
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();
			File file = new File(path + fileName);
			
			if (!file.exists())
				file.createNewFile();
			//读模板文件
			Workbook wbtem = jxl.Workbook.getWorkbook(tem);
			//写入到新文件
			WritableWorkbook wwb = Workbook.createWorkbook(file,wbtem);
			WritableSheet ws = wwb.getSheet(0);
			//写日期
			ws.addCell(new jxl.write.Label(1, 0,d));
			int r = 3;// 从第4行开始写
			String orderNo = null;
			String[] keys = new String[] {  
					"RequestDate", "CompleteDate", "OrderNo",
					"OrigOrderNo","RefOrderNo", "#淘宝", "#退货","OrderAmt","RefundAmt",
					"OrderItem.SkuCd", "OrderItem.Name",
					"OrderItem.Price", "OrderItem.Qty","OrderItem.SubTotal","","REFUND_DESC","BuyerNick","ReceiverName","ReceiverMobile","ReceiverPhone","ReceiverState","ReceiverCity","ReceiverDistrict","ReceiverAddress","ReceiverZip"};
			for (Map m : l) {
				for(int i=0;i<keys.length;i++)
				{
					if(keys[i].indexOf(".")>=0)
						continue;
					if(keys[i].indexOf("#")>=0)
					{
						ws.addCell(new jxl.write.Label(i, r,keys[i].replaceAll("#", "")));
					}
					else if(WebUtil.isNull(m.get(keys[i])))
					{
						ws.addCell(new jxl.write.Label(i, r,""));
					}
					else if (m.get(keys[i]) instanceof java.math.BigDecimal)
					{
						ws.addCell(new jxl.write.Number(i, r,m.get(keys[i])==null?0:Double.parseDouble(m.get(keys[i]).toString())));
					}
					else
					{
						ws.addCell(new jxl.write.Label(i, r,m.get(keys[i])==null?"":m.get(keys[i]).toString()));
					}
				}
				List<Map> orderItemList = (List) m.get("OrderItem");
				if(!WebUtil.isNullForList(orderItemList))
				{
					for(Map item:orderItemList)
					{
						for(int i=0;i<keys.length;i++)
						{
							if(keys[i].indexOf(".")<0)
								continue;
							String k = keys[i].substring(keys[i].indexOf(".")+1);
							if(keys[i].indexOf("#")>=0)
							{
								ws.addCell(new jxl.write.Label(i, r,keys[i].replaceAll("#", "")));
							}
							else if(WebUtil.isNull(item.get(k)))
							{
								ws.addCell(new jxl.write.Label(i, r,""));
							}
							else if (item.get(k) instanceof java.math.BigDecimal)
							{
								ws.addCell(new jxl.write.Number(i, r,item.get(k)==null?0:Double.parseDouble(item.get(k).toString())));
							}
							else
							{
								ws.addCell(new jxl.write.Label(i, r,item.get(k)==null?"":item.get(k).toString()));
							}
						}
						r++;
					}
				}
				else
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

}
