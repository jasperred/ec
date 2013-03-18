package com.sunmw.taobao.bean.refunds.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.TaoBaoApiParams;
import com.sunmw.taobao.bean.refunds.TbRefundsServices;
import com.sunmw.taobao.entity.TbRefund;
import com.sunmw.taobao.entity.TbStore;
import com.sunmw.web.bean.refund.RefundServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Refund;
import com.taobao.api.request.RefundGetRequest;
import com.taobao.api.request.RefundsReceiveGetRequest;
import com.taobao.api.response.RefundGetResponse;
import com.taobao.api.response.RefundsReceiveGetResponse;

public class TbRefundsServicesByFastImpl extends HibernateDaoSupport implements
		TbRefundsServices {

	public Map taobaoRefundsReceiveGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【退款下载】准备执行,店铺：" + storeId);
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
		int row = 0;
		try {
			TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
					tbStore.getAppKey(), tbStore.getAppSercet());
			// 参数设置
			RefundsReceiveGetRequest rrgr = new RefundsReceiveGetRequest();
			rrgr.setFields((String) params.get("fields"));
			rrgr.setStartModified((Date) params.get("startModified"));
			rrgr.setEndModified((Date) params.get("endModified"));
			rrgr.setBuyerNick((String) params.get("buyerNick"));
			rrgr.setStatus((String) params.get("status"));
			rrgr.setType((String) params.get("type"));
			long pageNo = 1;
			// 页数
			if (WebUtil.isNotNull(params.get("pageSize")))
				rrgr.setPageSize(Long.parseLong(params.get("pageSize").toString()));
			else
				rrgr.setPageSize(40L);
			row = 0;
			while (true) {
				try {
					RefundsReceiveGetResponse r = client.execute(rrgr, tbStore
							.getSessionKey());
					// 接口执行成功
					if (r.isSuccess()) {
						List<Refund> rl = r.getRefunds();
						if(rl!=null)
						{
							row += rl.size();
							for (Refund re : rl) {
								taobaoRefundGet(re.getRefundId(),
										TaoBaoApiParams.RefundGetFields, client,
										tbStore);
							}
						}
						else
						{
							Map<String,String> requestParam = r.getParams();
							StringBuffer str = new StringBuffer();
							Iterator<String> iter = requestParam.keySet().iterator();
							while(iter.hasNext())
							{
								String k = iter.next();
								if(str.length()>0)
									str.append(",");
								str.append(k+":"+requestParam.get(k));
							}
							logger.error("执行失败,[param:" +str.toString()+ "]");
						}
						// 有下一页
						if (r.getTotalResults() > pageNo * rrgr.getPageSize()) {
							pageNo++;
							rrgr.setPageNo(pageNo);
						}
						// 无下一页
						else {
							break;
						}
					}
					// 接口执行失败
					else {
						logger.error("执行失败,[" + r.getSubCode() + ","
								+ r.getSubMsg() + "]");
						break;
					}

				} catch (ApiException e) {
					logger.error("异常,[" + e.getErrCode() + "," + e.getErrMsg()
							+ "]");
					break;
				}
			}
		} catch (RuntimeException e) {
			logger.error("异常,[" + e.getMessage()+","+e.getCause()!=null?e.getCause().getMessage():""
					+ "]");
		}
		logger.info("执行成功，总记录：" + row);
		result.put("Flag", "success");
		return result;
	}

	public Map taobaoRefundGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		//logger.info("【退款详情】准备执行,店铺：" + storeId);
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
		//logger.info("准备调用淘宝client");
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		Long refundId = (Long) params.get("refundId");
		String fields = (String) params.get("fields");
		taobaoRefundGet(refundId, fields, client, tbStore);
		result.put("Flag", "success");
		return result;
	}

	public synchronized void taobaoRefundGet(long refundId, String fields,
			TaobaoClient client, TbStore tbStore) {
		RefundGetRequest rbr = new RefundGetRequest();
		if(fields==null)
			rbr.setFields(TaoBaoApiParams.RefundGetFields);
		else
			rbr.setFields(fields);
		rbr.setRefundId(refundId);
		try {
			RefundGetResponse r = client.execute(rbr, tbStore.getSessionKey());
			if (r.isSuccess()) {
				Refund re = r.getRefund();
				//直接导入到ERP中
				RefundServices refundServices = (RefundServices) GetBeanServlet.getBean("refundServices");
				Map refundUpdateMap = refundServices.updateSingleRefundFromTb(re, tbStore.getId());
				
				TbRefund tr = null;
				List<TbRefund> l = this.getHibernateTemplate().find(
						"from TbRefund where RefundId = " + re.getRefundId()+" and StoreId = "+tbStore.getId());
				if (l != null && l.size() > 0) {
					tr = l.get(0);
				} else
				{
					tr = new TbRefund();
					tr.setStoreId(tbStore.getId());
				}
				tr.setAddress(re.getAddress());
				tr.setAlipayNo(re.getAlipayNo());
				tr.setBuyerNick(re.getBuyerNick());
				tr.setCompanyName(re.getCompanyName());
				tr.setCreated(re.getCreated());
				tr.setDescription(re.getDesc());
				tr.setRefundId(BigDecimal.valueOf(Long
						.valueOf(re.getRefundId())));
				tr.setGoodReturnTime(re.getGoodReturnTime());
				tr.setGoodStatus(re.getGoodStatus());
				tr.setHasGoodReturn(re.getHasGoodReturn());
				tr.setIid(re.getIid());
				tr.setModified(re.getModified());
				tr.setNum(re.getNum().intValue());
				tr.setNumIid(re.getNumIid().intValue());
				tr.setOid(BigDecimal.valueOf(Long.valueOf(re.getOid())));
				tr.setOrderStatus(re.getOrderStatus());
				tr.setPayment(BigDecimal.valueOf(Double
						.valueOf(re.getPayment())));
				tr.setPrice(BigDecimal.valueOf(Double.valueOf(re.getPrice())));
				tr.setReason(re.getReason());
				tr.setRefundFee(BigDecimal.valueOf(Double.valueOf(re
						.getRefundFee())));
				tr.setSellerNick(re.getSellerNick());
				tr.setShippingType(re.getShippingType());
				tr.setSid(re.getSid());
				tr.setStatus(re.getStatus());
				tr.setTid(BigDecimal.valueOf(Long.valueOf(re.getTid())));
				tr.setTitle(re.getTitle());
				tr.setTotalFee(BigDecimal.valueOf(Double.valueOf(re
						.getTotalFee())));
				tr.setUpdateTime(new Date());
				//如果订单成功更新，UpdateFlag标记为E
				if(refundUpdateMap.get("Flag").equals("ERROR"))
					tr.setUpdateFlag("D");
				else
					tr.setUpdateFlag("E");
				if (tr.getId() == null)
					this.getHibernateTemplate().save(tr);
				else
					this.getHibernateTemplate().update(tr);
			} else {
				logger.error("下载【退款详情】失败");
			}
		} catch (ApiException e) {
			logger.error(e.getMessage());
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
		}
	}

	public List getNotUpdateTBRefund(int storeId) {
		List<TbRefund> l = this.getHibernateTemplate()
				.find(
						"from TbRefund where UpdateFlag = 'D' and StoreId = "
								+ storeId);
		return l;
	}

	// 淘宝订单导入到ERP后更新淘宝订单更新标记
	public void updateTbRefundStatusForImportErp(String refundIds) {
		if (refundIds == null || refundIds.trim().length() == 0)
			return;
		Session session = this.getSession();
		Transaction tx = session.beginTransaction();
		// 更新退货UpdateFlag状态
		session.createQuery(
				"update TbRefund set UpdateFlag = 'E' where RefundId in ("
						+ refundIds + ")").executeUpdate();
		tx.commit();
		session.close();
	}

}
