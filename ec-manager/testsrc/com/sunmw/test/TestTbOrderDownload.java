package com.sunmw.test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;


public class TestTbOrderDownload {
	@Test
	public void testTo()
	{
		ExecutorService ex = Executors.newFixedThreadPool(10);
		long a = System.currentTimeMillis();
		//logger.info("准备调用淘宝client");
		final TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",
				"12227029", "56494f3845f9d7790c15678a27e51467");
		// 参数设置
		TradesSoldGetRequest ts = new TradesSoldGetRequest();
		//logger.info("准备调用淘宝API[" + ts.getApiMethodName() + "],设置参数");
		// 查询修改开始时间(修改时间跨度不能大于一天)。格式:yyyy-MM-dd HH:mm:ss
		ts.setStartCreated(WebUtil.toDateForString("2012-12-01", "yyyy-MM-dd"));
		ts.setEndCreated(new Date());
		// 字段
		ts.setFields("tid");
		// 页数
		ts.setPageSize(50L);
		//logger.info("参数设置完成,准备执行");
		long pageNo = 1;
		//先取总记录数，再计算有多少页，从最后一页开始下载
		ts.setUseHasNext(false);
		TradesSoldGetResponse tr = null;
		try {
			long pageSize = ts.getPageSize();
			ts.setPageSize(1L);
			tr = client.execute(ts, "61022119d6b320f17111a36211b9af51d1949ce028d52a9608825099");
			if (tr.isSuccess())
			{
				long total = tr.getTotalResults();
				pageNo = total/pageSize+1;
				ts.setPageSize(pageSize);
				ts.setPageNo(pageNo);
			}
			else
			{
				
			}
				
		} catch (ApiException e1) {
			
		}
		// 是否启用has_next的分页方式，如果指定true,则返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段，通过此种方式获取增量交易，成功率在原有的基础上有80%的提升。
		ts.setUseHasNext(true);
		final Date date = new Date();
		final Date start = WebUtil.toDateForString("2012-12-01", "yyyy-MM-dd");
		// 记录总结果数
		int row = 0;
		final AtomicInteger cc = new AtomicInteger();
		final AtomicLong pz = new AtomicLong(pageNo);
		final String filds = "tid";
		// 订单下载
		while (true) {
			if(pz.get()==0)
				break;
			
			ex.execute(new Runnable() {
				public void run()
				{
					try {
						TradesSoldGetRequest fts = new TradesSoldGetRequest();
						fts.setStartCreated(start);
						fts.setEndCreated(date);
						// 字段
						fts.setFields(filds);
						fts.setPageSize(50L);
						fts.setUseHasNext(true);
						fts.setPageNo(pz.getAndDecrement());
						System.out.println(fts.getPageNo());
						TradesSoldGetResponse trr = client.execute(fts, "61022119d6b320f17111a36211b9af51d1949ce028d52a9608825099");

						// 接口执行成功
						if (trr.isSuccess()) {
							//logger.info("淘宝API执行成功,page[" + pageNo + "]");
							List<Trade> trades = trr.getTrades();
							if (WebUtil.isNullForList(trades))
							{
								System.out.println("is return!");
								return;
							}
							System.out.println(trades.size());
							for (Trade trade : trades) {
								final long tid = trade.getTid(); 
								cc.incrementAndGet();
							}
							System.out.println("cc:"+fts.getPageNo()+":"+cc.get());
						}
						// 接口执行失败
						else {
							System.out.println("is faild!");
							return;
						}

					} catch (ApiException e) {
						System.out.println(e.getErrMsg());
						return;
					}catch (RuntimeException e) {
						System.out.println(e.getMessage());
						return;
					}
				}
			});
			
		}
		System.out.println(ex.isShutdown());
		System.out.println("time:"+(System.currentTimeMillis()-a));
	}

}
