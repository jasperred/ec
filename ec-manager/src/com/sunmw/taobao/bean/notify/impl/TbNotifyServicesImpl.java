package com.sunmw.taobao.bean.notify.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.notify.TbNotifyServices;
import com.sunmw.taobao.bean.order.TBOrderServices;
import com.sunmw.taobao.bean.refunds.TbRefundsServices;
import com.sunmw.taobao.entity.TbStore;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.DiscardInfo;
import com.taobao.api.domain.NotifyRefund;
import com.taobao.api.domain.NotifyTrade;
import com.taobao.api.request.CometDiscardinfoGetRequest;
import com.taobao.api.request.IncrementRefundsGetRequest;
import com.taobao.api.request.IncrementTradesGetRequest;
import com.taobao.api.response.CometDiscardinfoGetResponse;
import com.taobao.api.response.IncrementRefundsGetResponse;
import com.taobao.api.response.IncrementTradesGetResponse;

public class TbNotifyServicesImpl extends HibernateDaoSupport implements
		TbNotifyServices {
	static Logger logger = Logger.getLogger(TbNotifyServicesImpl.class);

	@Override
	public Map taobaoCometDiscardInfo(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【主动业务通知-用户丢失消息】准备执行,店铺：" + storeId);
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
		// 参数设置
		CometDiscardinfoGetRequest ts = new CometDiscardinfoGetRequest();
		if (WebUtil.isNotNull(param.get("Start")))
			ts.setStart((Date) param.get("Start"));
		if (WebUtil.isNotNull(param.get("End")))
			ts.setEnd((Date) param.get("End"));
		if (WebUtil.isNotNull(param.get("Types")))
			ts.setTypes((String) param.get("Types"));
		if (WebUtil.isNotNull(param.get("UserId")))
			ts.setUserId((Long) param.get("UserId"));
		StringBuffer tids = new StringBuffer();
		StringBuffer refundIds = new StringBuffer();
		
		try {
			CometDiscardinfoGetResponse tr = client.execute(ts, tbStore
					.getSessionKey());
			if (tr.isSuccess()) {
				List<DiscardInfo> dfList = tr.getDiscardInfoList();
				if (!WebUtil.isNullForList(dfList)) {
					for (DiscardInfo df : dfList) {
						Date start = new Date(df.getStart());
						Date end = new Date(df.getEnd());
						// 订单
						if (df.getType().equalsIgnoreCase("trade")) {
							Map m = new HashMap();
							m.put("StoreId", storeId);
							m.put("StartModified", start);
							m.put("EndModified", end);
							Map r = taobaoIncrementTradesGet(m);
							if(r.get("Flag").equals("success")&&r.get("Tids")!=null)
							{
								if(tids.length()>0)
									tids.append(",");
								tids.append(r.get("Tids"));
							}
						}
						// 退货
						else if (df.getType().equalsIgnoreCase("refund")) {
							Map m = new HashMap();
							m.put("StoreId", storeId);
							m.put("StartModified", start);
							m.put("EndModified", end);
							Map r = taobaoIncrementRefundsGet(m);
							if(r.get("Flag").equals("success")&&r.get("RefundIds")!=null)
							{
								if(refundIds.length()>0)
									refundIds.append(",");
								refundIds.append(r.get("RefundIds"));
							}
						

						}
						// 商品
						else if (df.getType().equalsIgnoreCase("item")) {

						}

					}
				}
			} else {
				logger.error("执行失败,[" + tr.getErrorCode() + "," + tr.getMsg()
						+ "]");
			}
		} catch (ApiException e) {
			logger.error("执行失败,[" + e.getMessage() + "]");
		}
		logger.info("【主动业务通知-用户丢失消息】执行成功");
		result.put("Flag", "success");
		result.put("Tids", tids.toString());
		result.put("RefundIds", refundIds.toString());
		return result;
	}

	@Override
	public Map taobaoIncrementItemsGet(Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map taobaoIncrementRefundsGet(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【主动业务通知-增量退货单信息】准备执行,店铺：" + storeId);
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
		// 参数设置
		IncrementRefundsGetRequest ts = new IncrementRefundsGetRequest();
		if (WebUtil.isNotNull(param.get("Nick")))
			ts.setNick((String) param.get("Nick"));
		else
			ts.setNick(tbStore.getStoreNick());
		if (WebUtil.isNotNull(param.get("Status")))
			ts.setStatus((String) param.get("Status"));
		if (WebUtil.isNotNull(param.get("StartModified")))
			ts.setStartModified((Date) param.get("StartModified"));
		if (WebUtil.isNotNull(param.get("EndModified")))
			ts.setEndModified((Date) param.get("EndModified"));
		if (WebUtil.isNotNull(param.get("PageSize")))
			ts.setPageSize((Long) param.get("PageSize"));
		else
			ts.setPageSize(new Long(40));
		if (WebUtil.isNotNull(param.get("PageNo")))
			ts.setPageNo((Long) param.get("PageNo"));
		else
			ts.setPageNo(new Long(1));
		int error = 0;
		StringBuffer refundIds = new StringBuffer();//成功下载的Tid
		TbRefundsServices tbRefundServices = (TbRefundsServices) GetBeanServlet.getBean("tbRefundServices");
		while (true) {
			try {
				IncrementRefundsGetResponse tr = client.execute(ts, tbStore
						.getSessionKey());
				if (tr.isSuccess()) {
					List<NotifyRefund> refundList = tr.getNotifyRefunds();
					if(!WebUtil.isNullForList(refundList))
					{
						for(NotifyRefund nr:refundList)
						{
							Map m = new HashMap();
							m.put("StoreId", storeId);
							m.put("refundId", nr.getRid());
							tbRefundServices.taobaoRefundGet(m);
							if(refundIds.length()>0)
								refundIds.append(",");
							refundIds.append(nr.getRid());
						}
					}
					//执行结束
					if(tr.getTotalResults()<=ts.getPageNo()*ts.getPageSize())
					{
						break;
					}
					else
					{
						ts.setPageNo(ts.getPageNo()+1);
					}
					error = 0;
				} else {
					logger.error("执行失败,[" + tr.getErrorCode() + ","
							+ tr.getMsg() + "]");
					error++;
					if(error>3)
						break;
				}
			} catch (ApiException e) {
				logger.error("执行失败,[" + e.getMessage() + "]");
			}
		}
		logger.info("【主动业务通知-增量退货单信息】执行成功");
		result.put("Flag", "success");
		result.put("RefundIds", refundIds.toString());
		return result;
	}

	@Override
	public Map taobaoIncrementTradesGet(Map param) {
		Map result = new HashMap();
		Integer storeId = (Integer) param.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【主动业务通知-增量订单信息】准备执行,店铺：" + storeId);
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
		// 参数设置
		IncrementTradesGetRequest ts = new IncrementTradesGetRequest();
		if (WebUtil.isNotNull(param.get("Nick")))
			ts.setNick((String) param.get("Nick"));
		else
			ts.setNick(tbStore.getStoreNick());
		if (WebUtil.isNotNull(param.get("Type")))
			ts.setType((String) param.get("Type"));
		if (WebUtil.isNotNull(param.get("Status")))
			ts.setStatus((String) param.get("Status"));
		if (WebUtil.isNotNull(param.get("StartModified")))
			ts.setStartModified((Date) param.get("StartModified"));
		if (WebUtil.isNotNull(param.get("EndModified")))
			ts.setEndModified((Date) param.get("EndModified"));
		if (WebUtil.isNotNull(param.get("PageSize")))
			ts.setPageSize((Long) param.get("PageSize"));
		else
			ts.setPageSize(new Long(40));
		if (WebUtil.isNotNull(param.get("PageNo")))
			ts.setPageNo((Long) param.get("PageNo"));
		else
			ts.setPageNo(new Long(1));
		int error = 0;
		StringBuffer tids = new StringBuffer();//成功下载的Tid
		TBOrderServices tbOrderServices = (TBOrderServices) GetBeanServlet.getBean("tbOrderServices");
		while (true) {
			try {
				IncrementTradesGetResponse tr = client.execute(ts, tbStore
						.getSessionKey());
				if (tr.isSuccess()) {
					List<NotifyTrade> tradeList = tr.getNotifyTrades();
					if(!WebUtil.isNullForList(tradeList))
					{
						for(NotifyTrade nt:tradeList)
						{
							Map m = new HashMap();
							m.put("StoreId", storeId);
							m.put("tid", nt.getTid());
							tbOrderServices.taobaoTradeFullinfoGet(m);
							if(tids.length()>0)
								tids.append(",");
							tids.append(nt.getTid());
						}
					}
					//执行结束
					if(tr.getTotalResults()<=ts.getPageNo()*ts.getPageSize())
					{
						break;
					}
					else
					{
						ts.setPageNo(ts.getPageNo()+1);
					}
					error = 0;
				} else {
					logger.error("执行失败,[" + tr.getErrorCode() + ","
							+ tr.getMsg() + "]");
					error++;
					if(error>3)
						break;
				}
			} catch (ApiException e) {
				logger.error("执行失败,[" + e.getMessage() + "]");
			}
		}
		logger.info("【主动业务通知-增量订单信息】执行成功");
		result.put("Flag", "success");
		result.put("Tids", tids.toString());
		return result;
	}

}
