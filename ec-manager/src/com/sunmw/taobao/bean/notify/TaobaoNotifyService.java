package com.sunmw.taobao.bean.notify;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunmw.taobao.entity.TbStore;
import com.sunmw.web.util.WebUtil;
import com.taobao.api.internal.stream.Configuration;
import com.taobao.api.internal.stream.TopCometStream;
import com.taobao.api.internal.stream.TopCometStreamFactory;

public class TaobaoNotifyService extends HibernateDaoSupport {
	static Logger logger = Logger.getLogger(TaobaoNotifyService.class);
	
	public void startNotifyService()
	{
		List<TbStore> storeList = this.getHibernateTemplate().find("from TbStore where UseNotify = ?","Y");
		if(WebUtil.isNullForList(storeList))
			return;
		for(TbStore ts:storeList)
		{
			Configuration conf = new Configuration(ts.getAppKey(), ts.getAppSercet(), "-1");
			TopCometStream stream = new TopCometStreamFactory(conf).getInstance();
			ConnectionLifeCycleListenerImpl clcl = new ConnectionLifeCycleListenerImpl();
			clcl.setStoreId(ts.getId());
			TopCometMessageListenerImpl tcml = new TopCometMessageListenerImpl();
			tcml.setStoreId(ts.getId());
			stream.setConnectionListener(clcl);
			stream.setMessageListener(tcml);
			stream.start();
			logger.info("主动通知业务开始监听,店铺："+ts.getStoreNick()+"("+ts.getId()+")");
		}
	}

}
