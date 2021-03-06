
package com.sunmw.taobao.bean.order.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.bean.TaoBaoApiParams;
import com.sunmw.taobao.bean.order.TBOrderServices;
import com.sunmw.taobao.entity.TbLogisticsCompany;
import com.sunmw.taobao.entity.TbOrder;
import com.sunmw.taobao.entity.TbPromotionDetail;
import com.sunmw.taobao.entity.TbStore;
import com.sunmw.taobao.entity.TbTrade;
import com.sunmw.web.bean.order.OrderServices;
import com.sunmw.web.common.GetBeanServlet;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.PromotionDetail;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.LogisticsOfflineSendRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.request.TradesSoldIncrementvGetRequest;
import com.taobao.api.response.LogisticsOfflineSendResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;
import com.taobao.api.response.TradesSoldIncrementvGetResponse;

public class TBOrderServicesByThreadImpl extends HibernateDaoSupport implements
		TBOrderServices {
	static Logger logger = Logger.getLogger(TBOrderServicesByThreadImpl.class);
	private ScheduledThreadPoolExecutor tbOrderScheduledThreadPoolExecutor;

	public ScheduledThreadPoolExecutor getTbOrderScheduledThreadPoolExecutor() {
		return tbOrderScheduledThreadPoolExecutor;
	}

	public void setTbOrderScheduledThreadPoolExecutor(
			ScheduledThreadPoolExecutor tbOrderScheduledThreadPoolExecutor) {
		this.tbOrderScheduledThreadPoolExecutor = tbOrderScheduledThreadPoolExecutor;
	}

	// 增量下载订单
	public Map taobaoTradesSoldIncrementGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【增量下载订单】准备执行,店铺：" + storeId);
		// 店铺查询
		List<TbStore> storeList = this.getHibernateTemplate().find(
				"from TbStore where id = " + storeId);
		// 未登记此店铺
		if (storeList == null || storeList.size() == 0) {
			logger.error("未登记此店铺[" + storeId + "]");
			result.put("Flag", "error");
			return result;
		}
		final TbStore tbStore = storeList.get(0);
		// 此店铺状态为不可使用
		if (tbStore.getStatus() == null
				|| !tbStore.getStatus().equals("ACTIVE")) {
			logger.error("此店铺状态为不可使用[" + storeId + "]");
			result.put("Flag", "error");
			return result;
		}
		//logger.info("准备调用淘宝client");
		final TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		// 参数设置
		TradesSoldIncrementGetRequest ts = new TradesSoldIncrementGetRequest();
		//logger.info("准备调用淘宝API[" + ts.getApiMethodName() + "],设置参数");
		// 查询修改开始时间(修改时间跨度不能大于一天)。格式:yyyy-MM-dd HH:mm:ss
		ts.setStartModified((Date) params.get("startModified"));
		if( params.get("startModified")==null)
			ts.setStartModified(new Date());
		// 查询修改结束时间，必须大于修改开始时间(修改时间跨度不能大于一天)。格式:yyyy-MM-dd HH:mm:ss
		if(ts.getStartModified()!=null)
		{
			ts.setEndModified(new Date(ts.getStartModified().getTime()+24*60*60*1000-1000));
		}
		// 字段
		ts.setFields((String) params.get("fields"));
		// 交易状态，默认查询所有交易状态的数据，除了默认值外每次只能查询一种状态。 可选值
		// TRADE_NO_CREATE_PAY(没有创建支付宝交易) WAIT_BUYER_PAY(等待买家付款)
		// WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
		// WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)
		// TRADE_BUYER_SIGNED(买家已签收,货到付款专用) TRADE_FINISHED(交易成功)
		// TRADE_CLOSED(交易关闭) TRADE_CLOSED_BY_TAOBAO(交易被淘宝关闭)
		// ALL_WAIT_PAY(包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY)
		// ALL_CLOSED(包含：TRADE_CLOSED、TRADE_CLOSED_BY_TAOBAO)
		ts.setStatus((String) params.get("status"));
		// 卖家对交易的自定义分组标签，目前可选值为：time_card（点卡软件代充），fee_card（话费软件代充）
		ts.setTag((String) params.get("tag"));
		ts.setTimestamp((Long) params.get("timestamp"));
		// 交易类型列表，同时查询多种交易类型可用逗号分隔。默认同时查询guarantee_trade, auto_delivery, ec,
		// cod的4种交易类型的数据 。可选值： fixed(一口价) auction(拍卖) guarantee_trade(一口价、拍卖)
		// independent_simple_trade(旺店入门版交易) independent_shop_trade(旺店标准版交易)
		// auto_delivery(自动发货) ec(直冲) cod(货到付款) fenxiao(分销) game_equipment(游戏装备)
		// shopex_trade(ShopEX交易) netcn_trade(万网交易) external_trade(统一外部交易)
		// instant_trade (即时到账), b2c_cod(大商家货到付款)
		ts.setType((String) params.get("type"));
		// 页数
		if (WebUtil.isNotNull(params.get("pageSize")))
			ts.setPageSize(Long.parseLong(params.get("pageSize").toString()));
		else
			ts.setPageSize(50L);
		//logger.info("参数设置完成,准备执行");
		long pageNo = 1;
		//先取总记录数，再计算有多少页，从最后一页开始下载
		ts.setUseHasNext(false);
		TradesSoldIncrementGetResponse tr = null;
		try {
			long pageSize = ts.getPageSize();
			ts.setPageSize(1L);
			tr = client.execute(ts, tbStore
					.getSessionKey());
			if (tr.isSuccess())
			{
				long total = tr.getTotalResults();
				pageNo = total/pageSize+1;
				ts.setPageSize(pageSize);
				ts.setPageNo(pageNo);
			}
			else
			{
				logger.error("【增量下载订单】执行失败,[" + tr.getErrorCode() + ","
						+ tr.getMsg() + "]");
				result.put("Flag", "error");
				return result;
			}
				
		} catch (ApiException e1) {
			logger.error("【增量下载订单】执行失败,[" + e1.getMessage() + "]");
			result.put("Flag", "error");
			return result;
		}
		// 是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，成功率在原有的基础上有80%的提升。
		ts.setUseHasNext(true);
		// 记录总结果数
		int row = 0;
		// 订单下载
		while (true) {
			try {
				tr = client.execute(ts, tbStore
						.getSessionKey());
				// 接口执行成功
				if (tr.isSuccess()) {
					//logger.info("淘宝API执行成功,page[" + pageNo + "]");
					List<Trade> trades = tr.getTrades();
					if (WebUtil.isNullForList(trades))
						break;
					row += trades.size();
					for (Trade trade : trades) {
						final long tid = trade.getTid(); 
						tbOrderScheduledThreadPoolExecutor.schedule(new Runnable() {
							
							@Override
							public void run() {
								taobaoTradeFullinfo(tid,
										TaoBaoApiParams.TradeFullinfoGetFields, client,
										tbStore);
								
							}
						}, 0, TimeUnit.MILLISECONDS);
						
					}
					// 有下一页
					if (pageNo>1) {
						pageNo--;
						ts.setPageNo(pageNo);
					}
					// 无下一页
					else {
						break;
					}
				}
				// 接口执行失败
				else {
					logger.error("【增量下载订单】执行失败,[" + tr.getErrorCode() + ","
							+ tr.getMsg() + "]");
					break;
				}

			} catch (ApiException e) {
				logger.error("【增量下载订单】异常,[" + e.getErrCode() + "," + e.getErrMsg()
						+ "]");
				break;
			}
		}
		logger.info("【增量下载订单】执行成功，总记录：" + row);
		result.put("Flag", "success");
		return result;
	}
	
	// 增量下载订单
	public Map taobaoTradesSoldIncrementVGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【增量下载订单】准备执行,店铺：" + storeId);
		// 店铺查询
		List<TbStore> storeList = this.getHibernateTemplate().find(
				"from TbStore where id = " + storeId);
		// 未登记此店铺
		if (storeList == null || storeList.size() == 0) {
			logger.error("未登记此店铺[" + storeId + "]");
			result.put("Flag", "error");
			return result;
		}
		final TbStore tbStore = storeList.get(0);
		// 此店铺状态为不可使用
		if (tbStore.getStatus() == null
				|| !tbStore.getStatus().equals("ACTIVE")) {
			logger.error("此店铺状态为不可使用[" + storeId + "]");
			result.put("Flag", "error");
			return result;
		}
		//logger.info("准备调用淘宝client");
		final TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		// 参数设置
		TradesSoldIncrementvGetRequest ts = new TradesSoldIncrementvGetRequest();
		//logger.info("准备调用淘宝API[" + ts.getApiMethodName() + "],设置参数");
		// 查询修改开始时间(修改时间跨度不能大于一天)。格式:yyyy-MM-dd HH:mm:ss
		ts.setStartCreate((Date) params.get("startModified"));
		if( params.get("startModified")==null)
			ts.setStartCreate(new Date());
		// 查询修改结束时间，必须大于修改开始时间(修改时间跨度不能大于一天)。格式:yyyy-MM-dd HH:mm:ss
		if(ts.getStartCreate()!=null)
		{
			ts.setEndCreate(new Date(ts.getStartCreate().getTime()+24*60*60*1000-1000));
		}
		// 字段
		ts.setFields((String) params.get("fields"));
		// 交易状态，默认查询所有交易状态的数据，除了默认值外每次只能查询一种状态。 可选值
		// TRADE_NO_CREATE_PAY(没有创建支付宝交易) WAIT_BUYER_PAY(等待买家付款)
		// WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
		// WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)
		// TRADE_BUYER_SIGNED(买家已签收,货到付款专用) TRADE_FINISHED(交易成功)
		// TRADE_CLOSED(交易关闭) TRADE_CLOSED_BY_TAOBAO(交易被淘宝关闭)
		// ALL_WAIT_PAY(包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY)
		// ALL_CLOSED(包含：TRADE_CLOSED、TRADE_CLOSED_BY_TAOBAO)
		ts.setStatus((String) params.get("status"));
		// 卖家对交易的自定义分组标签，目前可选值为：time_card（点卡软件代充），fee_card（话费软件代充）
		ts.setTag((String) params.get("tag"));
		ts.setTimestamp((Long) params.get("timestamp"));
		// 交易类型列表，同时查询多种交易类型可用逗号分隔。默认同时查询guarantee_trade, auto_delivery, ec,
		// cod的4种交易类型的数据 。可选值： fixed(一口价) auction(拍卖) guarantee_trade(一口价、拍卖)
		// independent_simple_trade(旺店入门版交易) independent_shop_trade(旺店标准版交易)
		// auto_delivery(自动发货) ec(直冲) cod(货到付款) fenxiao(分销) game_equipment(游戏装备)
		// shopex_trade(ShopEX交易) netcn_trade(万网交易) external_trade(统一外部交易)
		// instant_trade (即时到账), b2c_cod(大商家货到付款)
		ts.setType((String) params.get("type"));
		// 页数
		if (WebUtil.isNotNull(params.get("pageSize")))
			ts.setPageSize(Long.parseLong(params.get("pageSize").toString()));
		else
			ts.setPageSize(50L);
		//logger.info("参数设置完成,准备执行");
		long pageNo = 1;
		//先取总记录数，再计算有多少页，从最后一页开始下载
		ts.setUseHasNext(false);
		TradesSoldIncrementvGetResponse tr = null;
		try {
			long pageSize = ts.getPageSize();
			ts.setPageSize(1L);
			tr = client.execute(ts, tbStore
					.getSessionKey());
			if (tr.isSuccess())
			{
				long total = tr.getTotalResults();
				pageNo = total/pageSize+1;
				ts.setPageSize(pageSize);
				ts.setPageNo(pageNo);
			}
			else
			{
				logger.error("【增量下载订单】执行失败,[" + tr.getErrorCode() + ","
						+ tr.getMsg() + "]");
				result.put("Flag", "error");
				return result;
			}
				
		} catch (ApiException e1) {
			logger.error("【增量下载订单】执行失败,[" + e1.getMessage() + "]");
			result.put("Flag", "error");
			return result;
		}
		// 是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，成功率在原有的基础上有80%的提升。
		ts.setUseHasNext(true);
		// 记录总结果数
		int row = 0;
		// 订单下载
		while (true) {
			try {
				tr = client.execute(ts, tbStore
						.getSessionKey());
				// 接口执行成功
				if (tr.isSuccess()) {
					//logger.info("淘宝API执行成功,page[" + pageNo + "]");
					List<Trade> trades = tr.getTrades();
					if (WebUtil.isNullForList(trades))
						break;
					row += trades.size();
					for (Trade trade : trades) {
						final long tid = trade.getTid(); 
						tbOrderScheduledThreadPoolExecutor.schedule(new Runnable() {
							
							@Override
							public void run() {
								taobaoTradeFullinfo(tid,
										TaoBaoApiParams.TradeFullinfoGetFields, client,
										tbStore);
								
							}
						}, 0, TimeUnit.MILLISECONDS);
						
					}
					// 有下一页
					if (pageNo>1) {
						pageNo--;
						ts.setPageNo(pageNo);
					}
					// 无下一页
					else {
						break;
					}
				}
				// 接口执行失败
				else {
					logger.error("【增量下载订单】执行失败,[" + tr.getErrorCode() + ","
							+ tr.getMsg() + "]");
					break;
				}

			} catch (ApiException e) {
				logger.error("【增量下载订单】异常,[" + e.getErrCode() + "," + e.getErrMsg()
						+ "]");
				break;
			}
		}
		logger.info("【增量下载订单】执行成功，总记录：" + row);
		result.put("Flag", "success");
		return result;
	}

	// 获得卖家的全部订单
	public Map taobaoTradesSoldGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【淘宝订单下载】准备执行,店铺：" + storeId);
		// 店铺查询
		List<TbStore> storeList = this.getHibernateTemplate().find(
				"from TbStore where id = " + storeId);
		// 未登记此店铺
		if (storeList == null || storeList.size() == 0) {
			logger.error("未登记此店铺[" + storeId + "]");
			result.put("Flag", "error");
			return result;
		}
		final TbStore tbStore = storeList.get(0);
		// 此店铺状态为不可使用
		if (tbStore.getStatus() == null
				|| !tbStore.getStatus().equals("ACTIVE")) {
			logger.error("此店铺状态为不可使用[" + storeId + "]");
			result.put("Flag", "error");
			return result;
		}
		//logger.info("准备调用淘宝client");
		final TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		// 参数设置
		TradesSoldGetRequest ts = new TradesSoldGetRequest();
		//logger.info("准备调用淘宝API[" + ts.getApiMethodName() + "],设置参数");
		ts.setBuyerNick((String) params.get("buyerNick"));
		ts.setEndCreated((Date) params.get("endCreated"));
		ts.setFields((String) params.get("fields"));
		ts.setRateStatus((String) params.get("rateStatus"));
		ts.setStartCreated((Date) params.get("startCreated"));
		ts.setStatus((String) params.get("status"));
		ts.setTag((String) params.get("tag"));
		ts.setType((String) params.get("type"));
		ts.setExtType((String) params.get("extType"));
		ts.setRateStatus((String) params.get("rateStatus"));
		// 页数
		if (WebUtil.isNotNull(params.get("pageSize")))
			ts.setPageSize(Long.parseLong(params.get("pageSize").toString()));
		else
			ts.setPageSize(50L);
		long pageNo = 1;
		//先取总记录数，再计算有多少页，从最后一页开始下载
		ts.setUseHasNext(false);
		TradesSoldGetResponse tr = null;
		try {
			long pageSize = ts.getPageSize();
			ts.setPageSize(1L);
			tr = client.execute(ts, tbStore
					.getSessionKey());
			if (tr.isSuccess())
			{
				long total = tr.getTotalResults();
				pageNo = total/pageSize+1;
				ts.setPageSize(pageSize);
				ts.setPageNo(pageNo);
			}
			else
			{
				logger.error("【增量下载订单】执行失败,[" + tr.getErrorCode() + ","
						+ tr.getMsg() + "]");
				result.put("Flag", "error");
				return result;
			}
				
		} catch (ApiException e1) {
			logger.error("【增量下载订单】执行失败,[" + e1.getMessage() + "]");
			result.put("Flag", "error");
			return result;
		}
		// 是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，成功率在原有的基础上有80%的提升。
		ts.setUseHasNext(true);
		//logger.info("参数设置完成,准备执行");
		// 记录总结果数
		int row = 0;
		// 订单下载
		while (true) {
			try {
				tr = client.execute(ts, tbStore
						.getSessionKey());
				// 接口执行成功
				if (tr.isSuccess()) {
					//logger.info("淘宝API执行成功,page[" + pageNo + "]");
					List<Trade> trades = tr.getTrades();
					if (WebUtil.isNullForList(trades))
						break;
					row += trades.size();
					for (Trade trade : trades) {
						final long tid = trade.getTid(); 
						tbOrderScheduledThreadPoolExecutor.schedule(new Runnable() {
							
							@Override
							public void run() {
								taobaoTradeFullinfo(tid,
										TaoBaoApiParams.TradeFullinfoGetFields, client,
										tbStore);
								
							}
						}, 0, TimeUnit.MILLISECONDS);
					}

					// 有下一页
					if (pageNo>1) {
						pageNo--;
						ts.setPageNo(pageNo);
					}
					// 无下一页
					else {
						break;
					}
				}
				// 接口执行失败
				else {
					logger.error("【淘宝订单下载】执行失败,[" + tr.getErrorCode() + ","
							+ tr.getMsg() + "]");
					break;
				}

			} catch (ApiException e) {
				logger.error("【淘宝订单下载】异常,[" + e.getErrCode() + "," + e.getErrMsg()
						+ "]");
			}
		}
		logger.info("【淘宝订单下载】执行成功，总记录：" + row);
		result.put("Flag", "success");
		return result;
	}

	// 获得订单的全部信息
	public Map taobaoTradeFullinfoGet(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		//logger.info("【获取单笔交易的详细信息】准备执行,店铺：" + storeId);
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
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		taobaoTradeFullinfo((Long) params.get("tid"), (String) params
				.get("fields"), client, tbStore);
		//logger.info("【获取单笔交易的详细信息】执行成功");
		result.put("Flag", "success");
		return result;
	}

	// 获得订单的全部信息,并保存到数据库
	//此方法使用同步，以保证数据的唯一性
	public void taobaoTradeFullinfo(long tid, String fields,
			TaobaoClient client, TbStore tbStore) {

		TradeFullinfoGetRequest tradeFullinfo = new TradeFullinfoGetRequest();
		if(fields==null)
			tradeFullinfo.setFields(TaoBaoApiParams.TradeFullinfoGetFields);
		else
			tradeFullinfo.setFields(fields);
		tradeFullinfo.setTid(tid);
		Date date = new Date();
		try {
			TradeFullinfoGetResponse tf = client.execute(tradeFullinfo, tbStore
					.getSessionKey());
			// 执行成功
			if (tf.isSuccess()) {
//				Session session = this.getSession();
//				Transaction tx = session.beginTransaction();
				Trade t = tf.getTrade();
				//此处直接插入到ERP的Order表中
				OrderServices orderServices = (OrderServices) GetBeanServlet.getBean("orderServices");
				Map updateOrderResult = orderServices.updateSingleOrderFromTb(formatTradeToMap(t), tbStore.getId());
				TbTrade tbTrade = null;
				List<TbTrade> tbTradeList = this.getHibernateTemplate().find(
						"from TbTrade where Tid = " + tid+" and StoreId = "+tbStore.getId());
				if (tbTradeList == null || tbTradeList.size() == 0)
				{
					tbTrade = new TbTrade();
					tbTrade.setStoreId(tbStore.getId());
				}
				else
					tbTrade = tbTradeList.get(0);
				tbTrade.setIsUpdateFinished(false);
				tbTrade.setEndTime(t.getEndTime());
				tbTrade.setSellerNick(t.getSellerNick());
				tbTrade.setBuyerNick(t.getBuyerNick());
				tbTrade.setTitle(t.getTitle());
				tbTrade.setType(t.getType());
				tbTrade.setCreated(t.getCreated());
				tbTrade.setIid(t.getIid());
				if (t.getPrice() != null)
					tbTrade.setPrice(BigDecimal.valueOf(Double.valueOf(t
							.getPrice())));
				tbTrade.setPicPath(t.getPicPath());
				if (t.getNum() != null)
					tbTrade.setNum(BigDecimal.valueOf(Double
							.valueOf(t.getNum())));
				if (t.getTid() != null)
					tbTrade.setTid(BigDecimal.valueOf(Double
							.valueOf(t.getTid())));
				tbTrade.setBuyerMessage(t.getBuyerMessage());
				tbTrade.setShippingType(t.getShippingType());
				tbTrade.setAlipayNo(t.getAlipayNo());
				if (t.getPayment() != null)
					tbTrade.setPayment(BigDecimal.valueOf(Double.valueOf(t
							.getPayment())));
				if (t.getDiscountFee() != null)
					tbTrade.setDiscountFee(BigDecimal.valueOf(Double.valueOf(t
							.getDiscountFee())));
				if (t.getAdjustFee() != null)
					tbTrade.setAdjustFee(BigDecimal.valueOf(Double.valueOf(t
							.getAdjustFee())));
				tbTrade.setSnapshotUrl(t.getSnapshotUrl());
				tbTrade.setSnapshot(t.getSnapshot());
				tbTrade.setStatus(t.getStatus());
				tbTrade.setSellerRate(t.getSellerRate());
				tbTrade.setBuyerRate(t.getBuyerRate());
				tbTrade.setBuyerMemo(t.getBuyerMemo());
				tbTrade.setSellerMemo(t.getSellerMemo());
				tbTrade.setTradeMemo(t.getTradeMemo());
				tbTrade.setPayTime(t.getPayTime());
				tbTrade.setModified(t.getModified());
				if (t.getBuyerObtainPointFee() != null)
					tbTrade.setBuyerObtainPointFee(BigDecimal.valueOf(Double
							.valueOf(t.getBuyerObtainPointFee())));
				if (t.getPointFee() != null)
					tbTrade.setPointFee(BigDecimal.valueOf(Double.valueOf(t
							.getPointFee())));
				if (t.getRealPointFee() != null)
					tbTrade.setRealPointFee(BigDecimal.valueOf(Double.valueOf(t
							.getRealPointFee())));
				if (t.getTotalFee() != null)
					tbTrade.setTotalFee(BigDecimal.valueOf(Double.valueOf(t
							.getTotalFee())));
				if (t.getPostFee() != null)
					tbTrade.setPostFee(BigDecimal.valueOf(Double.valueOf(t
							.getPostFee())));
				tbTrade.setBuyerAlipayNo(t.getBuyerAlipayNo());
				tbTrade.setReceiverName(t.getReceiverName());
				tbTrade.setReceiverState(t.getReceiverState());
				tbTrade.setReceiverCity(t.getReceiverCity());
				tbTrade.setReceiverDistrict(t.getReceiverDistrict());
				tbTrade.setReceiverAddress(t.getReceiverAddress());
				tbTrade.setReceiverZip(t.getReceiverZip());
				tbTrade.setReceiverMobile(t.getReceiverMobile());
				tbTrade.setReceiverPhone(t.getReceiverPhone());
				tbTrade.setConsignTime(t.getConsignTime());
				tbTrade.setBuyerEmail(t.getBuyerEmail());
				if (t.getCommissionFee() != null)
					tbTrade.setCommissionFee(BigDecimal.valueOf(Double
							.valueOf(t.getCommissionFee())));
				tbTrade.setSellerAlipayNo(t.getSellerAlipayNo());
				tbTrade.setSellerMobile(t.getSellerMobile());
				tbTrade.setSellerPhone(t.getSellerPhone());
				tbTrade.setSellerName(t.getSellerName());
				tbTrade.setSellerEmail(t.getSellerEmail());
				if (t.getAvailableConfirmFee() != null)
					tbTrade.setAvailableConfirmFee(BigDecimal.valueOf(Double
							.valueOf(t.getAvailableConfirmFee())));
				if (t.getHasPostFee() != null)
					tbTrade.setHasPostFee(t.getHasPostFee());
				if (t.getReceivedPayment() != null)
					tbTrade.setReceivedPayment(BigDecimal.valueOf(Double
							.valueOf(t.getReceivedPayment())));
				if (t.getCodFee() != null)
					tbTrade.setCodFee(BigDecimal.valueOf(Double.valueOf(t
							.getCodFee())));
				tbTrade.setCodStatus(t.getCodStatus());
				tbTrade.setTimeoutActionTime(t.getTimeoutActionTime());
				if (t.getIs3D() != null)
					tbTrade.setIs3d(t.getIs3D());
				if (t.getBuyerFlag() != null)
					tbTrade.setBuyerFlag(t.getBuyerFlag().intValue());
				if (t.getSellerFlag() != null)
					tbTrade.setSellerFlag(t.getSellerFlag().intValue());
				if (t.getNumIid() != null)
					tbTrade.setNumIid(BigDecimal.valueOf(Double.valueOf(t
							.getNumIid())));
				tbTrade.setPromotion(t.getPromotion());
				tbTrade.setInvoiceName(t.getInvoiceName());
				tbTrade.setTradeFrom(t.getTradeFrom());
				tbTrade.setAlipayUrl(t.getAlipayUrl());
				tbTrade.setUpdateTime(date);
				//如果订单成功更新，UpdateFlag标记为E
				if(updateOrderResult.get("Flag").equals("ERROR"))
					tbTrade.setUpdateFlag("D");
				else
					tbTrade.setUpdateFlag("E");
				if (tbTrade.getId() == null)
					this.getHibernateTemplate().save(tbTrade);
				else
					this.getHibernateTemplate().update(tbTrade);
				List<Order> orders = t.getOrders();
				// 无订单明细
				if (orders == null || orders.size() == 0) {
					logger.error("订单明细为空");
				} else {
					for (Order o : orders) {
						TbOrder tbOrder = null;
						List<TbOrder> tbOrderList = this.getHibernateTemplate().find(
								"from TbOrder where Oid = " + o.getOid());
						if (tbOrderList == null || tbOrderList.size() == 0)
							tbOrder = new TbOrder();
						else
							tbOrder = tbOrderList.get(0);
						if (o.getTotalFee() != null)
							tbOrder.setTotalFee(BigDecimal.valueOf(Double
									.valueOf(o.getTotalFee())));
						if (o.getDiscountFee() != null)
							tbOrder.setDiscountFee(BigDecimal.valueOf(Double
									.valueOf(o.getDiscountFee())));
						if (o.getAdjustFee() != null)
							tbOrder.setAdjustFee(BigDecimal.valueOf(Double
									.valueOf(o.getAdjustFee())));
						tbOrder.setIid(o.getIid());
						tbOrder.setSkuId(o.getSkuId());
						tbOrder.setSkuPropertiesName(o.getSkuPropertiesName());
						tbOrder.setItemMealName(o.getItemMealName());
						if (o.getNum() != null)
							tbOrder.setNum(BigDecimal.valueOf(Double.valueOf(o
									.getNum())));
						tbOrder.setTitle(o.getTitle());
						if (o.getPrice() != null)
							tbOrder.setPrice(BigDecimal.valueOf(Double
									.valueOf(o.getPrice())));
						tbOrder.setPicPath(o.getPicPath());
						tbOrder.setSellerNick(o.getSellerNick());
						tbOrder.setBuyerNick(o.getBuyerNick());
						tbOrder.setCreated(o.getModified());
						tbOrder.setRefundStatus(o.getRefundStatus());
						if (o.getOid() != null)
							tbOrder.setOid(BigDecimal.valueOf(Double.valueOf(o
									.getOid())));
						tbOrder.setOuterIid(o.getOuterIid());
						tbOrder.setOuterSkuId(o.getOuterSkuId());
						if (o.getPayment() != null)
							tbOrder.setPayment(BigDecimal.valueOf(Double
									.valueOf(o.getPayment())));
						tbOrder.setStatus(o.getStatus());
						tbOrder.setSnapshotUrl(o.getSnapshotUrl());
						tbOrder.setSnapshot(o.getSnapshot());
						tbOrder.setTimeoutActionTime(o.getTimeoutActionTime());
						if (o.getBuyerRate() != null)
							tbOrder.setBuyerRate(o.getBuyerRate());
						if (o.getSellerRate() != null)
							tbOrder.setSellerRate(o.getSellerRate());
						if (o.getRefundId() != null)
							tbOrder.setRefundId(BigDecimal.valueOf(Double
									.valueOf(o.getRefundId())));
						tbOrder.setSellerType(o.getSellerType());
						tbOrder.setModified(o.getModified());
						if (o.getNumIid() != null)
							tbOrder.setNumIid(BigDecimal.valueOf(Double
									.valueOf(o.getNumIid())));
						if (o.getCid() != null)
							tbOrder.setCid(BigDecimal.valueOf(Double.valueOf(o
									.getCid())));
						if (o.getIsOversold() != null)
							tbOrder.setIsOversold(o.getIsOversold());
						tbOrder.setTradeId(BigDecimal.valueOf(Double
								.valueOf(tid)));
						tbOrder.setUpdateTime(date);
						tbOrder.setUpdateFlag("D");
						tbOrder.setStoreId(tbStore.getId());
						if (tbOrder.getId() == null)
							this.getHibernateTemplate().save(tbOrder);
						else
							this.getHibernateTemplate().update(tbOrder);
					}
				}
				// 促销
				List<PromotionDetail> promotionDetailList = t
						.getPromotionDetails();
				if (promotionDetailList != null
						&& promotionDetailList.size() > 0
						&& promotionDetailList.get(0) != null) {
					for (PromotionDetail pd : promotionDetailList) {
						List<TbPromotionDetail> tbpdlist = this.getHibernateTemplate().find(
								"from TbPromotionDetail where TbPromotionDetailId = "
										+ pd.getId());
						TbPromotionDetail tbpd = null;
						if (tbpdlist == null || tbpdlist.size() == 0)
							tbpd = new TbPromotionDetail();
						else
							tbpd = tbpdlist.get(0);
						tbpd.setUpdateFlag("D");
						tbpd.setUpdateTime(date);
						tbpd.setStoreId(tbStore.getId());
						if (WebUtil.isNotNull(pd.getDiscountFee()))
							tbpd.setDiscountFee(new BigDecimal(pd
									.getDiscountFee()));
						tbpd.setGiftItemName(pd.getGiftItemName());
						tbpd.setTbPromotionDetailId(pd.getId());
						tbpd.setPromotionName(pd.getPromotionName());
						tbpd.setTradeId(BigDecimal.valueOf(t.getTid()
								.longValue()));
						if (tbpd.getId() == null)
							this.getHibernateTemplate().save(tbpd);
						else
							this.getHibernateTemplate().update(tbpd);
					}
				}
//				tx.commit();
//				session.close();
			}
			// 执行失败
			else {
				logger.error("插入订单失败");
			}
		} catch (ApiException e) {

			logger.error(e.getMessage());
		}
	}

	@Override
	public Map taobaoDeliverySend(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			result.put("Message", "店铺ID是空");
			return result;
		}
		List<Map> logisticsList = (List) params.get("LogisticsList");
		if(WebUtil.isNullForList(logisticsList))
		{
			logger.error("无物流信息");
			result.put("Flag", "error");
			result.put("Message", "无物流信息");
			return result;
		}
		// 接口执行消息
		logger.info("【淘宝发货信息上传】准备执行,店铺：" + storeId);
		// 店铺查询
		List<TbStore> storeList = this.getHibernateTemplate().find(
				"from TbStore where id = " + storeId);
		// 未登记此店铺
		if (storeList == null || storeList.size() == 0) {
			logger.error("未登记此店铺[" + storeId + "]");
			result.put("Flag", "error");
			result.put("Message", "未登记此店铺[" + storeId + "]");
			return result;
		}
		TbStore tbStore = storeList.get(0);
		// 此店铺状态为不可使用
		if (tbStore.getStatus() == null
				|| !tbStore.getStatus().equals("ACTIVE")) {
			logger.error("此店铺状态为不可使用[" + storeId + "]");
			result.put("Flag", "error");
			result.put("Message", "此店铺状态为不可使用[" + storeId + "]");
			return result;
		}
		logger.info("准备调用淘宝client");
		TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		//得到淘宝物流公司
		List<TbLogisticsCompany> tblcList = this.getHibernateTemplate().find("from TbLogisticsCompany");
		Map<String,TbLogisticsCompany> lcMap = new HashMap<String,TbLogisticsCompany>();
		if(tblcList!=null)
		{
			for(TbLogisticsCompany tblc:tblcList)
			{
				lcMap.put(tblc.getWhLogisticsName(), tblc);
			}
		}
		StringBuffer successTaobaoNos = new StringBuffer();//成功的淘宝订单号
		StringBuffer errorTaobaoNos = new StringBuffer();//失败的淘宝订单号
		for(Map<String,String> m:logisticsList)
		{
			LogisticsOfflineSendRequest locr = new LogisticsOfflineSendRequest();
			
			locr.setTid(Long.parseLong(m.get("TaobaoNo")));
			locr.setOutSid(m.get("OutSid"));
			//淘宝物流公司代码,通过对照表得到,如果对照表中无法得到则使用OTHER
			TbLogisticsCompany lc = lcMap.get(m.get("CompanyCode"));
			if(lc!=null)
				locr.setCompanyCode(lc.getCode());
			else
				locr.setCompanyCode("OTHER");
			try {
				LogisticsOfflineSendResponse ds = client.execute(locr,tbStore.getSessionKey());
				if(ds.isSuccess())
				{
					//物流信息上传成功
					if(ds.getShipping().getIsSuccess())
					{
						if(successTaobaoNos.length()>0)
							successTaobaoNos.append(",");
						successTaobaoNos.append("'"+m.get("TaobaoNo")+"'");						
					}
					//物流信息上传失败
					else
					{
						if(errorTaobaoNos.length()>0)
							errorTaobaoNos.append(",");
						errorTaobaoNos.append("'"+m.get("TaobaoNo")+"'");	
					}
				}
				else
				{
					logger.error("执行失败,[" + ds.getErrorCode() + ","
							+ ds.getMsg() + "]");
					result.put("Message", "执行失败,[" + ds.getErrorCode() + ","
							+ ds.getMsg() + "]");
				}
			} catch (ApiException e) {
				logger.error("异常,[" + e.getErrCode() + "," + e.getErrMsg()
						+ "]");
				result.put("Message", "异常,[" + e.getErrCode() + "," + e.getErrMsg()
						+ "]");
			}
			
		}
		logger.info("执行成功");
		result.put("Flag", "success");
		result.put("SuccessOrderNo", successTaobaoNos.toString());
		result.put("ErrorOrderNo", errorTaobaoNos.toString());
		return result;
	}

	@Override
	public Map taobaoCheckOrderMiss(Map params) {
		Map result = new HashMap();
		Integer storeId = (Integer) params.get("StoreId");
		if (storeId == null) {
			logger.error("店铺ID是空");
			result.put("Flag", "error");
			return result;
		}
		// 接口执行消息
		logger.info("【淘宝漏单检查】准备执行,店铺：" + storeId);
		// 店铺查询
		List<TbStore> storeList = this.getHibernateTemplate().find(
				"from TbStore where id = " + storeId);
		// 未登记此店铺
		if (storeList == null || storeList.size() == 0) {
			logger.error("未登记此店铺[" + storeId + "]");
			result.put("Flag", "error");
			return result;
		}
		final TbStore tbStore = storeList.get(0);
		// 此店铺状态为不可使用
		if (tbStore.getStatus() == null
				|| !tbStore.getStatus().equals("ACTIVE")) {
			logger.error("此店铺状态为不可使用[" + storeId + "]");
			result.put("Flag", "error");
			return result;
		}
		final TaobaoClient client = new DefaultTaobaoClient(tbStore.getSandboxUrl(),
				tbStore.getAppKey(), tbStore.getAppSercet());
		// 参数设置
		TradesSoldGetRequest ts = new TradesSoldGetRequest();
		ts.setFields("tid,status");
		ts.setStartCreated((Date) params.get("startCreated"));
		ts.setEndCreated((Date) params.get("endCreated"));
		// 页数
		if (WebUtil.isNotNull(params.get("pageSize")))
			ts.setPageSize(Long.parseLong(params.get("pageSize").toString()));
		else
			ts.setPageSize(50L);
		long pageNo = 1;
		//先取总记录数，再计算有多少页，从最后一页开始下载
		ts.setUseHasNext(false);
		TradesSoldGetResponse tr = null;
		try {
			long pageSize = ts.getPageSize();
			ts.setPageSize(1L);
			tr = client.execute(ts, tbStore
					.getSessionKey());
			if (tr.isSuccess())
			{
				long total = tr.getTotalResults();
				pageNo = total/pageSize+1;
				ts.setPageSize(pageSize);
				ts.setPageNo(pageNo);
			}
			else
			{
				logger.error("【增量下载订单】执行失败,[" + tr.getErrorCode() + ","
						+ tr.getMsg() + "]");
				result.put("Flag", "error");
				return result;
			}
				
		} catch (ApiException e1) {
			logger.error("【增量下载订单】执行失败,[" + e1.getMessage() + "]");
			result.put("Flag", "error");
			return result;
		}
		// 是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，成功率在原有的基础上有80%的提升。
		ts.setUseHasNext(true);
		StringBuffer returnTids = new StringBuffer();
		// 记录总结果数
		int row = 0;
		// 订单下载
		while (true) {
			try {
				tr = client.execute(ts, tbStore
						.getSessionKey());
				// 接口执行成功
				if (tr.isSuccess()) {
					List<Trade> trades = tr.getTrades();
					if (WebUtil.isNullForList(trades))
						break;
					StringBuffer tids = new StringBuffer();
					for (Trade trade : trades) {
						if(tids.length()>0)
							tids.append(",");
						tids.append(trade.getTid());
					}
					List<Object[]> ttList = this.getHibernateTemplate().find("select Tid,Status from TbTrade where Tid in ("+tids.toString()+")");
					if(!WebUtil.isNullForList(ttList))
					{
						Map tidMap = new HashMap();
						for(Object[] obj:ttList)
						{
							tidMap.put(obj[0], obj[1]);
						}
						for (Trade trade : trades) {
							//如果未下载订单或状态有更新则重新下载订单
							if(tidMap.get(trade.getTid().toString())==null||!trade.getStatus().equals(tidMap.get(trade.getTid().toString())))
							{

								final long tid = trade.getTid(); 
								tbOrderScheduledThreadPoolExecutor.schedule(new Runnable() {
									
									@Override
									public void run() {
										taobaoTradeFullinfo(tid,
												TaoBaoApiParams.TradeFullinfoGetFields, client,
												tbStore);
										
									}
								}, 0, TimeUnit.MILLISECONDS);
								
//								if(returnTids.length()>0)
//									returnTids.append(",");
//								returnTids.append(trade.getTid());

								row ++;
							}
							
						}
					}
					// 有下一页
					if (pageNo>1) {
						pageNo--;
						ts.setPageNo(pageNo);
					}
					// 无下一页
					else {
						break;
					}
				}
				// 接口执行失败
				else {
					logger.error("【淘宝漏单检查】执行失败,[" + tr.getErrorCode() + ","
							+ tr.getMsg() + "]");
					break;
				}

			} catch (ApiException e) {
				logger.error("【淘宝漏单检查】异常,[" + e.getErrCode() + "," + e.getErrMsg()
						+ "]");
			}
		}
		logger.info("【淘宝漏单检查】执行成功，总记录：" + row);
		result.put("Flag", "success");
		//result.put("Tids", returnTids.toString());
		return result;
	}
	
	private Map formatTradeToMap(Trade t)
	{
		Map r = new HashMap();
		r.put("Tid", t.getTid().toString());
		r.put("BuyerMessage", t.getBuyerMessage());
		r.put("Status", t.getStatus());
		r.put("OrigOrderType", "TAOBAO");
		r.put("BuyerNick", t.getBuyerNick());
		r.put("SellerNick", t.getSellerNick());
		r.put("ConsignTime", t.getConsignTime());
		r.put("PayTime", t.getPayTime());
		r.put("AlipayNo", t.getAlipayNo());
		r.put("ReceiverAddress", t.getReceiverAddress());
		r.put("ReceiverCity", t.getReceiverCity());
		r.put("ReceiverDistrict", t.getReceiverDistrict());
		r.put("ReceiverMobile", t.getReceiverMobile());
		r.put("ReceiverName", t.getReceiverName());
		r.put("ReceiverState", t.getReceiverState());
		r.put("ReceiverPhone", t.getReceiverPhone());
		r.put("ReceiverZip", t.getReceiverZip());
		r.put("BuyerEmail", t.getBuyerEmail());
		r.put("InvoiceName", t.getInvoiceName());
		r.put("TotalFee", new BigDecimal(t.getTotalFee()!=null?t.getTotalFee():"0"));
		r.put("Payment", new BigDecimal(t.getPayment()!=null?t.getPayment():"0"));
		r.put("PostFee", new BigDecimal(t.getPostFee()!=null?t.getPostFee():"0"));
		r.put("DiscountFee", new BigDecimal(t.getDiscountFee()!=null?t.getDiscountFee():"0"));
		r.put("PointFee", t.getPointFee()!=null?t.getPointFee().intValue():0);
		r.put("ObtainPoint", t.getBuyerObtainPointFee()!=null?t.getBuyerObtainPointFee().intValue():0);
		r.put("Created", t.getCreated());
		r.put("BuyerMemo", t.getBuyerMemo());
		r.put("SellerMemo", t.getSellerMemo());
		r.put("TradeMemo", t.getTradeMemo());
		if(!WebUtil.isNullForList(t.getOrders()))
		{
			List<Map> toList = new ArrayList();
			for(Order to:t.getOrders())
			{
				Map o = new HashMap();
				o.put("Oid", to.getOid().toString());
				o.put("OuterIid", to.getOuterIid());
				o.put("Title", to.getTitle());
				o.put("SkuId", to.getSkuId());
				o.put("Num", new BigDecimal(to.getNum()!=null?to.getNum():0));
				o.put("Price", new BigDecimal(to.getPrice()!=null?to.getPrice():"0"));
				o.put("AdjustFee", new BigDecimal(to.getAdjustFee()!=null?to.getAdjustFee():"0"));
				o.put("DiscountFee", new BigDecimal(to.getDiscountFee()!=null?to.getDiscountFee():"0"));
				o.put("OuterSkuId", to.getOuterSkuId());
				o.put("SkuPropertiesName", to.getSkuPropertiesName());
				o.put("TotalFee", new BigDecimal(to.getTotalFee()!=null?to.getTotalFee():"0"));
				toList.add(o);
			}
			r.put("Orders", toList);
		}
		if(!WebUtil.isNullForList(t.getPromotionDetails()))
		{
			List<Map> pdList = new ArrayList();
			for(PromotionDetail pd:t.getPromotionDetails())
			{
				Map p = new HashMap();
				p.put("Id", pd.getId().toString());
				p.put("DiscountFee", new BigDecimal(pd.getDiscountFee()!=null?pd.getDiscountFee():"0"));
				p.put("GiftItemName", pd.getGiftItemName());
				p.put("PromotionName", pd.getPromotionName());
				pdList.add(p);
			}
			r.put("PromotionDetails", pdList);
		}
		return r;
	}
}
