package com.sunmw.taobao.test;

import java.util.Date;

import com.sunmw.web.util.WebUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;

public class TaobaoTest {
	private TaobaoClient client;
	private String sandboxUrl = "http://gw.api.taobao.com";
	private String appKey = "12227029";
	private String appSercet = "56494f3845f9d7790c15678a27e51467";
	private String sessionKey = "61022119d6b320f17111a36211b9af51d1949ce028d52a9608825099";
	
	public TaobaoTest()
	{
		client = new DefaultTaobaoClient(sandboxUrl,
				appKey, appSercet);
	}
	
	public void testTaobaoSoldGet()
	{
		long l = System.currentTimeMillis();
		TradesSoldGetRequest ts = new TradesSoldGetRequest();

		ts.setFields("tid,status");
//		ts.setStartCreated(WebUtil.toDateForString("2012-03-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
//		ts.setEndCreated(WebUtil.toDateForString("2012-03-31 23:59:59", "yyyy-MM-dd HH:mm:ss"));
//		ts.setBuyerNick("Jackwalk旗舰");
//		ts.setPageSize(40L);
//		ts.setUseHasNext(true);
		try {
			TradesSoldGetResponse tr = client.execute(ts, sessionKey);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("time:"+(System.currentTimeMillis()-l));
	}
	
	public static void main(String[] args)
	{
		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com",
				"12227029", "56494f3845f9d7790c15678a27e51467");
		TradesSoldGetRequest req=new TradesSoldGetRequest();
		req.setFields("tid,status");
		try {
			TradesSoldGetResponse tr = client.execute(req, "61022119d6b320f17111a36211b9af51d1949ce028d52a9608825099");
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
